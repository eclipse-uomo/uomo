/*******************************************************************************
 * Crown Copyright (c) 2006, 2011, Copyright (c) 2006, 2007 Jiva Medical.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Jiva Medical - initial API and implementation
 *******************************************************************************/

package org.eclipse.uomo.core.impl;

// TODO make this really generic, like Pair<F,S>
/**
 * @author Werner Keil
 *
 * @param <V>
 * @param <C>
 */
public class Pair<V, C> {

	private V value;
	private C code;
	/**
	 * @param value
	 * @param code
	 */
	public Pair(V value, C code) {
		super();
		this.value = value;
		this.code = code;
	}
	/**
	 * @return the value
	 */
	public V getValue() {
		return value;
	}
	/**
	 * @return the code
	 */
	public C getCode() {
		return code;
	}	
}
