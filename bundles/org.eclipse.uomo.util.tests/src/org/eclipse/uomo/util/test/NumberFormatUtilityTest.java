package org.eclipse.uomo.util.test;

import static org.eclipse.uomo.core.UOMoNumberFormatException.Kind.*;
import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Locale;

import org.eclipse.uomo.core.UOMoNumberFormatException;
import org.eclipse.uomo.core.UOMoNumberFormatException.Kind;
import org.eclipse.uomo.util.numbers.NumberFormatUtilities;
import org.junit.Ignore;
import org.junit.Test;

public class NumberFormatUtilityTest {

	@Test
	public void testInteger() {
		assertValidInt("0");
		assertValidInt("-0");
		assertValidInt("+0");
		assertValidInt("1");
		assertValidInt("-1");
		assertValidInt("10000000000000000000000000000000000000000000000009000");

		assertInvalidInt("NaN");
		assertInvalidInt("INF");
		assertInvalidInt("+INF");
		assertInvalidInt("-INF");
		assertInvalidInt("1.0e0");
		assertInvalidInt("1.0e-0");
		assertInvalidInt("1.0e-1");
		assertInvalidInt("1.0e1");
		assertInvalidInt("1.0e10000000000000000000000000000000000000000000000000000000");
		assertValidInt("12678967543233");
		assertValidInt("+100000");
		assertInvalidInt("-1E4");
		assertInvalidInt("1267.43233E12");
		assertInvalidInt("12.78e-2");
		assertValidInt("12");
		assertInvalidInt("-1.23");
		assertInvalidInt("012.78e-2");
		assertValidInt("012");
		assertInvalidInt("-01.23");
		assertInvalidInt("12678967.543233");
		assertInvalidInt("+100000.00");
		assertInvalidInt("+100,000");
		assertValidInt("210");

		assertInvalidInt("-");
		assertInvalidInt("-a");
		assertInvalidInt("-b");
		assertInvalidInt(".");
		assertInvalidInt("+.");
		assertInvalidInt("-.");
		assertInvalidInt(".0");
		assertInvalidInt("+.0");
		assertInvalidInt("-.0");

		assertInvalidInt("0e0");
		assertInvalidInt("e0");
		assertInvalidInt("0e");

		assertParseInt("0", 0);
		assertParseInt("-0", 0);
		assertParseInt("+0", 0);
		assertParseInt("1", 1);
		assertParseInt("-1", -1);
		assertParseFailInt("10000000000000000000000000000000000000000000000009000");
		assertParseFailInt("-10000000000000000000000000000000000000000000000000090");
		assertParseFailInt("1.0e10000000000000000000000000000000000000000000000000000000");
		assertParseFailInt("12678967543233");
		assertParseInt("126789673", 126789673);
		assertParseInt("+100000", 100000);
		assertParseInt("12", 12);
		assertParseInt("012", 12);
		assertParseInt("210", 210);
	}

