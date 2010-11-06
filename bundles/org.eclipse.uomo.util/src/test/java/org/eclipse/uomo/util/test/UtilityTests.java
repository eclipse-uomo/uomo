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

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ Iso8601DateUtilityTests.class,
		NumberFormatUtilityTest.class })
public class UtilityTests {

	/**
	 * For use with JUnit3 runner
	 * 
	 * @deprecated
	 */
	public static Test suite() {
		return new JUnit4TestAdapter(UtilityTests.class);
	}
}
