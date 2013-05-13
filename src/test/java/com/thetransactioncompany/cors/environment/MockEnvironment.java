package com.thetransactioncompany.cors.environment;

import java.util.Properties;

import com.thetransactioncompany.cors.CORSFilter;

public class MockEnvironment implements Environment {

	private String configurationFileName;
	
	@Override
	public String getProperty(String name) {
		if(name.equalsIgnoreCase(CORSFilter.CONFIGURATION_FILE_ENV_VARIABLENAME))
			return configurationFileName;
		return null;
	}

	@Override
	public Properties getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getConfigurationFileName() {
		return configurationFileName;
	}

	public void setConfigurationFileName(String configurationFileName) {
		this.configurationFileName = configurationFileName;
	}

}
