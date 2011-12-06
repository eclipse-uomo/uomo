package org.eclipse.uomo.ucum.impl;

import java.io.InputStream;

import org.eclipse.ohf.h3et.core.H3ETException;
import org.eclipse.ohf.h3et.mif.core.MIFDocument;
import org.eclipse.uomo.core.UOMoException;

public class UcumMifServices extends UcumEssenceService {

	
	/**
	 * @param stream
	 * @throws OHFException
	 */
	public UcumMifServices(InputStream stream) throws UOMoException {
		super(stream);
	}

	/**
	 * @param filename
	 * @throws OHFException
	 */
	public UcumMifServices(String filename) throws UOMoException {
		super(filename);
	}

	/**
	 * convert to MIF - will be moved to a different package
	 * 
	 * @return
	 * @throws H3ETException
	 */
	public MIFDocument asMif() throws H3ETException {
		return new MIFGenerator().generate(getModel());
	}

}
