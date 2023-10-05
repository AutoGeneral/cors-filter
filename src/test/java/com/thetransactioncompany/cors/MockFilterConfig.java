package com.thetransactioncompany.cors;


import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;

import java.util.Enumeration;


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

		return new MockServletContext();
	}
}