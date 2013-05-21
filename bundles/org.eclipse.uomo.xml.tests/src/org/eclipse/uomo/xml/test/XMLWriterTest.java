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

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.eclipse.uomo.core.UOMoException;
import org.eclipse.uomo.xml.impl.XMLWriter;
import org.junit.Test;
import org.xmlpull.v1.XmlPullParserException;

public class XMLWriterTest extends XMLTestCase {

    @Test
    public final void testPlain() throws UOMoException, XmlPullParserException,
	    IOException, InterruptedException {
	ByteArrayOutputStream stream = new ByteArrayOutputStream();
	XMLWriter xml = new XMLWriter(stream, "UTF-8");
	xml.setPretty(false);
	xml.start();
	xml.namespace("urn:testuri.org", "ns");
	xml.open("urn:testuri.org", "n1");
	xml.open("urn:testuri.org", "n2");
	xml.text("test");
	xml.close();
	xml.open("urn:testuri.org", "n2");
	xml.text("test\n");
	xml.close();
	xml.close();
	xml.close();
	String output = stream.toString();
	String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
		+ "<ns:n1 xmlns:ns=\"urn:testuri.org\">"
		+ "<ns:n2>test</ns:n2>" + "<ns:n2>test\n</ns:n2>" + "</ns:n1>";
	assertTrue(output.equals(expected));
    }

    @Test
    public final void testPretty() throws UOMoException, XmlPullParserException,
	    IOException, InterruptedException {
	ByteArrayOutputStream stream = new ByteArrayOutputStream();
	XMLWriter xml = new XMLWriter(stream, "UTF-8");
	xml.setPretty(true);
	xml.start();
	xml.namespace("urn:testuri.org", "ns");
	xml.open("urn:testuri.org", "n1");
	xml.open("urn:testuri.org", "n2");
	xml.text("test");
	xml.close();
	xml.open("urn:testuri.org", "n2");
	xml.text("test\n");
	xml.close();
	xml.close();
	xml.close();
	String output = stream.toString();
	String expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n"
		+ "<ns:n1 xmlns:ns=\"urn:testuri.org\">\n"
		+ "  <ns:n2>test</ns:n2>\n" + "  <ns:n2>test\n</ns:n2>\n"
		+ "</ns:n1>";
	System.out.println("\r\noutput");
	System.out.print("--" + output + "--");
	System.out.println("\r\nexpected");
	System.out.print("--" + expected + "--");
	assertTrue(output.equals(expected));
    }

}
