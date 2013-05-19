package com.thetransactioncompany.cors;


import java.util.Enumeration;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;


/**
 * Mock servlet filter configuration.
 *
 * @author Vladimir Dzhuvinov
 */
class MockFilterConfig implements FilterConfig {


	@Override
	public String getFilterName() {

		return "CORSFilter";
	}


	@Override
	public String getInitParameter(final String name) {

		return null;
	}


	@Override
	public Enumeration getInitParameterNames() {

		return null;
	}


	@Override
	public ServletContext getServletContext() {

		return null;
	}
}