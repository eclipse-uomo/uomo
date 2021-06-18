/*******************************************************************************
 * Crown Copyright (c) 2006, 2008, Copyright (c) 2006, 2021 Kestral Computing P/L and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Kestral Computing P/L - initial implementation
 *******************************************************************************/

package org.eclipse.uomo.ucum.model;

import java.math.BigDecimal;

// FIXME consider changing name to UcumPrefix to avoid confusion with JSR type
public class Prefix extends Concept {
	
	/**
	 * value for the prefix.  
	 */
	private BigDecimal value; // 1^-24 through to 1^24

	/**
	 * @param code
	 * @param codeUC
	 */
	public Prefix(String code, String codeUC) {
		super(ConceptKind.PREFIX, code, codeUC);
	}

	/**
	 * @return the index
	 */
	public Number getValue() {
		return value;
	}

	/**
	 * @param index the index to set
	 */
	public void setValue(BigDecimal index) {
		this.value = index;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ohf.ucum.model.Concept#getDescription()
	 */
	@Override
	public String getDescription() {
		return super.getDescription()+" = "+value.toEngineeringString();
	}
	
	
	

}
