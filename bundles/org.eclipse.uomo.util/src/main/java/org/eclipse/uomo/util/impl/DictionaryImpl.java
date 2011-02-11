/**
 * Copyright (c) 2005, 2010, Werner Keil, Ikayzo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil, Ikayzo and others - initial API and implementation
 */
package org.eclipse.uomo.util.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.uomo.util.Dictionary;
import org.eclipse.uomo.util.DictionaryService;

@SuppressWarnings("deprecation")
public class DictionaryImpl implements DictionaryService {

	private List<String> fWords = new ArrayList<String>(Arrays.asList(new String[]{"osgi", "eclipse", "ucum"}));
	private String fLanguage = "English";
	
	public String getLanguage() {
		return fLanguage;
	}

	public boolean check(String word) {
		return fWords.contains(word);
	}
	
	public String toString() {
		return fLanguage;
	}

	public void registerDictionary(Dictionary dictionary) {
		// TODO Auto-generated method stub
		
	}

	public void unregisterDictionary(Dictionary dictionary) {
		// TODO Auto-generated method stub
		
	}

}
