package org.eclipse.uomo.examples.icu.console;

import com.ibm.icu.impl.ICUService;

public class NamesExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ICUService.Factory factory = new ICUService.SimpleFactory((Object)"a", "b");
		System.out.println(factory);
	}

}
