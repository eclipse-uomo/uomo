/**
 * Copyright (c) 2005, 2013, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.util.numbers.impl;

import org.eclipse.uomo.util.numbers.ISpellCode;

/**
* The Spell Code implementation
* 
* @author Werner Keil
* 
* @see {@link ISpellCode}
* 
*/
final class SpellCode implements ISpellCode {
	/**
	 * Represents the known word itself.
	 */
	private final String myName;

	/**
	 * Represents the dedicated case-sensitive single-character code for the
	 * word.
	 */
	private final String myCode;

	/**
	 * Represents the numeric value of the word if it is a word representing a
	 * value.
	 */
	private final Long myValue;

	/**
	 * Gets the known word representing this code.
	 * 
	 * @return the human-readable word or name of the number.
	 */
	public String getName() {
		return myName;
	}

	/**
	 * Gets the dedicated case-sensitive single-character code for the word.
	 * 
	 * @return the code of the word.
	 */
	public String getCode() {
		return myCode;
	}

	/**
	 * Gets the numeric value of the word if it is a word representing a value.
	 * 
	 * @return the numeric value equivalent this word.
	 */
	public Long getValue() {
		return myValue;
	}

	/**
	 * Constructs a new SpellCode object for a non-numeric word (e.g., minus,
	 * and, comma or dash).
	 * 
	 * @param name
	 *            the word represented by this code object.
	 * @param code
	 *            the code of this word.
	 */
	public SpellCode(String name, String code) {
		myName = name;
		myCode = code;
		myValue = Long.valueOf(-1);
	}

	/**
	 * Constructs a new SpellCode object for a numeric word (e.g., one, ten,
	 * hundred, ...)
	 * 
	 * @param name
	 *            the word represented by this code object.
	 * @param code
	 *            the code of this word.
	 * @param value
	 *            the equivalent numeric value of this word.
	 */
	public SpellCode(String name, String code, long value) {
		myName = name;
		myCode = code;
		myValue = Long.valueOf(value);
	}
}
