/**
 * Copyright (c) 2005, 2020, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.icu.types;

import static org.eclipse.uomo.icu.types.BDTHelper.Operation.*;

import java.math.BigDecimal;

import javax.measure.Unit;

import tech.units.indriya.AbstractQuantity;

/**
 * This class describes Percent amounts
 * @version 1.1
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 */
public class PercentAmount extends AbstractQuantity<Percent> implements IBasicType, Percent {
	final static long serialVersionUID = 362498820763181265L;

	final static int precision = 15; // will be held, but not set

	final BigDecimal percentValue;
	
	PercentAmount(char[] c, Unit<Percent> unit) {
		super(new BigDecimal(c), unit);
		percentValue = calcPercent(String.valueOf(c));
	}

	public PercentAmount(BigDecimal bd, Unit<Percent> unit) {
		super(bd, unit);
		percentValue = calcPercent(bd);
	}

	public PercentAmount(String s, Unit<Percent> unit) {
		this(new BigDecimal(s), unit);
	}

	/**
	 * Calculate a BigDecimal value for a Percent e.g. "3" (3 percent) will
	 * generate an array containing .03
	 * 
	 * @return java.math.BigDecimal
	 * @param s
	 *            java.lang.String
	 */
	static BigDecimal calcPercent(BigDecimal b) {
		BigDecimal temp = new BigDecimal("100"); //$NON-NLS-1$
		return b.divide(temp, BDTHelper.MATH_CONTEXT); // we now have .03
	}
	
	/**
	 * Calculate a BigDecimal value for a Percent e.g. "3" (3 percent) will
	 * generate an array containing .03
	 * 
	 * @return java.math.BigDecimal
	 * @param s
	 *            java.lang.String
	 */
	static BigDecimal calcPercent(String s) {
		return calcPercent(new BigDecimal(s));
	}

	/**
	 * Check if quantity is positive (>0)
	 * 
	 * @return boolean
	 */
	public boolean isPositive() {
		return +1 == ((BigDecimal)getValue()).compareTo(BigDecimal.ZERO);
	}

	/**
	 * Compare to see if <code> this </code> Percent amount is equal to
	 * parameter
	 * 
	 * @return boolean
	 * @param y
	 *            com.jpmorrsn.jbdtypes.Percent - second Percent
	 */
	public boolean eq(PercentAmount y) {
		return BDTHelper.comp(this, y, EQ);
	}

	/**
	 * Compare to see if <code> this </code> Percent amount is greater than or
	 * equal to parameter
	 * 
	 * @return boolean
	 * @param y
	 *            com.jpmorrsn.jbdtypes.Percent - second Percent
	 */
	public boolean ge(PercentAmount y) {
		return BDTHelper.comp(this, y, GE);
	}

	/**
	 * Compare to see if <code> this </code> Percent amount is greater than
	 * parameter
	 * 
	 * @return boolean
	 * @param y
	 *            com.jpmorrsn.jbdtypes.Percent - second Percent
	 */
	public boolean gt(PercentAmount y) {
		return BDTHelper.comp(this, y, GT);
	}

	/**
	 * Compare to see if <code> this </code> Percent amount is less or equal to
	 * parameter
	 * 
	 * @return boolean
	 * @param y
	 *            com.jpmorrsn.jbdtypes.Percent - second Percent
	 */
	public boolean le(PercentAmount y) {
		return BDTHelper.comp(this, y, LE);
	}

	/**
	 * Compare to see if <code> this </code> Percent amount is less than
	 * parameter
	 * 
	 * @return boolean
	 * @param y
	 *            com.jpmorrsn.jbdtypes.Percent - second Percent
	 */
	public boolean lt(PercentAmount y) {
		return BDTHelper.comp(this, y, LT);
	}

	/**
	 * Compare to see if <code> this </code> Percent amount is not equal to
	 * parameter
	 * 
	 * @return boolean
	 * @param y
	 *            com.jpmorrsn.jbdtypes.Percent - second Percent
	 */
	public boolean ne(PercentAmount y) {
		return BDTHelper.comp(this, y, NE);
	}

	/**
	 * Generate a 'preference neutral' string from Percent value.
	 * 
	 * @return java.lang.String
	 */
	public String serialize() {
		BigDecimal temp = (BigDecimal) getValue();
		temp = temp.multiply(BigDecimal.TEN, BDTHelper.MATH_CONTEXT);
		temp = temp.multiply(BigDecimal.TEN, BDTHelper.MATH_CONTEXT);
		temp = temp.divide(BigDecimal.ONE, BDTHelper.MATH_CONTEXT);
		return temp.toString();

	}

	/**
	 * Create a String from this object
	 * 
	 * @return java.lang.String
	 */
	public String toString() {
		return serialize();
	}
}
