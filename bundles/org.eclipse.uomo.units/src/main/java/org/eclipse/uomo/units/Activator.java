/**
 * Copyright (c) 2005, 2011, Werner Keil, JScience and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.units;

import java.util.Hashtable;
import java.util.Locale;

import org.eclipse.uomo.units.impl.SystemOfUnitsServiceImpl;
import org.eclipse.uomo.units.impl.format.UnitFormatServiceImpl;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.util.tracker.ServiceTracker;
import org.unitsofmeasurement.service.UnitFormatService;
import org.unitsofmeasurement.service.SystemOfUnitsService;
import org.unitsofmeasurement.unit.SystemOfUnits;

/**
 * OSGi part of implementation.
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 0.5.2 ($Revision: 215 $), $Date: 2010-09-19 22:12:08 +0200 (So, 19 Sep 2010) $
 */
public class Activator implements BundleActivator, ServiceListener {
	private SystemOfUnitsService souService;
	private ServiceTracker souServiceTracker;
	private UnitFormatService formatService;
	private ServiceTracker formatServiceTracker;
	private BundleContext fContext;
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		fContext = context;
//		dictService = new DictionaryServiceImpl();
		
		@SuppressWarnings("rawtypes")
		Hashtable<?, ?> props = new Hashtable();
		// register the services
		startFormat(context, props);
		startSOU(context, props);
//		startDictionary(context, props);
	}
	
	private void startFormat(BundleContext context, Hashtable<?, ?> props) throws Exception {
		formatService = new UnitFormatServiceImpl();
		
		// register the service
		context.registerService(UnitFormatService.class.getName(), formatService, props);

		// create a tracker and track the service
		formatServiceTracker = new ServiceTracker(context, UnitFormatService.class.getName(), null);
		formatServiceTracker.open();

		// have a service listener to implement the whiteboard pattern
	    fContext.addServiceListener(this, "(objectclass=" + Locale.class.getName() + ")");
		
		// grab the service
//		formatService = (LocalFormatService) formatServiceTracker.getService();
		
		// register the locale
//		formatService.registerDictionary(new DictionaryImpl());
	}
	
	private void startSOU(BundleContext context, Hashtable<?, ?> props) throws Exception {
		souService = new SystemOfUnitsServiceImpl();
		
		// register the service
		context.registerService(SystemOfUnitsService.class.getName(), souService, props);

		// create a tracker and track the service
		souServiceTracker = new ServiceTracker(context, SystemOfUnitsService.class.getName(), null);
		souServiceTracker.open();

		// have a service listener to implement the whiteboard pattern
	    fContext.addServiceListener(this, "(objectclass=" + SystemOfUnits.class.getName() + ")");
		
		// grab the service
//		formatService = (LocalFormatService) formatServiceTracker.getService();
		
	}	

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		// close the service tracker(s)
		stopFormat();
		stopSOU();
//		stopDictionary();
		fContext = null;
	}
	
	private void stopFormat() throws Exception {
		formatServiceTracker.close();
		formatServiceTracker = null;

		formatService = null;
	}
	
	private void stopSOU() throws Exception {
		souServiceTracker.close();
		souServiceTracker = null;

		souService = null;
	}

	public void serviceChanged(ServiceEvent ev) {
//		ServiceReference sr = ev.getServiceReference();
		switch(ev.getType()) {
			case ServiceEvent.REGISTERED:
			{
//				Dictionary dictionary = (Dictionary) fContext.getService(sr);
//				dictService.registerDictionary(dictionary);
			}
			break;
			case ServiceEvent.UNREGISTERING:
			{
//				Dictionary dictionary = (Dictionary) fContext.getService(sr);
//				dictService.unregisterDictionary(dictionary);
			}
			break;
		}
	}

}
