/**
 * Copyright (c) 2007-2009 Zauber S.A. <http://www.zauber.com.ar/>
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
package org.apache.maven.doxia.module.common;

//CHECKSTYLE:ALL:OFF
/**
 * The token are the new lines :)
 *
 * @author Juan F. Codagnone
 * @since Nov 4, 2005
 */
public interface ByLineSource
{
    /**
     * @return the next line. <code>null</code> if we reached the end.
     * @throws ParseException on I/O error
     */
    String getNextLine() throws ParseException;

    /**
     * @return the name of the input. could be the filename for example
     */
    String getName();

    /**
     * @return the current line number
     */
    int getLineNumber();

    /**
     * @throws IllegalStateException if the ungetLine/unget is called more than
     *                               one time without calling getNextLine()
     */
    void ungetLine() throws IllegalStateException;


    /**
     * @param s some text to push back to the parser
     * @throws IllegalStateException if the ungetLine/unget is called more than
     *                               one time without calling getNextLine()
     */
    void unget( String s ) throws IllegalStateException;


    /**
     * close the source ...
     */
    void close();
}
