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
package org.eclipse.uomo.core;

/**
 * @author <a href="mailto:oumo@catmedia.us">Werner Keil</a>
 * 
 * @param <V>
 *            the type to validate
 * 
 */
public interface IValidator<V> {
	public void validate(V v) throws UOMoException;
}
