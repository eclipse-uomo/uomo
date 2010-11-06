/**
 * 
 */
package org.eclipse.uomo.util.numbers;

class RealFormatOptions extends DecimalFormatOptions {
	
	/**
	 * null - exponent form is allowed
	 * false - exponent form is not allowed
	 * true - exponent form is required
	 * 
	 * TODO are these 3 options required, or is null and false the same?
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