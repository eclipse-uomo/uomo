/*******************************************************************************
 * Crown Copyright (c) 2006, 2007, Copyright (c) 2006, 2007 Jiva Medical.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Jiva Medical - initial API and implementation
 *******************************************************************************/

package org.eclipse.ohf.utilities;

import org.eclipse.uomo.core.UOMoException;


/**
 * @author Werner Keil
 * @deprecated for backwards compatibility
 */
public class OHFException extends UOMoException {

	private static final long serialVersionUID = 3333261489669700628L;

	public OHFException(String message) {
		super(message);
	}

	public OHFException(String message, Throwable causingException) {
		super(message, causingException);
	}

	public OHFException(Throwable causingException) {
		super(causingException);
	}

}
