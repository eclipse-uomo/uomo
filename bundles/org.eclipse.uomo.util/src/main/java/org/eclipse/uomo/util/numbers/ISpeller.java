/**
 * Copyright (c) 2009, 2017, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.util.numbers;

import tec.uom.lib.common.function.Parser;

/**
 * A tool for number spelling, text parsing and encoding.
 * 
 * @author Werner Keil
 * @version 1.1
 */
public interface ISpeller extends Parser<String, Number> {
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
	 *             If this method throws an exception. Please report it.
	 */
	public String spell(long number) throws SpellException;

	/**
	 * For public use: parses a human-readable spelling text of a number, and
	 * converts it to the corresponding numeric value.
	 * 
	 * @param text
	 *            the human-readable spelling text of a number.
	 * @return the numeric value corresponding to the human-readable number
	 *         text.
	 * @throws SpellException
	 *             if the text contains intolerable, misplaced or unknown word.
	 * 
	 * @see {@link #parseInternal(String)} which does a similar operation with
	 *      text not possibly started with word ''minus''. Actually, it does the
	 *      main operation.
	 */
	@Override
	public Number parse(String text) throws SpellException;
}
