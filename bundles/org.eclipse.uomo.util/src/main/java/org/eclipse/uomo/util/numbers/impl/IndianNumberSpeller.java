/**
 * Copyright (c) 2005, 2013, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.util.numbers.impl;

import org.eclipse.uomo.util.internal.Messages;
import org.eclipse.uomo.util.numbers.ISpeller;
import org.eclipse.uomo.util.numbers.SpellException;


public class IndianNumberSpeller implements ISpeller {
	String string;
	String a[] = { "", Messages.Speller_22, Messages.Speller_23,
			Messages.Speller_24, Messages.Speller_25, "five", "six", "seven",
			"eight", "nine", };
	String b[] = { "hundred", "thousand", "lakh", "crore" };
	String c[] = { "ten", "eleven", "twelve", "thirteen", "fourteen",
			"fifteen", "sixteen", "seventeen", "eighteen", "ninteen", };
	String d[] = { "twenty", "thirty", "fourty", "fifty", "sixty", "seventy",
			"eighty", "ninty" };

	public String spell(final long number) throws SpellException {
		if (number < Integer.MIN_VALUE || number < -Integer.MAX_VALUE || number > Integer.MAX_VALUE) {
			throw new SpellException(number + " exceeds allowed value for this algorithm.");
		}
		int numToConvert = (int)number;
		int in = 1;
		int num = -1;
		string = "";
		while (numToConvert != 0) {
			switch (in) {
			case 1:
				num = numToConvert % 100;
				passString(num);
				if (numToConvert > 100 && numToConvert % 100 != 0) {
					displayOutput(Messages.Speller_7);
				}
				numToConvert /= 100;
				break;
			case 2:
				num = numToConvert % 10;
				if (num != 0) {
					displayOutput(" ");
					displayOutput(b[0]);
					displayOutput(" ");
					passString(num);
				}
				numToConvert /= 10;
				break;

			case 3:
				num = numToConvert % 100;
				if (num != 0) {
					displayOutput(" ");
					displayOutput(b[1]);
					displayOutput(" ");
					passString(num);
				}
				numToConvert /= 100;
				break;
			case 4:
				num = numToConvert % 100;
				if (num != 0) {
					displayOutput(" ");
					displayOutput(b[2]);
					displayOutput(" ");
					passString(num);
				}
				numToConvert /= 100;
				break;

			case 5:
				num = numToConvert % 100;
				if (num != 0) {
					displayOutput(" ");
					displayOutput(b[3]);
					displayOutput(" ");
					passString(num);
				}
				numToConvert /= 100;
				break;

			}
			in++;
		}
		return string.trim();
	}

	private void passString(int number) {
		int num, q;
		if (number < 10) {
			displayOutput(a[number]);
		}
		if (number > 9 && number < 20) {
			displayOutput(c[number - 10]);
		}
		if (number > 19) {
			num = number % 10;
			if (num == 0) {
				q = number / 10;
				displayOutput(d[q - 2]);
			} else {
				q = number / 10;
				displayOutput(a[num]);
				displayOutput(" ");
				displayOutput(d[q - 2]);
			}
		}
	}

	private void displayOutput(String s) {
		StringBuilder sb = new StringBuilder(s);
//		String t = string;
//		string = s;
		sb.append(string);
		string = sb.toString();
	}

	@Override
	public Long parse(String text) throws SpellException {
		return null;
	}
}