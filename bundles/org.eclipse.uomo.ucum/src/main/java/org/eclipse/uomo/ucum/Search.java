/**
 * Copyright (c) 2010, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.ucum;

import java.util.List;

import org.eclipse.uomo.ucum.model.Concept;
import org.eclipse.uomo.ucum.model.ConceptKind;
import org.eclipse.uomo.ucum.model.UcumModel;

public interface Search {
	public List<Concept> doSearch(UcumModel model, ConceptKind kind,
			String text, boolean isRegex);
}
