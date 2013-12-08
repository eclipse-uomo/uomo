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
package org.eclipse.uomo.examples.units.console.sandbox;

import org.eclipse.osgi.util.NLS;

final class SandboxMessages extends NLS {
	private static final String BUNDLE_NAME = SandboxMessages.class.getPackage().getName() + ".messages"; //$NON-NLS-1$
	public static String REP_100mR;
	public static String REP_1R;
	public static String REP_2dot5R;
	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, SandboxMessages.class);
	}

	private SandboxMessages() {
	}
}
