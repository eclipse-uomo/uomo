/******************************************************************************
 * Copyright (c) 1996, 2014, Werner Keil, Creative Arts & Technologies.
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
 * Denote entity that has a code.
 *
 * @version $Id: ICode.java 63 2010-07-19 10:35:31Z werner.keil $
 * @author  <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 */
public interface ICode<C> {
	C getCode();
}