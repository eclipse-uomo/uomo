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

import java.io.IOException;

import org.eclipse.uomo.xml.XMLObjectParser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class XMLWriterParser extends XMLObjectParser {

	XMLWriter writer;
	
	public XMLWriterParser(XMLWriter writer) {
		super();
		this.writer = writer;
	}

	public void start(String namespace, String name, String defaultNamespace, Attributes attributes) throws SAXException {
		try {
			writer.start();
			startElement(namespace, name, defaultNamespace, attributes);
		} catch (IOException e) {
			throw new SAXException(e);
		}
	}

	public XMLObjectParser startElement(String namespace, String name, String defaultNamespace, Attributes attributes) throws SAXException {
		try {
			for (int i = 0; i < attributes.getLength(); i++) {
				writer.namespace(attributes.getURI(i));
				writer.attribute(attributes.getURI(i), attributes.getLocalName(i), attributes.getValue(i));
			}
			writer.namespace(namespace);
			writer.open(namespace, name);
		} catch (Exception e) {
			throw new SAXException(e);
		}
		return null;
	}

	public void endChild(XMLObjectParser child) throws SAXException {
		try {
			writer.close();
		} catch (Exception e) {
			throw new SAXException(e);
		}
	}

	public void endElement(String namespace, String name) throws SAXException {
		try {
			writer.close();
			writer.flush();
		} catch (Exception e) {
			throw new SAXException(e);
		}
	}

	public void findText(char[] ch, int start, int length, boolean whitespace) throws SAXException {
		try {
			if (!whitespace)
				writer.text(String.valueOf(ch, start, length));
		} catch (IOException e) {
			throw new SAXException(e);
		}
	}
}
