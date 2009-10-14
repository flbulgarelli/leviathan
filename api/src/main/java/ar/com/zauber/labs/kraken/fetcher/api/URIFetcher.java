/*
 * Copyright (c) 2009 Zauber S.A.  -- All rights reserved
 */
package ar.com.zauber.labs.kraken.fetcher.api;

import java.net.URI;

/**
 * URI fetcher
 * 
 * @author Juan F. Codagnone
 * @since Oct 12, 2009
 */
public interface URIFetcher {

    /** fetch uri */
    URIFetcherResponse fetch(URI uri);
}