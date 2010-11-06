/*******************************************************************************
 * Copyright (c) 2000, 2005 Jiva Medical and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jiva Medical - initial API and implementation
 *******************************************************************************/

package org.eclipse.uomo.xml.test;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.uomo.core.UOMoException;
import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

public class XMLTestingTests extends XMLTestCase {

    public static final String SAMPLE_1 = "<root xmlns='test'><child name='c'/></root>";
    public static final String SAMPLE_1_WS = "<root xmlns='test'> <child name='c'/> </root>";
    public static final String SAMPLE_2 = "<root xmlns='test'><child name='d'/></root>";

    @Test
    public final void testOk() throws UOMoException, XmlPullParserException,
	    IOException, InterruptedException, ParserConfigurationException,
	    SAXException {
	String f1 = TEMP_FILENAME + "1.test.xml";
	StringToFile(SAMPLE_1, f1);
	String f2 = TEMP_FILENAME + "2.test.xml";
	StringToFile(SAMPLE_1, f2);
	compareDOMs(f1, f2, "one", "two");
    }

    private void StringToFile(String source, String name) throws IOException {
	File file = new File(name);
	FileOutputStream out = new FileOutputStream(file);
	OutputStreamWriter writer = new OutputStreamWriter(out);
	writer.write(source);
	writer.flush();

    }

    @Test
    public final void testOkWS() throws UOMoException, XmlPullParserException,
	    IOException, InterruptedException, ParserConfigurationException,
	    SAXException {
	String f1 = TEMP_FILENAME + "1.test.xml";
	StringToFile(SAMPLE_1, f1);
	String f2 = TEMP_FILENAME + "2.test.xml";
	StringToFile(SAMPLE_1_WS, f2);
	compareDOMs(f1, f2, "one", "two");
    }

    @Test
    @Ignore
    public final void testNotOkWS() throws UOMoException,
	    XmlPullParserException, IOException, InterruptedException,
	    ParserConfigurationException, SAXException {
	String f1 = TEMP_FILENAME + "1.test.xml";
	StringToFile(SAMPLE_1, f1);
	String f2 = TEMP_FILENAME + "2.test.xml";
	StringToFile(SAMPLE_2, f2);
	try {
	    compareDOMs(f1, f2, "one", "two");
	    fail();
	} catch (UOMoException e) {
	    // empty catch
	}
    }

}
