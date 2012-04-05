package org.eclipse.uomo.util.numbers;

class DecimalFormatOptions {

	protected static final int ANY_DIGITS = -1;
	/**
	 * If totalDigits is specified, the number of digits must be less than or equal to totalDigits.
	 */
	protected int totalDigits;
	/**
	 * If fractionDigits is specified, the number of digits following the decimal point must be less than or equal to the fractionDigits 
	 */
	protected int fractionDigits;

	/**
	 * @return the totalDigits
	 */
	public int getTotalDigits() {
		return totalDigits;
	}

	/**
	 * @param totalDigits the totalDigits to set
	 */
	public void setTotalDigits(int totalDigits) {
		this.totalDigits = totalDigits;
	}

	/**
	 * @return the fractionDigits
	 */
	public int getFractionDigits() {
		return fractionDigits;
	}

	/**
	 * @param fractionDigits the fractionDigits to set
	 */
	public void setFractionDigits(int fractionDigits) {
		this.fractionDigits = fractionDigits;
	}

}
