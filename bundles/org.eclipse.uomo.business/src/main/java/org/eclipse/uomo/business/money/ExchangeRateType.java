/*
 * Backport, stub for JavaMoney 
 */
package org.eclipse.uomo.business.money;


/**
 * This class models the type of a given exchange rate as immutable value type.
 * Basically the types possible are determined by the concrete use cases and
 * implementations. Typical use cases is that exchange rates for different
 * credit card systems or debit/credit may differ. This class allows to
 * distinguish these rates.
 * 
 * @author Anatole Tresch
 * @deprecated stub
 */
public interface ExchangeRateType {

	/**
	 * Get the (non localized) identifier of the {@link ExchangeRateType}.
	 * 
	 * @return The identifier, never null.
	 */
	public String getId();

}
