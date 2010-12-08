/*******************************************************************************
 * Crown Copyright (c) 2009, 2009, Copyright (c) 2009, 2009 Kestral Computing.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Kestral Computing - initial API and implementation
 *******************************************************************************/
package org.eclipse.uomo.xml.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.uomo.xml.impl.DOMXMLWriter;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author dennisn
 *
 */
public class DOMXMLWriterTests {

	private static DocumentBuilder docBuilder;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setNamespaceAware(true);
		docBuilder = builderFactory.newDocumentBuilder();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	private Document doc;
	private DOMXMLWriter writer;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		doc = docBuilder.newDocument();
		writer = new DOMXMLWriter(doc);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link org.eclipse.uomo.xml.impl.ohf.utilities.xml.DOMXMLWriter#DOMXMLWriter(org.w3c.dom.Document)}.
	 */
	@Test
	public final void testDOMXMLWriterDocument() throws Exception {
		assertEquals(doc, writer.getDOMDocument());
	}

//	/**
//	 * Test method for {@link org.eclipse.ohf.utilities.xml.DOMXMLWriter#escapeXML(java.lang.String, boolean)}.
//	 * Should move to XMLUtil testing
//	 */
//	@Test
//	public final void testEscapeXML() {
//		assertEquals("", DOMXMLWriter.escapeXML(null, false));
//		assertEquals("abcdefg", DOMXMLWriter.escapeXML("abcdefg", false));
//		assertEquals("&#39;ab&amp;&quot;&lt;&gt;~", DOMXMLWriter.escapeXML("\'ab&\"<>~", false));
//		
//		assertEquals("\n\r", DOMXMLWriter.escapeXML("\n\r", false));
//		assertEquals("&#xA;", DOMXMLWriter.escapeXML("\n\r", true));
//	}

	/**
	 * Test method for {@link org.eclipse.uomo.xml.impl.ohf.utilities.xml.DOMXMLWriter#abbreviationDefined(java.lang.String)}.
	 */
	@Test
	public final void testAbbreviationDefined() throws Exception {
		// test abbreviation defined in root
		writer.namespace("www.test.com", "test");
		writer.open("root");
		assertTrue(writer.abbreviationDefined("test"));
		assertFalse(writer.abbreviationDefined("test_1"));
		
		// test abbreviation defined, but not yet commit to an element
		writer.namespace("www.test2.com", "test2");
		assertTrue(writer.abbreviationDefined("test"));
		assertTrue(writer.abbreviationDefined("test2"));
		assertFalse(writer.abbreviationDefined("test_1"));
		writer.open("child1");
		writer.close();
		
		// test abbreviation defined in another child
		assertTrue(writer.abbreviationDefined("test"));
		assertFalse(writer.abbreviationDefined("test2"));
		assertFalse(writer.abbreviationDefined("test_1"));
	}

	/**
	 * Test method for {@link org.eclipse.uomo.xml.impl.ohf.utilities.xml.DOMXMLWriter#attribute(java.lang.String, java.lang.String, java.lang.String, boolean)}.
	 */
	@Test
	public final void testAttributeStringStringStringBoolean() throws Exception {
		writer.namespace("www.test.com", "test");
		writer.open("root");
		
		writer.attribute("", "test1", null, true);
		writer.attribute("", "test2", "", true);
		writer.attribute("", "test3", null, false);
		writer.attribute("", "test4", "", false);
		writer.attribute("", "test5", "value", true);
		writer.open("child1");
		Element node = writer.getCurrentElement();
		writer.close();
		assertFalse(node.hasAttribute("test1"));
		assertFalse(node.hasAttribute("test2"));
		assertTrue(node.hasAttribute("test3"));
		assertEquals("", node.getAttribute("test3"));
		assertTrue(node.hasAttribute("test4"));
		assertEquals("", node.getAttribute("test4"));
		assertTrue(node.hasAttribute("test5"));
		assertEquals("value", node.getAttribute("test5"));
		
		writer.attribute("www.test.com", "test1", null, true);
		writer.attribute("www.test.com", "test2", "", true);
		writer.attribute("www.test.com", "test3", null, false);
		writer.attribute("www.test.com", "test4", "", false);
		writer.attribute("www.test.com", "test5", "value", true);
		writer.open("child2");
		node = writer.getCurrentElement();
		writer.close();
		assertFalse(node.hasAttribute("test:test1"));
		assertFalse(node.hasAttribute("test:test2"));
		assertTrue(node.hasAttribute("test:test3"));
		assertEquals("", node.getAttribute("test:test3"));
		assertTrue(node.hasAttribute("test:test4"));
		assertEquals("", node.getAttribute("test:test4"));
		assertTrue(node.hasAttribute("test:test5"));
		assertEquals("value", node.getAttribute("test:test5"));

		
		try {
			writer.attribute("www.error.com", "test1", "value", true);
			fail("Should not able to create attribute with not yet defined-namespace");
		} catch (Exception e) {
		}
	}

