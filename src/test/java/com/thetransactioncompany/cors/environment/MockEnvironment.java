package com.thetransactioncompany.cors.environment;

import java.util.Map;

import com.thetransactioncompany.cors.CORSFilter;
import com.thetransactioncompany.cors.environment.Environment;

public class MockEnvironment implements Environment {

	private String configurationFileName;
	
	@Override
	public String getenv(String name) {
		if(name.equalsIgnoreCase(CORSFilter.CONFIGURATION_FILE_ENV_VARIABLENAME))
			return configurationFileName;
		return null;
	}

	@Override
	public Map<String, String> getenv() {
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
