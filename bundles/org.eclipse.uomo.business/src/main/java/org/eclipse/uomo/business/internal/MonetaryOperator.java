/*
 * Backport, stub for JavaMoney 
 */
package org.eclipse.uomo.business.internal;

/**
 * This interface defines a {@link MonetaryOperator}. It is hereby important to
 * distinguish between <i>internal rounding</i> such as implied by the maximal
 * precision/scale of an amount, and <i>rounding</i> applied to a
 * {@link MonetaryAmount} or a calculation algorithm. Since different use cases
 * may require <i>rounding</i> done at very different stages and differently
 * within a complex financial calculation, {@link MonetaryOperator} is not
 * directly attached to a monetary type, e.g. a {@link MonetaryAmount}.
 * <p>
 * Nevertheless the JSR's extensions provide a RoundingMonetaryAmount, which
 * wraps a {@link MonetaryAmount} and adds implicit rounding.
 * 
 * <p>
 * This interface is considered to be adapted/compatible with {@code java.util.function.UnaryOperator} 
 * as introduced in Java 8.
 * 
 * @version 0.9.1
 * @author Werner Keil
 * @deprecated this is a stub, replacing it by JSR 354 as soon as it's in MavenCentral!
 */
public interface MonetaryOperator extends MonetaryFunction<MonetaryAmount,MonetaryAmount> {

}