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
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.eclipse.uomo.core.UOMoException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class XMLPullWriter {

	private XMLWriter xml;
	
	public XMLPullWriter(OutputStream stream) throws UnsupportedEncodingException, IOException {
		super();
		xml = new XMLWriter(stream, "UTF-8");
		xml.start();
	}

	public XMLPullWriter(XMLWriter writer) throws UnsupportedEncodingException {
		super();
		xml = writer;
	}

	public void write(XmlPullParser xpp, boolean rootElement, String elementName, String defaultNamespace) throws UOMoException, IOException, XmlPullParserException {
		if (defaultNamespace != null) {
			if (!xml.namespaceDefined(defaultNamespace))
				xml.setDefaultNamespace(defaultNamespace);
		} else if (rootElement) 
			xml.setDefaultNamespace(xpp.getNamespace());

		if (elementName != null)
			xml.open(defaultNamespace, elementName);
		
		if (rootElement)
			processElement(xpp);
		else {
			processContents(xpp);
			if (xpp.getEventType() != XmlPullParser.END_DOCUMENT)
				xpp.next();
		}
		
		if (elementName != null)
			xml.close();
		xml.flush();
	}
	
	private void processContents(XmlPullParser xpp) throws XmlPullParserException, UOMoException, IOException {
		while (xpp.getEventType() != XmlPullParser.END_TAG && xpp.getEventType() != XmlPullParser.END_DOCUMENT) {
			if (xpp.getEventType() == XmlPullParser.START_TAG)
				processElement(xpp);
			else if (xpp.getEventType() == XmlPullParser.TEXT)
				processText(xpp);
			else
				throw new UOMoException("unhandled event type "+Integer.toString(xpp.getEventType()));
		}
	}

	private void processElement(XmlPullParser xpp) throws UOMoException, XmlPullParserException, IOException {
		xml.namespace(xpp.getNamespace());

		processAttributes(xpp);
		xml.open(xpp.getNamespace(), xpp.getName());
		xpp.next();
		
		processContents(xpp);
		
		xml.close();
		if (xpp.getEventType() != XmlPullParser.END_DOCUMENT)
			xpp.next();
	}

	private void processText(XmlPullParser xpp) throws UOMoException, XmlPullParserException, IOException {
		xml.text(xpp.getText());
		xpp.next();
	}

	private void processAttributes(XmlPullParser xpp) throws IOException {
		for (int i = 0; i < xpp.getAttributeCount(); i++) {
			String ns = xpp.getAttributeNamespace(i);
			if (!"".equals(ns)) {
				xml.namespace(ns);
     			xml.attribute(ns, xpp.getAttributeName(i), xpp.getAttributeValue(i));
			} else 
     			xml.attribute(xpp.getAttributeName(i), xpp.getAttributeValue(i));
		}
		
	}

}
