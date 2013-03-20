/**
 * Copyright (c) 2010-2011, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.ucum.tests;
import static org.eclipse.uomo.core.impl.OutputHelper.print;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;


/**
 * @author Werner Keil
 *
 */
public class SystemTest {
	
	@Test
	public void testEnv() {
		Map<String, String> env = System.getenv();
		assertNotNull(env);
		assertNotNull(env.values());
		assertTrue(env.values().size()>0);
		for (String value : env.values()) {
			print(value);
		}		
	}
}
