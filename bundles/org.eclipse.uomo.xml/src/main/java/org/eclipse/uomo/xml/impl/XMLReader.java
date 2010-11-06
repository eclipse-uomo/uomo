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

import java.io.InputStream;

import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.lang.StringUtils;
import org.eclipse.uomo.xml.XMLObjectParser;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLReader extends DefaultHandler {
	
	private XMLObjectParsers stack = new XMLObjectParsers();
	private XMLObjectParser root;
	
	public XMLReader(XMLObjectParser handler) throws SAXException {
		super();
		root = handler;
	}
	
	private XMLObjectParser current() throws SAXException {
		return stack.current();
	}
	
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		XMLObjectParser handler;
		String namespace;
		String name;
		if (uri == null || uri.equals("")) {
			namespace = "";
			name = qName;
			
		} else {
			namespace = uri;
			name = localName;
		}
		
		if (!stack.hasCurrent()) {
			if (root != null) {
				stack.push(root);
				root.setPath(name);
				root.start(namespace, name, "", atts);
				root = null;
				current().use();
			} else
				throw new SAXException("no handler available");
		} else {			
			handler = current().startElement(namespace, name, "", atts);
			if (handler == null)
				current().use();
			else {
				handler.use();
				handler.setPath(current().getPath() + "\\" + name);
				handler.setNamespace(namespace);
				handler.setName(name);
				stack.push(handler);
				current().start(namespace, name, "", atts);
			}
		}
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {
		String namespace;
		String name;
		if (uri == null || uri.equals("")) {
			namespace = "";
			name = qName;
			
		} else {
			namespace = uri;
			name = localName;
		}
		if (current().unUse()) {
			XMLObjectParser old = current();
			old.end();
			stack.pop();
			if (stack.hasCurrent())
				current().endChild(old);
		} else
			current().endElement(namespace, name);
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		current().findText(ch, start, length, StringUtils.isWhitespace(new String(ch, start, length)));
		}
	
	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
		if (current().hasText())
			current().findText(ch, start, length, true);
	}

	public void parse(InputStream stream) throws SAXException {
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
	
	
	
	
}
