/**
 * 
 */
package org.eclipse.uomo.util.numbers.impl;

/*
* Maximum value to handle: 9,223,372,036,854,775,807
* 9223372036854775807
* Nine Quintillion, Two Hundred and Twenty-Three Quadrillion, Three Hundred and 
* Seventy-Two Trillion, Thirty-Six Billion, Eight Hundred and Fifty-Four Million, 
* Seven Hundred and Seventy-Five Thousand, Eight Hundred and Seven
* 
*/

import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.uomo.core.IValidator;
import org.eclipse.uomo.util.numbers.ISpeller;
import org.eclipse.uomo.util.numbers.SpellException;

/**
* SpellContext is main engine for numbers spelling and text parsing, encoding
* and validating.
* 
* It contains the following basic static service methods:
* 
* <ul>
* <li>spell (long) : spells a number.
* <li>parse (String) : parses a spelled text to convert it to an equivalent
* number (if it is a correct valid number).
* <li>validate (String) : checks an encoded text to examine if it is a valid
* text and equivalent to a correctly spelled number.
* <li>encode (String) : encodes a text string to a concise format suitable to
* be examined against a regular expression pattern.
* <li>decode (String) : decodes an encoded text to its human-readable format.
* <li>encode (long) encodes a long numeric value.
* <li>decodeToNumder (String) converts an encoded text to its equivalent long
* numeric value.
* </ul>
* TODO change static definitions into OSGi style
* @author Werner Keil
*/
public class SpellContext implements ISpeller, IValidator<String> {

	private static SpellContext INSTANCE;
	
