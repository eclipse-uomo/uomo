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

import org.eclipse.uomo.xml.XMLMessages;
import org.eclipse.uomo.xml.XMLObjectParser;
import org.xml.sax.SAXException;

public class XMLObjectParsers {

	private int stackTop = -1;
	private int stackSize = 10;
	private XMLObjectParser[] stack = new XMLObjectParser[10];

	private void grow () {
		XMLObjectParser[] temp = stack;
		stackSize = stackSize + 10;
		stack = new XMLObjectParser[stackSize];
		for (int i = 0; i < stackSize - 10; i++)
			stack[i] = temp[i];		
	}
	
	public void push(XMLObjectParser handler) {
		if (stackTop == stackSize -1)
			grow();
		stackTop++;
		stack[stackTop] = handler;
	}
	
	public void pop () throws SAXException {
		if (stackTop == -1)
			throw new SAXException(XMLMessages.XMLObjectParsers_stackUnderflow);
		stackTop--;
	}
	
	public XMLObjectParser current () throws SAXException {
		if (stackTop == -1)
			throw new SAXException(XMLMessages.XMLObjectParsers_stackUnderflow); //$NON-NLS-1$
		return stack[stackTop];
	}

	public boolean hasCurrent() {
		return stackTop >= 0;
	}
}
