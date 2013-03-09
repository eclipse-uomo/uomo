/*******************************************************************************
 * Crown Copyright (c) 2006, 2011, Copyright (c) 2006, 2008 Kestral Computing P/L.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Kestral Computing P/L - initial implementation
 *    Werner Keil - generic improvements
 *******************************************************************************/

package org.eclipse.uomo.ucum.impl;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.uomo.core.IListValidator;
import org.eclipse.uomo.core.UOMoException;
import org.eclipse.uomo.core.UOMoRuntimeException;
import org.eclipse.uomo.ucum.expression.Term;
import org.eclipse.uomo.ucum.model.DefinedUnit;
import org.eclipse.uomo.ucum.model.UcumModel;
import org.eclipse.uomo.ucum.model.UcumUnit;
import org.eclipse.uomo.ucum.parsers.ExpressionComposer;
import org.eclipse.uomo.ucum.parsers.ExpressionParser;
import org.eclipse.uomo.util.Registry;

/**
 * @author Grahame Grieve
 * @author Werner Keil
 * @version 1.1 ($Revision: 306 $), $Date: 2013-03-08 $
 */
public class UcumValidator implements IListValidator<String> {

	private final UcumModel model;
	private final Registry<?> handlers;
	private List<String> result;

	public UcumValidator(UcumModel model, Registry<?> handlers) {
		super();
		this.model = model;
		this.handlers = handlers;
	}

	public List<String> validate() {
		result = new ArrayList<String>();
		checkCodes();
		checkUnits();
		return result;
	}

	private void checkCodes() {
		for (UcumUnit unit : model.getBaseUnits()) {
			checkUnitCode(unit.getCode(), true);
		}
		for (UcumUnit unit : model.getDefinedUnits()) {
			checkUnitCode(unit.getCode(), true);
		}
	}

	private void checkUnits() {
		for (DefinedUnit unit : model.getDefinedUnits()) {
			if (!unit.isSpecial())
				checkUnitCode(unit.getValue().getUnit(), false);
			else if (!handlers.exists(unit.getCode()))
				result.add("No Handler for " + unit.getCode().toString());
		}
	}

	private void checkUnitCode(String code, boolean primary) {
		try {
			Term term = new ExpressionParser(model).parse(code);
			String c = new ExpressionComposer().compose(term);
			if (!c.equals(code))
				result.add("Round trip failed: " + code + " -> " + c);
			term = new UcumConverter(model, handlers).convert(term).getUnit();
		} catch (UOMoRuntimeException e) {
			result.add(code + ": " + e.getMessage());
		}
		if (primary)
			try {
				// there can't be any codes that have digits in them that aren't
				// inside []
				boolean inBrack = false;
				boolean nonDigits = false;
				for (int i = 0; i < code.length(); i++) {
					char ch = code.charAt(i);
					if (ch == '[')
						if (inBrack)
							throw new UOMoRuntimeException("nested [");
						else
							inBrack = true;
					if (ch == ']')
						if (!inBrack)
							throw new UOMoRuntimeException("] without [");
						else
							inBrack = false;
					nonDigits = nonDigits || !(ch >= '0' && ch <= '9');
					if (ch >= '0' && ch <= '9' && !inBrack && nonDigits) {
						throw new UOMoException(
								"code "
										+ code
										+ " is ambiguous because  it has digits outside []");
					}
				}
			} catch (UOMoException e) {
				result.add(e.getMessage());
			}
	}
}
