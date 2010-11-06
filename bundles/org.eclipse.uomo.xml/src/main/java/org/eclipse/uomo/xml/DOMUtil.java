/*******************************************************************************
 * Crown Copyright (c) 2006, 2007, Copyright (c) 2006, 2007 Jiva Medical.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Jiva Medical - initial API and implementation
 *    B2 International - added generateXMLString(DocumentFragment, boolean)
 *******************************************************************************/
package org.eclipse.uomo.xml;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XML utility class.
 * 
 * @author Grahame Grieve
 * @author Zsolt Torok
 */
public final class DOMUtil {

	//XML Settings
	private static final String XML_VERSION = "1.0";
    private static final String XML_ENCODING = "UTF-8";

    private DOMUtil() {}
    
	public static int getIndex(Node parentNode, Node child) {
		NodeList nodeList = parentNode.getChildNodes();
		int index = -1;
		int size = nodeList.getLength();
		for (int i = 0; i < size; i++) {
			if (nodeList.item(i) == child) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	public static boolean isCommentAllowed(Node node) {
		
		short type=node.getNodeType();
		return type==Node.ELEMENT_NODE || type==Node.TEXT_NODE;
	}
	
	/**
	 * Generates a string representation of an XML document
	 * @param DOM document
	 * @return XML document in a string
	 * @throws IOException
	 */
	public static synchronized String generateXMLString(Document doc) throws IOException {
		StringWriter  stringWriter    = null;
	    XMLSerializer serializer   = null;
	    OutputFormat  outputFormat    = null;
	    String result= null;

	    serializer = new XMLSerializer();
	    stringWriter = new StringWriter();
	    outputFormat = new OutputFormat();

	    // Setup format settings
	    outputFormat.setEncoding(XML_ENCODING);
	    outputFormat.setVersion(XML_VERSION);
	    outputFormat.setIndenting(true);
	    outputFormat.setIndent(4);
	    serializer.setOutputCharStream(stringWriter);
	    serializer.setOutputFormat(outputFormat);
	    serializer.serialize(doc);
	    result = stringWriter.toString();
	    stringWriter.close();	   
	    return result;
	}
	
	public static synchronized String generateXMLString(DocumentFragment fragment, boolean omitXMLDeclaration) throws IOException {
		if (omitXMLDeclaration) {
			StringWriter  stringWriter    = null;
		    XMLSerializer serializer   = null;
		    OutputFormat  outputFormat    = null;
		    String result= null;

		    serializer = new XMLSerializer();
		    stringWriter = new StringWriter();
		    outputFormat = new OutputFormat();

		    outputFormat.setOmitXMLDeclaration(true);
		    outputFormat.setIndenting(true);
		    outputFormat.setIndent(4);
		    serializer.setOutputCharStream(stringWriter);
		    serializer.setOutputFormat(outputFormat);
		    serializer.serialize(fragment);
		    result = stringWriter.toString();
		    stringWriter.close();	   
		    return result;
			
		} else
			generateXMLString(fragment);
		return null;
	}
	
	/**
	 * Generates a string representation of a document fragment.
	 * @param fragment to convert to XML
	 * @return String XML
	 * @throws IOException
	 */
	public static synchronized String generateXMLString(DocumentFragment fragment) throws IOException {
		StringWriter  stringWriter    = null;
	    XMLSerializer serializer   = null;
	    OutputFormat  outputFormat    = null;
	    String result= null;

	    serializer = new XMLSerializer();
	    stringWriter = new StringWriter();
	    outputFormat = new OutputFormat();

	    // Setup format settings
	    outputFormat.setEncoding(XML_ENCODING);
	    outputFormat.setVersion(XML_VERSION);
	    outputFormat.setIndenting(true);
	    outputFormat.setIndent(4);
	    serializer.setOutputCharStream(stringWriter);
	    serializer.setOutputFormat(outputFormat);
	    serializer.serialize(fragment);
	    result = stringWriter.toString();
	    stringWriter.close();	   
	    return result;
	}
	
	/**
	 * Generates a string representation of the XML element
	 * passed in.
	 * @param element to convert an XML string
	 * @return string XML
	 * @throws IOException
	 */
	public static synchronized String generateXMLString(Element element) throws IOException{

	    StringWriter  stringWriter    = null;
	    XMLSerializer serializer   = null;
	    OutputFormat  outputFormat    = null;
	    String result= null;

	    serializer = new XMLSerializer();
	    stringWriter = new StringWriter();
	    outputFormat = new OutputFormat();

	    // Setup format settings
	    outputFormat.setEncoding(XML_ENCODING);
	    outputFormat.setVersion(XML_VERSION);
	    outputFormat.setIndenting(true);
	    outputFormat.setIndent(4);
	    outputFormat.setOmitXMLDeclaration(true);
	    serializer.setOutputCharStream(stringWriter);
	    serializer.setOutputFormat(outputFormat);
	    serializer.serialize(element);
	    result = stringWriter.toString();
	    stringWriter.close();	   
	    return result;
	  }
	
	/**
	 * Use the basePrefix to define new name-space prefix
	 * If the basePrefix is already used for other name-space, a number will be added to the prefix to create
	 * a new unique prefix
	 * 
	 * @param parent
	 * @param namespace
	 * @param basePrefix
	 * @return the prefix that is now associated with this name-space.
	 */
	public static String addNamespacePrefix(Element parent, String namespace, String basePrefix) {
		String result = suggestNamespacePrefix(parent, namespace, basePrefix);
		
		parent.getOwnerDocument().getDocumentElement().setAttribute("xmlns:" + result, namespace);
		return result;
	}

	/**
	 * To find unallocated name-space prefix that can be used to define given name-space.
	 * This method often call with addNamespacePrefix;
	 * 
	 * @param parent
	 * @param namespace
	 * @param basePrefix
	 * @return
	 */
	public static String suggestNamespacePrefix(Element parent, String namespace, String basePrefix) {
		String result = basePrefix;
		if (result == null) result = "ns";
		int count = 0;
		String foundNS = lookupPrefixOnElement(parent, basePrefix);
		while (foundNS != null && !foundNS.equals(namespace)) {
			count++;
			result = basePrefix + count;
		}
		return result;
	}

	/**
	 * Moved from DefnANY
	 * Identified all prefix defined unto (and include) this element
	 * 
	 * @param element
	 * @param namespace
	 * @return null if this name-space is not declared, or the defined-prefix used for this name-space
	 * 
	 * @see
	 *	org.eclipse.ohf.h3et.instanceeditor.core.util.DOMUtility#lookupPrefixForNamespace(org.w3c.dom.Element, org.w3c.dom.Element, java.lang.String)
	 */
	public static String lookupPrefixForNamespace(Element element, String namespace) {
		String result = lookupNamespaceOnElement(element, namespace);
		if (result == null) {
			if (element.getParentNode() != null && element.getParentNode() instanceof Element)
				result = lookupPrefixForNamespace((Element) element.getParentNode(), namespace);
			else if (element.getOwnerDocument() != null && element.getOwnerDocument().getDocumentElement() != null
					&& element.getOwnerDocument().getDocumentElement() != element)
				/*
				 * if element is not yet hooked up to its parent, then parent is null
				 * so we must try to find prefix in root
				 * Note that this is not entirely correct, as other prefix may be defined enroute to its parent
				 */
				result = lookupPrefixForNamespace(element.getOwnerDocument().getDocumentElement(), namespace);
		}
		
		return result;
	}
	
	/**
	 * In case the element is not yet appended into the XML tree, the prospective parent
	 * need to be supplied to correctly find all the defined prefix in context
	 *
	 * @param parent the element parent-to-be (in case the element is not yet add to the XML structure)
	 * @param element
	 * @param namespace
	 * @return null if this name-space is not declared, 
	 * or the defined-prefix used for this name-space
	 */
	public static String lookupPrefixForNamespace(Element parent, Element element, String namespace) {
		String result = lookupNamespaceOnElement(element, namespace);
		if (result == null) {
			if (element.getParentNode() != null && element.getParentNode() instanceof Element)
				result = lookupPrefixForNamespace((Element) element.getParentNode(), namespace);
			else if (parent != null)
				result = lookupPrefixForNamespace(parent, namespace);
			else if (element.getOwnerDocument() != null && element.getOwnerDocument().getDocumentElement() != null)
				/*
				 * if element is not yet hooked up to its parent, then parent is null
				 * so we must try to find prefix in root
				 * Note that this is not entirely correct, as other prefix may be defined enroute to its parent
				 */
				result = lookupPrefixForNamespace(element.getOwnerDocument().getDocumentElement(), namespace);
		}
		
		return result;
	}
	
	/**
	 * @param element
	 * @param namespace
	 * @return
	 */
	private static String lookupNamespaceOnElement(Element element, String namespace) {
		
		for (int i = 0; i < element.getAttributes().getLength(); i++) {
			Attr attr = (Attr) element.getAttributes().item(i);
			String name = attr.getNodeName();
					
			if (name.startsWith("xmlns") && namespace.equals(attr.getNodeValue())) {
				if (name.equals("xmlns")) 
					return "";
				else 
					return name.substring(6);				
			}
		}
		return null;
	}
	
	/**
	 * @param element
	 * @param prefix
	 * @return the name-space corresponding to this prefix, or null
	 */
	public static String lookupNamespaceForPrefix(Element element, String prefix) {
		String result = lookupPrefixOnElement(element, prefix);
		if (result == null && element.getParentNode() != null && element.getParentNode() instanceof Element) {
			result = lookupNamespaceForPrefix((Element) element.getParentNode(), prefix);
		}
		return result;
	}

	
	/**
	 * @param element
	 * @param prefix
	 * @return the name-space corresponding to this prefix, or null
	 */
	public static String lookupPrefixOnElement(Element element, String prefix) {

		for (int i = 0; i < element.getAttributes().getLength(); i++) {
			Attr attr = (Attr) element.getAttributes().item(i);
			String name = attr.getNodeName();

			if (name.startsWith("xmlns:") && name.endsWith(":"+prefix)) {
				return attr.getNodeValue();
			}
		}
		return null;
	}
	
	/**
	 * Sometimes, the element is created in DOM 1 so prefix is prepend directly into node name.
	 * This method handle both case of prefix, and fall back to the name-space assigned at creation
	 * @param node
	 * @return the name-space of this element (can be null if using DOM 1 with default name-space)
	 */
	public static String lookupNamespaceForElement(Element node) {
		if (node.getPrefix() != null)
			return lookupNamespaceForPrefix(node, node.getPrefix());
		else if (node.getNodeName().indexOf(":") > 0)
			return lookupNamespaceForPrefix(node, node.getNodeName().substring(0, node.getNodeName().indexOf(":")));
		return node.getNamespaceURI();
	}
	
	/**
	 * If node is element, then return its own name-space.
	 * If node is an attribute, then return the name-space of its owner element
	 * Otherwise, return null.
	 * @param node
	 * @return name-space
	 */
	public static String lookupNamespaceForNode(Node node) {
		if (node instanceof Attr) {
			String name = node.getNodeName();
			if (name.indexOf(":") > 0)
				return lookupNamespaceForPrefix(((Attr)node).getOwnerElement(), name.substring(0, name.indexOf(":")));
			else
				return lookupNamespaceForElement(((Attr)node).getOwnerElement());
		}
		else if (node instanceof Element)
			return lookupNamespaceForElement((Element) node);
		else
			return null;
	}

	public static boolean hasContent(Element element) {
		Node node = element.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE || node.getNodeType() == Node.TEXT_NODE || 
					node.getNodeType() == Node.ENTITY_NODE || node.getNodeType() == Node.ENTITY_REFERENCE_NODE ||
					node.getNodeType() == Node.CDATA_SECTION_NODE)
				return true;
			node = node.getNextSibling();
		}
		return false;
	}

	public static String getNodeLocalName(Node node) {
		String result = node.getLocalName();
		if (result == null)
			result = node.getNodeName();
		return result;
	}

	public static Element getFirstElement(Element element) {
		Node node = element.getFirstChild();
		while (node != null && !(node instanceof Element))
			node = node.getNextSibling();
		return (Element) node;
	}

	public static Element getNextElement(Element element) {
		Node node = element.getNextSibling();
		while (node != null && !(node instanceof Element))
			node = node.getNextSibling();
		return (Element) node;
	}

	public static Element getFirstElementByName(Element element, String name) {
		Node node = element.getFirstChild();
		while (node != null && (!(node instanceof Element) || !node.getLocalName().equals(name)))
			node = node.getNextSibling();
		return (Element) node;
	}

	public static Element getNextElementByName(Element element, String name) {
		Node node = element.getNextSibling();
		while (node != null && (!(node instanceof Element) || !node.getLocalName().equals(name)))
			node = node.getNextSibling();
		return (Element) node;
	}


}