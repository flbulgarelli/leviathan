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
/*
 * Originaly from org.apache.doxia.module.apt.AptReaderSource. It was modified
 * to get unget support 
 */
package org.apache.maven.doxia.module.common;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
//CHECKSTYLE:ALL:OFF
/**
 * {@link org.apache.maven.doxia.module.common.ByLineSource} default implementation
 */
public class ByLineReaderSource implements ByLineSource
{
    /**
     * reader
     */
    private LineNumberReader reader;
    /**
     * current line number
     */
    private int lineNumber;

    /**
     * holds the last line returned by getNextLine()
     */
    private String lastLine;

    /**
     * <code>true</code> if ungetLine() was called and no getNextLine() was
     * called
     */
    private boolean ungetted = false;

    /**
     * Creates the ByLineReaderSource.
     *
     * @param in real source :)
     */
    public ByLineReaderSource( final Reader in )
    {
        reader = new LineNumberReader( in );

        lineNumber = -1;
    }

    /**
     * @see ByLineSource#getNextLine()
     */
    public final String getNextLine()
    {
        if ( reader == null )
        {
            return null;
        }

        if ( ungetted )
        {
            ungetted = false;
            return lastLine;
        }

        String line;

        try
        {
            line = reader.readLine();
            if ( line == null )
            {
                reader.close();
                reader = null;
            }
            else
            {
                lineNumber = reader.getLineNumber();
            }
        }
        catch ( IOException e )
        {
            throw new RuntimeException( e );
        }

        lastLine = line;

        return line;
    }

    /**
     * @see ByLineSource#getName()
     */
    public final String getName()
    {
        return "";
    }

    /**
     * @see ByLineSource#getLineNumber()
     */
    public final int getLineNumber()
    {
        return lineNumber;
    }

    /**
     * @see ByLineSource#close()
     */
    public final void close()
    {
        if ( reader != null )
        {
            try
            {
                reader.close();
            }
            catch ( IOException ignored )
            {
                // ignore
            }
        }
        reader = null;
    }

    /**
     * @see ByLineSource#ungetLine()
     */
    public final void ungetLine() throws IllegalStateException
    {
        if ( ungetted )
        {
            throw new IllegalStateException( "we support only one level of ungetLine()" );
        }
        ungetted = true;
    }

    /**
     * @see ByLineSource#unget(String)
     */
    public final void unget( final String s ) throws IllegalStateException
    {
        if ( s == null )
        {
            throw new IllegalArgumentException( "argument can't be null" );
        }
        if ( s.length() == 0 )
        {
            // dont do anything
        }
        else
        {
            ungetLine();
            lastLine = s;
        }
    }
}