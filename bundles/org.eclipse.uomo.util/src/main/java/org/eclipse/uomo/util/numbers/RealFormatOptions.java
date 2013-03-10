/**
 * Copyright (c) 2005, 2013, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Grahame Grieve - initial API and implementation
 */
package org.eclipse.uomo.util.numbers;

final class RealFormatOptions extends DecimalFormatOptions {
	
	/**
	 * null - exponent form is allowed
	 * false - exponent form is not allowed
	 * true - exponent form is required
	 * 
	 * FIXME change this to a more descriptive enum
	 */
	private Boolean exponent;
	private boolean allowSpecial;
	
	/**
	 * @param exponent
	 * @param totalDigits
	 * @param fractionDigits
	 */
	public RealFormatOptions(Boolean exponent, boolean allowSpecial, int totalDigits, int fractionDigits) {
		super();
		this.exponent = exponent;
		this.totalDigits = totalDigits;
		this.fractionDigits = fractionDigits;
		this.allowSpecial = allowSpecial;
	}

	/**
	 * @return the exponent
	 */
	public Boolean getExponent() {
		return exponent;
	}

	/**
	 * @param exponent the exponent to set
	 */
	public void setExponent(Boolean exponent) {
		this.exponent = exponent;
	}

	/**
	 * @return the allowSpecial
	 */
	public boolean isAllowSpecial() {
		return allowSpecial;
	}

	/**
	 * @param allowSpecial the allowSpecial to set
	 */
	public void setAllowSpecial(boolean allowSpecial) {
		this.allowSpecial = allowSpecial;
	}

	public static RealFormatOptions allowComplex() {
		return new RealFormatOptions(null, true, ANY_DIGITS, ANY_DIGITS);
	}
	
	
}