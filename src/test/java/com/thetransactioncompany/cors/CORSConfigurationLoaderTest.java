package com.thetransactioncompany.cors;


import com.thetransactioncompany.cors.environment.MockEnvironment;
import junit.framework.TestCase;


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
		
		assertTrue(c.isSupportedMethod("GET"));
		assertTrue(c.isSupportedMethod("POST"));
		assertTrue(c.isSupportedMethod("HEAD"));
		assertTrue(c.isSupportedMethod("OPTIONS"));
		assertTrue(c.isSupportedMethod("DELETE"));
		assertTrue(c.isSupportedMethod("PUT"));
		
		assertTrue(c.isSupportedHeader("Origin"));
		assertTrue(c.isSupportedHeader("X-Requested-With"));
		assertTrue(c.isSupportedHeader("Content-Type"));
		assertTrue(c.isSupportedHeader("Accept"));
		assertFalse(c.isSupportedHeader("X-Forwarded-Proto"));
		
		assertTrue(c.exposedHeaders.size() == 0);
		
		assertTrue(c.supportsCredentials);
		
		assertEquals(3600, c.maxAge);
	}
}
