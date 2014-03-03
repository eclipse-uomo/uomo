/*******************************************************************************
 * Crown Copyright (c) 2006, 2014, Copyright (c) 2006, 2007 Jiva Medical.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Jiva Medical - initial API and implementation
 *******************************************************************************/

package org.eclipse.uomo.core.impl;

import org.eclipse.uomo.core.ICode;
import org.eclipse.uomo.core.IValue;

/**
 * @author Werner Keil
 *
 * @param <V>
 * @param <C>
 */
public class CodeValuePair<V, C> implements ICode<C>, IValue<V> {

	private V value;
	private C code;
	/**
	 * @param value
	 * @param code
	 */
	public CodeValuePair(V value, C code) {
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
