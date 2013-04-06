/*******************************************************************************
 * Crown Copyright (c) 2008, 2011, Kestral Computing, (c) 2012, 2013 Werner Keil.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Kestral Computing - initial API and implementation
 *    Werner Keil - refactoring and improvements
 *******************************************************************************/
package org.eclipse.uomo.ucum.tests;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


/**
 * @author dennisn
 * @author Werner Keil
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({
	SystemTest.class, UcumServiceTest.class
})
public class UcumTestSuite {

	/**
	 * For use with JUnit3 runner
	 * @deprecated
	 */
	public static Test suite() {
		return new JUnit4TestAdapter(UcumTestSuite.class);
	}
}
