package com.thetransactioncompany.cors.environment;

import java.util.Properties;

/**
 * Encapsulates Environment settings
 *
 */
public interface Environment {
	public abstract String getProperty(String name);
	public abstract Properties getProperties();
}
