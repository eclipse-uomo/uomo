/**
 * Copyright (c) 2005, 2017, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.util.numbers.impl;

import tec.uom.lib.common.function.Nameable;

/**
* A simple class to hold the regular expression sub-definition to validate
* encoded spelled number.
* 
* Remarks: The pattern used to validate the encoded spelled number is long and
* difficult to be directly created manually. We have used an incremental
* expandable pattern with $(variable) notation and recursive expansion.
* 
* @author Werner Keil
* 
* @see {@link SpellContext#generatePattern(String)}
* @see {@link SpellContext#mySpellPatterns}
* 
*/
class PatternDefinition implements Nameable {

	/**
	 * Represents the name of pattern, for example teen in $(teen).
	 */
	private String myName;

	/**
	 * Represents the packed (unexpanded) definition of pattern.
	 */
	private String myPackedDefinition;

	/**
	 * Constructs a new PatternDefinition object with the given name and packed
	 * definition
	 * 
	 * @param name
	 *            The name or ID of pattern.
	 * @param packedDefinition
	 *            The packed (unexpanded) value of pattern.
	 * 
	 */
	public PatternDefinition(String name, String packedDefinition) {
		this.myName = name;
		this.myPackedDefinition = packedDefinition;
	}

	/**
	 * Gets the name or ID of the pattern variable like varname in $(varname).
	 * 
	 * @return the name or ID of pattern.
	 */
	public String getName() {
		return myName;
	}

	/**
	 * Gets the packed (unexpanded) value or definition of pattern.
	 * 
	 * @return the packed (unexpanded) value or definition of pattern.
	 */
	public String getPackedDefinition() {
		return myPackedDefinition;
	}
}