	/**
	 * Test method for {@link org.eclipse.uomo.xml.impl.ohf.utilities.xml.DOMXMLWriter#attribute(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testAttributeStringStringString() throws Exception {
		writer.namespace("www.test.com", "test");
		writer.open("root");
		
		writer.attribute("", "test1", null);
		writer.attribute("", "test2", "");
		writer.attribute("", "test3", "value");
		writer.open("child1");
		Element node = writer.getCurrentElement();
		writer.close();
		assertTrue(node.hasAttribute("test1"));
		assertEquals("", node.getAttribute("test1"));
		assertTrue(node.hasAttribute("test2"));
		assertEquals("", node.getAttribute("test2"));
		assertTrue(node.hasAttribute("test3"));
		assertEquals("value", node.getAttribute("test3"));
		
		writer.attribute("www.test.com", "test1", null);
		writer.attribute("www.test.com", "test2", "");
		writer.attribute("www.test.com", "test3", "value");
		writer.open("child2");
		node = writer.getCurrentElement();
		writer.close();
		assertTrue(node.hasAttribute("test:test1"));
		assertEquals("", node.getAttribute("test:test1"));
		assertTrue(node.hasAttribute("test:test2"));
		assertEquals("", node.getAttribute("test:test2"));
		assertTrue(node.hasAttribute("test:test3"));
		assertEquals("value", node.getAttribute("test:test3"));

		
		try {
			writer.attribute("www.error.com", "test1", "value", true);
			fail("Should not able to create attribute with not yet defined-namespace");
		} catch (Exception e) {
		}
	}

	/**
	 * Test method for {@link org.eclipse.uomo.xml.impl.ohf.utilities.xml.DOMXMLWriter#attribute(java.lang.String, java.lang.String, boolean)}.
	 */
	@Test
	public final void testAttributeStringStringBoolean() throws Exception {
		writer.open("root");
		
		writer.attribute("test1", null, true);
		writer.attribute("test2", "", true);
		writer.attribute("test3", null, false);
		writer.attribute("test4", "", false);
		writer.attribute("test5", "value", true);
		writer.open("child1");
		Element node = writer.getCurrentElement();
		writer.close();
		assertFalse(node.hasAttribute("test1"));
		assertFalse(node.hasAttribute("test2"));
		assertTrue(node.hasAttribute("test3"));
		assertEquals("", node.getAttribute("test3"));
		assertTrue(node.hasAttribute("test4"));
		assertEquals("", node.getAttribute("test4"));
		assertTrue(node.hasAttribute("test5"));
		assertEquals("value", node.getAttribute("test5"));
	}

