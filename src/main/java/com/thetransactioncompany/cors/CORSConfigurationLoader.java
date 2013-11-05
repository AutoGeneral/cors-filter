package com.thetransactioncompany.cors;


import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.FilterConfig;

import com.thetransactioncompany.cors.environment.Environment;
import com.thetransactioncompany.cors.environment.SystemVariablesEnvironment;


/**
 * CORS configuration loader.
 *
 * @author Vladimir Dzhuvinov
 * @author David Bellem
 */
public class CORSConfigurationLoader {


	/**
	 * The name of the system environment variable / web.xml filter 
	 * initialisation parameter that points to the file name holding the 
	 * CORS configuration properties.
	 */
	public static final String CONFIG_FILE_PARAM_NAME = "cors.configurationFile";


	/**
	 * The servlet filter configuration.
	 */
	private final FilterConfig filterConfig;


	/**
	 * Reference to the system environment.
	 */
	private Environment environment;


	/**
	 * Converts the web.xml filter initialisation parameters to a Java
	 * properties representation. The parameter names become property keys.
	 *
	 * @param config The filter configuration. Must not be {@code null}.
	 *
	 * @return The context parameters as Java properties.
	 */
	private static Properties getFilterInitParameters(final FilterConfig config) {
	
		Properties props = new Properties();
	
		Enumeration en = config.getInitParameterNames();
		
		while (en.hasMoreElements()) {
			
			String key = (String)en.nextElement();
			String value = config.getInitParameter(key);
			
			props.setProperty(key, value);
		}
	
		return props;
	}


	/**
	 * Creates a new CORS configuration loader.
	 *
	 * @param filterConfig The servlet filter configuration. Must not be
	 *                     {@code null}.
	 */
	public CORSConfigurationLoader(final FilterConfig filterConfig) {

		if (filterConfig == null)
			throw new IllegalArgumentException("The servlet filter configuration must not be null");

		this.filterConfig = filterConfig;
	}
	
	
	/**
	 * Loads the properties from the specified file.
	 *
	 * @param filename The file name. Must not be {@code null}.
	 * 
	 * @return The properties found in the file.
	 *
	 * @throws IOException If the file wasn't found, or the properties
	 *                     couldn't be loaded.
	 */
	private Properties loadPropertiesFromFile(final String filename)
		throws IOException {
		
		InputStream is = null;

		try {
			is = this.getClass().getResourceAsStream("/" + filename);
			
			Properties props = new Properties();
			props.load(is);
			return props;
			
		} finally {

			try {
				if (is != null)
					is.close();

			} catch (IOException e) {
				
				// ignore
			}
		}
	}
	
	
	/**
	 * Gets the current system variables environment (lazy loading).
	 *
	 * @return The system variables environment.
	 */
	private Environment getEnvironment() {

		if(environment == null)
			this.environment = new SystemVariablesEnvironment();

		return this.environment;
	}
	

	/**
	 * Sets the current system variables environment.
	 *
	 * @param env The system variables environment.
	 */
	public void setEnvironment(final Environment env) {

		this.environment = env;
	}


	/**
	 * Loads the CORS filter configuration.
	 *
	 * <p>The following precedence applies:
	 *
	 * <ul>
	 *     <li>The system environment is checked for a 
	 *         {@code cors.configurationFile} variable. If it exists, the
	 *         configuration properties are loaded from the file location
	 *         specified by the variable value. The location is typically
	 *         relative to the web application root directory.
	 *     <li>The web.xml filter initialisation parameters are checked for
	 *         a {@code cors.configurationFile} variable. If it exists, the
	 *         configuration properties are loaded from the file location
	 *         specified by the parameter value. The location is typically
	 *         relative to the web application root directory.
	 *     <li>The configuration is specified by the web.xml filter
	 *         initialisation parameters with the matching name. If an
	 *         initialisation parameter is not defined the default 
	 *         configuration property is applied.
	 * </ul>
	 *
	 * @return The loaded CORS filter configuration.
	 *
	 * @throws CORSConfigurationException If the configuration file
	 *                                    couldn't be loaded or parsing of
	 *                                    one or more properties failed due
	 *                                    to an illegal value.
	 */
	public CORSConfiguration load()
		throws CORSConfigurationException {

		Properties props;

		try {
			// Try to get the config file from the sys environment
			String configFile = getEnvironment().getProperty(CONFIG_FILE_PARAM_NAME);
			
			if (configFile == null || configFile.trim().isEmpty()) {

				// Try to get the config file from the filter init param
				configFile = filterConfig.getInitParameter(CONFIG_FILE_PARAM_NAME);
			}

			
			if (configFile != null) {

				props = loadPropertiesFromFile(configFile);

			} else {

				props = getFilterInitParameters(filterConfig);
			}

		} catch(IOException e) {

			throw new CORSConfigurationException(e.getMessage(), e);
		}

		return new CORSConfiguration(props);
	}
}