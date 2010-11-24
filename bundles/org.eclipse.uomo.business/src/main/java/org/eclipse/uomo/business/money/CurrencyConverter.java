/**
 * Copyright (c) 2005, 2010, Werner Keil, Ikayzo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil, Jean-Marie Dautelle - initial API and implementation
 */
package org.eclipse.uomo.business.money;

import static java.util.FormattableFlags.LEFT_JUSTIFY;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Formattable;
import java.util.Formatter;
import java.util.List;

import org.eclipse.osgi.util.NLS;
import org.eclipse.uomo.business.internal.Messages;
import org.eclipse.uomo.units.AbstractConverter;
import org.unitsofmeasurement.unit.Unit;
import org.unitsofmeasurement.unit.UnitConverter;

import com.ibm.icu.util.Currency;
import com.ibm.icu.util.ULocale;

/**
 * <p>
 * This class represents a converter between two currencies.
 * </p>
 * 
 * <p>
 * Currency converters convert values based upon the current exchange rate
 * {@link Currency#getExchangeRate() exchange rate}. If the
 * {@link Currency#getExchangeRate() exchange rate} from the target currency to
 * the source currency is not set, conversion fails. In others words, the
 * converter from a currency <code>A</code> to a currency <code>B</code> is
 * independant from the converter from <code>B</code> to <code>A</code>.
 * </p>
 * 
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 5.2.2 ($Revision: 214 $), $Date: 2010-09-13 23:54:08 +0200 (Mo, 13 Sep 2010) $
 */
public class CurrencyConverter extends AbstractConverter implements Formattable {

	/**
     * 
     */
	private static final long serialVersionUID = -3943843029548497620L;

	/**
	 * Holds the exchange rate.
	 */
	private final ExchangeRate rate;

	private void checkFactor(Number factor) {
		if (factor == null)
			throw new UnsupportedOperationException(NLS.bind(
					Messages.CurrencyConverter_exchangeRate_not_set,
					rate.getSource(), rate.getTarget())); //$NON-NLS-1$
	}

	private Currency fromJDK(java.util.Currency currency) {
		return Currency.getInstance(currency.getCurrencyCode());
	}

	/**
	 * Creates the currency converter from the source currency to the target
	 * currency.
	 * 
	 * @param source
	 *            the source currency.
	 * @param target
	 *            the target currency.
	 * @param factor
	 *            the multiplier factor from source to target.
	 * @return the corresponding converter.
	 */
	private CurrencyConverter(Currency source, Currency target, Number factor) {
		rate = new ExchangeRate(source, target, factor);
	}

	/**
	 * Creates the currency converter from the source currency to the target
	 * currency.
	 * 
	 * @param source
	 *            the source currency.
	 * @param target
	 *            the target currency.
	 * @param factor
	 *            the multiplier factor from source to target.
	 * @return the corresponding converter.
	 */
	@SuppressWarnings("unchecked")
	public CurrencyConverter(CurrencyUnit<?> source, Unit<Money> target,
			Number factor) {
		if (target instanceof CurrencyUnit<?>) {
			rate = new ExchangeRate(source, (CurrencyUnit<Money>) target,
					factor);
		} else {
			Currency defCurrency = Currency.getInstance(ULocale.getDefault());
			rate = new ExchangeRate(defCurrency, defCurrency, factor);
		}
	}

	/**
	 * Creates the currency converter from the source currency to the target
	 * currency using <strong>JDK</strong> types.
	 * 
	 * @param source
	 *            the source currency (<strong>JDK</strong>).
	 * @param target
	 *            the target currency (<strong>JDK</strong>).
	 * @param factor
	 *            the multiplier factor from source to target.
	 * @return the corresponding converter.
	 */
	public CurrencyConverter(java.util.Currency source,
			java.util.Currency target, Number factor) {
		rate = new ExchangeRate(fromJDK(source), fromJDK(target), factor);
	}

	/**
	 * Returns the source currency.
	 * 
	 * @return the source currency.
	 */
	public Currency getSource() {
		return rate.getSource();
	}

	/**
	 * Returns the target currency.
	 * 
	 * @return the target currency.
	 */
	public Currency getTarget() {
		return rate.getTarget();
	}

	public CurrencyConverter inverse() {
		return new CurrencyConverter(rate.getTarget(), rate.getSource(),
				rate.getFactor());
	}

	public CurrencyConverter negate() {
		return new CurrencyConverter(rate.getSource(), rate.getTarget(), -rate
				.getFactor().doubleValue());
	}

	public double convert(double value) {
		// Number factor = getExchangeRate(rate.getTarget());
		Number factor = rate.getFactor();
		checkFactor(factor);
		return factor.doubleValue() * value;
	}

	public BigDecimal convert(BigDecimal value, MathContext ctx)
			throws ArithmeticException {
		// Number factor = rate.getSource().getExchangeRate(rate.getTarget());
		Number factor = rate.getFactor();
		checkFactor(factor);
		if (factor instanceof BigDecimal)
			return value.multiply((BigDecimal) factor, ctx);
		if (factor instanceof Number) {
			return value.multiply(
					(BigDecimal.valueOf(((Number) factor).doubleValue())), ctx);
		} else { // Reverts to double convert.
			return value
					.multiply(BigDecimal.valueOf(factor.doubleValue()), ctx);
		}
	}

	public Number convert(Number value) {
		if (value instanceof BigDecimal) {
			return convert((BigDecimal) value, MathContext.DECIMAL128);
		} else {
			return convert(value.doubleValue());
		}
	}

	@Override
	public boolean equals(Object cvtr) {
		if (!(cvtr instanceof CurrencyConverter))
			return false;
		CurrencyConverter that = (CurrencyConverter) cvtr;
		return this.rate.getSource().equals(that.rate.getSource())
				&& this.rate.getTarget().equals(that.rate.getTarget());
	}

	@Override
	public int hashCode() {
		return rate.getSource().hashCode() + rate.getTarget().hashCode();
	}

	@Override
	public final String toString() {
		return String.format(Messages.CurrencyConverter_toString, getSource()
				.getSymbol(), getTarget().getSymbol());
	}

	public boolean isLinear() {
		return true;
	}

	public UnitConverter concatenate(UnitConverter converter) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<UnitConverter> getCompoundConverters() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isIdentity() {
		return false;
	}

	public ExchangeRate getExchangeRate() {
		return rate;
	}

	public void formatTo(Formatter fmt, int f, int width, int precision) {
		StringBuilder sb = new StringBuilder();

		// decide form of name
		String name = getSource().toString();
		String symbol = getSource().getSymbol();
		// if (fmt.locale().equals(Locale.FRANCE))
		// name = frenchCompanyName;
		// boolean alternate = (f & ALTERNATE) == ALTERNATE;
		boolean usesymbol = true; // alternate || (precision != -1 && precision
									// < 10);
		String out = (usesymbol ? symbol : name);

		// apply precision
		if (precision == -1 || out.length() < precision) {
			// write it all
			sb.append(out);
		} else {
			sb.append(out.substring(0, precision - 1)).append('*');
		}

		// apply width and justification
		int len = sb.length();
		if (len < width)
			for (int i = 0; i < width - len; i++)
				if ((f & LEFT_JUSTIFY) == LEFT_JUSTIFY)
					sb.append(' ');
				else
					sb.insert(0, ' ');

		fmt.format(sb.toString());
	}
}
