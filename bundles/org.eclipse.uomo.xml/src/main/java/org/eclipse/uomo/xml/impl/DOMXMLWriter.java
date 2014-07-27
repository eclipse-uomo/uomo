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
package org.eclipse.uomo.xml.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.uomo.xml.DOMUtil;
import org.eclipse.uomo.xml.IXMLWriter;
import org.eclipse.uomo.xml.XMLUtil;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * A DOM-base XML writer
 * 
 * @author dennisn
 *
 */
public class DOMXMLWriter implements IXMLWriter {
	
	/**
	 * The internal document
	 */
	private Document doc = null;
	
	private boolean isPretty = true;
	
	// the current stack of element
	private Stack<Node> current = new Stack<Node>();
	
	// defined attributes for the new coming element
	private Map attributes = new HashMap();
	
	// the defined namespace for the new coming element
	private Map definedNS = new HashMap();
	
	private DocumentFragment commentBlock = null;
	
	public DOMXMLWriter() throws ParserConfigurationException {
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		builderFactory.setNamespaceAware(true);
		DocumentBuilder docBuilder = builderFactory.newDocumentBuilder();
		doc = docBuilder.newDocument();
	}
	
	public DOMXMLWriter(Document doc) {
		this.doc = doc;
	}
	
	/**
	 * @return the internal document
	 * NOTE: be careful when changing the internal document, as it may
	 * cause error for the writer
	 */
	public Document getDOMDocument() {
		return doc;
	}
	
	public Element getCurrentElement() {
		return (Element) current.peek();
	}
	
	protected boolean condition(boolean bTest, String message) throws IOException {
		if (!bTest)
			throw new IOException(message);
		return bTest;
	}
	
	private void addAttribute(String name, String value) throws IOException {
		addAttribute(name, value, false);
	}

	private void addAttribute(String name, String value, boolean isNoLines) throws IOException {
		if (!XMLUtil.isNMToken(name))
			throw new IOException("XML name "+name+" is not valid");

		condition(!attributes.containsKey(name), "attempt to define attribute with name "+name+" more than once");
		attributes.put(name, XMLUtil.escapeXML(value, null, isNoLines));
	}
	
	private void defineNamespace(String namespace, String prefix) {
		if (namespace != null) {
			if (prefix == null)
				prefix = "";
			
			definedNS.put(prefix, namespace);
		}
	}
	
	private String getPrefixForNamespace(String namespace) throws IOException {
		if ("http://www.w3.org/XML/1998/namespace".equals(namespace))
			return "xml:";

		String prefix = null;
		if (definedNS.containsValue(namespace)) {
			Iterator iter = definedNS.keySet().iterator();
			while (prefix == null && iter.hasNext()) {
				Object k = iter.next();
				if (definedNS.get(k).equals(namespace))
					prefix = k.toString();
			}
		}
		else if (current.size() > 0)
			prefix = DOMUtil.lookupPrefixForNamespace((Element) current.peek(), namespace);
		
		if (prefix == null)
			throw new IOException("Namespace "+namespace+" is not defined");
		return prefix + ":";
	}
	
	private String getNamespaceForPrefix(String prefix) throws IOException {
		if ("xml".equals(prefix))
			return "http://www.w3.org/XML/1998/namespace";

		String result = null;
		if (definedNS.containsKey(prefix))
			result = definedNS.get(prefix).toString();
		else if (current.size() > 0)
			prefix = DOMUtil.lookupNamespaceForPrefix((Element) current.peek(), prefix);
		return result == null ? "" : result;
	}
	
	private void checkInElement() throws IOException {
		condition(current != null && current.size() > 0, "Not in an element");
	}

