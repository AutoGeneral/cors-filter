package com.thetransactioncompany.cors.environment;

import java.util.Map;

/**
 * Encapsulates Environment settings
 *
 */
public interface Environment {
	public abstract String getenv(String name);
	public abstract Map<String, String> getenv();
}
