/**
 * Copyright (c) 2011, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.util;

/**
 * @author  <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * 
 * @param <V> the type of mapped values
 */
public interface Registry<V> {
	public boolean exists(String key);

	public V get(String key);	
}
