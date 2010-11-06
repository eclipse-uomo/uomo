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

import org.xml.sax.SAXException;

public abstract class XMLStringParser extends XMLObjectParser {

	private final StringBuilder content = new StringBuilder();
	
	public void findText(char[] ch, int start, int length, boolean whitespace) throws SAXException {
		content.append(ch, start, length); 
	}

	public String getContent() {
		return content.toString();
	}

	public boolean hasText() throws SAXException {
		return true;
	}

}