	/**
	 * Test method for {@link org.eclipse.uomo.xml.impl.ohf.utilities.xml.DOMXMLWriter#attribute(java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testAttributeStringString() throws Exception {
		writer.open("root");
		
		writer.attribute("test3", null);
		writer.attribute("test4", "");
		writer.attribute("test5", "value", true);
		writer.open("child1");
		Element node = writer.getCurrentElement();
		assertTrue(node.hasAttribute("test3"));
		assertEquals("", node.getAttribute("test3"));
		assertTrue(node.hasAttribute("test4"));
		assertEquals("", node.getAttribute("test4"));
		assertTrue(node.hasAttribute("test5"));
		assertEquals("value", node.getAttribute("test5"));
	}

	/**
	 * Test method for {@link org.eclipse.uomo.xml.impl.ohf.utilities.xml.DOMXMLWriter#attributeNoLines(java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testAttributeNoLines() throws Exception {
		writer.open("root");
		
		writer.attributeNoLines("test3", "value\n and more");
		writer.open("child1");
		Element node = writer.getCurrentElement();
		assertEquals("value and more", node.getAttribute("test3"));
	}

	/**
	 * Test method for {@link org.eclipse.uomo.xml.impl.ohf.utilities.xml.DOMXMLWriter#cData(java.lang.String)}.
	 */
	@Test
	public final void testCData() throws Exception {
		writer.open("root");
		
		writer.cData("test");
		Node node = writer.getCurrentElement().getChildNodes().item(0);
		assertTrue(node instanceof CDATASection);
		assertEquals("test", node.getNodeValue());
	}

	/**
	 * Test method for {@link org.eclipse.uomo.xml.impl.ohf.utilities.xml.DOMXMLWriter#close(java.lang.String)}.
	 */
	@Test
	public final void testCloseString() throws Exception {
		writer.open("root");
		
		writer.open("test1");
		writer.close("test1");
		
		try {
			writer.close("not_root");
			fail("'close()' should not success when name not matched current node name");
		}
		catch (Exception e) {}
	}

	/**
	 * Test method for {@link org.eclipse.uomo.xml.impl.ohf.utilities.xml.DOMXMLWriter#close(java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testCloseStringString() throws Exception {
		writer.namespace("www.test.com");
		writer.open("root");
		
		writer.open("www.test.com", "test1");
		writer.close("www.test.com", "test1");
		
		try {
			writer.close("www.test.com", "not_root");
			fail("'close()' should not success when name not matched current node name");
		}
		catch (Exception e) {}
		
		try {
			writer.close("www.error.com", "root");
			fail("'close()' should not success when namespace not matched current node namespace");
		}
		catch (Exception e) {}
	}

	/**
	 * Test method for {@link org.eclipse.uomo.xml.impl.ohf.utilities.xml.DOMXMLWriter#close()}.
	 */
	@Test
	public final void testClose() throws Exception {
		writer.namespace("www.test.com");
		writer.open("root");
		
		writer.open("child");
		Node child = writer.getCurrentElement();
		assertEquals(doc.getDocumentElement(), child.getParentNode());
		writer.close();
		assertEquals(doc.getDocumentElement(), writer.getCurrentElement());
		
		writer.open("www.test.com", "child");
		Node child2 = writer.getCurrentElement();
		assertEquals(doc.getDocumentElement(), child2.getParentNode());
		assertNotSame(child, child2);
		writer.close();
		assertEquals(doc.getDocumentElement(), writer.getCurrentElement());
	}

	/**
	 * Test method for {@link org.eclipse.uomo.xml.impl.ohf.utilities.xml.DOMXMLWriter#closeToLevel(int)}.
	 */
	@Test
	public final void testCloseToLevel() throws Exception {
		writer.namespace("www.test.com");
		writer.open("root");
		
		writer.open("lvl2");
		writer.open("www.test.com", "lvl3");
		Element lvl3 = writer.getCurrentElement();
		writer.open("lvl4");
		writer.open("lvl5");
		writer.open("lvl6");
		
		writer.closeToLevel(3);
		assertEquals(lvl3, writer.getCurrentElement());
		writer.closeToLevel(1);
		assertEquals(doc.getDocumentElement(), writer.getCurrentElement());
		writer.closeToLevel(0);
		try {
			writer.getCurrentElement();
			fail("Should not able to access current element once close to level 0");
		} catch (Exception e) {}
	}

	/**
	 * Test method for {@link org.eclipse.uomo.xml.impl.ohf.utilities.xml.DOMXMLWriter#comment(java.lang.String, boolean)}.
	 */
	@Test
	public final void testComment() throws Exception {
		writer.namespace("www.test.com");
		writer.open("root");
		
		writer.comment("some comment", true);
		NodeList children = doc.getDocumentElement().getChildNodes();
		boolean found = false;
		for (int i = 0; !found && i < children.getLength(); i++) {
			if (children.item(i) instanceof Comment
					&& "some comment".equals(children.item(i).getNodeValue())) {
				found = true;
			}
		}
		assertTrue("Expected comment not found", found);
	}

