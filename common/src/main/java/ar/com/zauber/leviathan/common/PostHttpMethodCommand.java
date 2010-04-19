/*
 * Copyright (c) 2010 Zauber S.A. -- All rights reserved
 */
package ar.com.zauber.leviathan.common;

import java.io.InputStream;
import java.net.URI;
import java.util.Map;

import org.apache.commons.lang.Validate;

import ar.com.zauber.leviathan.api.URIFetcher;
import ar.com.zauber.leviathan.api.URIFetcherResponse;
import ar.com.zauber.leviathan.api.URIFetcherResponse.URIAndCtx;

/**
 * Implementaci�n de {@link HttpMethodCommand} para POST.
 * 
 * 
 * @author Francisco J. Gonz�lez Costanz�
 * @since Apr 12, 2010
 */
public class PostHttpMethodCommand implements HttpMethodCommand {

    private final URIFetcher fetcher;
    private final URIFetcherResponse.URIAndCtx uri;
    private final InputStream body;
    private final Map<String, String> formBody;

    /** Creates the PostHttpMethodCommand. */
    public PostHttpMethodCommand(final URIFetcher fetcher, final URIAndCtx uri,
            final InputStream body) {
        Validate.notNull(fetcher);
        Validate.notNull(uri);
        Validate.notNull(body);
        
        this.fetcher = fetcher;
        this.uri = uri;
        this.body = body;
        this.formBody = null;
    }
    
    /** Creates the PostHttpMethodCommand. */
    public PostHttpMethodCommand(final URIFetcher fetcher, final URIAndCtx uri,
            final Map<String, String> formBody) {
        Validate.notNull(fetcher);
        Validate.notNull(uri);
        Validate.notNull(formBody);
        
        this.fetcher = fetcher;
        this.uri = uri;
        this.body = null;
        this.formBody = formBody;
    }    

    /** @see HttpMethodCommand#execute() */
    public final URIFetcherResponse execute() {
        URIFetcherResponse ret;
        
        if (formBody != null) {
            ret = fetcher.post(uri, formBody);
        } else if (body != null) {
            ret = fetcher.post(uri, body);
        } else {
            ret = new InmutableURIFetcherResponse(uri, 
                    new IllegalStateException("Nothing to post!"));
        }
        
        return ret;
    }
    
    /** @see HttpMethodCommand#getURI() */
    public final URI getURI() {
        return uri.getURI();
    }

}