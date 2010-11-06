package org.eclipse.uomo.xml.test;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	XMLDateUtilTest.class,
	XMLPullWriterTests.class,
	XMLWriterTest.class,
	XMLTestingTests.class
})
public class XMLTests {

	/**
	 * For use with JUnit3 runner
	 * @deprecated
	 */
	public static Test suite() {
		return new JUnit4TestAdapter(XMLTests.class);
	}
}
