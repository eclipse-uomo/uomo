/*******************************************************************************
 * Crown Copyright (c) 2006, 2007, Copyright (c) 2006, 2007 Jiva Medical.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Jiva Medical - initial API and implementation
 *******************************************************************************/
package org.eclipse.uomo.xml.impl;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * SAX Content reader, returns the text content of any XML element.
 * 
 * @author Grahame
 * @deprecated Typo (Reaper) and unused
 */
class XMLTextReaper implements ContentHandler {

	private String text = "";
	
	/**
	 * Reads a byte array stream of a piece of XML
	 * in order to grab the text content of the elements. 
	 * @param stream
	 * @throws SAXException
	 */
	public void read(ByteArrayInputStream stream) throws SAXException {
		org.xml.sax.XMLReader xml;
		try {
			xml = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			xml.setFeature("http://xml.org/sax/features/namespaces", true);
			xml.setFeature("http://xml.org/sax/features/namespace-prefixes", false);
			xml.setContentHandler(this);
			xml.parse(new InputSource(stream));
		} catch (Exception e) {
			throw new SAXException(e);
		}
	}
	
	/**
	 * Returns the text content of the element.
	 * @return the text content of the element
	 */
	public String getText() {
		return text;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#characters(char[], int, int)
	 */
	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
		// taken out the extra space because it caused problems when handling long annotation content
//		text = text + " " + new String(arg0, arg1, arg2);
		text = text + new String(arg0, arg1, arg2);
	}

	/*
	 * (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#endDocument()
	 */
	public void endDocument() throws SAXException {
	}

	/*
	 * (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void endElement(String arg0, String arg1, String arg2) throws SAXException {
	}

	/*
	 * (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#endPrefixMapping(java.lang.String)
	 */
	public void endPrefixMapping(String arg0) throws SAXException {
	}

	/*
	 * (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#ignorableWhitespace(char[], int, int)
	 */
	public void ignorableWhitespace(char[] arg0, int arg1, int arg2) throws SAXException {
	}

	/*
	 * (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#processingInstruction(java.lang.String, java.lang.String)
	 */
	public void processingInstruction(String arg0, String arg1) throws SAXException {
	}

	/*
	 * (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#setDocumentLocator(org.xml.sax.Locator)
	 */
	public void setDocumentLocator(Locator arg0) {
	}

	/*
	 * (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#skippedEntity(java.lang.String)
	 */
	public void skippedEntity(String arg0) throws SAXException {
	}

	/*
	 * (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#startDocument()
	 */
	public void startDocument() throws SAXException {
	}

	/*
	 * (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement(String arg0, String arg1, String arg2, Attributes arg3) throws SAXException {
	}

	/*
	 * (non-Javadoc)
	 * @see org.xml.sax.ContentHandler#startPrefixMapping(java.lang.String, java.lang.String)
	 */
	public void startPrefixMapping(String arg0, String arg1) throws SAXException {
	}
}
