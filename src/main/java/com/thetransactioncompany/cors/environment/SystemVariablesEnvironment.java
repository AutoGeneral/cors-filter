package com.thetransactioncompany.cors.environment;

import java.util.Map;

/**
 * Loads Environment Variables from the System
 */
public class SystemVariablesEnvironment implements Environment {

	@Override
	public String getenv(String name) {
		return System.getenv(name);
	}

	@Override
	public Map<String, String> getenv() {
		return System.getenv();
	}

}
