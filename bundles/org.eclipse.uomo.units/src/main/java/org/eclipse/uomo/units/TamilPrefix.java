/**
 * Copyright (c) 2005, 2010, Werner Keil, JScience and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.units;

//import com.ibm.icu.text.n
import java.math.BigInteger;

import org.eclipse.uomo.units.impl.RationalConverter;
import org.unitsofmeasurement.quantity.Quantity;
import org.unitsofmeasurement.unit.Unit;


/**
 * Utility class holding prefixes used today in India, Pakistan, Bangladesh, Nepal
 * and Myanmar (Burma); based on grouping by two decimal places, rather than the
 * three decimal places common in most parts of the world. [code] import static
 * javax.measure.unit.NonSI.IndianPrefix.*; // Static import. ... Unit<Pressure>
 * LAKH_PASCAL = LAKH(PASCAL); Unit<Length> CRORE_METER = CRORE(METER); [/code]
 * 
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 1.0 ($Revision$), $Date: 2010-11-28 $
 * @see <a
 *      href="http://en.wikipedia.org/wiki/Tamil_units_of_measurement#Whole_numbers">Wikipedia:
 *      Tamil units of measurement - Whole numbers</a>
 */
public final class TamilPrefix  {

	private TamilPrefix() {
		// Utility class no visible constructor.
	}

	/**
	 * <p>
	 * onRu
	 * </p>
	 * Returns the specified unit multiplied by the factor <code>1</code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1)</code>.
	 */
	public static final <Q extends Quantity<?>> Unit<?> onRu(Unit<?> unit) {
		return unit;
	}
	
	/**
	 * <p>
	 * patthu
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>1</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(10)</code>.
	 */
	public static final <Q extends Quantity<?>> Unit<?> patthu(Unit<?> unit) {
		return unit.transform(E1);
	}

	/**
	 * <p>
	 * nooRu
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>2</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(100)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<?> nooRu(Unit<?> unit) {
		return unit.transform(E2);
	}

	/**
	 * <p>
	 * aayiram
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>3</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1e3)</code>.
	 */
	public static final <Q extends Quantity<?>> Unit<?> aayiram(Unit<?> unit) {
		return unit.transform(E3);
	}

	/**
	 * <p>
	 * nooRaayiram
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>5</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1e5)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<?> nooRaayiram(Unit<?> unit) {
		return unit.transform(E5);
	}

	static final RationalConverter E5 = new RationalConverter(
			BigInteger.TEN.pow(5), BigInteger.ONE);

	/**
	 * <p>
	 * thoLLunn
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>9</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1e9)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<?> thoLLunn(Unit<?> unit) {
		return unit.transform(E9);
	}

	/**
	 * <p>
	 * eegiyam
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>12</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1e12)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<?> eegiyam(Unit<?> unit) {
		return unit.transform(E12);
	}

	/**
	 * <p>
	 * neLai
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>15</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1e15)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<?> neLai(Unit<?> unit) {
		return unit.transform(E15);
	}

	/**
	 * <p>
	 * iLanji
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>18</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1e18)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<?> iLanji(Unit<?> unit) {
		return unit.transform(E18);
	}

	/**
	 * <p>
	 * veLLam
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>20</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1e20)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<?> veLLam(Unit<?> unit) {
		return unit.transform(E20);
	}
	
	/**
	 * <p>
	 * aambal
	 * </p>
	 * Returns the specified unit multiplied by the factor
	 * <code>10<sup>21</sup></code>
	 * 
	 * @param unit
	 *            any unit.
	 * @return <code>unit.times(1e21)</code>.
	 */
	public static final <Q extends Quantity<Q>> Unit<?> aambal(Unit<?> unit) {
		return unit.transform(E21);
	}
	
	public static final class Sanskrit {
		/**
		 * <p>
		 * ONDRU -one
		 * </p>
		 * Sanskrit translation for {@link #onRu}.
		 */
		public static final <Q extends Quantity<?>> Unit<?> ONDRU (Unit<?> unit) {
			return onRu(unit);
		}
		
		/**
		 * <p>
		 * PATHU -ten
		 * </p>
		 * Sanskrit translation for {@link #patthu}.
		 */
		public static final <Q extends Quantity<?>> Unit<?> PATHU(Unit<?> unit) {
			return patthu(unit);
		}
	}

	// Holds prefix converters (optimization).
	private static RationalConverter E21 = new RationalConverter(
			BigInteger.TEN.pow(21), BigInteger.ONE);
	private static RationalConverter E20 = new RationalConverter(
			BigInteger.TEN.pow(20), BigInteger.ONE);
	private static RationalConverter E18 = new RationalConverter(
			BigInteger.TEN.pow(18), BigInteger.ONE);
	private static RationalConverter E15 = new RationalConverter(
			BigInteger.TEN.pow(15), BigInteger.ONE);
	private static RationalConverter E12 = new RationalConverter(
			BigInteger.TEN.pow(12), BigInteger.ONE);
	private static RationalConverter E9 = new RationalConverter(
			BigInteger.TEN.pow(9), BigInteger.ONE);
	private static RationalConverter E3 = new RationalConverter(
			BigInteger.TEN.pow(3), BigInteger.ONE);
	private static RationalConverter E2 = new RationalConverter(
			BigInteger.TEN.pow(2), BigInteger.ONE);
	private static RationalConverter E1 = new RationalConverter(
			BigInteger.TEN.pow(1), BigInteger.ONE);
}
