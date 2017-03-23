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
 * @deprecated change this into something more useful for UOM, e.g. ConversionService
 */
public interface DictionaryService {
	
    /**
     * Register a dictionary
     * 
     * @param dictionary the dictionary to be added.
     */
    public void registerDictionary(Dictionary dictionary);
    
    /**
     * Remove a dictionary
     * 
     * @param dictionary the dictionary to be removed.
     */
    public void unregisterDictionary(Dictionary dictionary);
	
    /**
     * Check for the existence of a word across all dictionaries
     * 
     * @param word the word to be checked.
     * @return true if the word is in any dictionary
     */
    public boolean check(String word);

}
