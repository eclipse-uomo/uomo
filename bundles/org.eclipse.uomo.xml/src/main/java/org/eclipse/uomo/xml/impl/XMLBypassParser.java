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
import org.xml.sax.SAXException;

class XMLBypassParser extends XMLObjectParser {

	public boolean hasText() throws SAXException {
		return true;
	}

	public boolean allowChildren() {
		return true;
	}

}
