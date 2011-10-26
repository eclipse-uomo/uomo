package org.eclipse.uomo.ucum.tests;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.eclipse.uomo.ucum.UcumService;
import org.eclipse.uomo.ucum.impl.UcumEssenceService;
import org.junit.Ignore;
import org.junit.Test;


public class UcumServiceTest {

	@Test
	@Ignore
	public void testConversion() {
		UcumService ucumService = new UcumEssenceService(getClass().getClassLoader().getResourceAsStream("ucum-essence.xml"));

		Number mult = ucumService.convert(new BigDecimal(1000.0), "l", "m3");
		assertNotNull(mult);
		assertEquals(BigDecimal.ONE, mult);
//		Expected result: 1.0
//		Received result: 100.0
	}
	
}
