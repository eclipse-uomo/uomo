/*******************************************************************************
 * Crown Copyright (c) 2008, 2011, Copyright (c) 2008, 2008 Kestral Computing.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Kestral Computing - initial API and implementation
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
	SystemTest.class, UcumServiceTest.class, UCUMTest.class
})
public class UCUMTestSuite {

	/**
	 * For use with JUnit3 runner
	 * @deprecated
	 */
	public static Test suite() {
		return new JUnit4TestAdapter(UCUMTestSuite.class);
	}
}
