/**
 * Copyright (c) 2005, 2010, Werner Keil, Ikayzo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.util;

/**
 * @author Werner Keil
 * OSGi demo.
 * 
 * @deprecated change this into something more useful for UOM, e.g. Conversion
 */
public interface Dictionary {
	
    /**
     * Returns the language of the dictionary
     *
     * @return the language of the dictionary
     */
    public String getLanguage();
	
    /**
     * Check for the existence of a word in the dictionary
     * 
     * @param word the word to be checked.
     * @return true if the word is in the dictionary
     */
    public boolean check(String word);

}