	// IXMLWriter methods
	
	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#abbreviationDefined(java.lang.String)
	 */
	public boolean abbreviationDefined(String abbreviation) {
		return definedNS.containsKey(abbreviation) 
			|| (current.size() > 0 && DOMUtil.lookupNamespaceForPrefix(getCurrentElement(), abbreviation) != null);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#attribute(java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	public void attribute(String namespace, String name, String value, boolean onlyIfNotEmpty) throws IOException {
		if (!onlyIfNotEmpty || value != null && !value.equals(""))
			attribute(namespace, name, value);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#attribute(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void attribute(String namespace, String name, String value) throws IOException {
		if (namespace == null || namespace.equals("")) 
			addAttribute(name, value);
		else
			addAttribute(getPrefixForNamespace(namespace)+name, value);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#attribute(java.lang.String, java.lang.String, boolean)
	 */
	public void attribute(String name, String value, boolean onlyIfNotEmpty) throws IOException {
		if (!onlyIfNotEmpty || value != null && !value.equals(""))
			attribute(name, value);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#attribute(java.lang.String, java.lang.String)
	 */
	public void attribute(String name, String value) throws IOException {
		addAttribute(name, value);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#attributeNoLines(java.lang.String, java.lang.String)
	 */
	public void attributeNoLines(String name, String value) throws IOException {
		addAttribute(name, value, true);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#cData(java.lang.String)
	 */
	public void cData(String text) throws IOException {
		checkInElement();
		getCurrentElement().appendChild(doc.createCDATASection(text));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#close(java.lang.String)
	 */
	public void close(String name) throws IOException {
		if (current.size() == 0)
			throw new IOException("Unable to close null|"+name+", nothing to close");
		if (!getCurrentElement().getNodeName().equals(name))
			throw new IOException("Unable to close null|"+name+", found "+getCurrentElement().getNodeName());
		close();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#close(java.lang.String, java.lang.String)
	 */
	public void close(String namespace, String name) throws IOException {
		if (current.size() == 0)
			throw new IOException("Unable to close null|"+name+", nothing to close");
		// assume current is DOM level 3
		Element ele = getCurrentElement();
		if (!namespace.equals(ele.getNamespaceURI()) || !name.equals(ele.getLocalName()))
			throw new IOException("Unable to close "+namespace+"|"+name+", found "+ele.getNamespaceURI() +"|"+ele.getLocalName());
		close();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#close()
	 */
	public void close() throws IOException {
		checkInElement();
		current.pop();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#closeToLevel(int)
	 */
	public void closeToLevel(int count) throws IOException {
		while (current.size() > count)
			close();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#comment(java.lang.String, boolean)
	 */
	public void comment(String comment, boolean doPretty) throws IOException {
		Comment c = doc.createComment(comment);
		getCurrentElement().appendChild(c);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#element(java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	public void element(String namespace, String name, String content, boolean onlyIfNotEmpty) throws IOException {
		if (!onlyIfNotEmpty || content != null && !content.equals(""))
			element(namespace, name, content);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#element(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void element(String namespace, String name, String content, String comment) throws IOException {
		if (!XMLUtil.isNMToken(name))
			throw new IOException("XML name "+name+" is not valid");
		open(namespace, name, comment);
		text(content);
		close();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#element(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void element(String namespace, String name, String content) throws IOException {
		if (!XMLUtil.isNMToken(name))
			throw new IOException("XML name "+name+" is not valid");
		open(namespace, name);
		text(content);
		close();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#element(java.lang.String, java.lang.String, boolean)
	 */
	public void element(String name, String content, boolean onlyIfNotEmpty) throws IOException {
		if (!onlyIfNotEmpty || content != null && !content.equals(""))
			element(null, name, content);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#element(java.lang.String, java.lang.String)
	 */
	public void element(String name, String content) throws IOException {
		element(null, name, content);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#endCommentBlock()
	 */
	public void endCommentBlock() throws IOException {
		if (commentBlock == null)
			throw new IOException("Cannot close a comment block when none existed");
		else if (getCurrentElement().getParentNode() != commentBlock)
			throw new IOException("Cannot close a comment block when it's still opened");
		
		ByteArrayOutputStream temp = new ByteArrayOutputStream();
		try {
			// re-create the comment nodes
			NodeList children = getCurrentElement().getChildNodes();
			DocumentFragment frag = doc.createDocumentFragment();
			for (int i = 0; i < children.getLength(); i++) 
				frag.appendChild(children.item(i));
			
			// init DOM->String transformer
			Transformer trans = TransformerFactory.newInstance().newTransformer();
			trans.setOutputProperty(OutputKeys.STANDALONE, "no");
			trans.setOutputProperty(OutputKeys.INDENT, "yes");
			trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

			// convert from DOM to string comment
			
			trans.transform(new DOMSource(frag), new StreamResult(temp));
		}
		catch (Exception e) {
			throw new IOException(e.getLocalizedMessage());
		}
		finally {
			current.pop();
			commentBlock = null;
		}
		comment(temp.toString(), true);
	}

//	/* (non-Javadoc)
//	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#escapedText(java.lang.String)
//	 */
//	public void escapedText(String content) throws IOException {
//		checkInElement();
//		text(content);
//	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#getDefaultNamespace()
	 */
	public String getDefaultNamespace() {
		String result = null;
		if (definedNS.size() > 0) {
			try {
				result = getNamespaceForPrefix("");
			} catch (IOException e) {
				// do nothing
			}
		}
		
		if (result == null && current.size() > 0)
			result = DOMUtil.lookupNamespaceForPrefix(getCurrentElement(), "");
		
		return result == null ? "": result;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#isPretty()
	 */
	public boolean isPretty() throws IOException {
		return isPretty;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#namespace(java.lang.String)
	 */
	public void namespace(String namespace) throws IOException {
		if (!namespaceDefined(namespace)) {
			int index = 0;
			while (abbreviationDefined("ns"+Integer.toString(index))) 
				index++;
			defineNamespace(namespace, "ns"+Integer.toString(index));
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#namespace(java.lang.String, java.lang.String)
	 */
	public void namespace(String namespace, String prefix) throws IOException {
		String ns = getNamespaceForPrefix(prefix);
		if (ns == null || !ns.equals(namespace))
			defineNamespace(namespace, prefix);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#namespaceDefined(java.lang.String)
	 */
	public boolean namespaceDefined(String namespace) {
		try {
			return getPrefixForNamespace(namespace) != null;
		} catch (IOException e) {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#open(java.lang.String, java.lang.String)
	 */
	public void open(String namespace, String name) throws IOException {
		open(namespace, name, null);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#open(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void open(String namespace, String name, String comment) throws IOException {
		if (!XMLUtil.isNMToken(name))
			throw new IOException("XML name "+name+" is not valid");
		if (name == null)
			throw new IOException("XML name is null");

		Element ele;
		if (namespace != null) {
			name = getPrefixForNamespace(namespace) + name;
			ele = doc.createElementNS(namespace, name);
		}
		else if (getDefaultNamespace().length() > 0)
			ele = doc.createElementNS(getDefaultNamespace(), name);
		else
			ele = doc.createElement(name);
		defineAttributes(ele);
		defineNamespaces(ele);
		if (current.size() == 0)
			doc.appendChild(ele);
		else
			getCurrentElement().appendChild(ele);
		current.push(ele);
		if (comment != null) comment(comment, true);
	}

	private void defineNamespaces(Element ele) {
		Iterator<?> iter = definedNS.keySet().iterator();
		while (iter.hasNext()) {
			Object k = iter.next();
			ele.setAttribute("xmlns:" + k.toString(), definedNS.get(k).toString());
		}
		definedNS.clear();
	}

	private void defineAttributes(Element ele) {
		Iterator<?> iter = attributes.keySet().iterator();
		while (iter.hasNext()) {
			Object k = iter.next();
			ele.setAttribute(k.toString(), attributes.get(k).toString());
		}
		attributes.clear();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#open(java.lang.String)
	 */
	public void open(String name) throws IOException {
		open(null, name);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#setDefaultNamespace(java.lang.String)
	 */
	public void setDefaultNamespace(String namespace) throws IOException {
		if ((namespace == null && getDefaultNamespace() != null) ||
				(namespace != null && !namespace.equals(getDefaultNamespace())))
			defineNamespace(namespace, "");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#setPretty(boolean)
	 */
	public void setPretty(boolean pretty) throws IOException {
		this.isPretty = pretty;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#start()
	 */
	public void start() throws IOException {
		// dont need to do anything
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#startCommentBlock()
	 */
	public void startCommentBlock() throws IOException {
		if (commentBlock != null)
			throw new IOException("Cannot nest comments");
		/* we start a comment block by create a document fragment with 1 element root: commentRoot
		 * Further node will be add to be children of this element root.
		 */
		commentBlock = doc.createDocumentFragment();
		Node node = doc.createElement("commentRoot");
		commentBlock.appendChild(node);
		current.push(node);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#text(java.lang.String)
	 */
	public void text(String content) throws IOException {
		text(content, false);
	}
	
	public void text(String content, boolean dontEscape) throws IOException {
		if (!dontEscape)
			content = XMLUtil.escapeXML(content, null, false);
		checkInElement();
		Node node = doc.createTextNode(content);
		getCurrentElement().appendChild(node);	
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.utilities.xml.IXMLWriter#writeBytes(byte[])
	 */
	public void writeBytes(byte[] bytes) throws IOException {
		text(new String(bytes));
	}

}
