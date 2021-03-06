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
package ar.com.zauber.leviathan.impl.httpclient.charset;

import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

import junit.framework.Assert;

import org.junit.Test;

import ar.com.zauber.leviathan.impl.httpclient.InmutableResponseMetadata;

/**
 * Test de FixedCharsetStrategy
 *
 * @author Mariano Semelman
 * @since Dec 17, 2009
 */
public class FixedCharsetStrategyTest {

    /** invalid charset */
    @Test(expected = UnsupportedCharsetException.class)
    public final void wrongConstructor() throws Exception {
        new FixedCharsetStrategy("mimamamemima");
    }

    /** test del fixed strategy */
    @Test
    public final void fixedTest() throws Exception {
        Assert.assertEquals(
            Charset.forName("utf-8"), 
            new FixedCharsetStrategy("utf-8").getCharset(
                new InmutableResponseMetadata(
                    new URI("htt://foo.bar"), null, "gzip", 200, "bla"), null));
    }

}
