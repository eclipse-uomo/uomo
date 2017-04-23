package org.eclipse.uomo.examples.ucum.console;

import java.math.BigDecimal;

import org.eclipse.uomo.ucum.UcumService;
import org.eclipse.uomo.ucum.impl.UcumEssenceService;

public class UcumDemo {

	public static void main(String[] args) {
		final UcumService service = new UcumEssenceService(UcumDemo.class
				.getClassLoader().getResourceAsStream("ucum-essence.xml"));
		System.out.println("16 OZ to LBs    = "
				+ service.convert(new BigDecimal(16), "[oz_av]", "[lb_av]"));
		System.out.println("16 LBs to OZ    = "
				+ service.convert(new BigDecimal(16), "[lb_av]", "[oz_av]"));
		System.out.println("1 LB to Grams  = "
				+ service.convert(new BigDecimal(1), "[lb_av]", "g"));
		System.out.println("16 Oz to Grams  = "
				+ service.convert(new BigDecimal(16), "[oz_av]", "g"));
		System.out.println("1 Oz to Grams   = "
				+ service.convert(new BigDecimal(1), "[oz_av]", "g"));
		
		System.out.println("1 Oz TR to Grams   = "
				+ service.convert(new BigDecimal(1), "[oz_tr]", "g"));
		
		System.out.println("1 kg to Grams   = "
				+ service.convert(new BigDecimal(1), "kg", "g"));
		
		System.out.println("1 mStere to l   = "
				+ service.convert(BigDecimal.ONE, "ust", "l"));
		
//		System.out.println(service.validate("L"));
		System.out.println(service.analyse("l"));
		System.out.println(service.validateUCUM());
	}

}
