/**
 * Copyright (c) 2005, 2013, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API
 */
package org.eclipse.uomo.util.numbers;

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
public interface ISpellCode extends IName, ICode<String>, IValue<Number> {
}
