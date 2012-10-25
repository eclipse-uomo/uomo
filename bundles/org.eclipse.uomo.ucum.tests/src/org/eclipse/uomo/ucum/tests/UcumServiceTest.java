package org.eclipse.uomo.ucum.tests;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Set;

import org.eclipse.uomo.ucum.UcumService;
import org.eclipse.uomo.ucum.impl.UcumEssenceService;
import org.junit.Before;
import org.junit.Test;

import com.ibm.icu.text.DecimalFormat;
import com.ibm.icu.text.NumberFormat;


public class UcumServiceTest {
	private UcumService ucumService;
	
	@Before
	public void init() {
		if (ucumService == null) {
			ucumService = new UcumEssenceService(getClass().getClassLoader().getResourceAsStream("ucum-essence.xml"));
		}
	}
	
	@Test
	public void testConversion() {
		//UcumService 
		Number mult = ucumService.convert(new BigDecimal(1000d), "l", "m3");
		assertNotNull(mult);		
		NumberFormat fmt = new DecimalFormat("#,##0.000");
		assertEquals(fmt.format(BigDecimal.ONE), fmt.format(mult));
	}

	@Test
	public void testProperties() {
		//UcumService 
		Set<String> props = ucumService.getProperties();
//		for (String prop : props) {
//			System.out.println(prop);
//		}
		assertTrue(props.size() == 92);
	}
	
}
