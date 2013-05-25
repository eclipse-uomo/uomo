package org.eclipse.uomo.business.money;

import static org.junit.Assert.*;

import java.math.MathContext;

import org.eclipse.uomo.business.internal.CurrencyUnit;
import org.junit.Test;

import com.ibm.icu.math.BigDecimal;

public class ConverterTest {

	@Test
	public void testConvertDouble() {
		MoneyUnit currency1 = MoneyUnit.of("USD");
		MoneyUnit currency2 = MoneyUnit.of("CHF");
		MoneyConverter converter = new MoneyConverter(currency1, currency2, 
				BigDecimal.valueOf(0.961325d));
		double result = converter.convert(10d);
		assertEquals("USD", converter.getSource().getCurrencyCode());
		assertEquals("CHF", converter.getTarget().getCurrencyCode());
		assertEquals(9.61325d, result, 0);
	}

	@Test
	public void testConvertBigDecimalMathContext() {
		MoneyUnit currency1 = MoneyUnit.of("USD");
		MoneyUnit currency2 = MoneyUnit.of("CHF");
		MoneyConverter converter = new MoneyConverter(currency1, currency2, 
				BigDecimal.valueOf(0.961325d));
		java.math.BigDecimal result = converter.convert(BigDecimal.TEN.toBigDecimal(),
				MathContext.DECIMAL128);
		assertEquals("USD", converter.getSource().getCurrencyCode());
		assertEquals("CHF", converter.getTarget().getCurrencyCode());
		assertEquals(new BigDecimal("9.613250").toBigDecimal(), result);
	}

	@Test
	public void testConvertNumber() {
		MoneyUnit currency1 = MoneyUnit.of("USD");
		MoneyUnit currency2 = MoneyUnit.of("CHF");
		MoneyConverter converter = new MoneyConverter(currency1, currency2, 
				BigDecimal.valueOf(0.961325d));
		Number result = converter.convert(BigDecimal.TEN);
		assertEquals("USD", converter.getSource().getCurrencyCode());
		assertEquals("CHF", converter.getTarget().getCurrencyCode());
		assertEquals(Double.valueOf(9.61325d), result);
	}

}
