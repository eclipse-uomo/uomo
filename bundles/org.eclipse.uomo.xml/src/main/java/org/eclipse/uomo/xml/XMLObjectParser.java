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

package org.eclipse.uomo.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public abstract class XMLObjectParser {

	private int useCount = 0;
	private String namespace;
	private String name;
	private String path;
	private boolean root;
	
	public boolean isRoot() {
		return root;
	}

	public void setRoot(boolean root) {
		this.root = root;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public void use() {
		useCount++;
	}
	
	public boolean unUse() {
		useCount--;
		return useCount == 0; 
	}
	
	public void start(String namespace, String name, String defaultNamespace, Attributes attributes) throws SAXException {
	}
	
	public void end() throws SAXException {
		
	}
	
	public boolean hasText() throws SAXException {
		return false;
	}
	
	public void findText(char[] ch, int start, int length, boolean whitespace) throws SAXException {
		if (!whitespace && !hasText())
			throw new SAXException("Unexpected Text Content \""+new String(ch, start, length)+"\" at "+path);
	}
	
	public XMLObjectParser startElement(String namespace, String name, String defaultNamespace, Attributes attributes) throws SAXException {
		if (!allowChildren())
			throw new SAXException("unexpected child {"+namespace+"}"+name+" at "+path);
		else
			return null;
	}
	
	public void endElement(String namespace, String name) throws SAXException {
		
	}

	public void endChild(XMLObjectParser child) throws SAXException {
	}

	public boolean allowChildren() {
		return false;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
