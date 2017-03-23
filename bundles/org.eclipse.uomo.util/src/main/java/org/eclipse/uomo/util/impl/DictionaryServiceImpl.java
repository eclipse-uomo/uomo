/**
 * Copyright (c) 2005, 2013, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil and others - initial API and implementation
 */
package org.eclipse.uomo.util.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.uomo.util.Dictionary;
import org.eclipse.uomo.util.DictionaryService;

public class DictionaryServiceImpl implements DictionaryService {

	private final List<Dictionary> fDictionaries = new ArrayList<Dictionary>();

	public void registerDictionary(Dictionary dictionary) {
		fDictionaries.add(dictionary);
	}

	public void unregisterDictionary(Dictionary dictionary) {
		fDictionaries.remove(dictionary);
	}

	public boolean check(String word) {
		for (int i = 0; i < fDictionaries.size(); i++) {
			Dictionary dictionary = (Dictionary) fDictionaries.get(i);
			if (dictionary.check(word))
				return true;
		}
		return false;
	}

}
