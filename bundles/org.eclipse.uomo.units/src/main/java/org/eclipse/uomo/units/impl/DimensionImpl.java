/**
 * Copyright (c) 2005, 2013, Werner Keil, Ikayzo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil and others - initial API and implementation
 */
package org.eclipse.uomo.units.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.uomo.units.AbstractConverter;
import org.eclipse.uomo.units.AbstractUnit;
import org.eclipse.uomo.units.SI;
import javax.measure.quantity.Dimensionless;
import javax.measure.Dimension;
import javax.measure.Unit;
import javax.measure.UnitConverter;

/**
 * <p>
 * This class represents the dimension of an unit. Two units <code>u1</code> and
 * <code>u2</code> are {@linkplain Unit#isCompatible compatible} if and only if
 * <code>(u1.getDimension().equals(u2.getDimension())))</code>
 * </p>
 * 
 * <p>
 * Instances of this class are immutable.
 * </p>
 * 
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * 
 * @version 1.0.5, $Date: 2013-03-20 $
 * @see <a href="http://www.bipm.org/en/si/si_brochure/chapter1/1-3.html"> BIPM:
 *      SI Brochure Chapter 1.3</a>
 */
public final class DimensionImpl implements Dimension, Serializable {

	/**
	 * For cross-version compatibility.
	 */
	private static final long serialVersionUID = 2377803885472362640L;

	/**
	 * Holds the current physical model.
	 */
	private static Model model = Model.STANDARD;

	/**
	 * Holds dimensionless.
	 */
	public static final Dimension NONE = new DimensionImpl(AbstractUnit.ONE);

	/**
	 * Holds length dimension (L).
	 */
	public static final Dimension LENGTH = new DimensionImpl('L');

	/**
	 * Holds mass dimension (M).
	 */
	public static final Dimension MASS = new DimensionImpl('M');

	/**
	 * Holds time dimension (T).
	 */
	public static final Dimension TIME = new DimensionImpl('T');

	/**
	 * Holds electric current dimension (I).
	 */
	public static final Dimension ELECTRIC_CURRENT = new DimensionImpl('I');

	/**
	 * Holds temperature dimension (Θ). TODO use Theta again, currently not
	 * working (Bug 351656)
	 */
	public static final Dimension TEMPERATURE = new DimensionImpl('Q');

	/**
	 * Holds amount of substance dimension (N).
	 */
	public static final Dimension AMOUNT_OF_SUBSTANCE = new DimensionImpl('N');

	/**
	 * Holds luminous intensity dimension (J).
	 */
	public static final Dimension LUMINOUS_INTENSITY = new DimensionImpl('J');

	/**
	 * Holds the pseudo unit associated to this dimension.
	 */
	private final Unit<?> pseudoUnit;

