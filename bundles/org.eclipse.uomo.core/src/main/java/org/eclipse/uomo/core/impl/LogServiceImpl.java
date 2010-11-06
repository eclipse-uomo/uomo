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

import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;

public class LogServiceImpl implements LogService {

	@Override
	public void log(int arg0, String arg1) {
		OutputHelper.print(arg1);
	}

	@Override
	public void log(int arg0, String arg1, Throwable arg2) {
		OutputHelper.print(arg1 + " " + arg2);
	}

	@Override
	public void log(ServiceReference arg0, int arg1, String arg2) {
		OutputHelper.print(arg2);
	}

	@Override
	public void log(ServiceReference arg0, int arg1, String arg2, Throwable arg3) {
		log(arg1, arg2, arg3);
	}

}
