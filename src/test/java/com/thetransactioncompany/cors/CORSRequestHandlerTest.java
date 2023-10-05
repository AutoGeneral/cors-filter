package com.thetransactioncompany.cors;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests the CORS request handler.
 *
 * @author Vladimir Dzhuvinov
 */
public class CORSRequestHandlerTest {


	@Test
    public void testActualRequestWithDefaultConfiguration()
		throws Exception {

		CORSConfiguration config = new CORSConfiguration(new Properties());

		CORSRequestHandler handler = new CORSRequestHandler(config);

		MockServletRequest request = new MockServletRequest();
		request.setHeader("Origin", "http://example.com");

		MockServletResponse response = new MockServletResponse();

		handler.handleActualRequest(request, response);

		assertEquals("http://example.com", response.getHeader("Access-Control-Allow-Origin"));
		assertEquals("Origin", response.getHeader("Vary"));

		assertEquals("true", response.getHeader("Access-Control-Allow-Credentials"));

		assertEquals(3, response.getHeaders().size());
	}


	@Test
    public void testActualRequestWithCredentialsNotAllowed()
		throws Exception {

		Properties props = new Properties();
		props.setProperty("cors.supportsCredentials", "false");
		CORSConfiguration config = new CORSConfiguration(props);

		CORSRequestHandler handler = new CORSRequestHandler(config);

		MockServletRequest request = new MockServletRequest();
		request.setHeader("Origin", "http://example.com");

		MockServletResponse response = new MockServletResponse();

		handler.handleActualRequest(request, response);

		assertEquals("*", response.getHeader("Access-Control-Allow-Origin"));

		assertNull(response.getHeader("Access-Control-Allow-Credentials"));

		assertEquals(1, response.getHeaders().size());
	}


	@Test
    public void testActualRequestWithExposedHeaders()
		throws Exception {

		Properties props = new Properties();
		props.put("cors.exposedHeaders", "X-Custom");

		CORSConfiguration config = new CORSConfiguration(props);

		CORSRequestHandler handler = new CORSRequestHandler(config);

		MockServletRequest request = new MockServletRequest();
		request.setHeader("Origin", "http://example.com");

		MockServletResponse response = new MockServletResponse();

		handler.handleActualRequest(request, response);

		assertEquals("http://example.com", response.getHeader("Access-Control-Allow-Origin"));
		assertEquals("Origin", response.getHeader("Vary"));

		assertEquals("true", response.getHeader("Access-Control-Allow-Credentials"));

		assertEquals("X-Custom", response.getHeader("Access-Control-Expose-Headers"));

		assertEquals(4, response.getHeaders().size());
	}


	@Test
    public void testActualRequestWithDeniedOrigin()
		throws Exception {

		Properties props = new Properties();
		props.put("cors.allowOrigin", "http://example.com");

		CORSConfiguration config = new CORSConfiguration(props);

		CORSRequestHandler handler = new CORSRequestHandler(config);

		MockServletRequest request = new MockServletRequest();
		request.setHeader("Origin", "http://other.com");

		MockServletResponse response = new MockServletResponse();

		try {
			handler.handleActualRequest(request, response);
			fail();
		} catch (CORSOriginDeniedException e) {
			// ok
			assertEquals("CORS origin denied", e.getMessage());
		}
	}


	@Test
    public void testActualRequestWithUnsupportedMethod()
		throws Exception {

		Properties props = new Properties();
		props.put("cors.supportedMethods", "GET POST");

		CORSConfiguration config = new CORSConfiguration(props);

		CORSRequestHandler handler = new CORSRequestHandler(config);

		MockServletRequest request = new MockServletRequest();
		request.setHeader("Origin", "http://example.com");
		request.setMethod("DELETE");

		MockServletResponse response = new MockServletResponse();

		try {
			handler.handleActualRequest(request, response);
			fail();
		} catch (UnsupportedHTTPMethodException e) {
			// ok
			assertEquals("Unsupported HTTP method", e.getMessage());
		}
	}


	@Test
    public void testPreflightRequestWithDefaultConfiguration()
		throws Exception {

		CORSConfiguration config = new CORSConfiguration(new Properties());

		CORSRequestHandler handler = new CORSRequestHandler(config);

		MockServletRequest request = new MockServletRequest();
		request.setHeader("Origin", "http://example.com");
		request.setHeader("Access-Control-Request-Method", "POST");
		request.setMethod("OPTIONS");

		MockServletResponse response = new MockServletResponse();

		handler.handlePreflightRequest(request, response);

		assertEquals("http://example.com", response.getHeader("Access-Control-Allow-Origin"));
		assertEquals("Origin", response.getHeader("Vary"));

		Set<String> methods = new HashSet<String>(Arrays.asList(HeaderUtils.parseMultipleHeaderValues(response.getHeader("Access-Control-Allow-Methods"))));
		assertTrue(methods.contains("HEAD"));
		assertTrue(methods.contains("GET"));
		assertTrue(methods.contains("POST"));
		assertTrue(methods.contains("OPTIONS"));
		assertEquals(4, methods.size());

		assertEquals("true", response.getHeader("Access-Control-Allow-Credentials"));

		assertEquals(4, response.getHeaders().size());
	}


	@Test
    public void testPreflightRequestWithCredentialsNotAllowed()
		throws Exception {

		Properties props = new Properties();
		props.setProperty("cors.supportsCredentials", "false");
		CORSConfiguration config = new CORSConfiguration(props);

		CORSRequestHandler handler = new CORSRequestHandler(config);

		MockServletRequest request = new MockServletRequest();
		request.setHeader("Origin", "http://example.com");
		request.setHeader("Access-Control-Request-Method", "POST");
		request.setMethod("OPTIONS");

		MockServletResponse response = new MockServletResponse();

		handler.handlePreflightRequest(request, response);

		assertEquals("*", response.getHeader("Access-Control-Allow-Origin"));

		Set<String> methods = new HashSet<String>(Arrays.asList(HeaderUtils.parseMultipleHeaderValues(response.getHeader("Access-Control-Allow-Methods"))));
		assertTrue(methods.contains("HEAD"));
		assertTrue(methods.contains("GET"));
		assertTrue(methods.contains("POST"));
		assertTrue(methods.contains("OPTIONS"));
		assertEquals(4, methods.size());

		assertNull(response.getHeader("Access-Control-Allow-Credentials"));

		assertEquals(2, response.getHeaders().size());
	}


	@Test
    public void testPreflightRequestWithSupportAnyHeader()
		throws Exception {

		Properties props = new Properties();
		props.setProperty("cors.supportedHeaders", "*");

		CORSConfiguration config = new CORSConfiguration(props);

		CORSRequestHandler handler = new CORSRequestHandler(config);

		MockServletRequest request = new MockServletRequest();
		request.setHeader("Origin", "http://example.com");
		request.setHeader("Access-Control-Request-Method", "POST");
		request.setHeader("Access-Control-Request-Headers", "Authorization, Content-Type");
		request.setMethod("OPTIONS");

		MockServletResponse response = new MockServletResponse();

		handler.handlePreflightRequest(request, response);

		assertEquals("Authorization, Content-Type", response.getHeader("Access-Control-Allow-Headers"));
	}
}
