/**
 * Copyright (c) 2005, 2013, Werner Keil, Unit-API and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.core;

import java.util.List;

/**
 * @author  <a href="mailto:oumo@catmedia.us">Werner Keil</a>
 * 
 * @param <V> the validated type
 *
 */
public interface IListValidator<V> {
    public List<V> validate();
}
