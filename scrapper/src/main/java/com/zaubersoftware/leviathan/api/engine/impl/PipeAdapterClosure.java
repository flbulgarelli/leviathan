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
package com.zaubersoftware.leviathan.api.engine.impl;

import org.apache.commons.lang.Validate;

import ar.com.zauber.commons.dao.Closure;

import com.zaubersoftware.leviathan.api.engine.Pipe;

/**
 * TODO: Description of the class, Comments in english by default
 *
 *
 * @author Guido Marucci Blas
 * @since Aug 12, 2011
 */
public final class PipeAdapterClosure<I, O> implements Closure<I> {

    private final Pipe<I, O> pipe;

    /**
     * Creates the PipeAdapterClosure.
     *
     * @param pipe
     */
    public PipeAdapterClosure(final Pipe<I, O> pipe) {
        Validate.notNull(pipe, "The adaptee pipe cannot be null");
        this.pipe = pipe;
    }

    @Override
    public void execute(final I input) {
        pipe.execute(input);
    }

}
