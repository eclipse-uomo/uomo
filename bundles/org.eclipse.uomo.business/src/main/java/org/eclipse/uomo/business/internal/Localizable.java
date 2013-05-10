/*
 *  Copyright (c) 2012, 2013, Werner Keil, Anatole Tresch.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.eclipse.uomo.business.internal;

import java.util.Locale;

/**
 * This interface is implemented by types that are localiable for display.
 * 
 * @TODO check if this class can be moved to {@code java.util} or at least "format" module.
 * 
 * @author Anatole Tresch
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
