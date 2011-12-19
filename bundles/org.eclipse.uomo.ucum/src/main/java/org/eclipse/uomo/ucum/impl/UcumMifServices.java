package org.eclipse.uomo.ucum.impl;

import java.io.InputStream;

import org.eclipse.ohf.h3et.mif.core.MIFDocument;
import org.eclipse.uomo.core.UOMoException;

public class UcumMifServices extends UcumEssenceService {

	
	/**
	 * @param stream
	 * @throws UOMoException
	 */
	public UcumMifServices(InputStream stream) throws UOMoException {
		super(stream);
	}

	/**
	 * @param filename
	 * @throws UOMoException
	 */
	public UcumMifServices(String filename) throws UOMoException {
		super(filename);
	}

	/**
	 * convert to MIF - will be moved to a different package
	 * 
	 * @return
	 * @throws UOMoException
	 */
	public MIFDocument asMif() throws UOMoException {
		return new MIFGeneratorImpl().generate(getModel());
	}

}
