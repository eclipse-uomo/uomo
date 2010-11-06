/*******************************************************************************
 * Crown Copyright (c) 2006, 2007, Copyright (c) 2006, 2007 Jiva Medical.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Jiva Medical - initial API and implementation
 *******************************************************************************/

package org.eclipse.uomo.util.test;

import junit.framework.TestCase;

import org.eclipse.uomo.util.Iso8601Date;

public class Iso8601DateUtilityTests extends TestCase {
   
	private Iso8601Date du;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		du = new Iso8601Date();
	}
	
	public void testFullDate() {
		String st;
		st = du.parse("20071017111114.789", Iso8601Date.ISO_DATE_VALIDATION_FULL, Iso8601Date.OPTIONAL);
		assertTrue(st, st == null);
		st = du.parse("20061027000000.0020", Iso8601Date.ISO_DATE_VALIDATION_FULL, Iso8601Date.OPTIONAL);
		assertTrue(st, st == null);
	}

}
