/*
 * Backport, stub for JavaMoney 
 */
package org.eclipse.uomo.business.internal;

/**
 * This interface defines a {@link MonetaryFunction}. It is considered to be
 * adapted/compatible with {@code java.util.function.Function} as introduced in
 * Java 8.
 * 
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