	@Test
	@Ignore
	public void _estReal() {
		checkParse("0", new BigDecimal("0"));
		checkParseInvalid("e", TEXT_FORMAT);
		checkParse("-0", new BigDecimal("-0"));
		checkParse("+0", new BigDecimal("+0"));
		checkParse("1", new BigDecimal("1"));
		checkParse("-1", new BigDecimal("-1"));
		checkParse(
				"10000000000000000000000000000000000000000000000009000.000000000000000000000000000000000000000000000000",
				new BigDecimal(
						"10000000000000000000000000000000000000000000000009000.000000000000000000000000000000000000000000000000"));
		checkParse(
				"-10000000000000000000000000000000000000000000000000090.000000000000000000000000000000000000000000000090",
				new BigDecimal(
						"-10000000000000000000000000000000000000000000000000090.000000000000000000000000000000000000000000000090"));
		checkParseInvalid("NaN", NaN);
		checkParseInvalid("INF", PINF);
		checkParseInvalid("+INF", PINF);
		checkParseInvalid("-INF", NINF);
		checkParse("1.0e0", new BigDecimal("1.0e0"));
		checkParse("1.0e-0", new BigDecimal("1.0e-0"));
		checkParse("1.0e-1", new BigDecimal("1.0e-1"));
		checkParse("1.0e1", new BigDecimal("1.0e1"));
		checkParseInvalid(
				"1.0e10000000000000000000000000000000000000000000000000000000",
				UOMoNumberFormatException.Kind.SIZE);
		checkParse("12678967543233", new BigDecimal("12678967543233"));
		checkParse("+100000", new BigDecimal("+100000"));
		checkParse("-1E4", new BigDecimal("-1E4"));
		checkParse("1267.43233E12", new BigDecimal("1267.43233E12"));
		checkParse("12.78e-2", new BigDecimal("12.78e-2"));
		checkParse("12", new BigDecimal("12"));
		checkParse("-1.23", new BigDecimal("-1.23"));
		checkParse("012.78e-2", new BigDecimal("012.78e-2"));
		checkParse("012", new BigDecimal("012"));
		checkParse("-01.23", new BigDecimal("-01.23"));
		checkParse("12678967.543233", new BigDecimal("12678967.543233"));
		checkParse("+100000.00", new BigDecimal("+100000.00"));
		checkParse("210", new BigDecimal("210"));

		checkParseInvalid("-", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid("-a", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid("-b", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid(".", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid("+.", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid("-.", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid(".0", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid("+.0", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid("-.0", UOMoNumberFormatException.Kind.TEXT_FORMAT);

		checkParse("0e0", new BigDecimal("0e0"));
		checkParseInvalid("e0", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid("0e", UOMoNumberFormatException.Kind.TEXT_FORMAT);

		Locale.setDefault(Locale.GERMANY);

		checkParse("0", new BigDecimal("0"));
		checkParseInvalid("e", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParse("-0", new BigDecimal("-0"));
		checkParse("+0", new BigDecimal("+0"));
		checkParse("1", new BigDecimal("1"));
		checkParse("-1", new BigDecimal("-1"));
		checkParse(
				"10000000000000000000000000000000000000000000000009000.000000000000000000000000000000000000000000000000",
				new BigDecimal(
						"10000000000000000000000000000000000000000000000009000.000000000000000000000000000000000000000000000000"));
		checkParse(
				"-10000000000000000000000000000000000000000000000000090.000000000000000000000000000000000000000000000090",
				new BigDecimal(
						"-10000000000000000000000000000000000000000000000000090.000000000000000000000000000000000000000000000090"));
		checkParseInvalid("NaN", UOMoNumberFormatException.Kind.NaN);
		checkParseInvalid("INF", UOMoNumberFormatException.Kind.PINF);
		checkParseInvalid("+INF", UOMoNumberFormatException.Kind.PINF);
		checkParseInvalid("-INF", UOMoNumberFormatException.Kind.NINF);
		checkParse("1.0e0", new BigDecimal("1.0e0"));
		checkParse("1.0e-0", new BigDecimal("1.0e-0"));
		checkParse("1.0e-1", new BigDecimal("1.0e-1"));
		checkParse("1.0e1", new BigDecimal("1.0e1"));
		checkParseInvalid(
				"1.0e10000000000000000000000000000000000000000000000000000000",
				UOMoNumberFormatException.Kind.SIZE);
		checkParse("12678967543233", new BigDecimal("12678967543233"));
		checkParse("+100000", new BigDecimal("+100000"));
		checkParse("-1E4", new BigDecimal("-1E4"));
		checkParse("1267.43233E12", new BigDecimal("1267.43233E12"));
		checkParse("12.78e-2", new BigDecimal("12.78e-2"));
		checkParse("12", new BigDecimal("12"));
		checkParse("-1.23", new BigDecimal("-1.23"));
		checkParse("12678967.543233", new BigDecimal("12678967.543233"));
		checkParse("+100000.00", new BigDecimal("+100000.00"));
		checkParse("210", new BigDecimal("210"));

		checkParseInvalid("", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid(null, UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid("-", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid("-a", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid("-b", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid(".", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid("+.", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid("-.", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid(".0", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid("+.0", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid("-.0", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid("1/2", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid("1.", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid("0.", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid("a 12", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid(" 12", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid(" 12 ", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid(" 12 a", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid(" 12a", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid(" 1.2e2", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid(" 1.2e2 ", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid(" 1.2e2 a",
				UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid(" 1.2e2a", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid("1 .2e2", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid("1. 2e2", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid("1.2 e2", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid("1.2e 2", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid("1.2e2 ", UOMoNumberFormatException.Kind.TEXT_FORMAT);

		checkParse("0e0", new BigDecimal("0e0"));
		checkParse("0e00", new BigDecimal("0e00"));
		checkParseInvalid("e0", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid("0e", UOMoNumberFormatException.Kind.TEXT_FORMAT);
		checkParseInvalid("1e0.2", UOMoNumberFormatException.Kind.TEXT_FORMAT);

		checkParse("0", new BigDecimal("0"));
		checkParse("-0", new BigDecimal("-0"));
		checkParse("+0", new BigDecimal("+0"));
		checkParse("1", new BigDecimal("1"));
		checkParse("-1", new BigDecimal("-1"));
		checkParse(
				"10000000000000000000000000000000000000000000000009000.000000000000000000000000000000000000000000000000",
				new BigDecimal(
						"10000000000000000000000000000000000000000000000009000.000000000000000000000000000000000000000000000000"));
		checkParse(
				"-10000000000000000000000000000000000000000000000000090.000000000000000000000000000000000000000000000090",
				new BigDecimal(
						"-10000000000000000000000000000000000000000000000000090.000000000000000000000000000000000000000000000090"));
		checkParseInvalid("NaN", UOMoNumberFormatException.Kind.NaN);
		checkParseInvalid("INF", UOMoNumberFormatException.Kind.PINF);
		checkParseInvalid("+INF", UOMoNumberFormatException.Kind.PINF);
		checkParseInvalid("-INF", UOMoNumberFormatException.Kind.NINF);
		checkParse("1.0e0", new BigDecimal("1.0e0"));
		checkParse("1.0e-0", new BigDecimal("1.0e-0"));
		checkParse("1.0e2", new BigDecimal("1.0e2"));
		checkParse("1.0e-1", new BigDecimal("1.0e-1"));
		checkParse("1.0e1", new BigDecimal("1.0e1"));
		checkParseInvalid(
				"1.0e10000000000000000000000000000000000000000000000000000000",
				UOMoNumberFormatException.Kind.SIZE);
		checkParse("12678967543233", new BigDecimal("12678967543233"));
		checkParse("126789673", new BigDecimal("126789673"));
		checkParse("+100000", new BigDecimal("+100000"));
		checkParse("-1E4", new BigDecimal("-1E4"));
		checkParse("1E-4", new BigDecimal("1E-4"));
		checkParse("1267.43233E12", new BigDecimal("1267.43233E12"));
		checkParse("12.78e-2", new BigDecimal("12.78e-2"));
		checkParse("12", new BigDecimal("12"));
		checkParse("-1.23", new BigDecimal("-1.23"));
		checkParse("012.78e-2", new BigDecimal("012.78e-2"));
		checkParse("012", new BigDecimal("012"));
		checkParse("-01.23", new BigDecimal("-01.23"));
		checkParse("12678967.543233", new BigDecimal("12678967.543233"));
		checkParse("+100000.00", new BigDecimal("+100000.00"));
		checkParse("210", new BigDecimal("210"));
		checkParse("0e0", new BigDecimal("0e0"));

	}

	private void checkParse(String source, BigDecimal d) {
		try {
			BigDecimal v = NumberFormatUtilities.parseReal(source, null);
			assertTrue("Failed to parse " + source + " (" + d.toPlainString()
					+ "/" + v.toPlainString() + ")", v.equals(d));
		} catch (UOMoNumberFormatException e) {
			fail("Failed to parse " + source + ": " + e.getMessage() + " ["
					+ e.getMessageForKind() + "]");
		}
	}

	private void checkParseInvalid(String source, Kind kind) {
		try {
			NumberFormatUtilities.parseReal(source, null);
			fail("unexpectedly ok: '" + source + "'");
		} catch (UOMoNumberFormatException e) {
			assertTrue(
					"parsing failed for " + source + ", message = "
							+ e.getMessage() + ", should be "
							+ UOMoNumberFormatException.getMessageForKind(kind)
							+ " but was " + e.getMessageForKind(),
					e.getKind() == kind);
		}
	}

	private void assertValidInt(String source) {
		String v = NumberFormatUtilities.validateInteger(source);
		assertTrue(source + ": unexpected error: " + v, v == null);
	}

	private void assertInvalidInt(String source) {
		assertTrue("unexpectedly ok: '" + source + "'",
				NumberFormatUtilities.validateInteger(source) != null);
	}

	private void assertParseFailInt(String source) {
		try {
			NumberFormatUtilities.parseInteger(source);
		} catch (NumberFormatException e) {
			// that's ok
		}
	}

	private void assertParseInt(String source, long d) {
		try {
			long v = NumberFormatUtilities.parseInteger(source);
			assertTrue(source + ": parsing value wrong for " + Long.toString(d)
					+ ": " + Long.toString(v), d == v);
		} catch (NumberFormatException e) {
			assertTrue(
					"parsing failed for " + source + ", message = "
							+ e.getMessage(), false);
		}

	}

}
