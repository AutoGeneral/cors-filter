package com.thetransactioncompany.cors.environment;


import java.util.Properties;


/**
 * System variables environment.
 *
 * @author David Bellem
 */
public class SystemVariablesEnvironment implements Environment {


	@Override
	public String getProperty(final String name) {

		return System.getProperty(name);
	}


	@Override
	public Properties getProperties() {

		return System.getProperties();
	}
}
