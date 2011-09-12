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

import static org.eclipse.uomo.core.impl.OutputHelper.println;
import static org.eclipse.uomo.units.SI.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.uomo.units.AbstractConverter;
import org.eclipse.uomo.units.AbstractUnit;
import org.eclipse.uomo.units.impl.BaseAmount;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.unitsofmeasurement.quantity.Dimensionless;
import org.unitsofmeasurement.quantity.Power;
import org.unitsofmeasurement.unit.Unit;
import org.unitsofmeasurement.unit.UnitConverter;


/**
 * @author  <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 * @version $Revision: 195 $, $LastChangedDate: 2010-02-24 18:40:34 +0100 (Mi, 24 Feb 2010) $
 */
public class UnitTest {
	Unit<Dimensionless> one;

    /* (non-Javadoc)
     * @see junit.framework.TestCase#setUp()
     */
    @Before
    public void setUp() throws Exception {
//      super.setUp();
        one = AbstractUnit.ONE;
    }

    /* (non-Javadoc)
     * @see junit.framework.TestCase#tearDown()
     */
    @After
    public void tearDown() throws Exception {
//      super.tearDown();
        one = null;
    }

    /**
     * Test method for {@link org.unitsofmeasure.Unit#toMetric()}.
     */
//    @Test
//    public void testToMetric() {
//        AbstractUnit<? extends QuantityAmount> su = (AbstractUnit<? extends QuantityAmount>) one.toMetric();
//        assertTrue(su.isUnscaledMetric());
//    }

    /**
     * Test method for {@link org.unitsofmeasure.Unit#getConverterTo}.
     */
    @Test
    public void testConverterToSI() {
        Double factor = new Double(10);
        UnitConverter converter = one.getConverterTo(one);
        Double result = converter.convert(factor.doubleValue());
        assertEquals(result, factor);
        println(result.toString());
    }

    /**
     * Test method for {@link org.unitsofmeasure.Unit#isMetric()}.
     */
//    @Test
//    public void testIsMetric() {
//        boolean standard = one.isMetric();
//        assertTrue(standard);
//    }

    /**
     * Test method for {@link org.unitsofmeasure.Unit#asType(java.lang.Class)}.
     */
  @Test
  @Ignore
  public void testAsType() {
      one.asType(Dimensionless.class);
      try {
          METRE.asType(Dimensionless.class);
          fail("Should have raised ClassCastException");
      } catch (ClassCastException e) {
          assertTrue(true);
      }
  }

    /**
     * Test method for {@link org.unitsofmeasure.Unit#getDimension()}.
     */
    @Test
    public void testGetDimension() {
        one.getDimension();
    }

    /**
     * Test method for {@link org.unitsofmeasure.Unit#alternate(java.lang.String)}.
     */
    @Test
    public void testAlternate() {
        Unit<?> alternate = one.alternate(null);
        assertNotNull(alternate);
    }

    /**
     * Test method for {@link org.unitsofmeasure.Unit#compound(org.unitsofmeasure.Unit)}.
     */
    /*public void testCompound() {
        Unit<? extends Quantity> compound = one.compound(one);
        assertNotNull(compound);
    }*/

    /**
     * Test method for {@link org.unitsofmeasure.Unit#transform}.
     */
    @Test
    public void testTransform() {
        Unit<?> result = one.transform(AbstractConverter.IDENTITY);
        assertEquals(result, one);
    }

    /**
     * Test method for {@link org.unitsofmeasure.Unit#add(double)}.
     */
    @Test
    public void testAdd() {
    	Unit<?> result = one.add(10);
        assertNotSame(result, one);
    }

    /**
     * Test method for {@link org.unitsofmeasure.Unit#multiply(long)}.
     */
    @Test
    public void testMultiplyLong() {
    	Unit<?> result = one.multiply(2L);
        assertNotSame(result, one);
    }

    /**
     * Test method for {@link org.unitsofmeasure.Unit#multiply(double)}.
     */
    @Test
    public void testMultiplyDouble() {
    	Unit<?> result = one.multiply(2.1);
        assertNotSame(result, one);
    }

    /**
     * Test method for {@link org.unitsofmeasure.Unit#multiply(org.unitsofmeasure.Unit)}.
     */
    @Test
    public void testMultiplyUnitOfQ() {
        AbstractUnit<?> result = (AbstractUnit<?>) one.multiply(one);
        assertEquals(result, one);
    }

    /**
     * Test method for {@link org.unitsofmeasure.Unit#inverse()}.
     */
    @Test
    public void testInverse() {
    	Unit<?> result = one.inverse();
        assertEquals(result, one);
    }

    /**
     * Test method for {@link org.unitsofmeasure.Unit#divide(long)}.
     */
    @Test
    public void testDivideLong() {
    	Unit<?> result = one.divide(2L);
        assertNotSame(result, one);
    }

    /**
     * Test method for {@link org.unitsofmeasure.Unit#divide(double)}.
     */
    @Test
    public void testDivideDouble() {
    	Unit<?> result = one.divide(3.2);
        assertNotSame(result, one);
    }

    /**
     * Test method for {@link org.unitsofmeasure.Unit#divide(org.unitsofmeasure.Unit)}.
     */
    @Test
    public void testDivideUnitOfQ() {
    	Unit<?> result = one.divide(one);
        assertEquals(result, one);
    }

    /**
     * Test method for {@link org.unitsofmeasure.Unit#root(int)}.
     */
    @Test
    public void testRoot() {
    	Unit<?> result = one.root(2);
        assertEquals(result, one);
    }

    /**
     * Test method for {@link org.unitsofmeasure.Unit#pow(int)}.
     */
    @Test
    public void testPow() {
    	Unit<?> result = one.pow(10);
        assertEquals(result, one);
    }
    
    @Test
    public void testKiloIsAThousand() {
    	BaseAmount<Power> w2000 = new BaseAmount<Power>(2000, WATT);
    	BaseAmount<Power> kW2 = new BaseAmount<Power>(2, Prefix.KILO(WATT));
    	assertThat(w2000, is(kW2));
    }
}
