/*
 *  Copyright (c) 2012, 2013, Werner Keil, Anatole Tresch.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.eclipse.uomo.business.internal;

/**
 * This interface defines a {@link MonetaryFunction}. It is considered to be
 * adapted/compatible with {@code java.util.function.Function} as introduced in
 * Java 8.
 * 
 * @author Anatole Tresch
 * @author Werner Keil
 * @version 0.9.1
 * @param <T>
 *            The input type of the function.
 * @param <R>
 *            The result type of the function.
 * @deprecated this is a stub, replacing it by JSR 354 as soon as it's in MavenCentral!
 */
public interface MonetaryFunction<T, R> {

	/**
	 * Apply a function to the input argument T, yielding an appropriate result R.
	 * @param value the input value
	 * @return the result of the function
	 */
	public R apply(T value);

}