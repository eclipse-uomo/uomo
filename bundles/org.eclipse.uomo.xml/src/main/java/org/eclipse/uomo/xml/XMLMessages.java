package org.eclipse.uomo.xml;

import org.eclipse.osgi.util.NLS;

public final class XMLMessages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.uomo.xml.messages"; //$NON-NLS-1$
	public static String XMLObjectParsers_stackUnderflow;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, XMLMessages.class);
	}

	private XMLMessages() {
	}
}
