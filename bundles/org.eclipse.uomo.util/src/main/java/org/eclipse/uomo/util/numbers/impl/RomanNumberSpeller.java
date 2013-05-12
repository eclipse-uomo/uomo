/**
 * Copyright (c) 1996, 2013, Werner Keil and others.
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

public class RomanNumberSpeller implements ISpeller {
	private static final long MAX_ROMAN = 3999;
	private static RomanNumberSpeller INSTANCE;
	
	// singleton
	private RomanNumberSpeller() {}
	
	/**
	 * @return the default instance
	 */
	public static final RomanNumberSpeller of() {
		if (INSTANCE == null) {
			INSTANCE = new RomanNumberSpeller();
		}
		return INSTANCE;
	}
	
    /**
     * Converts a decimal number into a Roman number
     * Valid input in the range 1-3999
     * Negative values are accepted and returned with a "-" in front.
     * Zero (0) is returned as an <strong>empty</strong> string ("").
     * <p>
     * if the argument is greater than the maximum (3999) an exception is thrown.
     *
     */
	public String spell(final long number) throws SpellException {
    	if (number > MAX_ROMAN) {
    		throw new SpellException(Messages.RomanNumberSpeller_0 + MAX_ROMAN);
    	} else {
    		return toRoman(number);
    	}
    }

    /**
     * Converts a decimal number into a Roman number
     * Valid input in the range 1-3999
     * Negative values are accepted and returned with a "-" in front.
     * Zero (0) is returned as an <strong>empty</strong> string ("").
     *
     */
    public final String toRoman(long n) {
        return internalToRoman(n);
    }
	
    /**
     * Converts a decimal number into a Roman number
     * Valid input in the range 1-3999
     * Negative values are accepted and returned with a "-" in front.
     * Zero (0) is returned as an <strong>empty</strong> string ("").
     *
     */
    public final String toRoman(int n) {
        return toRoman((long)n);
    }

    /**
     * Converts a numeric string into a Roman number
     * Valid input in the range 1-3999
     * Negative values are accepted and returned with a "-" in front.
     * Zero (0) is returned as an <strong>empty</strong> string ("").
     *
     */
    public final String toRoman(String s) {
        return toRoman(Integer.parseInt(s));
    }
    
    private final String internalToRoman(long number) {
        final StringBuilder roman = new StringBuilder(""); //$NON-NLS-1$
        long N = number;
        while (N >= 1000) {
              // Move 1000 from N to roman.
        	roman.append(Messages.RomanNumberSpeller_2);
        	N -= 1000;
        }
        while (N >= 900) {
              // Move 900 from N to roman.
        	roman.append(Messages.RomanNumberSpeller_3);
        	N -= 900;
        }
        while (N >= 500) {
            // Move 500 from N to roman.
        	roman.append(Messages.RomanNumberSpeller_4);
        	N -= 500;
        }
        while (N >= 400) {
            // Move 400 from N to roman.
        	roman.append(Messages.RomanNumberSpeller_5);
        	N -= 400;
        }
        while (N >= 100) {
            // Move 100 from N to roman.
         roman.append(Messages.RomanNumberSpeller_6);
         N -= 100;
        }
        while (N >= 90) {
            // Move 90 from N to roman.
        	roman.append(Messages.RomanNumberSpeller_7);
        	N -= 90;
        }
        while (N >= 50) {
            // Move 50 from N to roman.
        	roman.append(Messages.RomanNumberSpeller_8);
        	N -= 50;
        }
        while (N >= 40) {
            // Move 40 from N to roman.
        	roman.append(Messages.RomanNumberSpeller_9);
        	N -= 40;
        }
        while (N >= 10) {
            // Move 10 from N to roman.
        	roman.append(Messages.RomanNumberSpeller_10);
        	N -= 10;
        }
        if (N == 9) {
            // Move 9 from N to roman.
        	roman.append(Messages.RomanNumberSpeller_11);
        	N -= 9;
        }
        while (N >= 5) {
            // Move 5 from N to roman.
        	roman.append(Messages.RomanNumberSpeller_12);
        	N -= 5;
        }
        if (N == 4) {
        	// Move 4 from N to roman.
        	roman.append(Messages.RomanNumberSpeller_13);
        	N -= 4;
        }
        while (N >= 1) {
            // Move 1 from N to roman.
        	roman.append(Messages.RomanNumberSpeller_14);
        	N -= 1;
        }
        return roman.toString();
    }
	
	@Override
	public Long parse(String text) throws SpellException {
		return null;
	}

}