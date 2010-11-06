/*******************************************************************************
 * Crown Copyright (c) 2006, 2010, Copyright (c) 2006, 2010 Jiva Medical.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Jiva Medical - initial API and implementation
 *    Werner Keil  - usage of UnitException
 *******************************************************************************/
package org.eclipse.uomo.xml.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import org.eclipse.uomo.core.UOMoException;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * @author Werner Keil
 * @version 1.1 ($Revision: 63 $), $Date: 2010-07-19 11:35:31 +0100 (Mo, 19 Jul
 *          2010) $
 */
public class XMLDomWriter {

	private XMLWriter xml;

	public XMLDomWriter(OutputStream stream)
			throws UnsupportedEncodingException, IOException {
		super();
		xml = new XMLWriter(stream, "UTF-8");
		xml.start();
	}

	public XMLDomWriter(XMLWriter writer) throws UnsupportedEncodingException {
		super();
		xml = writer;
	}

	public void write(Element element, boolean rootElement, String elementName,
			String defaultNamespace) throws UOMoException, IOException {
		if (defaultNamespace != null) {
			if (!xml.namespaceDefined(defaultNamespace))
				xml.setDefaultNamespace(defaultNamespace);
		} else if (rootElement)
			xml.setDefaultNamespace(element.getNamespaceURI());

		if (elementName != null)
			xml.open(defaultNamespace, elementName);

		if (rootElement)
			processElement(element);
		else {
			processContents(element);
		}

		if (elementName != null)
			xml.close();
		xml.flush();
	}

	private void processContents(Element element) throws UOMoException,
			IOException {
		Node node = element.getFirstChild();
		while (node != null) {
			switch (node.getNodeType()) {
			case Node.ELEMENT_NODE:
				processElement((Element) node);
				break;
			case Node.TEXT_NODE:
				processText(node);
				break;
			case Node.COMMENT_NODE:
				processComment((Comment) node);
				break;
			default:
				throw new UOMoException("unhandled node type "
						+ Integer.toString(node.getNodeType()));
			}

			node = node.getNextSibling();
		}
	}

	private void processComment(Comment node) throws DOMException, IOException {
		xml.comment(node.getNodeValue(), true);
	}

	private void processElement(Element element) throws UOMoException,
			IOException {
		xml.namespace(element.getNamespaceURI());

		processAttributes(element);
		xml.open(element.getNamespaceURI(), element.getLocalName());

		processContents(element);

		xml.close();
	}

	private void processText(Node node) throws UOMoException, IOException {
		xml.text(node.getNodeValue());
	}

	private void processAttributes(Element element) throws IOException {
		NamedNodeMap nodes = element.getAttributes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node attr = nodes.item(i);
			if (attr.getNamespaceURI() != null) {
				xml.namespace(attr.getNamespaceURI());
				xml.attribute(attr.getNamespaceURI(), attr.getLocalName(),
						attr.getNodeValue());
			} else
				xml.attribute(attr.getLocalName(), attr.getNodeValue());
		}

	}

}
