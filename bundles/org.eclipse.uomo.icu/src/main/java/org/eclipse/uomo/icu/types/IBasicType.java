package org.eclipse.uomo.icu.types;

/**
 * This interface indicates that a class is a Basic Data Type
 * 
 * @author Werner Keil
 */
public interface IBasicType {
	
	/**
	 * This requires that all Basic Data Types implement a serialize method
	 * 
	 * @return java.lang.String
	 */
	String serialize();	
}
