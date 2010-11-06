/*******************************************************************************
 * Copyright (c) 2010, Copyright (c) 2010 Creative Arts & Technologies.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Werner Keil, Creative Arts & Technologies - initial API and implementation
 *******************************************************************************/

package org.eclipse.uomo.core;

/**
 * @author  <a href="mailto:oumo@catmedia.us">Werner Keil</a>
 * @version 1.1, $Date: 2010-07-19 11:35:31 +0100 (Mo, 19 Jul 2010) $
 */
public class UOMoRuntimeException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5878541642974988679L;

	/**
	 * Constructs from an error message.
	 * 
	 * @param message
	 *            The error message.
	 */
	public UOMoRuntimeException(final String message) {
		super(message);
	}
	
	public UOMoRuntimeException(String message, Throwable causingException) {
		super(message, causingException);
	}

	public UOMoRuntimeException(Throwable causingException) {
		super(causingException);
	}
}
