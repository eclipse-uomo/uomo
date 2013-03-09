/**
 * Copyright (c) 2011, 2013 Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.util;

import org.eclipse.uomo.core.UOMoException;

/**
 * @author  <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 *
 * @param <I> the input
 * @param <O> the output
 */
public interface Parser<I, O> {
	public O parse(String s) throws UOMoException;
}
