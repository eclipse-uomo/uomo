/**
 * Copyright (c) 2005, 2011, Werner Keil, Ikayzo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.icu.types;

import org.eclipse.uomo.core.UOMoRuntimeException;


/**
 * Basic Data Type Exception class
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @deprecated Try using UOMoRuntimeException directly
 */
public class BDTypeException extends UOMoRuntimeException  {
	final static long serialVersionUID = 362498820763181265L;
/**
 * BDTypeException constructor with String.
 */
public BDTypeException(String s)  {
	super(s);
	
}
}
