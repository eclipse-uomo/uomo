/**
 * Copyright (c) 1999, 2000, 2001, 2002, 2011, J. Paul Morrison and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Paul Morrison - initial API and implementation
 */
package org.eclipse.uomo.business.types;

import org.eclipse.uomo.core.UOMoRuntimeException;


/**
 * Basic Data Type Exception class
 * @author paul.morrison
 * @author werner.keil
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
