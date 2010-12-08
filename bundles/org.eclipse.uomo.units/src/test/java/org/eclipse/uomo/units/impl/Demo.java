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
package org.eclipse.uomo.units.impl;

import static org.eclipse.uomo.units.SI.*;

import org.eclipse.uomo.units.impl.quantity.LengthAmount;
import org.eclipse.uomo.units.impl.quantity.MassAmount;
import org.unitsofmeasurement.quantity.Length;

/**
 * @author <a href="mailto:desruisseaux@users.sourceforge.net">Martin Desruisseaux</a>
 * @author <a href="mailto:jcp@catmedia.us">Werner Keil</a>
 * @version 0.3 ($Revision: 210 $), $Date: 2010-02-25 23:34:46 +0100 (Do, 25 Feb 2010) $
 */
public class Demo {

	private static Length getSomeLength() {
        return new LengthAmount(20, METRE);
    }

	private static MassAmount getSomeMass() {
        return new MassAmount(30, KILOGRAM);
    }

    public static void main(String[] args) {
        Length someLength = getSomeLength();
        System.out.println("toString = " + someLength);
        System.out.println();

        MassAmount someMass = getSomeMass();
        System.out.println("toString = " + someMass);
    }
}
