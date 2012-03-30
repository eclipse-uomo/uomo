/**
 * Copyright (c) 2005, 2011, Werner Keil, Ikayzo and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil, Ikayzo and others - initial API and implementation
 */
package org.eclipse.uomo.units.impl;

import static org.eclipse.uomo.units.SI.GRAM;
import static org.eclipse.uomo.units.SI.KILOGRAM;
import static org.eclipse.uomo.units.SI.Prefix.*;

import java.util.Random;
import java.util.logging.Logger;

import org.eclipse.uomo.units.impl.quantity.MassAmount;
import org.unitsofmeasurement.quantity.Quantity;
import org.unitsofmeasurement.unit.IncommensurableException;
import org.unitsofmeasurement.unit.UnconvertibleException;
import org.unitsofmeasurement.unit.Unit;
import org.unitsofmeasurement.unit.UnitConverter;

/**
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version $Revision$, $Date$
 * @param <Q> a quantity
 */
public class Benchmark<Q extends Quantity<Q>> {
    // Create logger object (usually with the class name)
	private static Logger benchmarkLogger = Logger.getLogger(Benchmark.class.getName());
	
	private static final int N = 100000;

	// Because of generic array creation.
	private static final Unit<?>[] UNITS = new Unit[] { MEGA(GRAM), KILOGRAM,
			GRAM, CENTI(GRAM), MILLI(GRAM), MICRO(GRAM) };

	@SuppressWarnings("unchecked")
	private static long usingQuantities(final long seed) {
		long time = System.currentTimeMillis();
		final Random r = new Random(seed);
		// final QuantityFactory<Mass> factory =
		// QuantityFactory.getInstance(Mass.class);
		final MassAmount[] m = new MassAmount[N];
		for (int i = 0; i < N; i++) {
			m[i] = new MassAmount(r.nextGaussian(), KILOGRAM);
		}
		// Now perform some computation in a random unit.
		@SuppressWarnings("rawtypes")
		final Unit targetUnit = (Unit<?>) UNITS[r.nextInt(UNITS.length)];
		double sum = 0;
		for (int i = 0; i < N; i++) {
			sum += m[i].doubleValue(targetUnit);
		}
		time = System.currentTimeMillis() - time;
		benchmarkLogger.info("Using quantities: ellapsed time=" + (time / 1000f)
				+ " s., result=" + sum);
		return time;
	}

	private static long usingDouble(final long seed)
			throws UnconvertibleException, IncommensurableException {
		long time = System.currentTimeMillis();
		final Random r = new Random(seed);
		final double[] m = new double[N];
		for (int i = 0; i < N; i++) {
			m[i] = r.nextGaussian();
		}
		// Now perform some computation in a random unit.
		final Unit<?> sourceUnit = KILOGRAM;
		final Unit<?> targetUnit = (Unit<?>) UNITS[r.nextInt(UNITS.length)];
		UnitConverter cv = sourceUnit.getConverterToAny((Unit<?>) targetUnit);
		double sum = 0;
		for (int i = 0; i < N; i++) {
			sum += cv.convert(m[i]);
		}
		time = System.currentTimeMillis() - time;
		benchmarkLogger.info("Using primitives: ellapsed time=" + (time / 1000f)
				+ " s., result=" + sum);
		return time;
	}

	public static void main(String[] args) throws InterruptedException {
		// TODO add IE and UE to main
		// Execute the loop many time for letting Hotspot to "warn up".
		try {
			final Random r = new Random();
			for (int i = 0; i < 20; i++) {
				final long seed = r.nextLong();
				long t1 = usingQuantities(seed);
				long t2 = usingDouble(seed);
				benchmarkLogger.fine("Ratio: " + (float) t1 / (float) t2);
				Thread.sleep(100);
			}
		} catch (IncommensurableException ie) {
			throw new InterruptedException();
		} catch (UnconvertibleException ue) {
			throw new InterruptedException();
		}
	}
}
