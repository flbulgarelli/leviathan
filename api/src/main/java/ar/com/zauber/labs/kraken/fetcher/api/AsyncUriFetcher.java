/**
 * Copyright (c) 2008-2009 Clarin Global S.A. -- All rights reserved
 */
package ar.com.zauber.labs.kraken.fetcher.api;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.labs.kraken.fetcher.api.URIFetcherResponse.URIAndCtx;

/**
 * Obtenci�n de una p�gina de manera asincr�nica
 * 
 * 
 * @author Marcelo Turrin
 * @since Jan 21, 2010
 */
public interface AsyncUriFetcher {

    /**
     * Busca la p�gina deda por la uri indicada y al terminar llama al closure
     * con la respuesta
     * 
     * @param uriAndCtx
     * @param closure
     */
    void fetch(URIAndCtx uriAndCtx, Closure<URIFetcherResponse> closure);
}
