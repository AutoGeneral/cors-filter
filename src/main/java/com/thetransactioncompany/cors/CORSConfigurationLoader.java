package com.thetransactioncompany.cors;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
	 * The name of the system environment variable / filter initialisation
	 * parameter that points to the file name holding the CORS 
	 * configuration properties.
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
	 * Converts the initial filter parameters (typically specified in the 
	 * {@code web.xml} file) to a Java properties representation. The 
	 * parameter names become property keys.
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
	 * @param filterConfig The filter configuration. Must not be
	 *                     {@code null}.
	 */
	public CORSConfigurationLoader(final FilterConfig filterConfig) {

		if (filterConfig == null)
			throw new IllegalArgumentException("The filter configuration must not be null");

		this.filterConfig = filterConfig;
	}
	
	
	/**
	 * Loads the properties from the specified file.
	 *
	 * @param filename The file name. Must not be {@code null}.
	 * 
	 * @return The properties found in the file.
	 *
	 * @throws SecurityException If access to the file system was denied.
	 * @throws IOException       If the file wasn't found, or the 
	 *                           properties couldn't be loaded.
	 */
	private Properties loadPropertiesFromFile(final String filename)
		throws SecurityException, IOException {
		
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
	 * Gets the current system variables environment (using lazy loading).
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

		} catch (SecurityException e) {

			throw new CORSConfigurationException(e.getMessage(), e);

		} catch(IOException e) {

			throw new CORSConfigurationException(e.getMessage(), e);
		}

		return new CORSConfiguration(props);
	}
}