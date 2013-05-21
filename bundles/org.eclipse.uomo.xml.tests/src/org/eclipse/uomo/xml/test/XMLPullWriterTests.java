/*******************************************************************************
 * Copyright (c) 2000, 2010 Jiva Medical and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jiva Medical - initial API and implementation
 *******************************************************************************/

package org.eclipse.uomo.xml.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.uomo.core.UOMoException;
import org.eclipse.uomo.xml.impl.XMLPullWriter;
import org.junit.Ignore;
import org.junit.Test;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class XMLPullWriterTests extends XMLTestCase {

    @Test
    @Ignore
    public final void test1() throws UOMoException, XmlPullParserException,
	    IOException, InterruptedException {
	XmlPullParserFactory factory = XmlPullParserFactory.newInstance(System
		.getProperty(XmlPullParserFactory.PROPERTY_NAME), null);
	factory.setNamespaceAware(true);
	XmlPullParser xpp = factory.newPullParser();

	xpp.setInput(new FileInputStream(new File(RESOURCE_PATH
		+ "testPullWriterSource.xml")), null);

	int eventType = xpp.next();
	if (eventType != XmlPullParser.START_TAG)
	    throw new XmlPullParserException("Unable to process XML document");

	FileOutputStream stream = new FileOutputStream(new File(TEMP_FILENAME));
	XMLPullWriter xwr = new XMLPullWriter(stream);
	xwr.write(xpp, true, null, null);
	stream.flush();
	stream.close();

	compareFiles(RESOURCE_PATH + "testPullWriterDest.xml", TEMP_FILENAME);
    }

}
