package org.eclipse.uomo.ucum.tests;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.eclipse.uomo.ucum.UcumService;
import org.eclipse.uomo.ucum.impl.UcumEssenceService;
import org.junit.Test;

import com.ibm.icu.text.DecimalFormat;
import com.ibm.icu.text.NumberFormat;


public class UcumServiceTest {

	@Test
	public void testConversion() {
		UcumService ucumService = new UcumEssenceService(getClass().getClassLoader().getResourceAsStream("ucum-essence.xml"));

		Number mult = ucumService.convert(new BigDecimal(1000d), "l", "m3");
		assertNotNull(mult);		
		NumberFormat fmt = new DecimalFormat("#,##0.000");
		assertEquals(fmt.format(BigDecimal.ONE), fmt.format(mult));
	}
	
}
