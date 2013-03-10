package org.eclipse.uomo.util.test.numbers;

import org.eclipse.uomo.util.numbers.ISpeller;
import org.eclipse.uomo.util.numbers.impl.IndianNumberSpeller;

public class IndianNumberDemos {

	public static void main(String args[]) throws Exception {
//		Reader buff = new BufferedReader(new InputStreamReader(
//				System.in));
		System.out.println("Display massage number to Text!");
		ISpeller num = new IndianNumberSpeller();
		System.out.println("Spelling: " + num.spell(10) + ".");
		System.out.println("Spelling: " + num.spell(15) + ".");
		System.out.println("Spelling: " + num.spell(50) + ".");
		System.out.println("Spelling: " + num.spell(99) + ".");
		System.out.println("Spelling: " + num.spell(150) + ".");
		System.out.println("Spelling: " + num.spell(234) + ".");
		System.out.println("Spelling: " + num.spell(250) + ".");
		System.out.println("Spelling: " + num.spell(250000) + ".");
//		System.out.println("Spelling: " + num.spell(Integer.MAX_VALUE + 1) + ".");
	}
}