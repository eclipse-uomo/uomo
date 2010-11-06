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
package org.eclipse.uomo.units;

import org.unitsofmeasurement.quantity.Quantity;
import org.unitsofmeasurement.unit.Unit;

/**
 * <p> This interface represents the measurable, countable, or comparable 
 *     property or aspect of a thing.</p>
 *     
 * <p> Implementing instances are typically the result of a measurement:[code]
 *         Measurable<Mass> weight = BaseAmount.valueOf(180.0, POUND);
 *     [/code]
 *     They can also be created from custom classes:[code]
 *     class Delay implements Measurable<Duration> {
 *          private long nanoSeconds; // Implicit internal unit.
 *          public double doubleValue(Unit<Velocity> unit) { ... }
 *          public long longValue(Unit<Velocity> unit) { ... }
 *     }
 *     Thread.wait(new Delay(24, HOUR)); // Assuming Thread.wait(Measurable<Duration>) method.
 *     [/code]</p>
 *     
 * <p> Although measurable instances are for the most part scalar quantities; 
 *     more complex implementations (e.g. vectors, data set) are allowed as 
 *     long as an agregate magnitude can be determined. For example:[code]
 *     class Velocity3D implements Measurable<Velocity> {
 *          private double x, y, z; // Meter per seconds.
 *          public double doubleValue(Unit<Velocity> unit) { ... } // Returns vector norm.
 *          ... 
 *     }
 *     class Sensors<Q extends Quantity> extends QuantityAmount<double[], Q> {
 *          public doubleValue(Unit<Q> unit) { ... } // Returns median value. 
 *          ...
 *     } [/code]</p>
 * 
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author <a href="mailto:jcp@catmedia.us">Werner Keil</a>
 * @version 4.3, $Date: 2010-09-13 23:50:44 +0200 (Mo, 13 Sep 2010) $
 */
public interface Measurable<Q extends Quantity<Q>> extends Quantity<Q> {
    
    /**
     * Returns the estimated value of this measurable quantity stated 
     * in the specified unit as a <code>double</code>.
     * 
     * @param unit the unit in which the measurement value is stated.
     * @return the numeric value after conversion to type <code>double</code>.
     */
    double doubleValue(Unit<Q> unit);

    /**
     * Returns the estimated value of this quantity stated in the specified 
     * unit as a <code>long</code>.
     * 
     * @param unit the unit in which the measurement value is stated.
     * @return the numeric value after conversion to type <code>long</code>.
     * @throws ArithmeticException if this quantity cannot be represented 
     *         as a <code>long</code> number in the specified unit.
     */
    long longValue(Unit<Q> unit) throws ArithmeticException;
    
    /**
     * Get the unit (convenience to avoid cast).
     * @draft UOMo 0.5
     * @provisional This API might change or be removed in a future release.
     */
    Unit<Q> getQuantityUnit();
    
    /**
     * Returns the sum of this amount with the one specified.
     *
     * @param  that the amount to be added.
     * @return <code>this + that</code>.
     */
    Measurable<Q> plus(Measurable<Q> that);
    
    /**
     * Returns the difference between this amount and the one specified.
     *
     * @param  that the number to be subtracted.
     * @return <code>this - that</code>.
     */
    Measurable<Q> minus(Measurable<Q> that);
    
    /**
     * Returns the product of this amount with the one specified.
     *
     * @param  that the number multiplier.
     * @return <code>this · that</code>.
     */
    Measurable<?> times(Measurable<?> that);
    
    /**
     * Returns this amount divided by the one specified.
     *
     * @param  that the amount divisor.
     * @return <code>this / that</code>.
     */
    Measurable<Q> divide(Measurable<Q> that);
}
