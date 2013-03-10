/**
 * Copyright (c) 2009, 2013, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.util.numbers;

import org.eclipse.uomo.core.UOMoException;


/**
* @author Werner Keil
* 
*         A simple checked exception class.
* 
*/
public class SpellException extends UOMoException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1358891223155703756L;

	/**
	 * @param message
	 *            The message carried by this exception object
	 */
	public SpellException(String message) {
		super(message);
	}
}