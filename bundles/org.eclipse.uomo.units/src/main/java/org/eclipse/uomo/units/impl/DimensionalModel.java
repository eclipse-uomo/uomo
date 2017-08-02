/*
 * Copyright (c) 2005, 2017, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.units.impl;

import javax.measure.Dimension;
import javax.measure.UnitConverter;

import org.eclipse.uomo.units.AbstractConverter;

import java.util.Map;

/**
 * <p>
 * This class represents the physical model used for dimensional analysis.
 * </p>
 *
 * <p>
 * In principle, dimensions of physical quantities could be defined as "fundamental" (such as momentum or energy or electric current) making such
 * quantities uncommensurate (not comparable). Modern physics has cast doubt on the very existence of incompatible fundamental dimensions of physical
 * quantities. For example, most physicists do not recognize temperature, {@link QuantityDimension#TEMPERATURE Θ}, as a fundamental dimension since it
 * essentially expresses the energy per particle per degree of freedom, which can be expressed in terms of energy (or mass, length, and time). To
 * support, such model the method {@link #getConverter} may returns a non-null value for distinct dimensions.
 * </p>
 * 
 * <p>
 * The default model is {@link StandardModel Standard}. Applications may use one of the predefined model or create their own. <code>
 *     DimensionalModel relativistic = new DimensionalModel() {
 *         public Dimension getFundamentalDimension(Dimension dimension) {
 *             if (dimension.equals(QuantityDimension.LENGTH)) return QuantityDimension.TIME; // Consider length derived from time.
 *                 return super.getDimension(dimension); // Returns product of fundamental dimension.
 *             }
 *             public UnitConverter getDimensionalTransform(Dimension dimension) {
 *                 if (dimension.equals(QuantityDimension.LENGTH)) return new RationalConverter(1, 299792458); // Converter (1/C) from LENGTH SI unit (m) to TIME SI unit (s).
 *                 return super.getDimensionalTransform(dimension);
 *             }
 *     };
 *     try {
 *         DimensionalModel.setCurrent(relativistic); // Current thread use the relativistic model.
 *         Units.KILOGRAM.getConverterToAny(Units.JOULE); // Allowed.
 *         ...
 *     } finally {
 *        cleanup();
 *     }
 *     </code>
 * </p>
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Dimensional_analysis">Wikipedia: Dimensional Analysis</a>
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @version 0.5.5, $Date: 2015-07-25 $
 */
public abstract class DimensionalModel {

  /**
   * Holds the current model.
   */
  private static DimensionalModel currentModel = new StandardModel();

  /**
   * Returns the current model (by default an instance of {@link StandardModel}).
   *
   * @return the current dimensional model.
   */
  public static DimensionalModel current() {
    return currentModel;
  }

  /**
   * Sets the current dimensional model
   *
   * @param model
   *          the new current model.
   * @see #current
   */
  protected static void setCurrent(DimensionalModel model) {
    currentModel = model;
  }

  /**
   * DefaultQuantityFactory constructor (allows for derivation).
   */
  protected DimensionalModel() {
  }

  /**
   * Returns the fundamental dimension for the one specified. If the specified dimension is a dimensional product, the dimensional product of its
   * fundamental dimensions is returned. Physical quantities are considered commensurate only if their fundamental dimensions are equals using the
   * current physics model.
   *
   * @param dimension
   *          the dimension for which the fundamental dimension is returned.
   * @return <code>this</code> or a rational product of fundamental dimension.
   */
  public Dimension getFundamentalDimension(Dimension dimension) {
    Map<? extends Dimension, Integer> dimensions = dimension.getBaseDimensions();
    if (dimensions == null)
      return dimension; // Fundamental dimension.
    // Dimensional Product.
    Dimension fundamentalProduct = QuantityDimension.NONE;
    for (Map.Entry<? extends Dimension, Integer> e : dimensions.entrySet()) {
      fundamentalProduct = fundamentalProduct.multiply(this.getFundamentalDimension(e.getKey())).pow(e.getValue());
    }
    return fundamentalProduct;
  }

  /**
   * Returns the dimensional transform of the specified dimension. If the specified dimension is a fundamental dimension or a product of fundamental
   * dimensions the identity converter is returned; otherwise the converter from the system unit (SI) of the specified dimension to the system unit
   * (SI) of its fundamental dimension is returned.
   *
   * @param dimension
   *          the dimension for which the dimensional transform is returned.
   * @return the dimensional transform (identity for fundamental dimensions).
   */
  public UnitConverter getDimensionalTransform(Dimension dimension) {
    Map<? extends Dimension, Integer> dimensions = dimension.getBaseDimensions();
    if (dimensions == null)
      return AbstractConverter.IDENTITY; // Fundamental dimension.
    // Dimensional Product.
    UnitConverter toFundamental = AbstractConverter.IDENTITY;
    for (Map.Entry<? extends Dimension, Integer> e : dimensions.entrySet()) {
    	UnitConverter cvtr = this.getDimensionalTransform(e.getKey());
      if (!(cvtr.isLinear()))
        throw new UnsupportedOperationException("Non-linear dimensional transform");
      int pow = e.getValue();
      if (pow < 0) { // Negative power.
        pow = -pow;
        cvtr = cvtr.inverse();
      }
      for (int j = 0; j < pow; j++) {
        toFundamental = toFundamental.concatenate(cvtr);
      }
    }
    return toFundamental;
  }
}
