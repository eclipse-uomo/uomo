/**
 * 
 */
package org.eclipse.uomo.util.numbers.impl;

import org.eclipse.uomo.core.ICode;
import org.eclipse.uomo.core.IName;
import org.eclipse.uomo.core.IValue;

/**
* In this project, we have used a unique way of encoding spelled number to a
* string. Each known word (or punctuation) in a spelled number string is
* converted to a case-sensitive single-character code. For example, the spelled
* number:
* 
* Two Thousand, Three Hundred and Nine (2,309)
* 
* is encoded as:
* 
* 2T,3I&9
* 
* In this encoding,
* 
* 2 represents the word: "two", T represents the word: :thousand", Comma (,)
* represents itself, 3 represents the word: "three", I represents the word:
* "hundred", ampersand (&) represents the word: "and", and finally, 9
* represents the word nine.
* 
* The great advantage of this encoding is that it would be fairly easy to
* validate a text as being a correct spelled number using regular expression.
* 
* SpellCode class is a way to hold encoding and recognition information for a
* single known word in spelling contexts. These are words like thousand,
* fourteen etc which all have a name (e.g., fourteen), a dedicated
* case-sensitive single-character code (U) and an associative value (14).
* 
* @author Werner Keil
* 
* @see {@link SpellContext#myCodes} the definition of myCodes array (a static
*      member of SpellContext class) for the codes uniquely proposed and used
*      in this project.
* @see {@link SpellContext#encode(String)}
* 
*/
final class SpellCode implements IName, ICode, IValue {
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
