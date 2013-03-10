package org.eclipse.uomo.util.internal;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.eclipse.osgi.util.NLS;

public class ErrorMessages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.uomo.util.errors"; //$NON-NLS-1$
	private static ResourceBundle RESOURCE_BUNDLE;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, ErrorMessages.class);
		RESOURCE_BUNDLE = ResourceBundle
				.getBundle(BUNDLE_NAME);
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	
	private ErrorMessages() {
	}
}