	/**
	 * Test method for {@link org.eclipse.uomo.xml.impl.ohf.utilities.xml.DOMXMLWriter#element(java.lang.String, java.lang.String, java.lang.String, boolean)}.
	 */
	@Test
	public final void testElementStringStringStringBoolean() throws Exception {
		writer.namespace("www.test.com", "test");
		writer.open("root");
		Element root = doc.getDocumentElement();
		
		writer.element("www.test.com", "test1", null, true);
		assertEquals(0, root.getChildNodes().getLength());
		writer.element("www.test.com", "test2", "", true);
		assertEquals(0, root.getChildNodes().getLength());
		writer.element("www.test.com", "test3", null, false);
		assertEquals(1, root.getElementsByTagNameNS("www.test.com", "test3").getLength());
		writer.element("www.test.com", "test4", "", false);
		assertEquals(1, root.getElementsByTagNameNS("www.test.com", "test4").getLength());
		writer.element("www.test.com", "test5", "value", true);
		assertEquals(1, root.getElementsByTagNameNS("www.test.com", "test5").getLength());
		assertEquals("value", ((Element)root.getElementsByTagNameNS("www.test.com", "test5").item(0)).getTextContent());
		
		try {
			writer.element("", "test5", "value", true);
			fail("Shouldn't created element with unknown namespace");
		} catch (Exception e) {}
	}

	/**
	 * Test method for {@link org.eclipse.uomo.xml.impl.ohf.utilities.xml.DOMXMLWriter#element(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testElementStringStringStringString() throws Exception {
		writer.namespace("www.test.com", "test");
		writer.open("root");
		Element root = doc.getDocumentElement();
		
		writer.element("www.test.com", "test3", null, null);
		assertEquals(1, root.getElementsByTagNameNS("www.test.com", "test3").getLength());
		writer.element("www.test.com", "test4", "", "");
		assertEquals(1, root.getElementsByTagNameNS("www.test.com", "test4").getLength());
		
		writer.element("www.test.com", "test5", "value", "comment");
		assertEquals(1, root.getElementsByTagNameNS("www.test.com", "test5").getLength());
		Element node = (Element)root.getElementsByTagNameNS("www.test.com", "test5").item(0);
		assertEquals("value", node.getTextContent());
		boolean found = false;
		NodeList nodes = node.getChildNodes();
		for (int i = 0; !found && i < nodes.getLength(); i++) {
			if (nodes.item(i) instanceof Comment 
					&& "comment".equals(nodes.item(i).getNodeValue())) {
				found = true;
			}
		}
		assertTrue("Comment has not created", found);
		
		try {
			writer.element("", "test5", "value", "comment");
			fail("Shouldn't created element with unknown namespace");
		} catch (Exception e) {}
	}

	/**
	 * Test method for {@link org.eclipse.uomo.xml.impl.ohf.utilities.xml.DOMXMLWriter#element(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testElementStringStringString() throws Exception {
		writer.namespace("www.test.com", "test");
		writer.open("root");
		Element root = doc.getDocumentElement();
		
		writer.element("www.test.com", "test3", null);
		assertEquals(1, root.getElementsByTagNameNS("www.test.com", "test3").getLength());
		writer.element("www.test.com", "test4", "");
		assertEquals(1, root.getElementsByTagNameNS("www.test.com", "test4").getLength());
		writer.element("www.test.com", "test5", "value");
		assertEquals(1, root.getElementsByTagNameNS("www.test.com", "test5").getLength());
		assertEquals("value", ((Element)root.getElementsByTagNameNS("www.test.com", "test5").item(0)).getTextContent());
		
		try {
			writer.element("", "test5", "value");
			fail("Shouldn't created element with unknown namespace");
		} catch (Exception e) {}
	}

	/**
	 * Test method for {@link org.eclipse.uomo.xml.impl.ohf.utilities.xml.DOMXMLWriter#element(java.lang.String, java.lang.String, boolean)}.
	 */
	@Test
	public final void testElementStringStringBoolean() throws Exception {
		writer.namespace("www.test.com", "test");
		writer.open("root");
		Element root = doc.getDocumentElement();
		
		writer.element("test1", null, true);
		assertEquals(0, root.getChildNodes().getLength());
		writer.element("test:test2", "", true);
		assertEquals(0, root.getChildNodes().getLength());
		writer.element("test3", null, false);
		assertEquals(1, root.getElementsByTagName("test3").getLength());
		writer.element("test:test4", "", false);
		assertEquals(1, root.getElementsByTagName("test:test4").getLength());
		writer.element("test5", "value", true);
		assertEquals(1, root.getElementsByTagName("test5").getLength());
		assertEquals("value", ((Element)root.getElementsByTagName("test5").item(0)).getTextContent());
		
		try {
			writer.element("", "test5", "value", true);
			fail("Shouldn't created element with unknown namespace");
		} catch (Exception e) {}
	}

