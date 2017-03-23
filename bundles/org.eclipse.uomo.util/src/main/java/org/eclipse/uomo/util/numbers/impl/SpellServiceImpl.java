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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.uomo.util.numbers.ISpeller;
import org.eclipse.uomo.util.numbers.SpellService;

public class SpellServiceImpl implements SpellService {

	private final List<ISpeller> spellers = new ArrayList<ISpeller>();

	@Override
	public void registerSpeller(ISpeller speller) {
		spellers.add(speller);
	}

	@Override
	public void unregisterSpeller(ISpeller speller) {
		spellers.remove(speller);
	}

}
