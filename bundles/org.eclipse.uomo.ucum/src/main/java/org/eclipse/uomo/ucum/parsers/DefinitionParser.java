/*******************************************************************************
 * Crown Copyright (c) 2006, 2012, Copyright (c) 2006, 2017 Kestral Computing P/L and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *    Kestral Computing P/L - initial implementation
 *    Werner Keil - added static imports
 *******************************************************************************/

package org.eclipse.uomo.ucum.parsers;

import static org.eclipse.uomo.ucum.model.ConceptKind.*;
import static org.xmlpull.v1.XmlPullParser.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.eclipse.uomo.core.UOMoException;
import org.eclipse.uomo.ucum.internal.Messages;
import org.eclipse.uomo.ucum.model.BaseUnit;
import org.eclipse.uomo.ucum.model.DefinedUnit;
import org.eclipse.uomo.ucum.model.Prefix;
import org.eclipse.uomo.ucum.model.UcumModel;
import org.eclipse.uomo.ucum.model.Value;
import org.eclipse.uomo.util.Parser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;



/**
 * Parses the file ucum-essense.xml
 * 
 * @author Grahame Grieve
 * @author Werner Keil
 * @version 1.1
 * @since 0.6
 */

public class DefinitionParser implements Parser<String, UcumModel> {

	public UcumModel parse(String filename) throws UOMoException {
		try {
			return parse(new FileInputStream(new File(filename)));
		} catch (XmlPullParserException x) {
			throw new UOMoException(x);
		}  catch (ParseException p) {
			throw new UOMoException(p);
		}  catch (IOException i) {
			throw new UOMoException(i);
		}
	}
	
	public UcumModel parse(InputStream stream) throws XmlPullParserException, IOException, ParseException {
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance(
				System.getProperty(XmlPullParserFactory.PROPERTY_NAME), null);
		factory.setNamespaceAware(true);
		XmlPullParser xpp = factory.newPullParser();

		xpp.setInput(stream, null);
		
        int eventType = xpp.next();
        if (eventType != START_TAG)
        	throw new XmlPullParserException(Messages.DefinitionParser_0);
        if (!xpp.getName().equals("root"))  //$NON-NLS-1$
        	throw new XmlPullParserException(Messages.DefinitionParser_2+xpp.getName()+Messages.DefinitionParser_3);
        final DateFormat fmt = new SimpleDateFormat(Messages.DefinitionParser_DateFormat);
        Date date = fmt.parse(xpp.getAttributeValue(null, "revision-date").substring(7, 32));         //$NON-NLS-1$
		UcumModel root = new UcumModel(xpp.getAttributeValue(null, "version"), xpp.getAttributeValue(null, "revision"), date); //$NON-NLS-1$ //$NON-NLS-2$
        xpp.next();
		while (xpp.getEventType() != END_TAG) {
			if (xpp.getEventType() == TEXT) {
				if (StringUtils.isWhitespace(xpp.getText()))
					xpp.next();
				else
					throw new XmlPullParserException(Messages.DefinitionParser_8+xpp.getText());
			} else if (xpp.getName().equals(PREFIX.visibleName())) 
				root.getPrefixes().add(parsePrefix(xpp));
			else if (xpp.getName().equals(BASEUNIT.visibleName())) 
				root.getBaseUnits().add(parseBaseUnit(xpp));
			else if (xpp.getName().equals(UNIT.visibleName())) 
				root.getDefinedUnits().add(parseUnit(xpp));
			else 
				throw new XmlPullParserException(Messages.DefinitionParser_9+xpp.getName());
		}
		return root;
	}

	private DefinedUnit parseUnit(XmlPullParser xpp) throws XmlPullParserException, IOException {
		DefinedUnit unit = new DefinedUnit(xpp.getAttributeValue(null, "Code"), xpp.getAttributeValue(null, "CODE")); //$NON-NLS-1$ //$NON-NLS-2$
		unit.setMetric("yes".equals(xpp.getAttributeValue(null, "isMetric"))); //$NON-NLS-1$ //$NON-NLS-2$
		unit.setSpecial("yes".equals(xpp.getAttributeValue(null, "isSpecial"))); //$NON-NLS-1$ //$NON-NLS-2$
		unit.setClass_(xpp.getAttributeValue(null, "class")); //$NON-NLS-1$
		xpp.next();
		skipWhitespace(xpp);
		while (xpp.getEventType() == START_TAG && "name".equals(xpp.getName())) //$NON-NLS-1$
			unit.getNames().add(readElement(xpp, "name", UNIT.visibleName()+" "+unit.getCode(), false)); //$NON-NLS-1$ //$NON-NLS-2$
		if (xpp.getEventType() == START_TAG && "printSymbol".equals(xpp.getName())) //$NON-NLS-1$
			unit.setPrintSymbol(readElement(xpp, "printSymbol", UNIT.visibleName()+" "+unit.getCode(), true)); //$NON-NLS-1$ //$NON-NLS-2$
		unit.setProperty(readElement(xpp, "property", UNIT.visibleName()+" "+unit.getCode(), false)); //$NON-NLS-1$ //$NON-NLS-2$
		unit.setValue(parseValue(xpp, "unit "+unit.getCode())); //$NON-NLS-1$
		xpp.next();
		skipWhitespace(xpp);
		return unit;
	}

