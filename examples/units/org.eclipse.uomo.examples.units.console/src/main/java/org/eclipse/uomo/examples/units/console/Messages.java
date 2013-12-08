/**
 * Copyright (c) 2005, 2011, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.examples.units.console;

import org.eclipse.osgi.util.NLS;

final class Messages extends NLS {
	private static final String BUNDLE_NAME = Messages.class.getPackage().getName() + ".messages"; //$NON-NLS-1$
	public static String Demo_0;
	public static String Demo_1;
	public static String Demo_10;
	public static String Demo_11;
	public static String Demo_2;
	public static String Demo_3;
	public static String Demo_4;
	public static String Demo_5;
	public static String Demo_6;
	public static String Demo_7;
	public static String Demo_8;
	public static String Demo_9;
	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
