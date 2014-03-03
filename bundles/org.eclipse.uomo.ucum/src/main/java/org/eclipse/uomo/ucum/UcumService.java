/*******************************************************************************
 * Crown Copyright (c) 2006, 2011, Copyright (c) 2006, 2008 Kestral Computing P/L.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Kestral Computing P/L - initial implementation
 *******************************************************************************/

package org.eclipse.uomo.ucum;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.eclipse.uomo.core.impl.CodeValuePair;
import org.eclipse.uomo.core.IVersion;
import org.eclipse.uomo.core.UOMoRuntimeException;
import org.eclipse.uomo.ucum.model.Concept;
import org.eclipse.uomo.ucum.model.ConceptKind;
import org.eclipse.uomo.ucum.model.DefinedUnit;
import org.eclipse.uomo.ucum.model.UcumModel;


/**
 * General UCUM Service
 * This is a tightly bound interface - consumers use the internal model classes
 * 
 * TODO: define a remoteable service that interface that doesn't express the 
 * functionality using internal model classes
 * @author Grahame Grieve
 * @author Werner Keil
 *
 */
public interface UcumService {

	/**
	 * provided for various utility uses. Should not be used 
	 * for general use
	 * 
	 * @return the model
	 */
	public UcumModel getModel();

	// TODO replace this with a more generic Module definition (->Jigsaw, OSGi or SDJ)
	public class UcumVersionDetails implements IVersion {
		private final Date releaseDate;
		private final String version;
		/**
		 * @param releaseDate
		 * @param version
		 */
		public UcumVersionDetails(Date releaseDate, String version) {
			super();
			this.releaseDate = releaseDate;
			this.version = version;
		}
		/**
		 * @return the releaseDate
		 */
		public Date getReleaseDate() {
			return releaseDate;
		}
		/**
		 * @return the version
		 */
		public String getVersion() {
			return version;
		}
		
	}
	
	/**
	 * return Ucum Identification details
	 */
	public UcumVersionDetails ucumIdentification();


	/**
	 * Check UCUM. Note that this stands as a test of the service
	 * more than UCUM itself (for version 1.7, there are no known
	 * semantic errors in UCUM). But you should always run this test at least
	 * once with the version of UCUM you are using to ensure that 
	 * the service implementation correctly understands the UCUM data
	 * to which it is bound
	 *   
	 * @return a list of internal errors in the UCUM spec.
	 * 
	 */
	public List<String> validateUCUM();

	/**
	 * Search through the UCUM concepts for any concept containing matching text.
	 * Search will be limited to the kind of concept defined by kind, or all if kind
	 * is null
	 * 
	 * @param kind - can be null. scope of search
	 * @param text - required
	 * @param isRegex
	 * @return
	 */
	public List<Concept> search(ConceptKind kind, String text, boolean isRegex);

	/**
	 * return a list of the defined types of units in this UCUM version
	 * 
	 * @return
	 */
	public Set<String> getProperties();

	/**
	 * validate whether a unit code are valid UCUM units
	 *  
	 * @param units - the unit code to check
	 * @return nil if valid, or an error message describing the problem
	 */
	public String validate(String unit);

	/**
	 * given a unit, return a formal description of what the units stand for using
	 * full names 
	 * @param units the unit code
	 * @return formal description
	 * @throws UOMoRuntimeException 
	 */
	public String analyse(String unit) throws UOMoRuntimeException;
	
	/**
	 * validate whether a units are valid UCUM units and additionally require that the 
	 * units from a particular property
	 *  
	 * @param units - the unit code to check
	 * @return nil if valid, or an error message describing the problem
	 */
	public String validateInProperty(String unit, String property);

	/**
	 * validate whether a units are valid UCUM units and additionally require that the 
	 * units match a particular base canonical unit
	 *  
	 * @param units - the unit code to check
	 * @return nil if valid, or an error message describing the problem
	 */
	public String validateCanonicalUnits(String unit, String canonical);

	/**
	 * given a set of units, return their canonical form
	 * @param unit
	 * @return the canonical form
	 * @throws UOMoRuntimeException 
	 */
	public String getCanonicalUnits(String unit) throws UOMoRuntimeException;

	/**
	 * for a given canonical unit, return all the defined units that have the 
	 * same canonical unit. 
	 * 
	 * @param code
	 * @return
	 * @throws UOMoRuntimeException
	 */
	public List<DefinedUnit> getDefinedForms(String code) throws UOMoRuntimeException;

	/**
	 * given a value/unit pair, return the canonical form as a value/unit pair
	 * 
	 * 1 mm -> 1e-3 m
	 * @param value
	 * @return
	 * @throws UOMoRuntimeException 
	 */
	public CodeValuePair<Number, String> getCanonicalForm(CodeValuePair<Number, String> value) throws UOMoRuntimeException;

	/**
	 * given a value and source unit, return the value in the given dest unit
	 * an exception is thrown if the conversion is not possible
	 * 
	 * @param value
	 * @param sourceUnit
	 * @param destUnit
	 * @return the value if a conversion is possible
	 * @throws UOMoRuntimeException
	 */
	public Number convert(Number value, String sourceUnit, String destUnit) throws UOMoRuntimeException;

	/**
	 * multiply two value/units pairs together and return the result in canonical units
	 * 
	 * Note: since the units returned are canonical, 
	 * @param o1
	 * @param o2
	 * @return
	 */
	public CodeValuePair<Number, String> multiply(CodeValuePair<Number, String> o1, CodeValuePair<Number, String> o2);

}