	private Value<?> parseValue(XmlPullParser xpp, String context) throws XmlPullParserException, IOException {
		checkAtElement(xpp, "value", context); //$NON-NLS-1$
		BigDecimal val = null;
		if (xpp.getAttributeValue(null, "value") != null)  //$NON-NLS-1$
		try {
			val = new BigDecimal(xpp.getAttributeValue(null, "value")); //$NON-NLS-1$
		} catch (NumberFormatException e) {
			throw new XmlPullParserException(Messages.DefinitionParser_29+context+Messages.DefinitionParser_30+e.getMessage());
		}
		@SuppressWarnings("rawtypes")
		Value<?> value = new Value(xpp.getAttributeValue(null, "Unit"), xpp.getAttributeValue(null, "UNIT"), val); //$NON-NLS-1$ //$NON-NLS-2$
		value.setString(readElement(xpp, "value", context, true)); //$NON-NLS-1$
		return value;
	}

	private BaseUnit parseBaseUnit(XmlPullParser xpp) throws XmlPullParserException, IOException {
		BaseUnit base = new BaseUnit(xpp.getAttributeValue(null, "Code"), xpp.getAttributeValue(null, "CODE")); //$NON-NLS-1$ //$NON-NLS-2$
		base.setDim(xpp.getAttributeValue(null, "dim").charAt(0)); //$NON-NLS-1$
		xpp.next();
		skipWhitespace(xpp);
		base.getNames().add(readElement(xpp, "name", BASEUNIT.visibleName()+" "+base.getCode(), false)); //$NON-NLS-1$ //$NON-NLS-2$
		base.setPrintSymbol(readElement(xpp, "printSymbol", BASEUNIT.visibleName()+" "+base.getCode(), false)); //$NON-NLS-1$ //$NON-NLS-2$
		base.setProperty(readElement(xpp, "property", BASEUNIT.visibleName()+" "+base.getCode(), false)); //$NON-NLS-1$ //$NON-NLS-2$
		xpp.next();
		skipWhitespace(xpp);
		return base;
	}

	private Prefix parsePrefix(XmlPullParser xpp) throws XmlPullParserException, IOException {
		Prefix prefix = new Prefix(xpp.getAttributeValue(null, "Code"), xpp.getAttributeValue(null, "CODE")); //$NON-NLS-1$ //$NON-NLS-2$
		xpp.next();
		skipWhitespace(xpp);
		prefix.getNames().add(readElement(xpp, "name", PREFIX.visibleName()+" "+prefix.getCode(), false)); //$NON-NLS-1$ //$NON-NLS-2$
		prefix.setPrintSymbol(readElement(xpp, "printSymbol", PREFIX.visibleName()+" "+prefix.getCode(), false)); //$NON-NLS-1$ //$NON-NLS-2$
		checkAtElement(xpp, "value", PREFIX.visibleName()+" "+prefix.getCode()); //$NON-NLS-1$ //$NON-NLS-2$
		prefix.setValue(new BigDecimal(xpp.getAttributeValue(null, "value"))); //$NON-NLS-1$
		readElement(xpp, "value", PREFIX.visibleName()+" "+prefix.getCode(), true); //$NON-NLS-1$ //$NON-NLS-2$
		xpp.next();
		skipWhitespace(xpp);
		return prefix;
	}

	private String readElement(XmlPullParser xpp, String name, String context, boolean complex) throws XmlPullParserException, IOException {
		checkAtElement(xpp, name, context);
		xpp.next();
		skipWhitespace(xpp);
		String val = null;
		if (complex) {
			val = readText(xpp);
		} else if (xpp.getEventType() == TEXT) {
			val = xpp.getText();
			xpp.next();
			skipWhitespace(xpp);
		}
		if (xpp.getEventType() != END_TAG) {
			throw new XmlPullParserException(Messages.DefinitionParser_54+context);
		}
		xpp.next();
		skipWhitespace(xpp);
		return val;
	}

	private String readText(XmlPullParser xpp) throws XmlPullParserException, IOException {
		StringBuilder bldr = new StringBuilder();
		while (xpp.getEventType() != END_TAG) {
			if (xpp.getEventType() == TEXT) {
				bldr.append(xpp.getText());
				xpp.next();
			} else {
				xpp.next();
				bldr.append(readText(xpp));
				xpp.next();
				skipWhitespace(xpp);
			}
		}
		return bldr.toString();
	}

	private void skipWhitespace(XmlPullParser xpp) throws XmlPullParserException, IOException {
		while (xpp.getEventType() == TEXT && StringUtils.isWhitespace(xpp.getText())) 
			xpp.next();		
	}

	private void checkAtElement(XmlPullParser xpp, String name, String context) throws XmlPullParserException {
		if (xpp.getEventType() != START_TAG)
			throw new XmlPullParserException(Messages.DefinitionParser_55+name+Messages.DefinitionParser_56+Integer.toString(xpp.getEventType())+Messages.DefinitionParser_57+context);
		if (!xpp.getName().equals(name))
			throw new XmlPullParserException(Messages.DefinitionParser_58+name+Messages.DefinitionParser_59+xpp.getName()+Messages.DefinitionParser_60+context);		
	}
}
