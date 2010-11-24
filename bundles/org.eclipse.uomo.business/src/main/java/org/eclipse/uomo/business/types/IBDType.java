package org.eclipse.uomo.business.types;

/**
 * This interface indicates that a class is a Basic Data Type
 * 
 * @author paul.morrison
 */
public interface IBDType {
	/**
	 * This requires that all Basic Data Types implement a serialize method
	 * 
	 * @return java.lang.String
	 */
	String serialize();
}
