package com.thetransactioncompany.cors;


import javax.servlet.ServletException;

import junit.framework.TestCase;

import com.thetransactioncompany.cors.environment.MockEnvironment;


/**
 * Tests the CORS Filter class.
 *
 * @author David Bellem
 */
public class CORSFilterTest extends TestCase {
	
	
	public void testFileBasedConfig() {
        
		MockEnvironment mock = new MockEnvironment();
		mock.setConfigurationFileName("cors-sample.configuration");
		
		CORSFilter filter = new CORSFilter();
		filter.setEnvironment(mock);
		
		try {
			filter.init(null);
		} catch (ServletException e) {
			fail();
		}
		
		CORSConfiguration c = filter.getConfiguration();
		
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
		
		assertTrue(c.isSupportedHeader(new HeaderFieldName("Origin")));
		assertTrue(c.isSupportedHeader(new HeaderFieldName("X-Requested-With")));
		assertTrue(c.isSupportedHeader(new HeaderFieldName("Content-Type")));
		assertTrue(c.isSupportedHeader(new HeaderFieldName("Accept")));		
		assertFalse(c.isSupportedHeader(new HeaderFieldName("X-Forwarded-Proto")));
		
		assertTrue(c.exposedHeaders.size() == 0);
		
		assertTrue(c.supportsCredentials);
		
		assertEquals(3600, c.maxAge);
	}
}
