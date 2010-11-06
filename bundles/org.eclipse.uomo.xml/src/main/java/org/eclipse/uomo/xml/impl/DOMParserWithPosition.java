/*******************************************************************************
 * Crown Copyright (c) 2010, 2010, Copyright (c) 2010, 2010 Kestral Computing.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Kestral Computing - initial API and implementation
 *******************************************************************************/
package org.eclipse.uomo.xml.impl;

import org.apache.xerces.parsers.DOMParser;
import org.apache.xerces.xni.Augmentations;
import org.apache.xerces.xni.NamespaceContext;
import org.apache.xerces.xni.QName;
import org.apache.xerces.xni.XMLAttributes;
import org.apache.xerces.xni.XMLLocator;
import org.apache.xerces.xni.XMLString;
import org.apache.xerces.xni.XNIException;
import org.w3c.dom.Node;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

/**
 * @author dennisn
 * 
 */
public class DOMParserWithPosition extends DOMParser {

	/**
	 * Constants used to find the start line in element's user data
	 */
	public static final String START_LINE = "startLine"; //$NON-NLS-1$
	public static final String START_COLUMN = "startColumn"; //$NON-NLS-1$
	public static final String END_LINE = "endLine"; //$NON-NLS-1$
	public static final String END_COLUMN = "endColumn"; //$NON-NLS-1$

	private static boolean NotIncludeIgnorableWhiteSpaces = false;
	private XMLLocator locator;

	// constructor
	public DOMParserWithPosition() throws SAXNotRecognizedException,
			SAXNotSupportedException {
		this.setFeature(
				"http://apache.org/xml/features/dom/defer-node-expansion", false); //$NON-NLS-1$
	}

	/* Methods that we override */

	/* We override startElement/endElement callback from DocumentHandler */
	public void startElement(QName elementQName, XMLAttributes attrList,
			Augmentations augs) throws XNIException {
		super.startElement(elementQName, attrList, augs);

		Node node = getCurrentNode();
		if (node != null) {
			// Save location into node
			node.setUserData(START_LINE, new Integer(locator.getLineNumber()),
					null);
			node.setUserData(START_COLUMN,
					new Integer(locator.getColumnNumber()), null);
		}
	}

	public void endElement(QName elementQName, Augmentations augs)
			throws XNIException {
		super.endElement(elementQName, augs);

		Node node = getCurrentNode();
		if (node != null) {
			// Save location into node
			node.setUserData(END_LINE, new Integer(locator.getLineNumber()),
					null);
			node.setUserData(END_COLUMN,
					new Integer(locator.getColumnNumber()), null);
		}
	}

	/* We override startDocument/endDocument callback from DocumentHandler */
	public void startDocument(XMLLocator locator, String encoding,
			NamespaceContext namespaceContext, Augmentations augs)
			throws XNIException {
		super.startDocument(locator, encoding, namespaceContext, augs);
		this.locator = locator;
		Node node = getCurrentNode();
		if (node != null)
			node.setUserData(START_LINE, new Integer(locator.getLineNumber()),
					null); // Save location String into node
	}

	public void endDocument(Augmentations augs) throws XNIException {
		super.endDocument(augs);
		Node node = getCurrentNode();
		if (node != null)
			node.setUserData(START_LINE, new Integer(locator.getLineNumber()),
					null); // Save location String into node
	}

	private Node getCurrentNode() {
		try {
			return (Node) this
					.getProperty("http://apache.org/xml/properties/dom/current-element-node"); //$NON-NLS-1$
		} catch (org.xml.sax.SAXException ex) {
			System.err.println("except" + ex);; //$NON-NLS-1$
			return null;
		}
	}

	public void ignorableWhitespace(XMLString text, Augmentations augs)
			throws XNIException {
		if (!NotIncludeIgnorableWhiteSpaces)
			super.ignorableWhitespace(text, augs);
		else
			;// Ignore ignorable white spaces
	}

	// Static utility methods to facilitate accessing node value
	public static Integer getNodeLocation(Node node, String locationId,
			Integer defaultValue) {
		if (node != null && node.getUserData(locationId) instanceof Integer) {
			return (Integer) node.getUserData(locationId);
		}
		return defaultValue;
	}

	public static Integer getNodeStartLine(Node node, Integer defaultValue) {
		return getNodeLocation(node, START_LINE, defaultValue);
	}

	public static Integer getNodeStartColumn(Node node, Integer defaultValue) {
		return getNodeLocation(node, START_COLUMN, defaultValue);
	}

	public static Integer getNodeEndLine(Node node, Integer defaultValue) {
		return getNodeLocation(node, END_LINE, defaultValue);
	}

	public static Integer getNodeEndColumn(Node node, Integer defaultValue) {
		return getNodeLocation(node, END_COLUMN, defaultValue);
	}
}
