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
/**
 * Provides support for programatic unit handling.
 *
 * <h3> Standard/NonStandard Units</h3>
 *      Standard units and prefixes are provided by the
 *      {@link org.eclipse.uomo.units.SI} class (Système International d'Unités) and
 *      about 50 non-standard units are available through the
 *      {@link org.eclipse.uomo.units.impl.system.USCustomary} class.
 *
 * <h3>Usage examples:</h3>
 * [code]
 *
 * import static org.eclipse.uomo.units.SI.*;
 * import static org.eclipse.uomo.units.impl.system.USCustomary.*;

 * public class Main {
 *     public void main(String[] args) {
 *
 *         // Conversion between units (explicit way).
 *         Unit<Length> sourceUnit = KILO(METRE);
 *         Unit<Length> targetUnit = MILE;
 *         UnitConverter uc = sourceUnit.getConverterTo(targetUnit);
 *         System.out.println(uc.convert(10)); // Converts 10 km to miles.
 *
 *         // Same conversion than above, packed in one line.
 *         System.out.println(KILO(METRE).getConverterTo(MILE).convert(10));
 *
 *         // Retrieval of the system unit (identifies the measurement type).
 *         System.out.println(REVOLUTION.divide(MINUTE).toMetric());
 *
 *         // Dimension checking (allows/disallows conversions)
 *         System.out.println(ELECTRON_VOLT.isCompatible(WATT.times(HOUR)));
 *
 *         // Retrieval of the unit dimension (depends upon the current model).
 *         System.out.println(ELECTRON_VOLT.getDimension());
 *     }
 * }
 *
 * > 6.2137119223733395
 * > 6.2137119223733395
 * > rad/s
 * > true
 * > [L]²·[M]/[T]²
 * [/code]
 *
 * <h3>Unit Parameterization</h3>
 *
 *     Units are parameterized (e.g. &lt;Time extends {@link org.unitsofmeasure.Quantity Quantity}>) to
 *     enforce compile-time checks of units/measures consistency, for example:[code]
 *
 *     Unit<Time> MINUTE = SECOND.times(60); // Ok.
 *     Unit<Time> MINUTE = METRE.times(60); // Compile error.
 *
 *     Unit<Pressure> HECTOPASCAL = HECTO(PASCAL); // Ok.
 *     Unit<Pressure> HECTOPASCAL = HECTO(NEWTON); // Compile error.
 *
 *     TimeAmount duration = new TimeAmount(2, MINUTE); // Ok.
 *     TimeAmount duration = new TimeAmount(2, CELSIUS); // Compile error.
 *
 *     long milliseconds = duration.longValue(MILLI(SECOND)); // Ok.
 *     long milliseconds = duration.longValue(POUND); // Compile error.
 *     [/code]
 *
 *     Runtime checks of dimension consistency can be done for more complex cases.
 *
 *     [code]
 *     Unit<Area> SQUARE_FOOT = FOOT.times(FOOT).asType(Area.class); // Ok.
 *     Unit<Area> SQUARE_FOOT = FOOT.times(KELVIN).asType(Area.class); // Runtime error.
 *
 *     Unit<Temperature> KELVIN = Unit.valueOf("K").asType(Temperature.class); // Ok.
 *     Unit<Temperature> KELVIN = Unit.valueOf("kg").asType(Temperature.class); // Runtime error.
 *     [/code]
 *     </p>
 *
 * @author  <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @author  <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 0.5.1, $Date: 2010-08-10 07:04:41 +0100 (Di, 10 Aug 2010) $
 */
package org.eclipse.uomo.units;
