/**
 * Copyright (c) 2010, Werner Keil, emergn and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil, emergn and others - initial API and implementation
 */
package org.eclipse.uomo.core;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class Messages {
	private static final String BUNDLE_NAME = "org.eclipse.uomo.core.messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}