/*******************************************************************************
 * Copyright (c) 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.uomo.util.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Test;

/**
 * Generates a (mostly) ASN.1 complaint UUID. <br>
 * Under Windows the MAC address of the machine is used, under Linux and other
 * OS's a randomly generated construct using the SHA-1 message digest is used.
 * It is thus possible that UUIDs will not be completely unique under non
 * Windows platforms, but given the number of bits of random data used to
 * generate the fake MAC addresses for non Windows machines, it is very unlikely
 * any collosions will every occur in practice.
 * 
 * @author Glenn Deen
 * @since IHII Phase 1
 * 
 */
public class UUIDTest {

	/**
	 * Holds the time this alogorithm was last run
	 */
	private static long lastTimeCalled = 0;

	/**
	 * 
	 */
	private static String MAC = null;

	/**
	 * Generates and returns a formatted UUID holding a 128 bit UUID in the
	 * form: AABBCCDDEEFF-GGHH-IIJJ-KKLLMMNNOOPP
	 * 
	 * @return the generated uuid
	 */
	static String generate() {
		UUID uuid = UUID.randomUUID();
		lastTimeCalled = System.currentTimeMillis();
		return uuid.toString();
	}

	/**
	 * Generate a new UUID prefixed by urn:uuid:
	 */
	static String generateURN() {
		return "urn:uuid:" + UUID.randomUUID();
	}

	@Test
	public void testUUID() {
		String uuid = generate();
		assertNotNull(uuid);
		assertTrue(uuid.contains("-"));
	}

	/**
	 * Runs a simple test of the UUID class, generating a set of UUIDs
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		int count = 100;
		System.out.println("Generating " + count + " test URNs ...");
		long start = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			System.out.println(UUID.randomUUID());
		long end = System.currentTimeMillis();
		System.out.println("Generated " + count + " URN's at rate of "
				+ Math.round((double) count / (double) (end - start) * 1000)
				+ " per second");
	}

}
