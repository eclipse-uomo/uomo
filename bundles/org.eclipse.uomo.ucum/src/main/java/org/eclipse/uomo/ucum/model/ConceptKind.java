/*******************************************************************************
 * Crown Copyright (c) 2006, 2011, Copyright (c) 2006, 2008 Kestral Computing P/L.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Kestral Computing P/L - initial implementation
 *    Werner Keil - added visible name
 *******************************************************************************/

package org.eclipse.uomo.ucum.model;

import org.eclipse.uomo.core.IVisibleName;

public enum ConceptKind implements IVisibleName {
	PREFIX("prefix"),
	BASEUNIT("base-unit"),
	UNIT("unit");
	
	private final String visName;
	
	private ConceptKind(String vName) {
		visName = vName;
	}
	
	public String visibleName() {
		return visName;
	}
}