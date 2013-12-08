/**
 * Copyright (c) 2005, 2013, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.examples.units.types;

import org.eclipse.osgi.util.NLS;

final class Messages extends NLS {
	
	private static final String BUNDLE_NAME = Messages.class.getPackage().getName() + ".messages"; //$NON-NLS-1$
	public static String Planet_Radius;
	public static String Planet_SurfaceGravity;
	public static String Planet_Usage;
	public static String Planet_SurfaceWeight;
	
	public static String BEAT;
	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
