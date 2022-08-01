/**
 * Copyright (c) 2005, 2022, Werner Keil and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Werner Keil - initial API and implementation
 */
package org.eclipse.uomo.examples.icu.console;

//Constants (Java 5 static import)
//import static org.eclipse.uomo
import org.eclipse.uomo.icu.impl.MeasureAmount;

import com.ibm.icu.util.Measure;
import com.ibm.icu.util.MeasureUnit;

/**
 * @author Werner Keil
 * @version 0.9.9, $Date: 2022-08-01 $
 */
public class ICUDemo {

	/**
	 * @param args
	 *            The application arguments if required.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		
		System.out.println("Hello ICU");
		Measure m = MeasureAmount.of(2, MeasureUnit.CENTILITER);
		System.out.println(m);
	}
}
