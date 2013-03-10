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
package org.eclipse.uomo.util.test.numbers;

/**
 * Maximum value to handle : 9223372036854775807
 * (9,223,372,036,854,775,807) Nine Quintillion, Two Hundred and
 * Twenty-Three Quadrillion, Three Hundred and Seventy-Two Trillion,
 * Thirty-Six Billion, Eight Hundred and Fifty-Four Million, Seven
 * Hundred and Seventy-Five Thousand, Eight Hundred and Seven
 * 
 * Thanks to 911 Programming
 */

import java.io.IOException;
import java.util.Scanner;

import org.eclipse.uomo.util.numbers.SpellException;
import org.eclipse.uomo.util.numbers.impl.SpellContext;

/**
 * A multiple demo program to test SpellContext class.
 * 
 * @author Werner Keil
 * 
 */
public class SpellDemos {

	/**
	 * The main program of this demo runs five demos one after the other with a
	 * pause between each two of them.
	 * 
	 * @param args
	 *            Not used in this demo.
	 */
	public static void main(String[] args) {

		/*
		 * Enables or disables formal hyphen in cent-digit combination like
		 * 'Forty-Five' or 'Forty Five'.
		 */
		SpellContext.usingCentHyphen = true;

		// to disable pause between outputs, set the following variable to
		// false.
		pauseBetweenOutputs = false;

		// Print Introduction:
		System.out.println("Spelling Number Test Console  0.6");
		System.out
				.println("A Java demo to spell numbers and to parse spelled number text.");
		System.out.println("Version 0.6-SNAPSHOT");
		System.out.println("Eclipse Foundation.");

		// Run demos one after the other. Pause between them.
		pause("Demo One - Testing Auto-Generated Numbers");
		demoOne();
		pause("Demo Two - Testing Auto-Generated Numbers");
		demoTwo();
		pause("Demo Three - Testing Auto-Generated Numbers");
		demoThree();
		pause("Demo Max - Testing Maximum Long Value");
		demoMax();
		pause("Demo Four - Interactive user mode");
		demoFour();
	}

	/**
	 * This variable enables or disables extra pauses within each
	 * non-interactive demos.
	 */
	private static boolean pauseBetweenOutputs = false;

