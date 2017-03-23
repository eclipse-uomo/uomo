/**
 * Copyright (c) 2010, 2012 Werner Keil, JScience and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.core.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * OSGi part of implementation.
 * 
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 0.3 ($Revision$), $Date$
 */
public class Activator implements BundleActivator {
	private BundleContext fContext;
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		fContext = context;
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		// close the service tracker(s)
		// stopDictionary();
		fContext = null;
	}

}
