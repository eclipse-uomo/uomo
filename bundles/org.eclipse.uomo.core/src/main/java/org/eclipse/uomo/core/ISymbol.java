/*******************************************************************************
 * Copyright (c) 1996, 2010, Werner Keil, Creative Arts & Technologies.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Werner Keil, Creative Arts & Technologies - initial API and implementation
 *******************************************************************************/

package org.eclipse.uomo.core;

/**
 * Denote entity that has a symbol. 
 * This is true for units and similar entities.
 * 
 * @version $Id: INameAndSymbol.java 94 2010-07-30 18:36:25Z werner.keil $
 * @author <a href="mailto:oumo@catmedia.us">Werner Keil</a>
 */
public interface ISymbol {
	String getSymbol();
}