	/**
	 * Pause used within each demo.
	 */
	private static void pause() {
		if (pauseBetweenOutputs) {

			System.out.print("\nPress <RETURN> to continue");
			try {
				while (System.in.read() != '\r')
					;
			} catch (IOException e) {
				// Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Pause used between demos with an introductory caption.
	 * 
	 * @param message
	 *            The caption of pause.
	 */
	private static void pause(String message) {
		System.out.printf("\nPress <RETURN> to prceed to the next step : %1$s",
				message);
		try {
			while (System.in.read() != '\r')
				;
		} catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Demo One : Tests some automatically generated numbers.
	 */
	private static void demoOne() {
		System.out.println("\n\n----  ----  ----  ----  ----  ----  ");
		System.out.println("Demo One - Auto-Generated Numbers (1)");

		long value = 0L;
		for (long i = 0; i <= 10L; i++) {
			value = value * 10 + i;
			dump(value);
			pause();
		}
	}

	/**
	 * Demo Two : Tests some automatically generated numbers.
	 */
	private static void demoTwo() {
		System.out.println("\n\n----  ----  ----  ----  ----  ----  ");
		System.out.println("Demo Two - Auto-Generated Numbers (2)");

		for (int i = 0; i < 20; i++) {
			dump(i * 10000 + i);
			pause();
		}

	}

	/**
	 * Demo Three : Tests some automatically generated numbers.
	 */
	private static void demoThree() {
		System.out.println("\n\n----  ----  ----  ----  ----  ----  ");
		System.out.println("Demo Three - Auto-Generated Numbers (3)");

		long value;
		value = 0L;

		for (long i = 32; i >= 0L; i--) {
			value = value * 10 + i;
			dump(value);
			pause();
		}
	}

	/**
	 * Demo Four : The interactive user mode. It gets a numeric value or spelled
	 * number text from user. If it is a numeric value, it spells it. If it is a
	 * spelled text, it parses the text and displays its equivalent numeric
	 * value. All works are displayed in the same format. This demo checks if
	 * the grammar associated with the spelled number entered by user is correct
	 * or not, and if it is not, it prints the formal correct spelling of the
	 * perceived number.
	 * 
	 * @see {@link #dump(long)}, {@link #dump(String)},
	 *      {@link #dump(long, String)} and
	 *      {@link SpellDemos#DumpSpellText(String)}
	 */
	private static void demoFour() {
		System.out.println("\n\n----  ----  ----  ----  ----  ----  ");
		System.out.println("Demo Four - User Interactive Mode\n");

		// create a scanner for std input (keyboard input).
		Scanner in = new Scanner(System.in);
		in.nextLine();

		// lineToProcess keeps the user input text. It is specially used for
		// multi-line text input.
		String lineToProcess = null;

		for (;;) {
			// print prompt only if it is not (still) a multi-line input.
			if (lineToProcess == null) {
				System.out
						.println("Enter 'numeric value' or 'spelled number text' :");
				System.out
						.println("(Type '.' to terminate the loop, type '\\' to continue the text at next line)");
			}

			// read next line as text.
			String lineText = in.nextLine();

			// Check if it is a single dot (.)
			if (lineText.equals(".")) {
				// if it is, check if there is some text still to be processed,
				if (lineToProcess != null) {
					try {
						// if it is, dump the line.
						dump(lineToProcess);
					} catch (Exception e) {
						// check errors.
						System.err.println("\n\n" + e.getMessage() + "\n\n");
					}
				}
				// break to terminate the demo.
				break;
			} else if (lineText.equals(""))
				// otherwise, If it is an empty line, ignore it.
				continue;

			// If it is not either a dot (.) or an empty line, check data.

			// Check if there is some previous unprocessed text,
			if (lineToProcess == null)
				// if it is not, set it as a fresh text.
				lineToProcess = lineText.replaceAll("\\\\", "");
			else
				// Otherwise, append this line to that (for non-numeric spelled
				// number text only).
				lineToProcess += " " + lineText.replaceAll("\\\\", "");

			// If this line is not marked to be continued,
			if (!lineText.endsWith("\\")) {
				// process it:
				try {
					// dump the line of text.
					dump(lineToProcess);

				} catch (Exception e) {

					// Report error is there is any.
					System.err.println("\n\n" + e.getMessage() + "\n\n");

				} finally {
					// mark the line to be processed as fresh.
					lineToProcess = null;

				}

			}
		}

		// close the scanner.
		in.close();
	}

	/**
	 * Demo Max : Tests the maximum number to handle.
	 */
	private static void demoMax() {
		System.out.println("\n\n----  ----  ----  ----  ----  ----  ");
		System.out.println("Demo Max - Testing Maximum Value");

		System.out.println("\nMaximim value to handle : " + Long.MAX_VALUE);
		dump(Long.MAX_VALUE);
		System.out.println();
	}

	/**
	 * Dump the data based on the numeric value.
	 * 
	 * @param value
	 *            The numeric value to dump
	 * 
	 * @see {@link #dump(long, String)} dump the number data based on its
	 *      numeric value or spelled text.
	 */
	private static void dump(long value) {
		dump(value, null);
	}

	/**
	 * Dump the data based on the spelled text or numeric value represented as
	 * text String format.
	 * 
	 * @param line
	 *            The text string containing the spelled number or numeric
	 *            value.
	 * 
	 * @see {@link #dump(long, String)} dump the number data based on its
	 *      numeric value or spelled text.
	 */
	private static void dump(String line) {
		long value;

		try {
			// first try to dump it as number.
			value = Long.parseLong(line.replaceAll(",", ""));
			dump(value, null);

		} catch (NumberFormatException e) {
			// If not successful, try it as text.
			dump(0, line);
		}
	}

	/**
	 * Dump the data based on the spelled text (if not null), otherwise, based
	 * on the numeric value. This is the most basic method which actually tests
	 * the SpellContext class.
	 * 
	 * @param value
	 *            If spellText is null, this is the numeric value to dump.
	 * @param spellText
	 *            If not null, it is the spelled number text to dump.
	 * 
	 * @see {@link #DumpSpellText(String)} to check how spelled text is nicely
	 *      formatted, possibly in multi-lines.
	 */
	private static void dump(long value, String spellText) {

		// assume user-spelled format which might need correction.
		boolean userSpelledText = true;

		// decide which one to choose : the numeric value or spelled text.
		// if spellText is null, assume numeric value, otherwise, assume spelled
		// text.
		if (spellText == null) {
			// Report to user that it is a numeric value to dump.
			System.out.printf("%1$d (Numeric)\n", value);
			try {
				// create the spelled text from the numeric value.
				spellText = SpellContext.getDefault().spell(value);
				// There is no need for formal spelled text because the spelled
				// text is generated from this program (not user), and it does
				// not contain any spelling error.
				userSpelledText = false;
			} catch (SpellException e) {
				e.printStackTrace();
			}
		} else {
			// Otherwise, report to user that it is a spelled number to dump.
			System.out.println("(Spelled Number)");
		}

		// Now, spellText is the basis of the data dump.

		try {
			// If it is user spelled text, evaluate the corresponding value.
			if (userSpelledText)
				value = SpellContext.getDefault().parse(spellText);

			// encode the spelled text
			String encoded = SpellContext.encode(spellText);
			// decode the encoded text.
			String decoded = SpellContext.decode(encoded);

			// first, assume that the spelling is correct.
			boolean spellIsCorrect = true;

			try {
				// validate the spelling.
				SpellContext.getDefault().validate(encoded);

			} catch (SpellException e) {
				// mark incorrect, if validation failed.
				spellIsCorrect = false;
			}

			// The dump table :

			// The numeric value
			System.out.printf("\tNumeric Value : %1$s\n",
					SpellContext.withSeparator(value));

			// The spell text
			DumpSpellText(spellText);

			// Decide if it is a user spelled text and needs a formal spelled
			// text to compare.
			if (userSpelledText) {
				String formalSpell = SpellContext.getDefault().spell(value);
				String formalEncode = SpellContext.encode(formalSpell);

				if (!formalEncode.equals(encoded)) {
					System.out.println("\tFormal Spell: ");
					DumpSpellText(formalSpell);
				}
			}

			// The encoded text:
			System.out.println("\tEncoded : " + encoded);

			// The decoded text to compare to the original spelled text.
			System.out.println("\tDecoded : ");
			DumpSpellText(decoded);

			// if user spelled text, check if it is correct or not.
			if (userSpelledText)
				System.out.println("\tSpelling : "
						+ (spellIsCorrect ? "Correct" : "Incorrect"));

			System.out.println();

		} catch (SpellException e) {
			// report spell error if any.
			System.err.println("\n\n" + e.getMessage() + "\n\n");
		} catch (Exception e) {
			// report general error if any.
			System.err.println("\n\n" + e.getMessage() + "\n\n");
		}
	}

	/**
	 * Prints the spelled text in nice width-limited format.
	 * 
	 * @param spellText
	 *            The spelled text.
	 */
	private static void DumpSpellText(String spellText) {
		String[] tokens = spellText.split("\\s+");

		// set it as the first column
		int columnIndex = 0;

		// The space chars between words.
		final String space = "  ";

		// the number of spaces between word.
		final int spaceCount = space.length();

		// prefix the first line with two tabs.
		System.out.print("\t\t");

		// for each word of the spelled text,
		for (String token : tokens) {
			// if it goes beyond the line width limit,
			if (columnIndex + token.length() + spaceCount > 80) {
				// go to the next line,
				System.out.println();
				// prefix the line with two tabs.
				System.out.print("\t\t");
				// set it as the first column.
				columnIndex = 0;
			}

			// if it is not the first word of the line, print word separator.
			if (columnIndex > 0)
				System.out.print(space);

			// Now, print the word itself.
			System.out.print(token);

			// update column index;
			columnIndex += token.length();
		}

		// print a line-separator.
		System.out.println();
	}
}