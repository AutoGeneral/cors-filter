package com.thetransactioncompany.cors;


import javax.servlet.http.HttpServletRequest;


/**
 * Enumeration of the CORS request types.
 *
 * @author Vladimir Dzhuvinov
 * @author Brandon Murray
 */
public enum CORSRequestType {

	
	/**
	 * Simple / actual CORS request.
	 */
	ACTUAL,
	
	
	/**
	 * Preflight CORS request.
	 */
	PREFLIGHT,
	
	
	/**
	 * Other (non-CORS) request.
	 */
	OTHER;
	
	
	/**
	 * Detects the CORS type of the specified HTTP request.
	 *
	 * @param request The HTTP request to check. Must not be {@code null}.
	 *
	 * @return The CORS request type.
	 */
	public static CORSRequestType detect(final HttpServletRequest request) {

		if (request.getHeader(HeaderName.ORIGIN) == null) {

			// All CORS request have an Origin header
			return OTHER;
		}

		// Some browsers include the Origin header even when submitting 
		// from the same domain. This is legal according to RFC 6454, 
		// section-7.3
		String serverOrigin = request.getScheme() + "://" + request.getHeader(HeaderName.HOST);

		if (request.getHeader(HeaderName.HOST) != null && request.getHeader(HeaderName.ORIGIN).equals(serverOrigin)) {
			return OTHER;
		}
		
		// We have a CORS request - determine type
		if (request.getHeader(HeaderName.ACCESS_CONTROL_REQUEST_METHOD) != null &&
		    request.getMethod()                                         != null &&
		    request.getMethod().equalsIgnoreCase("OPTIONS")                        ) {

			return PREFLIGHT;

		} else {

			return ACTUAL;
		}
	}
}
