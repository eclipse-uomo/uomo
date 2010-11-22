/******************************************************************************
 * Copyright (c) 1996, 2010, Werner Keil, Creative Arts & Technologies.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Werner Keil, Creative Arts & Technologies - initial API and implementation
 *****************************************************************************/

package org.eclipse.uomo.core;

/**
 * Denote entity that has both name and symbol. This is true for units and
 * similar entities.
 * @deprecated simple enough to use IName and ISymbol together
 * @version $Id: INameAndSymbol.java 63 2010-07-19 10:35:31Z werner.keil $
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 */
public interface INameAndSymbol extends IName, ISymbol {
}