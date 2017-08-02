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
package org.eclipse.uomo.units.impl.format;

/**
 * Defines the different variants of unit formatting.
 * 
 * @author Werner Keil
 * @version 1.0
 * @since 0.7
 */
public enum UnitStyle {

  /**
   * The unit will be rendered as its name.
   * 
   * @see Unit#getName()
   */
  NAME,

  /**
   * The unit will be rendered as its symbol.
   * 
   * @see Unit#getSymbol()
   */
  SYMBOL,

  /**
   * The unit will be rendered as its label.
   * 
   * @see UnitFormat#label()
   */
  LABEL,

  /**
   * The unit will be rendered as its symbol and also labeled.
   * 
   * @see Unit#getSymbol()
   * @see UnitFormat#label()
   */
  SYMBOL_AND_LABEL
}