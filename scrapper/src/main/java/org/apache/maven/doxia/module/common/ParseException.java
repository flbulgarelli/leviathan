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

/*
 * Copyright 2004-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
//CHECKSTYLE:ALL:OFF
/**
 * @author <a href="mailto:jason@maven.org">Jason van Zyl</a>
 * @version $Id:ParseException.java 348605 2005-11-24 12:02:44 +1100 (Thu, 24 Nov 2005) brett $
 */
public class ParseException
    extends Exception
{
    protected String fileName;

    protected int lineNumber;

    public ParseException( String message )
    {
        this( null, message, null, -1 );
    }

    public ParseException( String message, Exception e )
    {
        this( e, message, null, -1 );
    }

    public ParseException( Exception e )
    {
        this( e, null, null, -1 );
    }

    public ParseException( Exception e, String fileName, int lineNumber )
    {
        this( e, null, fileName, lineNumber );
    }

    public ParseException( Exception e, String message, String fileName, int lineNumber )
    {
        super( ( message == null ) ? ( ( e == null ) ? null : e.getMessage() ) : message, e );

        this.fileName = fileName;
        this.lineNumber = lineNumber;
    }

    public String getFileName()
    {
        return fileName;
    }

    public int getLineNumber()
    {
        return lineNumber;
    }
}
