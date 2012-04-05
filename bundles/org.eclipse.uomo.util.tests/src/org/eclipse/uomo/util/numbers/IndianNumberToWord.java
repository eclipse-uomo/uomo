package org.eclipse.uomo.util.numbers;

public class IndianNumberToWord implements INumberToWord {
	String string;
	String a[] = { "", "one", "two", "three", "four", "five", "six", "seven",
			"eight", "nine", };
	String b[] = { "hundred", "thousand", "lakh", "crore" };
	String c[] = { "ten", "eleven", "twelve", "thirteen", "fourteen",
			"fifteen", "sixteen", "seventeen", "eighteen", "ninteen", };
	String d[] = { "twenty", "thirty", "fourty", "fifty", "sixty", "seventy",
			"eighty", "ninty" };

	public String convertNumber(int count) {
		int in = 1;
		int num;
		string = "";
		while (count != 0) {
			switch (in) {
			case 1:
				num = count % 100;
				passString(num);
				if (count > 100 && count % 100 != 0) {
					displayOutput("and ");
				}
				count /= 100;
				break;
			case 2:
				num = count % 10;
				if (num != 0) {
					displayOutput(" ");
					displayOutput(b[0]);
					displayOutput(" ");
					passString(num);
				}
				count /= 10;
				break;

			case 3:
				num = count % 100;
				if (num != 0) {
					displayOutput(" ");
					displayOutput(b[1]);
					displayOutput(" ");
					passString(num);
				}
				count /= 100;
				break;
			case 4:
				num = count % 100;
				if (num != 0) {
					displayOutput(" ");
					displayOutput(b[2]);
					displayOutput(" ");
					passString(num);
				}
				count /= 100;
				break;

			case 5:
				num = count % 100;
				if (num != 0) {
					displayOutput(" ");
					displayOutput(b[3]);
					displayOutput(" ");
					passString(num);
				}
				count /= 100;
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

	public static void main(String args[]) throws Exception {
//		Reader buff = new BufferedReader(new InputStreamReader(
//				System.in));
		System.out.println("Display massage number to Text!");
		INumberToWord num = new IndianNumberToWord();
		System.out.println("Number to word: " + num.convertNumber(10) + ".");
		System.out.println("Number to word: " + num.convertNumber(15) + ".");
		System.out.println("Number to word: " + num.convertNumber(50) + ".");
		System.out.println("Number to word: " + num.convertNumber(99) + ".");
		System.out.println("Number to word: " + num.convertNumber(150) + ".");
		System.out.println("Number to word: " + num.convertNumber(234) + ".");
		System.out.println("Number to word: " + num.convertNumber(250) + ".");
		System.out.println("Number to word: " + num.convertNumber(250000) + ".");
	}
}