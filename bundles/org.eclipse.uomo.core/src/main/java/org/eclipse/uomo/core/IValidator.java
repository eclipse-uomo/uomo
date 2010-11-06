/**
 * Copyright (c) 2005, 2010, Werner Keil, emergn and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil, emergn and others - initial API and implementation
 */
package org.eclipse.uomo.core;

import java.util.List;

/**
 * @author  <a href="mailto:oumo@catmedia.us">Werner Keil</a>
 *
 */
public interface IValidator {
    public List<String> validate();
}