	/**
	 * Creates a new dimension associated to the specified symbol.
	 * 
	 * @param symbol
	 *            the associated symbol.
	 */
	private DimensionImpl(char symbol) {
		pseudoUnit = new BaseUnit<Dimensionless>("[" + symbol + "]"); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Creates a dimension having the specified pseudo-unit (base unit or
	 * product of base unit).
	 * 
	 * @param pseudoUnit
	 *            the pseudo-unit identifying this dimension.
	 */
	private DimensionImpl(Unit<?> pseudoUnit) {
		this.pseudoUnit = pseudoUnit;
	}

	/**
	 * Returns the product of this dimension with the one specified.
	 * 
	 * @param that
	 *            the dimension multiplicand.
	 * @return <code>this * that</code>
	 */
	@Override
	public final Dimension multiply(Dimension that) {
		return new DimensionImpl(
				this.pseudoUnit.multiply(((DimensionImpl) that).pseudoUnit));
	}

	/**
	 * Returns the quotient of this dimension with the one specified.
	 * 
	 * @param that
	 *            the dimension divisor.
	 * @return <code>this / that</code>
	 */
	public final Dimension divide(Dimension that) {
		return new DimensionImpl(
				this.pseudoUnit.divide(((DimensionImpl) that).pseudoUnit));
	}

	/**
	 * Returns this dimension raised to an exponent.
	 * 
	 * @param n
	 *            the exponent.
	 * @return the result of raising this dimension to the exponent.
	 */
	public final Dimension pow(int n) {
		return new DimensionImpl(this.pseudoUnit.pow(n));
	}

	/**
	 * Returns the given root of this dimension.
	 * 
	 * @param n
	 *            the root's order.
	 * @return the result of taking the given root of this dimension.
	 * @throws ArithmeticException
	 *             if <code>n == 0</code>.
	 */
	public final Dimension root(int n) {
		return new DimensionImpl(this.pseudoUnit.root(n));
	}

	/**
	 * Returns the fundamental dimensions and their exponent whose product is
	 * this dimension or <code>null</code> if this dimension is a fundamental
	 * dimension.
	 * 
	 * @return the mapping between the fundamental dimensions and their
	 *         exponent.
	 */
	public Map<Dimension, Integer> getProductDimensions() {
		if (pseudoUnit == null)
			return null;
		@SuppressWarnings("unchecked")
		Map<? extends Unit<?>, Integer> pseudoUnits = (Map<? extends Unit<?>, Integer>) pseudoUnit
				.getProductUnits();
		Map<Dimension, Integer> fundamentalDimensions = new HashMap<Dimension, Integer>();
		for (Entry<? extends Unit<?>, Integer> entry : pseudoUnits.entrySet()) {
			fundamentalDimensions.put(new DimensionImpl(entry.getKey()),
					entry.getValue());
		}
		return fundamentalDimensions;
	}

	/**
	 * Returns the representation of this dimension.
	 * 
	 * @return the representation of this dimension.
	 */
	@Override
	public String toString() {
		return String.valueOf(pseudoUnit);
	}

	/**
	 * Indicates if the specified dimension is equals to the one specified.
	 * 
	 * @param that
	 *            the object to compare to.
	 * @return <code>true</code> if this dimension is equals to that dimension;
	 *         <code>false</code> otherwise.
	 */
	@Override
	public boolean equals(Object that) {
		if (this == that)
			return true;
		return (that instanceof DimensionImpl)
				&& pseudoUnit.equals(((DimensionImpl) that).pseudoUnit);
	}

	/**
	 * Returns the hash code for this dimension.
	 * 
	 * @return this dimension hashcode value.
	 */
	@Override
	public int hashCode() {
		return pseudoUnit.hashCode();
	}

	/**
	 * Sets the model used to determinate the units dimensions.
	 * 
	 * @param model
	 *            the new model to be used when calculating unit dimensions.
	 */
	public static void setModel(Model model) {
		DimensionImpl.model = model;
	}

	/**
	 * Returns the model used to determinate the units dimensions (default
	 * {@link Model#STANDARD STANDARD}).
	 * 
	 * @return the model used when calculating unit dimensions.
	 */
	public static Model getModel() {
		return DimensionImpl.model;
	}
	
	/**
	 * Creates a new dimension associated to the specified symbol.
	 * 
	 * @param symbol
	 *            the associated symbol.
	 */
	public static Dimension valueOf(char symbol) {
		return new DimensionImpl(symbol);
	}

	/**
	 * This interface represents the mapping between {@linkplain BaseUnit base
	 * units} and {@linkplain DimensionImpl dimensions}. Custom models may allow
	 * conversions not possible using the {@linkplain #STANDARD standard} model.
	 * For example:[code] public static void main(String[] args) {
	 * Dimension.Model relativistic = new Dimension.Model() { RationalConverter
	 * metreToSecond = new RationalConverter(BigInteger.ONE,
	 * BigInteger.valueOf(299792458)); // 1/c
	 * 
	 * public Dimension getDimension(BaseUnit unit) { if (unit.equals(METRE))
	 * return Dimension.TIME; return
	 * Dimension.Model.STANDARD.getDimension(unit); }
	 * 
	 * public UnitConverter getTransform(BaseUnit unit) { if
	 * (unit.equals(METRE)) return metreToSecond; return
	 * Dimension.Model.STANDARD.getTransform(unit); }};
	 * Dimension.setModel(relativistic);
	 * 
	 * // Converts 1.0 GeV (energy) to kg (mass).
	 * System.out.println(Unit.valueOf
	 * ("GeV").getConverterTo(KILOGRAM).convert(1.0)); }
	 * 
	 * > 1.7826617302520883E-27[/code]
	 */
	public interface Model {

		/**
		 * Holds the standard model (default).
		 */
		public Model STANDARD = new Model() {

			public Dimension getDimension(Unit<?> unit) {
				if (unit.equals(SI.METRE))
					return DimensionImpl.LENGTH;
				if (unit.equals(SI.KILOGRAM))
					return DimensionImpl.MASS;
				if (unit.equals(SI.KELVIN))
					return DimensionImpl.TEMPERATURE;
				if (unit.equals(SI.SECOND))
					return DimensionImpl.TIME;
				if (unit.equals(SI.AMPERE))
					return DimensionImpl.ELECTRIC_CURRENT;
				if (unit.equals(SI.MOLE))
					return DimensionImpl.AMOUNT_OF_SUBSTANCE;
				if (unit.equals(SI.CANDELA))
					return DimensionImpl.LUMINOUS_INTENSITY;
				return new DimensionImpl(new BaseUnit<Dimensionless>(
						"[" + unit.getSymbol() + "]")); //$NON-NLS-1$ //$NON-NLS-2$
			}

			public UnitConverter getTransform(Unit<?> unit) {
				return AbstractConverter.IDENTITY;
			}
		};

		/**
		 * Returns the dimension of the specified base unit (a dimension
		 * particular to the base unit if the base unit is not recognized).
		 * 
		 * @param unit
		 *            the base unit for which the dimension is returned.
		 * @return the dimension of the specified unit.
		 */
		Dimension getDimension(Unit<?> unit);

		/**
		 * Returns the normalization transform of the specified base unit (
		 * {@link UnitConverter#IDENTITY IDENTITY} if the base unit is not
		 * recognized).
		 * 
		 * @param unit
		 *            the base unit for which the transform is returned.
		 * @return the normalization transform.
		 */
		UnitConverter getTransform(Unit<?> unit);
	}
}
