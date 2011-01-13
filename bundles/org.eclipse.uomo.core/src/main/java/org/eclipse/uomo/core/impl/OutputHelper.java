/**
 * Copyright (c) 2005, 2010, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.core.impl;

/**
 * A static helper class, checking e.g. if some tests require optional console
 * output XXX this could have options for using a logging framework eventually
 * TODO change to Service (either OSGi LogService or separate service)
 * @version $Revision: 97 $, $Date: 2010-07-30 20:13:27 +0100 (Fr, 30 Jul 2010) $
 * @author Werner Keil
 */
public abstract class OutputHelper {
	public static final String CONSOLE_OUTPUT = "consoleOutput";

	public static final boolean isConsoleOutput() {
		return ("true".equals(System.getProperty(CONSOLE_OUTPUT)));
	}

	public static final void print(String message) {
		if (isConsoleOutput()) {
			System.out.print(message);
		}
	}

	public static final void println(String message) {
		if (isConsoleOutput()) {
			System.out.println(message);
		}
	}

	public static final void print(Object object) {
		print(String.valueOf(object));
	}

	public static final void println(Object object) {
		println(String.valueOf(object));
	}

	/**
	 * This is a Fantom-style convenience method for console output
	 */
	public static final void echo(Object obj) {
		println(obj);
	}

	/**
	 * This is a Fantom-style convenience method for console output
	 */
	public static final void echo(String str) {
		println(str);
	}
}
