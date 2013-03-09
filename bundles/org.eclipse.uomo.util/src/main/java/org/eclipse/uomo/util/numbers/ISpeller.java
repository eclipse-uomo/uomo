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


/**
 * @author Werner Keil
 *
 */
public interface ISpeller {
	/**
	 * Spells a number; converts a number to its equivalent read-out text
	 * string.
	 * 
	 * @param number
	 *            The number to be spelled.
	 * 
	 * @return The word by word read-out of the number - correctly spelled and
	 *         punctuated.
	 * 
	 * @throws SpellException
	 *             If this method throws an exception.
	 *             Please report it.
	 */
	public String spell(long number) throws SpellException;
}
