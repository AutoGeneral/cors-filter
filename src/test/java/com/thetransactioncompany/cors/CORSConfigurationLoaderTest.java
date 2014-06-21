package com.thetransactioncompany.cors;


import junit.framework.TestCase;

import com.thetransactioncompany.cors.environment.MockEnvironment;


/**
 * Tests the CORS configuration loader.
 *
 * @author David Bellem
 * @author Vladimir Dzhuvinov
 */
public class CORSConfigurationLoaderTest extends TestCase {
	
	
	public void testEnvVarBasedConfig() {
		
		CORSConfigurationLoader configLoader = new CORSConfigurationLoader(new MockFilterConfig());

		MockEnvironment mockEnv = new MockEnvironment();
		mockEnv.setConfigurationFileName("cors-sample.configuration");
		configLoader.setEnvironment(mockEnv);
		
		CORSConfiguration c = null;

		try {
			c = configLoader.load();

		} catch (CORSConfigurationException e) {

			fail(e.getMessage());
		}
		
		assertTrue(c.allowGenericHttpRequests);
		
		assertFalse(c.allowAnyOrigin);
		assertFalse(c.allowSubdomains);
		assertTrue(c.isAllowedOrigin(new Origin("https://www.example.org:9000")));
		assertTrue(c.isAllowedOrigin(new Origin("http://example.com:8008")));
		assertFalse(c.isAllowedOrigin(new Origin("https://sub.example.org:9000")));
		assertFalse(c.isAllowedOrigin(new Origin("http://example.com")));
		assertFalse(c.isAllowedOrigin(new Origin("http://deny-origin.com")));
		
		assertTrue(c.isSupportedMethod(HTTPMethod.GET));
		assertTrue(c.isSupportedMethod(HTTPMethod.POST));
		assertTrue(c.isSupportedMethod(HTTPMethod.HEAD));
		assertTrue(c.isSupportedMethod(HTTPMethod.OPTIONS));
		assertTrue(c.isSupportedMethod(HTTPMethod.DELETE));
		assertTrue(c.isSupportedMethod(HTTPMethod.PUT));
		
		assertTrue(c.isSupportedHeader(new HeaderName("Origin")));
		assertTrue(c.isSupportedHeader(new HeaderName("X-Requested-With")));
		assertTrue(c.isSupportedHeader(new HeaderName("Content-Type")));
		assertTrue(c.isSupportedHeader(new HeaderName("Accept")));
		assertFalse(c.isSupportedHeader(new HeaderName("X-Forwarded-Proto")));
		
		assertTrue(c.exposedHeaders.size() == 0);
		
		assertTrue(c.supportsCredentials);
		
		assertEquals(3600, c.maxAge);
	}
}
