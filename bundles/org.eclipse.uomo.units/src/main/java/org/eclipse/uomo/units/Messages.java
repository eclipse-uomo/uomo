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
package org.eclipse.uomo.units;

import org.eclipse.osgi.util.NLS;

/**
 * Message class for unit messages. These messages are used throughout the
 * package.
 * 
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 */
public class Messages extends NLS {
	private static final String BUNDLE_NAME = Messages.class.getPackage()
			.getName() + ".messages";//$NON-NLS-1$

	private Messages() {
	}

	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
	public static String LocalFormat_Pattern;
	public static String SI_A;
	public static String SI_At;
	public static String SI_bit;
	public static String SI_Bq;
	public static String SI_C;
	public static String SI_cd;
	public static String SI_F;
	public static String SI_Gy;
	public static String SI_H;
	public static String SI_Hz;
	public static String SI_J;
	public static String SI_K;
	public static String SI_kat;
	public static String SI_kg;
	public static String SI_kg_name;
	public static String SI_lm;
	public static String SI_lx;
	public static String SI_m;
	public static String SI_m_name;
	public static String SI_mol;
	public static String SI_N;
	public static String SI_OhmS;
	public static String SI_Pa;
	public static String SI_rad;
	public static String SI_s;
	public static String SI_S;
	public static String SI_Sv;
	public static String SI_sr;
	public static String SI_T;
	public static String SI_V;
	public static String SI_W;
	public static String SI_Wb;
	public static String US_lb_name;
	public static String NonSI_R;
	public static String NonSI_R_name;
}
