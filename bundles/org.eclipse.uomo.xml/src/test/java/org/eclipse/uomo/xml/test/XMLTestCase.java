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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.eclipse.uomo.core.UOMoException;
import org.eclipse.uomo.xml.impl.XMLReader;
import org.eclipse.uomo.xml.impl.XMLWriter;
import org.eclipse.uomo.xml.impl.XMLWriterParser;
import org.junit.Before;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

public abstract class XMLTestCase implements
	XMLTestConfiguration {

    @Before
    public void setUp() throws Exception {
//	super.setUp();
	checkOsxXmlHandlers();
    }

    public static void checkOsxXmlHandlers() {
	// this is a workaround for a weird OSX related issue - something to do
	// with the way Apple register Crimson.
	// feel free to disable it if it's not a problem for you
	if (isOsx()) {
	    System
		    .setProperty("javax.xml.parsers.DocumentBuilderFactory",
			    "com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl");
	    System
		    .setProperty("javax.xml.parsers.SAXParserFactory",
			    "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl");
	    System
		    .setProperty("org.w3c.dom.DOMImplementationSourceList",
			    "com.sun.org.apache.xerces.internal.dom.DOMImplementationSourceImpl");
	}
    }

    private void prettyXML(String source, String dest) throws SAXException,
	    IOException {
	XMLWriter dst = new XMLWriter(new FileOutputStream(new File(dest)),
		"UTF-8");
	dst.setPretty(true);
	dst.setSortAttributes(true);
	XMLReader src = new XMLReader(new XMLWriterParser(dst));
	src.parse(new FileInputStream(new File(source)));
    }

    private final static int CR = 13;
    private final static int LF = 10;

    protected void compareDOMs(String filename1, String filename2,
	    String name1, String name2) throws FileNotFoundException,
	    ParserConfigurationException, SAXException, IOException,
	    InterruptedException, UOMoException {
	Document one = loadDom(filename1);
	Document two = loadDom(filename2);

	String diff = compareElements(one.getDocumentElement(), two
		.getDocumentElement(), "/");
	if (diff != null) {
	    showDiff(filename1, filename2, name1, name2, true, diff);
	}
    }

    private String compareElements(Element e1, Element e2, String p) {
	if (!e1.getNamespaceURI().equals(e2.getNamespaceURI()))
	    return "element namespaces differ at " + p;
	if (!e1.getLocalName().equals(e2.getLocalName()))
	    return "element names differ at " + p;

	String msg = compareAttributes(e1, e2, p);
	if (msg != null)
	    return msg;

	p = p + "/" + e1.getNodeName();

	int i = 0;
	Node c1 = getNextRelevantNode(e1.getFirstChild());
	Node c2 = getNextRelevantNode(e2.getFirstChild());
	while (c1 != null && c2 != null) {
	    if (c1.getNodeType() != c2.getNodeType())
		return "Different node types ("
			+ Integer.toString(c1.getNodeType()) + "/"
			+ c2.getNodeType() + ") @ " + p;
	    msg = null;
	    if (c1.getNodeType() == Node.TEXT_NODE) {
		msg = compareTexts((Text) c1, (Text) c2, p + "["
			+ Integer.toString(i) + "]");
	    } else if (c1.getNodeType() == Node.ELEMENT_NODE) {
		msg = compareElements((Element) c1, (Element) c2, p + "["
			+ Integer.toString(i) + "]");
	    } else
		msg = "unknown node type " + Integer.toString(c1.getNodeType());
	    if (msg != null)
		return msg;

	    c1 = getNextRelevantNode(c1.getNextSibling());
	    c2 = getNextRelevantNode(c2.getNextSibling());
	    i++;
	}
	if (c1 != null && c2 == null)
	    return "node present in one and not in two @ " + p;
	if (c2 != null && c1 == null)
	    return "node present in two and not in one @ " + p;
	return null;
    }

    private String compareAttributes(Element e1, Element e2, String p) {
	NamedNodeMap n1 = e1.getAttributes();
	NamedNodeMap n2 = e2.getAttributes();

	for (int i = 0; i < n1.getLength(); i++) {
	    Node a1 = n1.item(0);
	    Node a2 = n2
		    .getNamedItemNS(a1.getNamespaceURI(), a1.getLocalName());
	    if (a2 == null)
		return "Unable to find attribute " + a1.getNodeName() + " @ "
			+ p;
	    if (a1.getNodeValue() != null || a2.getNodeValue() != null) {
		if (a1.getNodeValue() == null || a2.getNodeValue() == null
			|| !a1.getNodeValue().equals(a2.getNodeValue()))
		    return "Attribute text differs @ " + p + "/@"
			    + a1.getNodeName() + ": '" + a1.getNodeValue()
			    + "' / '" + a2.getNodeValue() + "'";
	    }
	}

	for (int i = 0; i < n2.getLength(); i++) {
	    Node a2 = n2.item(0);
	    Node a1 = n1
		    .getNamedItemNS(a2.getNamespaceURI(), a2.getLocalName());
	    if (a1 == null)
		return "Unable to find attribute " + a2.getNodeName() + " @ "
			+ p;
	    if (a1.getNodeValue() != null || a2.getNodeValue() != null) {
		if (a1.getNodeValue() == null || a2.getNodeValue() == null
			|| !a1.getNodeValue().equals(a2.getNodeValue()))
		    return "Attribute text differs @ " + p + "/@"
			    + a1.getNodeName() + ": '" + a1.getNodeValue()
			    + "' / '" + a2.getNodeValue() + "'";
	    }
	}

	return null;
    }

    private String compareTexts(Text c1, Text c2, String p) {
	String s1 = normaliseWhitespace(c1.getNodeValue());
	String s2 = normaliseWhitespace(c2.getNodeValue());
	if (s1 == null && s2 == null)
	    return null;
	else if (s1 == null || s2 == null || !s1.equals(s2))
	    return "Text differs @ " + p + ": '" + s1 + "' / '" + s2 + "'";
	else
	    return null;
    }

    private String normaliseWhitespace(String s) {
	StringBuilder bldr = new StringBuilder();
	boolean w = false;
	for (Character ch : s.toCharArray()) {
	    if (!Character.isWhitespace(ch)) {
		bldr.append(ch);
		w = false;
	    } else if (!w) {
		bldr.append(' ');
		w = true;
	    } else {
		// nothing
	    }

	}
	return bldr.toString();
    }

    private Node getNextRelevantNode(Node node) {
	while (node != null
		&& node.getNodeType() != Node.ELEMENT_NODE
		&& !(node.getNodeType() == Node.TEXT_NODE && !StringUtils
			.isWhitespace(node.getNodeValue())))
	    node = node.getNextSibling();
	return node;
    }

    private Document loadDom(String filename)
	    throws ParserConfigurationException, FileNotFoundException,
	    SAXException, IOException {

	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	factory.setNamespaceAware(true);
	DocumentBuilder builder = factory.newDocumentBuilder();
	File file = new File(filename);
	return builder.parse(new FileInputStream(file));

    }

    protected void compareXMLs(String filename1, String filename2,
	    String name1, String name2, boolean makePretty) throws IOException,
	    UOMoException, InterruptedException, SAXException {

	FileInputStream one = new FileInputStream(new File(filename1));
	FileInputStream two = new FileInputStream(new File(filename2));
	int size1 = one.available();
	int size2 = two.available();

	boolean same = true;
	int count = 0;
	int ia1 = -1;
	int ia2 = -1;

	while (same && one.available() > 0 && two.available() > 0) {
	    int i1 = ia1 != -1 ? ia1 : one.read();
	    ia1 = -1;
	    int i2 = ia2 != -1 ? ia2 : two.read();
	    ia2 = -1;
	    if (i1 == CR) {
		i1 = LF;
		ia1 = one.read();
		if (ia1 == LF)
		    ia1 = -1;
	    }
	    if (i2 == CR) {
		i2 = LF;
		ia2 = two.read();
		if (ia2 == LF)
		    ia2 = -1;
	    }
	    same = i1 == i2;
	    count++;
	}

	int left1 = one.available();
	int left2 = two.available();

	one.close();
	two.close();

	if (!same || left1 > 0 || left2 > 0) {
	    if (!same)
		showDiff(filename1, filename2, name1, name2, makePretty,
			"Content is not as expected @ "
				+ Integer.toString(count) + " of "
				+ Integer.toString(size1) + "/"
				+ Integer.toString(size2));
	    else
		showDiff(filename1, filename2, name1, name2, makePretty,
			"Content is same until end of file @ "
				+ Integer.toString(size1) + "/"
				+ Integer.toString(size2));
	}

    }

    private void showDiff(String filename1, String filename2, String name1,
	    String name2, boolean makePretty, String msg) throws SAXException,
	    IOException, InterruptedException, UOMoException {
	if (makePretty) {
	    prettyXML(filename1, TEMP_PATH + name1 + ".xml");
	    prettyXML(filename2, TEMP_PATH + name2 + ".xml");

	} else {
	    copyFile(filename1, TEMP_PATH + name1 + ".xml");
	    copyFile(filename2, TEMP_PATH + name2 + ".xml");
	}
	if (!SKIP_THIRD_PARTY) {
	    if (isOsx())
		Runtime.getRuntime().exec(
			COMPARE_PATH + " " + TEMP_PATH + name1 + ".xml" + " "
				+ TEMP_PATH + name2 + ".xml");
	    else
		Runtime.getRuntime().exec(
			COMPARE_PATH + " \"" + TEMP_PATH + name1 + ".xml"
				+ "\" \"" + TEMP_PATH + name2 + ".xml" + "\"");
	}
	Thread.sleep(1000);
	throw new UOMoException(msg);

    }

    private void copyFile(String source, String dest) throws SAXException,
	    IOException {
	InputStream in = new FileInputStream(new File(source));
	OutputStream out = new FileOutputStream(new File(dest));

	// Transfer bytes from in to out
	byte[] buf = new byte[1024];
	int len;
	while ((len = in.read(buf)) > 0) {
	    out.write(buf, 0, len);
	}
	in.close();
	out.close();
    }

    protected void compareFiles(String filename1, String filename2)
	    throws IOException, UOMoException, InterruptedException {
	FileInputStream one = new FileInputStream(new File(filename1));
	FileInputStream two = new FileInputStream(new File(filename2));
	boolean same = one.available() == two.available();
	while (same && one.available() > 0) {
	    same = one.read() == two.read();
	}

	if (!same) {
	    if (!SKIP_THIRD_PARTY)
		Runtime.getRuntime().exec(
			COMPARE_PATH + " \"" + filename1 + "\" \"" + filename2
				+ "\"");
	    Thread.sleep(1000);
	    throw new UOMoException("Content is not as expected @ "
		    + Integer.toString(one.available()));
	}
    }

    protected void compareStrings(String content1, String content2)
	    throws IOException, UOMoException, InterruptedException {
	String filename1 = TEMP_FILENAME + "1";
	String filename2 = TEMP_FILENAME + "2";
	stringToFile(content1, filename1);
	stringToFile(content2, filename2);
	compareFiles(filename1, filename2);
    }

    private void stringToFile(String content, String filename)
	    throws IOException {
	FileWriter file = new FileWriter(new File(filename));
	file.write(content);
	file.flush();
	file.close();
    }

    protected static boolean isOsx() {
	String lcOSName = System.getProperty("os.name").toLowerCase();
	boolean MAC_OS_X = lcOSName.startsWith("mac os x");
	return MAC_OS_X;
    }
}
