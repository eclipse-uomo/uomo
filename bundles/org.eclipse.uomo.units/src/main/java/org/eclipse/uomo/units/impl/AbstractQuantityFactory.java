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

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.spi.QuantityFactory;

/**
 * An abstract factory producing simple quantities instances (tuples {@link Number}/ {@link Unit}).
 *
 * For example:<br>
 * <code>
 *      Mass m = QuantityFactoryImpl.of(Mass.class).create(23.0, KILOGRAM); // 23.0 kg<br>
 *      Time m = QuantityFactoryImpl.of(Time.class).create(124, MILLI(SECOND)); // 124 ms
 * </code>
 * 
 * @param <Q>
 *          The type of the quantity.
 *
 * @author <a href="mailto:martin.desruisseaux@geomatys.com">Martin Desruisseaux</a>
 * @author <a href="mailto:units@catmedia.us">Werner Keil</a>
 * @author <a href="mailto:jean-marie@dautelle.com">Jean-Marie Dautelle</a>
 * @version 1.0.0, $Date: 2017-06-16 $
 * @since 0.7
 */
abstract class AbstractQuantityFactory<Q extends Quantity<Q>> implements QuantityFactory<Q> {

  /**
   * Holds the current instances.
   */
  @SuppressWarnings("rawtypes")
  static final Map<Class, QuantityFactory> INSTANCES = new HashMap<Class, QuantityFactory>();

  static final Logger logger = Logger.getLogger(AbstractQuantityFactory.class.getName());

  static final Level LOG_LEVEL = Level.FINE;

  /**
   * Overrides the default implementation of the factory for the specified quantity type.
   *
   * @param <Q>
   *          The type of the quantity
   * @param type
   *          the quantity type
   * @param factory
   *          the quantity factory
   */
  protected static <Q extends Quantity<Q>> void setInstance(final Class<Q> type, QuantityFactory<Q> factory) {
    if (!Quantity.class.isAssignableFrom(type))
      // This exception is not documented because it should never happen
      // if the
      // user don't try to trick the Java generic types system with unsafe
      // cast.
      throw new ClassCastException();
    INSTANCES.put(type, factory);
  }
}