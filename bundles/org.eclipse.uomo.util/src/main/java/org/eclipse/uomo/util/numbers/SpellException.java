/**
 * 
 */
package org.eclipse.uomo.util.numbers;

import org.eclipse.uomo.core.UOMoException;


/**
* @author Werner Keil
* 
*         A simple checked exception class.
* 
*/
public class SpellException extends UOMoException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1358891223155703756L;

	/**
	 * @param message
	 *            The message carried by this exception object
	 */
	public SpellException(String message) {
		super(message);
	}
}