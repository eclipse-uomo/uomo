/*
 * Backport, stub for JavaMoney 
 */
package org.eclipse.uomo.business.internal;

import java.util.Locale;

/**
 * This interface is implemented by types that are localiable for display.
 * 
 * @TODO check if this class can be moved to {@code java.util} or at least "format" module.
 * 
 * @author Werner Keil
 * @deprecated this is a stub, replacing it by JSR 354 as soon as it's in MavenCentral!
 */
public interface Localizable {

	/**
	 * Access a display name for the item, that can be shown for display.
	 * 
	 * @param locale
	 *            The {@link Locale} to be used.
	 * @return the formatted display name
	 */
	public String getDisplayName(Locale locale);

}
