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

/**
 * SpellService is main engine for number spelling, text parsing and encoding
 * and validating.
 * 
 * @author Werner Keil
 * @version 1.1
 */
public interface SpellService {

	/**
	 * Register a speller
	 * 
	 * @param speller
	 *            the speller to be added.
	 */
	public void registerSpeller(ISpeller speller);

	/**
	 * Remove a speller
	 * 
	 * @param speller
	 *            the speller to be removed.
	 */
	public void unregisterSpeller(ISpeller dictionary);
}
