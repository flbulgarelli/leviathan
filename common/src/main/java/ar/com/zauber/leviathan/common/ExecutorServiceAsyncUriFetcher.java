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
package ar.com.zauber.leviathan.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.leviathan.api.AsyncUriFetcher;
import ar.com.zauber.leviathan.api.URIFetcher;
import ar.com.zauber.leviathan.api.URIFetcherResponse;
import ar.com.zauber.leviathan.api.URIFetcherResponse.URIAndCtx;

/**
 * {@link AsyncUriFetcher} que utiliza un {@link ExecutorService} para no 
 * bloquearse. Segun el ExecutorService que se use (ver su queue) es lo que suceda
 * cuando todos los workers estan ocupados (se encolan; se descartan; etc) 
 * 
 * @author Juan F. Codagnone
 * @since Jan 21, 2010
 */
public class ExecutorServiceAsyncUriFetcher extends AbstractAsyncUriFetcher {
    private final ExecutorService executorService;
    private URIFetcher fetcher;

    /** Creates the ExecutorServiceAsyncUriFetcher. */
    public ExecutorServiceAsyncUriFetcher(final ExecutorService executorService,
            final URIFetcher fetcher) {
        Validate.notNull(executorService);
        Validate.notNull(fetcher);
        
        this.executorService = executorService;
        this.fetcher = fetcher;
    }
    
    /** @see AsyncUriFetcher#fetch(URIFetcherResponse.URIAndCtx, Closure) */
    public final void fetch(final URIAndCtx uriAndCtx, 
            final Closure<URIFetcherResponse> closure) {
        Validate.notNull(uriAndCtx);
        Validate.notNull(closure);
        
        executorService.submit(new Runnable() {
            public void run() {
                closure.execute(fetcher.fetch(uriAndCtx));
            }
        });
    }
    
    /** @see AsyncUriFetcher#shutdown() */
    public final void shutdown() {
        executorService.shutdown();
    }

    /** @see AsyncUriFetcher#awaitTermination(long, TimeUnit) */
    public final boolean awaitTermination(final long timeout, final TimeUnit unit)
            throws InterruptedException {
        return false;
    }

    /** @see AsyncUriFetcher#awaitIdleness(long, TimeUnit) */
    public final void awaitIdleness() throws InterruptedException {
        throw new NotImplementedException("not coming very soon");
    }
    
    /** @see AsyncUriFetcher#awaitIdleness(long, TimeUnit) */
    public final boolean awaitIdleness(final long timeout, final TimeUnit unit)
            throws InterruptedException {
        throw new NotImplementedException("not coming very soon");
    }
}


