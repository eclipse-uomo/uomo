package org.eclipse.uomo.examples.units.console;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.math.BigInteger;

import org.junit.Ignore;
import org.junit.Test;

public class EqualsTest {

	@Test
	@Ignore("this will fail due to inconsistency of JDK")
	public void testInteger() {
		Number n1 = Integer.valueOf(2);
		Number n2 = BigInteger.valueOf(2);
		assertThat(n1, is(n2));
	}

}
