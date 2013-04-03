package com.thetransactioncompany.cors;


import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Handles incoming cross-origin (CORS) requests according to the configured 
 * access policy. Encapsulates the CORS processing logic as specified by the
 * <a href="http://www.w3.org/TR/2012/WD-cors-20120403/">W3C draft</a> from 
 * 2012-04-03.
 *
 * <p>Note that the actual CORS exception handling (which is outside the CORS
 * specification scope) is left to the invoking class to implement.
 *
 * @author Vladimir Dzhuvinov
 * @version $version$ (2012-10-19)
 */
public class CORSRequestHandler {

	
	/**
	 * The CORS filter configuration, detailing the cross-origin access 
	 * policy.
	 */
	private CORSConfiguration config;
	
	
	/**
	 * Pre-computed string of the CORS supported methods.
	 */
	private String supportedMethods;
	
	
	/**
	 * Pre-computed string of the CORS supported headers.
	 */
	private String supportedHeaders;
	
	
	/**
	 * Pre-computed string of the CORS exposed headers.
	 */
	private String exposedHeaders;
	
	
	/**
	 * Creates a new CORS request handler.
	 *
	 * @param config Specifies the cross-origin access policy.
	 */
	public CORSRequestHandler(final CORSConfiguration config) {
	
		this.config = config;
		
		// Pre-compute response headers where possible
		supportedMethods = serialize(config.supportedMethods, ", ");
		supportedHeaders = serialize(config.supportedHeaders, ", ");
		exposedHeaders = serialize(config.exposedHeaders, ", ");	
	}
	
	
	/**
	 * Serialises the items of a set into a string. Each item must have a 
	 * meaningful {@code toString()} method.
	 * 
	 * @param set The set to serialise.
	 * @param sep The string separator to apply.
	 *
	 * @return The serialised set as string.
	 */
	private static String serialize(final Set set, final String sep) {
	
		Iterator it = set.iterator();
	
		String s = "";
	
		while (it.hasNext()) {
			
			s = s + it.next().toString();
			
			if (it.hasNext())
				s = s + sep;
		}
		
		return s;
	}
	
	
	/**
	 * Parses a header value consisting of zero or more space / comma / 
	 * space+comma separated strings. The input string is trimmed before 
	 * splitting.
	 *
	 * @param headerValue The header value, may be {@code null}.
	 *
	 * @return A string array of the parsed string items, empty if none
	 *         were found or the input was {@code null}.
	 */
	private static String[] parseMultipleHeaderValues(final String headerValue) {
	
		if (headerValue == null)
			return new String[0];
	
		String trimmedHeaderValue = headerValue.trim();
		
		if (trimmedHeaderValue.isEmpty())
			return new String[0];
	
		return trimmedHeaderValue.split("\\s*,\\s*|\\s+");
	}
	
	
	/**
	 * Tags an HTTP servlet request to provide CORS information to 
	 * downstream handlers.
	 *
	 * <p>Tagging is provided via {@code HttpServletRequest.setAttribute()}.
	 *
	 * <ul>
	 *     <li>{@code cors.isCorsRequest} set to {@code true} or {@code false}.
	 *     <li>{@code cors.origin} set to the value of the "Origin" header, 
	 *         {@code null} if undefined.
	 *     <li>{@code cors.requestType} set to "actual" or "preflight" (for 
	 *         CORS requests).
	 *     <li>{@code cors.requestHeaders} set to the value of the 
	 *         "Access-Control-Request-Headers" or {@code null} if 
	 *         undefined (added for preflight CORS requests only).
	 * </ul>
	 *
	 * @param request The servlet request to inspect and tag. Must not be
	 *                {@code null}.
	 */
	public void tagRequest(final HttpServletRequest request) {
		
		final CORSRequestType type = CORSRequestType.detect(request);
		
		switch (type) {
		
			case ACTUAL:
				request.setAttribute("cors.isCorsRequest", true);
				request.setAttribute("cors.origin", request.getHeader("Origin"));
				request.setAttribute("cors.requestType", "actual");
				break;
				
			case PREFLIGHT:
				request.setAttribute("cors.isCorsRequest", true);
				request.setAttribute("cors.origin", request.getHeader("Origin"));
				request.setAttribute("cors.requestType", "preflight");
				request.setAttribute("cors.requestHeaders", request.getHeader("Access-Control-Request-Headers"));
				break;
				
			case OTHER:
				request.setAttribute("cors.isCorsRequest", false);
		}
	}
	
	
	/**
	 * Handles a simple or actual CORS request.
	 *
	 * <p>CORS specification: <a href="http://www.w3.org/TR/access-control/#resource-requests">Simple Cross-Origin Request, Actual Request, and Redirects</a>
	 *
	 * @param request  The HTTP request.
	 * @param response The HTTP response.
	 *
	 * @throws InvalidCORSRequestException    If not a valid CORS simple/
	 *                                        actual request.
	 * @throws CORSOriginDeniedException      If the origin is not allowed.
	 * @throws UnsupportedHTTPMethodException If the requested HTTP method
	 *                                        is not supported by the CORS
	 *                                        policy.
	 */
	public void handleActualRequest(final HttpServletRequest request, final HttpServletResponse response)
		throws InvalidCORSRequestException, 
		       CORSOriginDeniedException, 
		       UnsupportedHTTPMethodException {
	
		if (CORSRequestType.detect(request) != CORSRequestType.ACTUAL)
			throw new InvalidCORSRequestException("Invalid simple/actual CORS request");
		
		
		// Check origin against allow list
		Origin requestOrigin = new Origin(request.getHeader("Origin"));
		
		if (! config.isAllowedOrigin(requestOrigin))
			throw new CORSOriginDeniedException("CORS origin denied", requestOrigin);
		
		
		// Check method
		
		HTTPMethod method = null;
		
		try {
			method = HTTPMethod.valueOf(request.getMethod());
			
		} catch (Exception e) {
			// Parse exception
			throw new UnsupportedHTTPMethodException("Unsupported HTTP method: " + request.getMethod());
		}
		
		if (! config.isSupportedMethod(method))
			throw new UnsupportedHTTPMethodException("Unsupported HTTP method", method);
		
		
		// Success, append response headers
		
		response.addHeader("Access-Control-Allow-Origin", requestOrigin.toString());
		
		if (config.supportsCredentials)
			response.addHeader("Access-Control-Allow-Credentials", "true");
		
		if (! exposedHeaders.isEmpty())
			response.addHeader("Access-Control-Expose-Headers", exposedHeaders);
		
		
		// Tag request
		request.setAttribute("cors.origin", requestOrigin.toString());
		request.setAttribute("cors.requestType", "actual");
	}
	
	
	/**
	 * Handles a preflight CORS request.
	 *
	 * <p>CORS specification: <a href="http://www.w3.org/TR/access-control/#resource-preflight-requests">Preflight Request</a>
	 *
	 * @param request  The HTTP request.
	 * @param response The HTTP response.
	 *
	 * @throws InvalidCORSRequestException    If not a valid CORS preflight
	 *                                        request.
	 * @throws CORSOriginDeniedException      If the origin is not allowed.
	 * @throws UnsupportedHTTPMethodException If the requested HTTP method
	 *                                        is not supported by the CORS
	 *                                        policy.
	 * @throws UnsupportedHTTPHeaderException If the requested HTTP header
	 *                                        is not supported by the CORS
	 *                                        policy.
	 */
	public void handlePreflightRequest(final HttpServletRequest request, final HttpServletResponse response)
		throws InvalidCORSRequestException, 
		       CORSOriginDeniedException, 
		       UnsupportedHTTPMethodException, 
		       UnsupportedHTTPHeaderException {
		
		if (CORSRequestType.detect(request) != CORSRequestType.PREFLIGHT)
			throw new InvalidCORSRequestException("Invalid preflight CORS request");
		
		// Check origin against allow list
		Origin requestOrigin = new Origin(request.getHeader("Origin"));
		
		if (! config.isAllowedOrigin(requestOrigin))
			throw new CORSOriginDeniedException("CORS origin denied", requestOrigin);
			
		
		// Parse requested method
		// Note: method checking must be done after header parsing, see CORS spec
		
		String requestMethodHeader = request.getHeader("Access-Control-Request-Method");
		
		if (requestMethodHeader == null)
			throw new InvalidCORSRequestException("Invalid preflight CORS request: Missing Access-Control-Request-Method header");
		
		HTTPMethod requestedMethod = null;
		
		try {
			requestedMethod = HTTPMethod.valueOf(requestMethodHeader.toUpperCase());
			
		} catch (Exception e) {
			// Parse exception
			throw new UnsupportedHTTPMethodException("Unsupported HTTP method: " + requestMethodHeader);
		}
		
		
		// Parse custom headers
		
		final String[] requestHeaderValues = parseMultipleHeaderValues(request.getHeader("Access-Control-Request-Headers"));
		
		final HeaderFieldName[] requestHeaders = new HeaderFieldName[requestHeaderValues.length];
		
		for (int i=0; i<requestHeaders.length; i++) {
		
			try {
				requestHeaders[i] = new HeaderFieldName(requestHeaderValues[i]);
				
			} catch (IllegalArgumentException e) {
				// Invalid header name
				throw new InvalidCORSRequestException("Invalid preflight CORS request: Bad request header value");
			}
		}
		
		
		// Now, do method check
		if (! config.isSupportedMethod(requestedMethod))
			throw new UnsupportedHTTPMethodException("Unsupported HTTP method", requestedMethod);
		
		
		// Author request headers check
		
		for (int i=0; i<requestHeaders.length; i++) {
		
			if (! config.supportedHeaders.contains(requestHeaders[i]))
				throw new UnsupportedHTTPHeaderException("Unsupported HTTP request header", requestHeaders[i]);
				
		}
		
		// Success, append response headers
		
		if (config.supportsCredentials) {
			response.addHeader("Access-Control-Allow-Origin", requestOrigin.toString());
			response.addHeader("Access-Control-Allow-Credentials", "true");
		}
		else {
			if (config.allowAnyOrigin)
				response.addHeader("Access-Control-Allow-Origin", "*");
			else
				response.addHeader("Access-Control-Allow-Origin", requestOrigin.toString());
		}
		
		if (config.maxAge > 0)
			response.addHeader("Access-Control-Max-Age", Integer.toString(config.maxAge));
		
		response.addHeader("Access-Control-Allow-Methods", supportedMethods);
		
		if (! supportedHeaders.isEmpty())
			response.addHeader("Access-Control-Allow-Headers", supportedHeaders);
	}
}