	/**
	 * @return the default instance
	 */
	public static SpellContext getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SpellContext();
		}
		return INSTANCE;
	}
	
	/**
	 * Spells a number; converts a number to its equivalent read-out text
	 * string.
	 * 
	 * @param number
	 *            The number to be spelled.
	 * 
	 * @return The word by word read-out of the number - correctly spelled and
	 *         punctuated.
	 * 
	 * @throws SpellException
	 *             Actually, it is a bug if this method throws an exception.
	 *             Please report it.
	 */
	public String spell(long number) throws SpellException {

		// text holds the intermediate and final result.
		String text;

		// first check if it is a negative value and adjust the text properly.
		if (number < 0L) { // if it is negative :
			// invoke level-one spell and prefix the result with the word: Minus
			text = "Minus " + spell(-number, 1);
		} else { // otherwise,
			// simply, invoke level-one spell.
			text = spell(number, 1);
		}

		// (see the doc of spell (number, level) for the use and meaning of
		// level)

		// The resulted text returned from spell (number, level = 1) has some
		// place-holder characters: '%' and '$'.

		// The place-holder '%' represents where we should place an 'and' in the
		// spelled text in sub-phrases containing 'hundred'; for example :
		//
		// three hundred and twenty-four
		//
		// would be like
		//
		// three hundred%twenty-four
		//
		// a place-holder '%' must always be substituted by ' and ' conjunction
		// in the final output.

		// The place-holder '$' represents where we should place a comma (,) or
		// 'and' in the spelled text in sub-phrases containing suffixes:
		// 'thousand', 'million', 'billion', ...

		// All place-holders '$' save the last one must be substituted by comma
		// (,). The final place-holder '$' must be substituted by ' and '
		// conjunction if and only if there is no place-holder '%' after it.
		// Otherwise, it is substituted by comma, too.

		// The following code before the return statement implements this logic:
		int index_amp, index_perc;

		index_amp = text.lastIndexOf("$");
		index_perc = text.lastIndexOf("%");

		if (index_amp >= 0) {
			if (index_perc < 0 || index_amp > index_perc) {

				String textBeforeAmp = text.substring(0, index_amp);
				String textAfterAmp = text.substring(index_amp + 1, text
						.length());

				text = textBeforeAmp + " and " + textAfterAmp;
			}
		}

		text = text.replaceAll("\\$", ", ");
		text = text.replaceAll("%", " and ");

		return text;
	}

	/**
	 * Converts the given number to text string. Digits are grouped 3 by 3 and
	 * separated with thousands-separator character: ','.
	 * 
	 * This is a recursive algorithm which could be implemented in all
	 * languages.
	 * 
	 * @param number
	 *            The number to convert.
	 * 
	 * @return the thousand-separated text string equivalent to the given
	 *         number.
	 */
	public static String withSeparatorAlt(long number) {
		if (number < 0) {
			return "-" + withSeparator(-number);
		}

		if (number / 1000L > 0) {
			return withSeparator(number / 1000L) + ","
					+ String.format("%1$03d", number % 1000L);
		} else {
			return String.format("%1$d", number);
		}
	}

	/**
	 * Converts the given number to text string. Digits are grouped 3 by 3 and
	 * separated with thousands-separator character: ','.
	 * 
	 * This is a Java direct and standard way.
	 * 
	 * @param number
	 *            The number to convert.
	 * 
	 * @return the thousand-separated text string equivalent to the given
	 *         number.
	 */
	public static String withSeparator(long number) {
		return String.format("%1$,d", number);
	}

	/**
	 * Here, a 'suffix' is a word representing the multiples of thousands.
	 * 
	 * mySuffixText is an array of suffixes which could be indexed based on the
	 * current level of spell.
	 * 
	 * @see {@link #spell(long, int)} the implementation of spell (number,
	 *      level) to check how this array is used.
	 */
	private static String mySuffixText[] = {
			"", // Dummy! no level 0 (added for nicer indexing in code)
			"", // Nothing for level 1
			" Thousand", " Million", " Billion", " Trillion", " Quadrillion",
			" Quintillion", };

	/**
	 * A teen is the word equivalent of numbers [0, 19].
	 * 
	 * @see {@link #SpellBelow1000(long)} implementation to check how this array
	 *      is used.
	 */
	private static String myTeenText[] = { "Zero", "One", "Two", "Three",
			"Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven",
			"Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen",
			"Seventeen", "Eighteen", "Nineteen", };

	/**
	 * A cent has two meanings in this project: it is either any value below one
	 * hundred, or it is the multiples of ten bellow one hundred.
	 * 
	 * @see {@link #SpellBelow1000(long)} implementation to check how this array
	 *      is used.
	 */
	private static String myCentText[] = { "Twenty", "Thirty", "Forty",
			"Fifty", "Sixty", "Seventy", "Eighty", "Ninety" };

	/**
	 * Formal English requires to use hyphen between a cent and digit; for
	 * example, 21 is correctly spelled as twenty-one.
	 * 
	 * usingCentHyphen (static public data property) controls the behavior of
	 * this class whether to use hyphen after a cent/digit combination or not.
	 */
	public static boolean usingCentHyphen = false;

	/**
	 * For internal use. Decides whether to use a hyphen or space for cent-digit
	 * combination.
	 * 
	 * @return hyphen or space based on the current global setting.
	 */
	private static String centHyphen() {
		return usingCentHyphen ? "-" : " ";
	}

	/**
	 * Spells a mil value. A mil is any value below one thousand ([0, 999]).
	 * 
	 * @param number
	 *            the number (< 1000) to be spelled.
	 * @return the spelled text.
	 * @throws SpellException
	 *             if number is negative or it is greater than or equals to
	 *             1000.
	 */
	private static String SpellBelow1000(long number) throws SpellException {
		// if number is negative or above 999, throw a SpellException.
		if (number < 0 || number >= 1000)
			throw new SpellException("Expecting a number between 0 and 999: "
					+ number);

		if (number < 20L) {
			// if number is a teen,
			// find it in teen table and return its equivalent text (word).
			return myTeenText[(int) number];
		} else if (number < 100L) {
			// otherwise, if it is a cent,
			// find the most (div) and least (rem) significant digits (MSD/LSD)
			int div = (int) number / 10;
			int rem = (int) number % 10;

			if (rem == 0) {
				// if LSD is zero, return the cent key word directly (like
				// fifty).
				return myCentText[div - 2];
			} else {
				// otherwise, return the text as cent-teen (like fifty-one)
				return myCentText[div - 2] + centHyphen() + myTeenText[rem];
			}
		} else {
			// otherwise, it is a mil;
			// find it's MSD and remaining cent.
			int div = (int) number / 100;
			int rem = (int) number % 100;

			// Prepare the mil prefix:
			String milText = myTeenText[div] + " Hundred";

			// decide whether to append the cent tail or not.
			if (rem == 0) {
				// if it does have a non-zero cent, that's it.
				// return the mil prefix, for example three hundred:
				return milText;
			} else {
				// otherwise, spell the cent and append it to mil prefix.
				// (now, rem is a cent).
				// '%' is a place-holder which will eventually converted to
				// ' and ' conjunction in final output string. It cannot be done
				// right here, because we would need it in conjunction with
				// another place-holder: '$' to properly set all the punctuation
				// properly.
				// For example, three Hundred%Sixty-Four:
				return milText + "%" + SpellBelow1000(rem);
			}
		}
	}

	/**
	 * Spells the number based on successive (recursive) division by 1000.
	 * Starting from 1, each division increments the level of evaluation which
	 * is passed to next nested level of recursion.
	 * 
	 * Level helps to use the appropriate suffix for the spelled sub-value.
	 * 
	 * @param number
	 *            the (sub-) number to be spelled.
	 * @param level
	 *            The current level of evaluation.
	 * @return the spell text equivalent to the (sub-) number and its level.
	 * @throws SpellException
	 *             Actually, it is a bug if this method throws an exception.
	 *             Exceptions happens if the method SpellBelow1000 (long) is
	 *             called for an out-of-ranged value (negative or above 999)
	 *             which should never happen in this algorithm.
	 */
	private static String spell(long number, int level) throws SpellException {
		// first, find the normed over-mil value (div) and the remaining mil
		// value (rem).
		long div = number / 1000L;
		long rem = number % 1000L;

		// check if over-mil value is zero:
		if (div == 0) {
			// if it is, then it is a mil value [0, 999];
			// spell it and append the right suffix based on the current level.
			return SpellBelow1000(rem) + mySuffixText[level];
		} else {
			// otherwise, increment the level for the over-mil value and spell
			// it; append the spell of mil portion if it is non-zero.
			// '$' is a place-holder which will eventually be converted to
			// either comma (,) or ' and ' conjunction. The decision about which
			// one should be taken cannot be made here, but after the original
			// number is fully spelled. See the comment in the implementation of
			// spell (long) to check how this decision is made.

			if (rem == 0) {
				return spell(div, level + 1);
			} else {
				return spell(div, level + 1) + "$" + SpellBelow1000(rem)
						+ mySuffixText[level];
			}
		}
	}

	/**
	 * Examines the given text to check if it is a single number word
	 * corresponding to a value below 1000.
	 * 
	 * @param text
	 *            The text under examination.
	 * @return true if the text is a single number word corresponding to a value
	 *         below 1000.
	 */
	private static boolean isBelowThousandWord(String text) {
		// simply, check the dictionary: myNameMap

		if (!myNameMap.containsKey(text))
			return false;

		long value = myNameMap.get(text).getValue();

		return value >= 0L && value < 1000L;
	}

	/**
	 * Parses a mil text. If the given text is the spelled text equivalent to a
	 * mil number [0, 999], this method would return that number.
	 * 
	 * This method is extremely tolerant. If the spelled number (text) contains
	 * all valid mil words (words corresponding to a single number less than
	 * 1000), it would return some value (intended or unintended) any way. It
	 * works correctly for correctly spelled mil text.
	 * 
	 * @param text
	 *            The text to be parsed.
	 * @return the number equivalent to the text.
	 * @throws SpellException
	 *             if the text is not a valid mil text.
	 * 
	 */
	private static long parseBelow1000(String text) throws SpellException {

		// The algorithm is fairly simple;

		// Initially, assume value is zero.
		long value = 0;

		// The only valid punctuation is ' and ' conjunction. It senses only for
		// human read.
		// It has no use here. simple remove them. This algorithm is tolerant,
		// we do not validate the text here.
		// Then, split the text to words.
		String[] words = text.replaceAll(" and ", " ").split("\\s+");

		// Now, for each word in text:
		for (String word : words) {
			// check if the word is a mil word.
			// throw exception if it is not.
			if (!isBelowThousandWord(word)) {
				throw new SpellException("Unknown or misplaced token : " + word);
			}

			// get the nominal value of the mil word.
			long subval = getValueOf(word);

			// if it is the word "hundred" (value == 100),
			if (subval == 100) {
				// based on the previous evaluated value,
				if (value == 0)
					// either set it to 100 (the previous value was 0).
					value = 100;
				else
					// or multiply it by 100.
					value *= 100;
			} else
				// otherwise, simply add it to the value (whatever it is, and
				// whatever previously parsed for value)
				value += subval;

		}

		// return the evaluated value.
		return value;
	}

	/**
	 * Gets the value equivalent to a single-word spelled number.
	 * 
	 * @param word
	 *            a single-word spelled number.
	 * @return the equivalent value.
	 */
	private static long getValueOf(String word) {

		// simply look-up the word in dictionary: myNameMap.
		return myNameMap.get(word).getValue();
	}

	/**
	 * Suffixes used in spelling (and parsing) a word.
	 * 
	 * @see {@link #parseInternal(String)} to check how this array is used.
	 */
	private final static String[] mySuffixWords = { "quintillion",
			"quadrillion", "trillion", "billion", "million", "thousand" };

	/**
	 * The equivalent numeric value of suffixes used in spelling (and parsing) a
	 * word.
	 * 
	 * @see {@link #mySuffixWords} This array matches mySuffixWords element by
	 *      element.
	 * @see {@link #parseInternal(String)} to check how this array is used.
	 */
	private final static long[] mySuffixValues = { 1000000000000000000L,
			1000000000000000L, 1000000000000L, 1000000000L, 1000000L, 1000L };

	/**
	 * For public use: parses a human-readable spelling text of a number, and
	 * converts it to the corresponding numeric value.
	 * 
	 * @param text
	 *            the human-readable spelling text of a number.
	 * @return the numeric value corresponding to the human-readable number
	 *         text.
	 * @throws SpellException
	 *             if the text contains intolerable, misplaced or unknown word.
	 * 
	 *@see {@link #parseInternal(String)} which does a similar operation with
	 *      text not possibly started with word ''minus''. Actually, it does the
	 *      main operation.
	 */
	public static long parse(String text) throws SpellException {

		// remove all punctuation.
		text = toFriendlyString(text);

		// if the text starts with word: ''minus''.
		if (text.startsWith("minus")) {
			// extract substring after ''minus''
			String subtext = text.substring("minus".length());

			// It must not be empty and must not be started with a none-white
			// character
			if (subtext.equals("")
					|| !Character.isWhitespace(subtext.charAt(0))) {

				// if it is, throw exception.
				throw new SpellException("Invalid starting token for text : "
						+ text);
			}

			// If it is OK, parse the substring after ''minus'' and negate the
			// evaluated value.
			return -parseInternal(subtext.trim());
		} else {
			// If it is not started with ''minus'', simply parse it, and return
			// the evaluated value.
			return parseInternal(text);
		}
	}

	/**
	 * For internal use only: parses a human-readable spelling text of a number,
	 * and converts it to the corresponding numeric value.
	 * 
	 * The algorithm works recursively this way: It looks for known suffixes
	 * like: ''thousand'', ''million'', ... (of course, in reversed order). If
	 * it found one, It parses the substring before it as a mil text (a spelled
	 * number text below 1000), and it calls itself once more for the substring
	 * after the suffix.
	 * 
	 * If the substring before suffix is empty (like 'thousand and twenty
	 * three'), then it assumes 'one' ('one thousand and twenty three').
	 * 
	 * If the substring after suffix is empty (like 'two thousand'), then it
	 * assumes 'zero' ('two thousand and zero').
	 * 
	 * If the text does not have any suffix, it parses it as a mil text (a value
	 * below 1000).
	 * 
	 * @param text
	 *            the human-readable spelling text of a number.
	 * @return the numeric value corresponding to the human-readable number
	 *         text.
	 * @throws SpellException
	 *             if the text contains intolerable, misplaced or unknown word.
	 */
	private static long parseInternal(String text) throws SpellException {

		// First, assume the evaluated value is zero.
		long totalValue = 0;

		// Also, first assume that there is no suffix in the text.
		boolean suffixFound = false;

		// Examines all suffixed from biggest to lowest:
		// Check if the text contain a suffix:
		for (int n = 0; n < mySuffixWords.length; n++) {

			// look for the next suffix.
			int index = text.indexOf(mySuffixWords[n]);

			// If it has a suffix,
			if (index >= 0) {
				// Extract substrings before and after suffix.
				String textBeforeSuffix = text.substring(0, index).trim();
				String textAfterSuffix = text.substring(
						index + mySuffixWords[n].length()).trim();

				// if the substring before suffix is empty, assume 'one'.
				if (textBeforeSuffix.equals(""))
					textBeforeSuffix = "one";

				// if the substring after suffix is empty, assume 'zero'.
				if (textAfterSuffix.equals(""))
					textAfterSuffix = "zero";

				// parse both substrings properly, and evaluate the total value.
				totalValue = parseBelow1000(textBeforeSuffix)
						* mySuffixValues[n] + parseInternal(textAfterSuffix);

				// mark 'suffix is found'.
				suffixFound = true;

				// no need to look for another suffix, they are done in
				// recursive loops. End the loop.
				break;
			}

			// If the text does not have this suffix, check next suffix.
		}

		// check if there was a suffix in the text.
		if (suffixFound)
			// If there is a suffix, the total value has already been evaluated,
			// return it:
			return totalValue;
		else
			// Otherwise, parse it as a mil text (a spelled number text below
			// 1000).
			return parseBelow1000(text);
	}

	/**
	 * Removes usual punctuation from a spelled number text. The SpellContext
	 * parser does not need the punctuation, and they are normally useful only
	 * for human-readability.
	 * 
	 * @param text
	 *            The text containing punctuation.
	 * @return The text without punctuation.
	 */
	private static String toFriendlyString(String text) {
		return text.toLowerCase().replaceAll("[\\-,]", " ").replaceAll(" and ",
				" ").trim();
	}

	/**
	 * Represent the desired behavior of the encode/decode method when
	 * encountering an error.
	 */
	public static enum CodingErrorBehavior {
		SPECIAL_TOKEN, EXCEPTION;
	}

	/**
	 * Represent the desired action of the encode/decode method when
	 * encountering an error.
	 */
	public static CodingErrorBehavior codingErrorAction = CodingErrorBehavior.SPECIAL_TOKEN;

	/**
	 * Encodes a spelled number text to a unique string containing predefined
	 * single character for each known (recognize) token of the text.
	 * 
	 * @param text
	 *            The spelled number text to be encoded
	 * @return The encoded string
	 * @throws SpellException
	 *             if the codingErrorAction is set to be Exception and the text
	 *             contains some unknown token.
	 * 
	 * @see {@link SpellCode} the class containing the definition for each known
	 *      token.
	 * @see {@link CodingErrorBehavior} and
	 * @see {@link codingErrorAction}
	 * @see {@link SpellContext#decode(String)}
	 */
	/**
	 * @param text
	 * @return
	 * @throws SpellException
	 */
	public static String encode(String text) throws SpellException {
		/**
		 * In this project, we have used a unique way of encoding spelled number
		 * to a string. Each known word (or punctuation) in a spelled number
		 * string is converted to a case-sensitive single-character code. For
		 * example, the spelled number:
		 * 
		 * Two Thousand, Three Hundred and Nine (2,309)
		 * 
		 * is encoded as:
		 * 
		 * 2T,3I&9
		 * 
		 * In this encoding,
		 * 
		 * 2 represents the word: "two", T represents the word: :thousand",
		 * Comma (,) represents itself, 3 represents the word: "three", I
		 * represents the word: "hundred", ampersand (&) represents the word:
		 * "and", and finally, 9 represents the word nine.
		 * 
		 * The great advantage of this encoding is that it would be fairly easy
		 * to validate a text as being a correct spelled number using regular
		 * expression.
		 * 
		 * SpellCode class is a way to hold encoding and recognition information
		 * for a single known word in spelling contexts. These are words like
		 * thousand, fourteen etc which all have a name (e.g., fourteen), a
		 * dedicated case-sensitive single-character code (U) and an associative
		 * value (14).
		 */

		// first, make the text case-insensitive.
		text = text.toLowerCase();

		// The pattern to extract known tokens, It is either dash (-) or comma
		// (,) (first group), an identifier (second group), or others (a string
		// of one or more punctuation characters) (other)
		Pattern pat = Pattern.compile("(?:[\\-,]|\\w+|\\S+)");

		// match the text against pattern.
		Matcher m = pat.matcher(text);

		// a string builder to hold the encoded the string.
		StringBuilder sb = new StringBuilder();

		// know search the text for the known tokens:
		while (m.find()) {
			// If still there is a token,
			String token = m.group();

			// look it up in dictionary : myNamemap.
			if (myNameMap.containsKey(token)) {
				// If it is in dictionary, append its code to the string
				sb.append(myNameMap.get(token).getCode());
			} else {
				// otherwise,

				// If the desired action is to insert special error token,
				if (codingErrorAction == CodingErrorBehavior.SPECIAL_TOKEN)
					// append a special error token to the string.
					sb.append('#');
				else
					// otherwise, the desired action is exception, throw it.
					throw new SpellException("Unknonw token : " + token);
			}
		}

		// convert the encoded string builder to string and return it.
		return sb.toString();

	}

	/**
	 * Decodes an encoded string back to a human-readable spelled number text.
	 * 
	 * @param text
	 *            The encoded text.
	 * @return a human-readable spelled number text representing the encoded
	 *         number.
	 * @throws SpellException
	 *             if the codingErrorAction is set to be Exception and the
	 *             encoded text contains a special token representing some
	 *             unknown token.
	 * 
	 * @see {@link SpellCode} the class containing the definition for each known
	 *      token.
	 * @see {@link CodingErrorBehavior} and
	 * @see {@link codingErrorAction}
	 * @see {@link SpellContext#encode(String)}
	 */
	public static String decode(String text) throws SpellException {

		// a string builder to hold the final decoded string.
		StringBuilder sb = new StringBuilder();

		// represent the last token; used to decide when to prefix a token with
		// a space.
		String lastToken = null;

		// scan all encoded characters in the input string
		for (char c : text.toCharArray()) {

			// find the code in dictionary : myCodeMap.
			SpellCode sc = myCodeMap.get(Character.toString(c));
			String token;

			// if there is no code for this encoded character,
			if (sc == null) {
				// depending on the current desired code error behavior,
				if (codingErrorAction == CodingErrorBehavior.SPECIAL_TOKEN)
					// either append a special token,
					token = "(?)";
				else
					// or throw an exception.
					throw new SpellException(
							"Unknonw or invalid code character : " + c);
			} else
				// otherwise, if there is a code in dictionary, read its token
				// word.
				token = sc.getName();

			// If there is no last token, or the last token and this tokens are
			// not hyphen, and also this token is not comma,
			if (lastToken != null && !lastToken.equals("-")
					&& !token.equals("-") && !token.equals(",")) {

				// append a space before token.
				sb.append(' ');
			}

			// append the token.
			sb.append(token);

			// for next loop, mark the last token as this token.
			lastToken = token;
		}

		// convert builder to string and return it.
		return sb.toString();
	}

	/**
	 * Converts a number directly to encoded string.
	 * 
	 * @param number
	 *            The number to be encoded.
	 * @return The encoded string representing the given number.
	 * @throws SpellException
	 *             Actually, there is no error ever in directly encoding a
	 *             numeric value.
	 */
	public static String encode(long number) throws SpellException {
		return encode(getInstance().spell(number));
	}

	/**
	 * Decodes an encoded text directly to its numeric value.
	 * 
	 * @param text
	 *            The encoded text to be decoded.
	 * @return The numeric value corresponding the given encoded text.
	 * @throws SpellException
	 *             if the codingErrorAction is set to be Exception and the
	 *             encoded text contains a special token representing some
	 *             unknown token.
	 */
	public static long decodeToNumber(String text) throws SpellException {
		return parse(decode(text));
	}

	/**
	 * The array containing all single character codes dedicated to known spell
	 * number word.
	 */
	private static SpellCode[] myCodes = {
	// First element

			// Zero
			new SpellCode("Zero", "0", 0L),

			// One
			new SpellCode("One", "1", 1L),

			// Two
			new SpellCode("Two", "2", 2L),

			// Three
			new SpellCode("Three", "3", 3L),

			// Four
			new SpellCode("Four", "4", 4L),

			// Five
			new SpellCode("Five", "5", 5L),

			// Six
			new SpellCode("Six", "6", 6L),

			// Seven
			new SpellCode("Seven", "7", 7L),

			// Eight
			new SpellCode("Eight", "8", 8L),

			// Nine
			new SpellCode("Nine", "9", 9L),

			// Ten
			new SpellCode("Ten", "R", 10L),

			// Eleven
			new SpellCode("Eleven", "P", 11L),

			// Twelve
			new SpellCode("Twelve", "Q", 12L),

			// Thirteen
			new SpellCode("Thirteen", "K", 13L),

			// Fourteen
			new SpellCode("Fourteen", "U", 14L),

			// Fifteen
			new SpellCode("Fifteen", "Y", 15L),

			// Sixteen
			new SpellCode("Sixteen", "A", 16L),

			// Seventeen
			new SpellCode("Seventeen", "B", 17L),

			// Eighteen
			new SpellCode("Eighteen", "C", 18L),

			// Nineteen
			new SpellCode("Nineteen", "D", 19L),

			// Twenty
			new SpellCode("Twenty", "H", 20L),

			// Thirty
			new SpellCode("Thirty", "S", 30L),

			// Forty
			new SpellCode("Forty", "F", 40L),

			// Fifty
			new SpellCode("Fifty", "E", 50L),

			// Sixty
			new SpellCode("Sixty", "X", 60L),

			// Seventy
			new SpellCode("Seventy", "V", 70L),

			// Eighty
			new SpellCode("Eighty", "G", 80L),

			// Ninety
			new SpellCode("Ninety", "N", 90L),

			// Hundred
			new SpellCode("Hundred", "I", 100L),

			// Thousand
			new SpellCode("Thousand", "T", 1000L),

			// Million
			new SpellCode("Million", "M", 1000000L),

			// Billion
			new SpellCode("Billion", "J", 1000000000L),

			// Trillion
			new SpellCode("Trillion", "L", 1000000000000L),

			// Quadrillion
			new SpellCode("Quadrillion", "W", 1000000000000000L),

			// Quintillion
			new SpellCode("Quintillion", "Z", 1000000000000000000L),

			// and
			new SpellCode("and", "&"),

			// Minus
			new SpellCode("Minus", "-"),

			// Comma
			new SpellCode(",", ","),

			// dash
			new SpellCode("-", "_"),

	// last element
	}; // private static SpellCode[] myCodes

	/**
	 * The array containing the known and acceptable encoded patterns for all
	 * numeric values. The patterns are in the packed format which will be
	 * expanded to regular expression.
	 * 
	 * @see {@link #generatePattern(String)} the method which expands a pattern
	 *      definition from its packed format to ultimate regular expression.
	 * 
	 * @see {@link #myCodes} to check how categories: 'digit', 'odig', 'teen'
	 *      and 'oteen' are interpreted.
	 */
	private static PatternDefinition[] mySpellPatterns = {
			// SpellPattern begin

			// pattern name
			new PatternDefinition("zero", "0"),
			new PatternDefinition("digit", "[1-9]"),
			new PatternDefinition("odig", "[RPQKUYA-D]"),
			new PatternDefinition("teen", "$(digit)|$(odig)"),
			new PatternDefinition("oteen", "[HSFEXVGN](_?$(digit))?"),
			new PatternDefinition("cent", "$(teen)|$(oteen)"),
			new PatternDefinition("ocent", "$(digit)I(&?$(cent))?"),
			new PatternDefinition("mil", "$(cent)|$(ocent)"),
			new PatternDefinition("omil", "$(mil)T([,&]?$(mil))?"),
			new PatternDefinition("e3", "$(mil)|$(omil)"),
			new PatternDefinition("oe3", "$(mil)M([,&]?$(e3))?"),
			new PatternDefinition("e6", "$(e3)|$(oe3)"),
			new PatternDefinition("oe6", "$(mil)J([,&]?$(e6))?"),
			new PatternDefinition("e9", "$(e6)|$(oe6)"),
			new PatternDefinition("oe9", "$(mil)L([,&]?$(e9))?"),
			new PatternDefinition("e12", "$(e9)|$(oe9)"),
			new PatternDefinition("oe12", "$(mil)W([,&]?$(e12))?"),
			new PatternDefinition("e15", "$(e12)|$(oe12)"),
			new PatternDefinition("oe15", "$(mil)Z([,&]?$(e15))?"),
			new PatternDefinition("e18", "$(e15)|$(oe15)"),
			new PatternDefinition("num", "\\-?$(zero)|\\-?$(e18)"),

	// SpellPattern end
	};

	/**
	 * Represents a dictionary mapping a known spell word to its coding
	 * information.
	 */
	private static Hashtable<String, SpellCode> myNameMap;

	/**
	 * Represents a dictionary mapping a known spell single-character code to
	 * its coding information.
	 */
	private static Hashtable<String, SpellCode> myCodeMap;

	/**
	 * Represents a dictionary mapping a pattern variable name to its packed
	 * format definition.
	 */
	private static Hashtable<String, PatternDefinition> mySpellPatternMap;

	/**
	 * Holds the ultimate regular expression pattern to validate all encoded
	 * spelled number texts. It is the expanded value of the final pattern
	 * definition.
	 */
	private static String myNumberPattern;

	/**
	 * Initializes all definition and information arrays and dictionary which
	 * could not be done at declaration point.
	 * 
	 * @return a dummy true to set a dummy myInit static boolean variable.
	 */
	private static boolean init() {
		// Initialize dictionaries:
		myNameMap = new Hashtable<String, SpellCode>();
		myCodeMap = new Hashtable<String, SpellCode>();
		mySpellPatternMap = new Hashtable<String, PatternDefinition>();

		// Load dictionary data : myNameMap and myCodeMap
		for (SpellCode sc : myCodes) {
			myNameMap.put(sc.getName().toLowerCase(), sc);
			myCodeMap.put(sc.getCode(), sc);
		}

		// load dictionary data : mySpellPatternMap
		for (PatternDefinition pd : mySpellPatterns) {
			mySpellPatternMap.put(pd.getName(), pd);

		}

		// expand ultimate regular expression pattern to validate encoded
		// spelled number texts.
		try {
			myNumberPattern = "^\\-?" + generatePattern("num") + "$";

		} catch (SpellException e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * Validates the given encoded text as being a correctly spelled number with
	 * a valid grammar. Always throws exception on validation failure.
	 * 
	 * @param encodedText
	 *            The encoded text to be validated.
	 * @throws SpellException
	 *             if the encoded text is not a valid encoded spelled number.
	 */
	public void validate(String encodedText) throws SpellException {
		// Simply, compile the pattern.
		Pattern pat = Pattern.compile(myNumberPattern);

		// match the given encoded spelled number against the validation
		// pattern.
		Matcher mat = pat.matcher(encodedText);

		// If not matches, throw exception.
		if (!mat.matches()) {
			throw new SpellException("Invalid encoded text : " + encodedText);
		}
	}

	/**
	 * Generates a regular expression pattern from its pattern-definition name
	 * from its packed definition by recursive expansion of all other packed
	 * definition inside it.
	 * 
	 * @param name
	 *            The name of the pattern to expand.
	 * @return The regular expression expanded definition.
	 * @throws SpellException
	 *             if the definition with the given name does exist.
	 */
	private static String generatePattern(String name) throws SpellException {

		// first check if the name exists in dictionary.
		if (!mySpellPatternMap.containsKey(name))
			// if not exists, throw exception.
			throw new SpellException("Undefined pattern variable name : "
					+ name);

		// get the pattern.
		PatternDefinition pd = mySpellPatternMap.get(name);

		// get the nested definitions inside the definition:

		// The reg-exp pattern to extract variable names in definition.
		Pattern pat = Pattern.compile("\\$\\((\\w+)\\)");

		// match the packed format definition against the reg-exp for variable
		// name.
		Matcher mat = pat.matcher(pd.getPackedDefinition());
		String output = pd.getPackedDefinition();

		// while there is a nested variable name in the packed definition,
		while (mat.find()) {

			// expand it recursively,

			// Get the var name.
			String varName = mat.group(1);

			// create the replacement text
			String repText = String.format("\\$\\(%1$s\\)", varName);

			// Get the value corresponding the var name.
			String value = String.format("(?:%1$s)", generatePattern(mat
					.group(1)));

			// replace the packed definition variable with its equivalent
			// expanded value.
			output = output.replaceAll(repText, value);
		}

		// return the ultimately expanded reg-exp;
		return output;
	}

	/**
	 * A dummy variable to ensure that all static arrays and dictionaries are
	 * initialized before first use. Not used anywhere in code. It just makes
	 * sure that {@link #init()} has been invoked once and for all.
	 */
	@SuppressWarnings("unused")
	private static boolean myInit = init();
}