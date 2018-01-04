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
 * Defines different ways of formatting.
 * 
 * @author Werner Keil
 * @version 1.0
 * @since 0.7
 */
public enum FormatBehavior {

  /**
   * Formatting occurs in a locale-neutral way.
   * 
   */
  LOCALE_NEUTRAL,

  /**
   * Formatting occurs in a locale-sensitive way.
   * 
   * @see java.util.Locale
   */
  LOCALE_SENSITIVE
}