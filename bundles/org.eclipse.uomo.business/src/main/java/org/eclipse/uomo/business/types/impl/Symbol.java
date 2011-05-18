package org.eclipse.uomo.business.types.impl;

import static org.eclipse.uomo.core.impl.DataHelper.BDT_DELIM;

import org.eclipse.uomo.business.types.BDTHelper;
import org.eclipse.uomo.business.types.BDTypeException;
import org.eclipse.uomo.business.types.IBDType;
import org.eclipse.uomo.business.types.IMarket;
import org.eclipse.uomo.core.ISymbol;

/**
 * Symbol - composed of market, symbol and country codes - at least one of
 * market and country will usually be present - if both are missing, we use the
 * default country
 * 
 * @author: Werner Keil
 */
public class Symbol implements IBDType, ISymbol {
	IMarket m_market = null;
	final String m_sym;
	String m_country = "";

	/**
	 * Symbol constructor using market;sym;country, where at least one of market
	 * and country will usually be present - if both are missing, we use the
	 * default country
	 * 
	 * @throws BDTypeException
	 */
	public Symbol(String s) throws BDTypeException {
		super();

		int sp = s.indexOf(BDT_DELIM);
		if (sp == -1)
			throw new BDTypeException("Invalid symbol string: " + s);
		else {
			String tmpMarket = s.substring(0, sp);
			if (sp > 0) {
				try {
					m_market = Market.get(tmpMarket);
				} // verify market is valid (added Oct. 3)
				catch (BDTypeException e) {
					throw new BDTypeException("Invalid market code in symbol: "
							+ s);
				}
			}

			String s2 = s.substring(sp + 1);
			sp = s2.indexOf(BDT_DELIM);
			if (sp == -1)
				m_sym = s2;
			else {
				m_sym = s2.substring(0, sp);
				m_country = s2.substring(sp + 1);
				if (!m_country.equals("")
						&& !(BDTHelper.getCountries().containsKey(m_country)))
					throw new BDTypeException(
							"Invalid country code in symbol: " + s);
			}
		}

		if (m_sym.equals(""))
			throw new BDTypeException("Missing symbol within Symbol string: "
					+ s);

		if (m_market.equals("") && m_country.equals(""))
			m_country = BDTHelper.DEFAULT_COUNTRY;
	}

	/**
	 * Get country String from object
	 */
	public String getCountry() {
		return m_country;
	}

	/**
	 * Get market String from object
	 */
	public IMarket getMarket() {
		return m_market;
	}

	/**
	 * Get symbol String from object
	 */
	public String getSymbol() {
		return m_sym;
	}

	/**
	 * Convert object to String
	 * 
	 * @return java.lang.String
	 */
	public String serialize() {

		String s1 = "";

		if (m_market != null && !m_market.equals(""))
			s1 = m_market.getName();

		s1 = s1 + BDT_DELIM + m_sym;

		if (!m_country.equals(""))
			s1 = s1 + BDT_DELIM + m_country;

		return s1;
	}

	/**
	 * Create a String from this object
	 * 
	 * @return java.lang.String
	 */
	public String toString() {
		return serialize();
	}
}
