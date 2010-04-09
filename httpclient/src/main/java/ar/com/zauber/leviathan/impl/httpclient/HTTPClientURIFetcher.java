/**
 * Copyright (c) 2009-2010 Zauber S.A. <http://www.zaubersoftware.com/>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ar.com.zauber.leviathan.impl.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import ar.com.zauber.leviathan.api.URIFetcher;
import ar.com.zauber.leviathan.api.URIFetcherResponse;
import ar.com.zauber.leviathan.api.URIFetcherResponse.URIAndCtx;
import ar.com.zauber.leviathan.common.AbstractURIFetcher;
import ar.com.zauber.leviathan.common.CharsetStrategy;
import ar.com.zauber.leviathan.common.InmutableURIFetcherHttpResponse;
import ar.com.zauber.leviathan.common.InmutableURIFetcherResponse;
import ar.com.zauber.leviathan.common.ResponseMetadata;
import ar.com.zauber.leviathan.impl.httpclient.charset.DefaultHttpCharsetStrategy;

/**
 * {@link URIFetcher} that uses Apache's HttpClient components
 *
 * @author Juan F. Codagnone
 * @since Oct 12, 2009
 */
public class HTTPClientURIFetcher extends AbstractURIFetcher {
    private final HttpClient httpClient;
    private final CharsetStrategy charsetStrategy;

    /** constructor utiliza la default charset strategy */
    public HTTPClientURIFetcher(final HttpClient httpClient) {
        this(httpClient, new DefaultHttpCharsetStrategy());
    }
     
    /** Creates the HTTPClientURIFetcher.*/
    public HTTPClientURIFetcher(final HttpClient httpClient,
            final CharsetStrategy defaultStrategy) {
        Validate.notNull(httpClient);
        Validate.notNull(defaultStrategy);

        this.charsetStrategy = defaultStrategy;
        this.httpClient = httpClient;
    }

    /** @see URIFetcher#fetch(URIFetcherResponse.URIAndCtx) */
    public final URIFetcherResponse fetch(final URIAndCtx uriAndCtx) {
        final URI uri = uriAndCtx.getURI();
        Validate.notNull(uri, "uri is null");
        HttpResponse response = null;
        try {
            ResponseMetadata meta = null;
            InputStream content = null;
            response  = httpClient.execute(new HttpGet(uri));
            final HttpEntity entity = response.getEntity();
            if(entity != null) {
                content = response.getEntity().getContent();
                meta = getMetaResponse(uri, response, entity);
            }
            final Charset charset = charsetStrategy.getCharset(meta, content);

            final int status = response.getStatusLine().getStatusCode();
            return new InmutableURIFetcherResponse(uriAndCtx,
                new InmutableURIFetcherHttpResponse(
                                IOUtils.toString(content, charset.displayName()),
                                status));
        } catch (final Throwable e) {
            return new InmutableURIFetcherResponse(uriAndCtx, e);
        } finally {
            if(response != null) {
                try {
                    response.getEntity().consumeContent();
                } catch (final IOException e) {
                    return new InmutableURIFetcherResponse(uriAndCtx, e);
                }
            }
        }
    }



    /** obtiene el encoding */
    private ResponseMetadata getMetaResponse(final URI uri,
            final HttpResponse response, final HttpEntity entity) {
        String contentType = null;
        String contentEncoding = null;
        if(entity.getContentType() != null) {
            contentType = entity.getContentType().getValue();
        }
        if(entity.getContentEncoding() != null) {
            contentEncoding = entity.getContentEncoding().getValue();
        } else {
            contentEncoding = getCharsetFromContentType(contentType);
        }
        final int status = response.getStatusLine().getStatusCode();
        return new InmutableResponseMetadata(uri, contentType,
                contentEncoding, status);
    }

    /**
     * @param contentType
     * @return el charset presente en el contentType, o null.
     */
    private String getCharsetFromContentType(final String contentType) {
        if (!StringUtils.contains(contentType, "charset")) {
            return null;
        }
        
        String charset = contentType.substring(contentType
                .indexOf("charset") + 7);
        if (StringUtils.contains(charset, ';')) {
            charset = charset.substring(0,
                    charset.indexOf(';'));
        }
        return StringUtils.replace(charset, "=", "").trim();
    }



    /**
     * When HttpClient instance is no longer needed,
     * shut down the connection manager to ensure
     */
    public final void shutdown() {
        httpClient.getConnectionManager().shutdown();
    }
}
