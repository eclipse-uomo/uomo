package org.eclipse.uomo.core.impl;

import junit.framework.TestCase;

import org.eclipse.uomo.core.IBean;
import org.eclipse.uomo.core.impl.Bean;

public class BeanImplTest extends TestCase {

    public void testBeanIsABean() {
	IBean aBean = new Bean();
        assertTrue(aBean.isABean());
    }

}