	/**
	 * Test method for {@link org.eclipse.uomo.xml.impl.ohf.utilities.xml.DOMXMLWriter#element(java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testElementStringString() throws Exception {
		writer.namespace("www.test.com", "test");
		writer.open("root");
		Element root = doc.getDocumentElement();
		
		writer.element("test1", "value");
		assertEquals(1, root.getElementsByTagName("test1").getLength());
		assertEquals("value", ((Element)root.getElementsByTagName("test1").item(0)).getTextContent());
		
		writer.element("test2", null);
		assertEquals(1, root.getElementsByTagName("test2").getLength());
//		assertNotNull((Element)root.getElementsByTagName("test2").item(0)).getTextContent()));
		// FIXME
		
	}
	

	/**
	 * Test method for {@link org.eclipse.uomo.xml.impl.ohf.utilities.xml.DOMXMLWriter#startCommentBlock()}.
	 */
	@Test
	public final void testCommentBlock() throws Exception {
		writer.open("root");
		writer.startCommentBlock();
		writer.attribute("attr1", "value1");
		writer.attribute("attr2", "value2");
		writer.element("node", "node-content");
		writer.endCommentBlock();
		
		Node node = doc.getDocumentElement().getChildNodes().item(0);
		assertTrue(node instanceof Comment);
		assertEquals("<node attr1=\"value1\" attr2=\"value2\">node-content</node>", node.getNodeValue().trim());
	}

	/**
	 * Test method for {@link org.eclipse.uomo.xml.impl.ohf.utilities.xml.DOMXMLWriter#namespace(java.lang.String)}.
	 */
	@Test
	public final void testNamespaceString() throws Exception {
		String ns = "www.test.com";
		writer.namespace(ns);
		writer.open("root");
		writer.open(ns, "child");
		Element node = writer.getCurrentElement();
		assertEquals(ns, node.getNamespaceURI());
		NamedNodeMap attrs = doc.getDocumentElement().getAttributes();
		boolean found = false;
		for (int i = 0; !found && i < attrs.getLength(); i++) {
			if (attrs.item(i).getNodeName().startsWith("xmlns")
					&& ns.equals(attrs.item(i).getNodeValue()))
				found = true;
		}
		assertTrue(found);
	}

	/**
	 * Test method for {@link org.eclipse.uomo.xml.impl.ohf.utilities.xml.DOMXMLWriter#namespace(java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testNamespaceStringString() throws Exception {
		String ns = "www.test.com";
		String prefix = "test";
		writer.namespace(ns, prefix);
		writer.open("root");
		writer.open(ns, "child");
		Element node = writer.getCurrentElement();
		assertEquals(ns, node.getNamespaceURI());
		assertEquals(prefix, node.getPrefix());
		NamedNodeMap attrs = doc.getDocumentElement().getAttributes();
		boolean found = false;
		for (int i = 0; !found && i < attrs.getLength(); i++) {
			if (attrs.item(i).getNodeName().equals("xmlns:" + prefix)
					&& ns.equals(attrs.item(i).getNodeValue()))
				found = true;
		}
		assertTrue(found);
	}

	/**
	 * Test method for {@link org.eclipse.uomo.xml.impl.ohf.utilities.xml.DOMXMLWriter#namespaceDefined(java.lang.String)}.
	 */
	@Test
	public final void testNamespaceDefined() throws Exception {
		writer.namespace("www.test.com");
		writer.namespace("www.test2.com", "test");
		assertTrue(writer.namespaceDefined("www.test.com"));
		assertTrue(writer.namespaceDefined("www.test2.com"));
		assertFalse(writer.namespaceDefined("www.test3.com"));
		writer.open("root");
		writer.open("www.test.com", "child");
		assertTrue(writer.namespaceDefined("www.test.com"));
		assertTrue(writer.namespaceDefined("www.test2.com"));
		assertFalse(writer.namespaceDefined("www.test3.com"));
	}

