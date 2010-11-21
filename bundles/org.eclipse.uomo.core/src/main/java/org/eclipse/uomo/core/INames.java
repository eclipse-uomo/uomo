/******************************************************************************
 * Copyright (c) 2005, 2010, Werner Keil, Creative Arts & Technologies.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Werner Keil, Creative Arts & Technologies - initial API and implementation
 *****************************************************************************/

package org.eclipse.uomo.core;

import java.util.List;

/**
 * Denote entity that has a list of names.
 * 
 * @version $Id: IName.java 152 2010-08-22 20:48:31Z werner.keil $
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 */
public interface INames {
	List<String> getNames();
}