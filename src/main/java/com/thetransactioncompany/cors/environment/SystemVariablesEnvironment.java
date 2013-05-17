package com.thetransactioncompany.cors.environment;

import java.util.Properties;

/**
 * Loads Environment Variables from the System
 */
public class SystemVariablesEnvironment implements Environment {

	@Override
	public String getProperty(String name) {
		return System.getProperty(name);
	}

	@Override
	public Properties getProperties() {
		return System.getProperties();
	}

}
