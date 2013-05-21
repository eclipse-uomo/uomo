package org.eclipse.uomo.xml.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.util.Date;

import org.eclipse.uomo.xml.XMLDateUtil;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class XMLDateUtilTest {

    @Test
    public final void testDate01() {
	Date test = parse("2006-07-04T10:53:52");
	assertEquals(2006, test.getYear() + 1900);
	assertEquals(7, test.getMonth() + 1);
	assertEquals(4, test.getDate());
	assertEquals(10, test.getHours());
	assertEquals(53, test.getMinutes());
	assertEquals(52, test.getSeconds());
    }

    @Test
    public final void testDate02() {
	// Time zone offset is thrown away
	Date test = parse("2006-07-04T10:53:52-03:00");
	assertEquals(2006, test.getYear() + 1900);
	assertEquals(7, test.getMonth() + 1);
	assertEquals(4, test.getDate());
	assertEquals(10, test.getHours());
	assertEquals(53, test.getMinutes());
	assertEquals(52, test.getSeconds());
    }

    @Test
    public final void testInvalidDate() {
	try {
	    XMLDateUtil.parseXMLDate("3323--333-22");
	} catch (ParseException e) {
	    return;
	}
	fail("Expected parse exception");
    }

    private Date parse(String xmlDate) {
	Date result = null;
	try {
	    result = XMLDateUtil.parseXMLDate(xmlDate);
	} catch (ParseException e) {
	    fail("Parse failed");
	}
	return result;
    }

}
