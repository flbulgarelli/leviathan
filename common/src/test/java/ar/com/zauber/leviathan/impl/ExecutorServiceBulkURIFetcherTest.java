/**
 * Copyright (c) 2009-2011 Zauber S.A. <http://www.zaubersoftware.com/>
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
package ar.com.zauber.leviathan.impl;

import java.net.URI;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

import org.junit.Assert;
import org.junit.Test;

import ar.com.zauber.leviathan.api.BulkURIFetcherResponse;
import ar.com.zauber.leviathan.api.URIFetcherResponse;
import ar.com.zauber.leviathan.common.ExecutorServiceBulkURIFetcher;
import ar.com.zauber.leviathan.common.InmutableBulkURIFetcherResponse;
import ar.com.zauber.leviathan.common.InmutableURIFetcherHttpResponse;
import ar.com.zauber.leviathan.common.InmutableURIFetcherResponse;
import ar.com.zauber.leviathan.common.mock.FixedURIFetcher;

/**
 * Tests {@link ExecutorServiceBulkURIFetcher}
 * 
 * @author Juan F. Codagnone
 * @since Oct 12, 2009
 */
public class ExecutorServiceBulkURIFetcherTest {

    /** */
    @SuppressWarnings("unchecked")
    @Test
    public final void foo() throws Exception {
        final URI bar = new URI("http://bar");
        final URI lalara = new URI("http://lalara");
        final URI foo = new URI("http://foo");

        final Map<URI, String> map = new HashMap<URI, String>();
        final String resource = "ar/com/zauber/leviathan/impl/mock/noexiste.txt";
        map.put(bar, resource);
        map.put(lalara, resource);

        final BulkURIFetcherResponse response = new ExecutorServiceBulkURIFetcher(
                Executors.newSingleThreadExecutor(), new FixedURIFetcher(map))
                .get(Arrays.asList(bar, lalara, foo));
        Assert.assertNotNull(response);

        Assert.assertEquals(new InmutableBulkURIFetcherResponse(Arrays
                .asList(new URIFetcherResponse[] {
                        new InmutableURIFetcherResponse(bar,
                                new InmutableURIFetcherHttpResponse("foo", 200,
                                        Collections.EMPTY_MAP)),
                        new InmutableURIFetcherResponse(lalara,
                                new InmutableURIFetcherHttpResponse("foo", 200,
                                        Collections.EMPTY_MAP)),
                        new InmutableURIFetcherResponse(foo,
                                new UnknownHostException("foo")), })), response);
    }

}