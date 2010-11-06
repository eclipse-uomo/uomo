/**
 * 
 */
package org.eclipse.uomo.ucum.tests;

import java.util.Map;

import org.junit.Test;


/**
 * @author Werner
 *
 */
public class SystemTest {
	
	@Test
	public void testEnv() {
		Map<String, String> env = System.getenv();
		
		for (String value : env.values()) {
			System.out.println(value);
		}
	}
}
