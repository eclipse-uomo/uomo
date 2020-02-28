package org.eclipse.uomo.icu.types;

/**
 * This interfaces includes Monetary Price and Percent Price.
 * 
 * @author <a href="mailto:uomo@catmedia.us">Werner Keil</a>
 */
public interface IPrice  extends IBDType {


/**
 * Determine if <code> this </code> IPrice is a multiple of the parameter IPrice.
 * @return boolean
 * @param p org.eclipse.uomo.business.types.IPrice
 */
public boolean isMultipleOf(IPrice p) ;
/**
 * Determine if <code> this </code> IPrice is postive (greater than zero)
 * @return boolean
 */
public boolean isPositive() ;
}
