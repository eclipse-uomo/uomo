package org.eclipse.uomo.util.numbers;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;


/**
 * This provides services directly targeted at supporting messages like HL7. The formats are
 * not localized
 * 
 * @author Grahame Grieve
 * @author Werner Keil
 * @version 1.1
 * 
 */
public abstract class NumberFormatUtilities {

	/**
	 * validate that a string is a valid real number. validates the entire
	 * source string.
	 * 
	 * this accepts either xs:double or xs:decimal
	 * 
	 * @param source
	 *            - the source to check
	 * @param options
	 *            - may be null
	 * @return null if valid, otherwise an error
	 */
	public static String validateReal(String source, RealFormatOptions options) {
		try {
			new NumberValidator(source, options).validateReal();
			return null;
		} catch (NumberFormatException e) {
			return e.getMessage();
		}
	}

	/**
	 * convert a real number to a BigDecimal
	 * 
	 * @param source
	 * @return
	 * @throws UOMoNumberFormatException
	 */
	public static BigDecimal parseReal(String source, RealFormatOptions options)
			throws UOMoNumberFormatException {
		return new NumberValidator(source, null).parseReal();
	}

	@Deprecated
	public static String formatReal(BigDecimal num) {
		return NumberFormat.getInstance(Locale.US).format(num);
	}

	@Deprecated
	public static String formatReal(Double num) {
		return NumberFormat.getInstance(Locale.US).format(num);
	}

	public static String validateInteger(String source) {
		try {
			new NumberValidator(source, null).validateInteger();
			return null;
		} catch (NumberFormatException e) {
			return e.getMessage();
		}
	}

	public static long parseInteger(String source)
			throws UOMoNumberFormatException {
		return new NumberValidator(source, null).parseInteger();
	}

	public static String formatInteger(Long num) {
		return NumberFormat.getInstance(Locale.US).format(num.longValue());
	}

	public static String formatInteger(long num) {
		return NumberFormat.getInstance(Locale.US).format(num);
	}

	public static String validateDecimal(String source,
			DecimalFormatOptions options) {
		try {
			new NumberValidator(source, options).validateDecimal();
			return null;
		} catch (NumberFormatException e) {
			return e.getMessage();
		}
	}

	public static BigDecimal parseDecimal(String source,
			DecimalFormatOptions options) throws UOMoNumberFormatException {
		return new NumberValidator(source, null).parseDecimal();
	}

	public static String formatDecimal(BigDecimal num) {
		return NumberFormat.getInstance(Locale.US).format(num);
	}

}