	/**
	 * Test method for {@link org.eclipse.uomo.xml.impl.ohf.utilities.xml.DOMXMLWriter#open(java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testOpenStringString() throws Exception {
		writer.namespace("www.test.com", "test");
		writer.open("root");
		writer.open("www.test.com", "child");
		assertEquals("test:child", writer.getCurrentElement().getNodeName());
		assertEquals("www.test.com", writer.getCurrentElement().getNamespaceURI());
	}

	/**
	 * Test method for {@link org.eclipse.uomo.xml.impl.ohf.utilities.xml.DOMXMLWriter#open(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public final void testOpenStringStringString() throws Exception {
		writer.namespace("www.test.com", "test");
		writer.open("root");
		writer.open("www.test.com", "child", "comment");
		assertEquals("child", writer.getCurrentElement().getLocalName());
		assertEquals("www.test.com", writer.getCurrentElement().getNamespaceURI());
		boolean found = false;
		NodeList nodes = writer.getCurrentElement().getChildNodes();
		for (int i = 0; !found && i < nodes.getLength(); i++) {
			if (nodes.item(i) instanceof Comment 
					&& "comment".equals(nodes.item(i).getNodeValue()))
				found = true;
		}
		assertTrue(found);
	}

	/**
	 * Test method for {@link org.eclipse.uomo.xml.impl.ohf.utilities.xml.DOMXMLWriter#open(java.lang.String)}.
	 */
	@Test
	public final void testOpenString() throws Exception {
		writer.open("root");
		assertEquals("root", writer.getCurrentElement().getNodeName());
	}

	/**
	 * Test method for {@link org.eclipse.uomo.xml.impl.ohf.utilities.xml.DOMXMLWriter#setDefaultNamespace(java.lang.String)}.
	 */
	@Test
	public final void testSetDefaultNamespace() throws Exception {
		writer.setDefaultNamespace("www.default.com");
		assertEquals("www.default.com", writer.getDefaultNamespace());
		writer.open("root");
		assertEquals("www.default.com", doc.getDocumentElement().getNamespaceURI());
	}

	/**
	 * Test method for {@link org.eclipse.uomo.xml.impl.ohf.utilities.xml.DOMXMLWriter#setPretty(boolean)}.
	 */
	@Test
	public final void testSetPretty() throws Exception {
		writer.setPretty(true);
		assertEquals(true, writer.isPretty());
		
		writer.setPretty(false);
		assertEquals(false, writer.isPretty());
	}

	/**
	 * Test method for {@link org.eclipse.uomo.xml.impl.ohf.utilities.xml.DOMXMLWriter#text(java.lang.String)}.
	 */
	@Test
	public final void testText() throws Exception {
		writer.namespace("www.test.com", "test");
		writer.open("root");
		
		writer.text("value");
		assertEquals("value", doc.getDocumentElement().getTextContent());
	}

	/**
	 * Test method for {@link org.eclipse.uomo.xml.impl.ohf.utilities.xml.DOMXMLWriter#writeBytes(byte[])}.
	 */
	@Test
	public final void testWriteBytes() throws Exception {
		writer.namespace("www.test.com", "test");
		writer.open("root");
		
		writer.writeBytes("value".getBytes());
		assertEquals("value", doc.getDocumentElement().getTextContent());
	}

}
