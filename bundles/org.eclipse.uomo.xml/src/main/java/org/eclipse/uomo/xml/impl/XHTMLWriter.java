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
 
//import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * XHTML Writer 
 * 
 *
 */
class XHTMLWriter extends XMLWriter {

	public final static String NS_XHTML = "http://www.w3.org/1999/xhtml";
	public final static int NULL_INTEGER = -1;
	
	private String headerStyle = null;
	
	public XHTMLWriter(OutputStream stream, String charset) throws IOException {
		super(stream, charset);
		setPretty(true);
		setLineType(LINE_WINDOWS);
	}

	// -- CSS -----------------------------------------------------------
	
	private List styles = new ArrayList();
	
	private class StyleDetail {
		private String name;
		private String value;
	}

	// override
	protected void commitAttributes() throws IOException {
		String s = grabStyles();
		if (s != null) {
			String attr = getAttribute("style");
			setAttribute("style", attr == null ? s : attr+"; "+s);
		}
	}

	public String grabStyles() {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < styles.size(); i++) {
			StyleDetail det = (StyleDetail) styles.get(i);
			if (i > 0)
				buffer.append(' ');
			buffer.append(det.name);
			buffer.append(": ");
			buffer.append(det.value);
			buffer.append(';');
		}
		styles.clear();
		return buffer.toString();
	}

	public void cssStyle(String name, String value) {
		StyleDetail det;
		for (int i = 0; i < styles.size(); i++) {
			det = (StyleDetail) styles.get(i);
			if (det.name.equals(name)) {
				det.value = value;
				return;
			}
		}
		det = new StyleDetail();
		det.name = name;
		det.value = value;
		styles.add(det);		
	}
		
	public void cssStyle(String name, boolean value) {
		cssStyle(name, Boolean.toString(value));
	}
	
	public void cssStyle(String name, int value) {
		cssStyle(name, Integer.toString(value));
	}

	//-- HTML -----------------------------------------------------------
	
	public void start() throws IOException {
		super.start();
		setDefaultNamespace(NS_XHTML);
	}

	public void openDiv(String style) throws IOException {
		if (style != null)
			attribute(NS_XHTML, "style", style);
		open(NS_XHTML, "div");
	}

	public void closeDiv() throws IOException, IOException {
		close(NS_XHTML, "div");
	}

	public void openSpan(String style) throws IOException {
		if (style != null)
			attribute(NS_XHTML, "style", style);
		open(NS_XHTML, "span");
	}

	public void closeSpan() throws IOException, IOException {
		close(NS_XHTML, "span");
	}

	public void span(String style, String content) throws IOException, IOException {
		if (style != null)
			attribute(NS_XHTML, "style", style);

		element(NS_XHTML, "span", content);
	}

	public void nonBreakingSpace(int count) throws IOException {
		for (int i = 0; i < count; i++)
			text("&nbsp;");		
	}

	public void paragraph() throws IOException, IOException {
		element(NS_XHTML, "p", null);		
	}

	public void paragraph(String content) throws IOException, IOException {
		element(NS_XHTML, "p", content);		
	}

	
	public void header(int level, String content) throws IOException, IOException {
		if (headerStyle != null)
			attribute(NS_XHTML, "style", headerStyle);
		element(NS_XHTML, "h"+Integer.toString(level), content);		
	}

	public void anchor(String url, String content,String style) throws IOException, IOException {
		if (style != null)
			attribute(NS_XHTML, "style", style);
		anchor(url, content);
	}
	
	public void anchor(String anchor) throws IOException, IOException {
		attribute(NS_XHTML, "name", anchor);
		element(NS_XHTML, "a", null);		
	}
	
	public void openAnchor() throws IOException {
		open(NS_XHTML, "a");		
	}
	
	public void closeAnchor() throws IOException {
		close(NS_XHTML, "a");		
	}
	
	public void anchor(String url, String content) throws IOException, IOException {
		attribute(NS_XHTML, "href", url);
		element(NS_XHTML, "a", content);		
	}

	public void br() throws IOException, IOException {
		element(NS_XHTML, "br", null);		
	}

	public void openTable(int border, int padding, int spacing, int width, String style) throws IOException {
		if (border != NULL_INTEGER)
			attribute(NS_XHTML, "border", Integer.toString(border));
		if (padding != NULL_INTEGER)
			attribute(NS_XHTML, "cellpadding", Integer.toString(border));
		if (spacing != NULL_INTEGER)
			attribute(NS_XHTML, "cellspacing", Integer.toString(border));
		if (style != null)
			attribute(NS_XHTML, "style", style);
		if (width != NULL_INTEGER)
			attribute(NS_XHTML, "width", Integer.toString(width)+"%");
		open(NS_XHTML, "table");
	}

	public void closeTable() throws IOException, IOException {
		close(NS_XHTML, "table");
	}

	public void openTableRow(String style) throws IOException {
		if (style != null)
			attribute(NS_XHTML, "style", style);
		open(NS_XHTML, "tr");
	}

	public void openTableRow() throws IOException {
		open(NS_XHTML, "tr");
	}

	public void openTableRow(int marker) throws IOException {
		if (marker == -1)
			attribute(NS_XHTML, "style", "font-weight: bold");
		else if (marker == 1)
			attribute(NS_XHTML, "style", "background-color:#E0E0E0");

		open(NS_XHTML, "tr");
	}

	public void closeTableRow() throws IOException, IOException {
		close(NS_XHTML, "tr");
	}
	
	public void openTableCell() throws IOException {
		openTableCell(null);
	}
	
	public void closeTableCell() throws IOException, IOException {
		close(NS_XHTML, "td");
	}
	
	public void openTableCell(String style) throws IOException {
		if (style != null)
			attribute(NS_XHTML, "style", style);
		open(NS_XHTML, "td");
	}

	public void openTableCell(int span, String style) throws IOException {
		attribute(NS_XHTML, "colspan", Integer.toString(span));
		if (style != null)
			attribute(NS_XHTML, "style", style);
		open(NS_XHTML, "td");
	}


	public void tableCellSpacer(int count, int colspan) throws IOException, IOException {
		tableCellSpacer(null, count, colspan);		
	}
	
	public void tableCellSpacer(int count) throws IOException, IOException {
		tableCellSpacer(null, count, NULL_INTEGER);		
	}
	
	public void tableCellSpacer(String style, int i) throws IOException, IOException {
		tableCellSpacer(style, i, NULL_INTEGER);
	}
	
	public void tableCellSpacer(String style, int i, int colspan) throws IOException, IOException {
		if (colspan != NULL_INTEGER)
			attribute(NS_XHTML, "colspan", Integer.toString(colspan));
		
		openTableCell(style);
		nonBreakingSpace(i);
		close();
	}

	public void tableCell(String content) throws IOException, IOException {
		tableCell(null, content);
	}

	public void tableCell(String style, String content, String hint) throws IOException, IOException {
		attribute(NS_XHTML, "title", hint);
		tableCell(style, content);
	}
	public void tableCell(String style, String content) throws IOException, IOException {
		openTableCell(style);
		if (content == null)
			nonBreakingSpace(1);
		else
			text(content);
		close();		
	}

	public void openBlockQuote() throws IOException {
		open(NS_XHTML, "blockQuote");	
	}

	public void closeBlockQuote() throws IOException, IOException {
		close(NS_XHTML, "blockQuote");	
	}

	public void openList(boolean ordered) throws IOException {
		open(NS_XHTML, ordered ? "ol" : "ul");
		
	}

	public void closeList(boolean ordered) throws IOException, IOException {
		close(NS_XHTML, ordered ? "ol" : "ul");
		
	}

	public void openItem() throws IOException {
		open(NS_XHTML, "li");
	}

	public void closeItem() throws IOException, IOException {
		close(NS_XHTML, "li");
	}

	public void openHtml() throws IOException {
		open(NS_XHTML, "html");
	}

	public void closeHtml() throws IOException, IOException {
		close(NS_XHTML, "html");
	}

	public void openHead() throws IOException {
		open(NS_XHTML, "head");
	}


	public void closeHead() throws IOException, IOException {
		close(NS_XHTML, "head");
	}

	public void title(String title) throws IOException {
		element(NS_XHTML, "title", title);
	}

	public void openBody() throws IOException {
		openBody(null);
	}

	public void openBody(String style) throws IOException {
		if (style != null)
			attribute(NS_XHTML, "style", style);
		open(NS_XHTML, "body");
	}


	public void closeBody() throws IOException, IOException {
		close(NS_XHTML, "body");
	}

	public void openFont(String color) throws IOException {
		attribute(NS_XHTML, "color", color);
		open(NS_XHTML, "font");
	}

	public void closeFont() throws IOException, IOException {
		close(NS_XHTML, "font");
	}

	public String getHeaderStyle() {
		return headerStyle;
	}

	public void setHeaderStyle(String headerStyle) {
		this.headerStyle = headerStyle;
	}

	public void image(String source) throws IOException {
		attribute(NS_XHTML, "src", source);
		element(NS_XHTML, "img", null);		
	}

	public void object(String source, String type, String altText, int width, int height) throws IOException {
		attribute(NS_XHTML, "data", source);
		attribute(NS_XHTML, "type", type);
		attribute(NS_XHTML, "width", Integer.toString(width));
		attribute(NS_XHTML, "height", Integer.toString(height));
		element(NS_XHTML, "object", altText);
	}

	public void hr(int i) throws IOException {
		attribute(NS_XHTML, "width", Integer.toString(i)+"%");
		element(NS_XHTML, "hr", null);
	}

	public void openParagraph() throws IOException {
		open(NS_XHTML, "p");
	}

	public void closeParagraph() throws IOException, IOException {
		close(NS_XHTML, "p");
	}

	public void openBold() throws IOException {
		open(NS_XHTML, "b");
	}

	public void closeBold() throws IOException, IOException {
		close(NS_XHTML, "b");
	}

	public void openItalic() throws IOException {
		open(NS_XHTML, "i");
	}

	public void closeItalic() throws IOException, IOException {
		close(NS_XHTML, "i");
	}

	public void anchorDest(String id) throws IOException {
		attribute(NS_XHTML, "name", id);
		element(NS_XHTML, "a", null);
	}

	public void horizontalRule()  throws IOException {
		element(NS_XHTML, "hr", null);
	}

	public void openStyle() throws IOException {
		open(NS_XHTML, "STYLE");		
	}

	public void closeStyle() throws IOException {
		close(NS_XHTML, "STYLE");		
	}

	public void openXhtmlTag(String tag) throws IOException {
		open(NS_XHTML, tag);
	}

	public void closeXhtmlTag(String tag) throws IOException {
		close(NS_XHTML, tag);
	}


}



