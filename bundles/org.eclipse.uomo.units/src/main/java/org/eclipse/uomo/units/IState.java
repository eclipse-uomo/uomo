/**
 * Copyright (c) 2005, 2017, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.units;

import tec.uom.lib.common.function.Nameable;
import tec.uom.lib.common.function.ValueSupplier;
import javax.measure.Quantity;
import javax.measure.quantity.Time;

/**
 * Groups a state name, value and timestamp.
 * The state itself is represented as a <type>Number</type> and the time is measured in Quantity<Time>
 * A State object is immutable so that it may be easily shared.
 * 
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version 1.2, $Date: 2017-11-23 $
 */
public interface IState<Q extends Quantity<Q>> extends Nameable, ValueSupplier<Quantity<Q>> {
	Quantity<Time> time();
	/** @deprecated use #getValue() instead */
	Quantity<Q> value(); // TODO get* vs. getter-less, also avoid value().value()
}
