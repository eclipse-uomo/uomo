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

package org.eclipse.uomo.ucum.tests;

//import org.eclipse.uomo.util.test.UOMoTestConfiguration;



/*
 * we need some ideas here. 
 * 1. copyright. The testing uses a sample project. It's full of things that are open but
 *    not sufficiently unencumbered to be EPL. So it's on
 *    sourceforge @ https://sourceforge.net/projects/ohf-he3t-test
 * 
 * 2. Local directory. We've given up trying to organise some better way to 
 *    organise where the data resides. So it's a constant here, and you simply
 *    change the constant for your local setup.
 *    
 *     Better ideas are welcome
 */

public interface H3ETTestConfiguration   {
	
	/**
	 * The test workspace, which should contains the test project
	 * org.eclipse.ohf.h3et.test
	 * 
	 * NOTE: current setting assume the test workspace contains two projects:
	 * * org.eclipse.ohf.h3et.test: with a minimised MIF and its related test data
	 * * ca.infoway.cerx.mif21: Using the Infoway MIFs settings for more general testing 
	 */
	public static final String TEST_WORKSPACE = System.getenv("WORKSPACE") != null ? System.getenv("WORKSPACE") : "C:/workspace/data/";
//	public static final String TEST_PROJECT 			= TEST_WORKSPACE + "org.eclipse.ohf.h3et.test";
//	public static final String TEST_PROJECT_VOCAB 		= TEST_PROJECT+"/Vocab";
//	public static final String TEST_PROJECT_MIF 		= TEST_PROJECT+"/Mif";
//	public static final String TEST_PROJECT_CASES 		= TEST_PROJECT+"/TestCases";
//	public static final String TEST_PROJECT_FRAGMENTS 	= TEST_PROJECT+"/Mif/Fragments";
	
	public static final String WORK_PROJECT 			= TEST_WORKSPACE + "ca.infoway.cerx.mif21";
	public static final String WORK_PROJECT_VOCAB 		= WORK_PROJECT+"/Vocab";
	public static final String WORK_PROJECT_MIF 		= WORK_PROJECT+"/Mif";
	public static final String WORK_PROJECT_CASES 		= WORK_PROJECT+"/TestCases";
	public static final String WORK_PROJECT_FRAGMENTS 	= WORK_PROJECT+"/Mif/Fragments";
	public static final String WORK_PROJECT_TESTFOLDER	= WORK_PROJECT+"/unitTests";
	
}
