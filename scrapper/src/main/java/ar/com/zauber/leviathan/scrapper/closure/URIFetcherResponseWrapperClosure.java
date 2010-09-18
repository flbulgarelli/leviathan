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
package ar.com.zauber.leviathan.scrapper.closure;

import java.io.Reader;
import java.util.Formatter;

import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Closure;
import ar.com.zauber.leviathan.api.URIFetcherResponse;
import ar.com.zauber.leviathan.api.URIFetcherResponse.URIAndCtx;
import ar.com.zauber.leviathan.scrapper.utils.ContextualResponse;

/**
 * 
 * 
 * @author Marcelo Turrin
 * @since Sep 10, 2010
 */
public class URIFetcherResponseWrapperClosure implements
        Closure<URIFetcherResponse> {
    private final Closure<ContextualResponse<URIAndCtx, Reader>> target;

    /** Crea el closure con target el closure pasado */
    public URIFetcherResponseWrapperClosure(
            final Closure<ContextualResponse<URIAndCtx, Reader>> target) {
        Validate.notNull(target);
        this.target = target;
    }

    /** @see Closure#execute(Object) */
    @Override
    public final void execute(final URIFetcherResponse response) {
        try {
            if (response.isSucceeded()) {
                target.execute(new ContextualResponse<URIAndCtx, Reader>(response
                        .getURIAndCtx(), response.getHttpResponse().getContent()));
            } else {
                throw response.getError();
            }
        } catch (Throwable e) {
            Formatter formatter = new Formatter();
            formatter.format(
                "Error procesando %s", response.getURIAndCtx().getURI());
            throw new UnhandledException(formatter.toString(), e);
        }
    }

}
