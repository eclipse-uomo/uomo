/**
 * Copyright (c) 2010, Werner Keil, emergn and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.core.impl;

import java.util.Hashtable;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;

/**
 * OSGi part of implementation.
 * 
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.2 ($Revision$), $Date$
 */
public class Activator implements BundleActivator, ServiceListener {
	private ServiceTracker logServiceTracker;
	private BundleContext fContext;
	private LogService logService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		fContext = context;

		@SuppressWarnings("rawtypes")
		Hashtable<?, ?> props = new Hashtable();
		// register the services
		startLog(context, props);
	}

	private void startLog(BundleContext context, Hashtable<?, ?> props)
			throws Exception {
		logService = new LogServiceImpl();

		// register the service
		context.registerService(LogService.class.getName(), logService,
				props);

		// create a tracker and track the service
		logServiceTracker = new ServiceTracker(context,
				LogService.class.getName(), null);
		logServiceTracker.open();

		// have a service listener to implement the whiteboard pattern
		fContext.addServiceListener(this,
				"(objectclass=" + LogService.class.getName() + ")");

		// grab the service
		logService = (LogService) logServiceTracker.getService();

		// register the dictionary
//		logService.registerDictionary(new DictionaryImpl());
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

	// @Deprecated
	// private void stopDictionary() throws Exception {
	// logServiceTracker.close();
	// logServiceTracker = null;
	//
	// logService = null;
	// }

	public void serviceChanged(ServiceEvent ev) {
		ServiceReference sr = ev.getServiceReference();
		if (ev != null) {
			switch (ev.getType()) {
			// case ServiceEvent.REGISTERED: {
			// Dictionary dictionary = (Dictionary) fContext.getService(sr);
			// logService.registerDictionary(dictionary);
			// }
			// break;
			// case ServiceEvent.UNREGISTERING: {
			// Dictionary dictionary = (Dictionary) fContext.getService(sr);
			// logService.unregisterDictionary(dictionary);
			// }
			default:
				break;
			}
		}
	}

}
