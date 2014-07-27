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

import org.eclipse.uomo.xml.XMLObjectParser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class XMLStringsParser extends XMLObjectParser {

	private String namespace;
	private String name;
	
	private String[] content;
	private StringBuffer current = null;
	
	
	public XMLStringsParser(String namespace, String name) {
		super();
		this.namespace = namespace;
		this.name = name;
	}

	public void endElement(String namespace, String name) throws SAXException {
		if (current != null)
			addContent(current.toString());
			
		current = null;
	}

	private void addContent(String string) {
		if (content == null)
			content = new String[] {string};
		else {
			String[] newContent = new String[content.length + 1];
			for (int i = 0; i < content.length; i++)
				newContent[i] = content[i];
			newContent[content.length] = string;
			content = newContent;
		}
	}

	public XMLObjectParser startElement(String namespace, String name, String defaultNamespace, Attributes attributes) throws SAXException {
		if (namespace.equals(this.namespace) && name.equals(this.name)) {
			current = new StringBuffer();
			return null;
		}
		else
			return super.startElement(namespace, name, defaultNamespace, attributes);
	}

	public void findText(char[] ch, int start, int length, boolean whitespace) throws SAXException {
		if (current == null)
			super.findText(ch, start, length, whitespace);
		else
			current.append(ch, start, length); 
	}

	public String[] getContent() {
		return content;
	}

	public boolean hasText() throws SAXException {
		return true;
	}

}
