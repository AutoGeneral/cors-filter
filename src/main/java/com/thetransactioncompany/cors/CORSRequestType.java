package com.thetransactioncompany.cors;


import javax.servlet.http.HttpServletRequest;


/**
 * Enumeration of the CORS request types.
 *
 * @author Vladimir Dzhuvinov
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
	
		if (request == null)
			throw new NullPointerException("The HTTP request must not be null");
		
		// All CORS request have an Origin header
		if (request.getHeader("Origin") == null    ||
                // Some browsers include the Origin header even when submitting from the same domain
                (request.getHeader("Host") != null &&
                 request.getHeader("Origin").equals(request.getScheme() + "://" + request.getHeader("Host"))))
			return OTHER;
		
		// We have a CORS request - determine type
		if (request.getHeader("Access-Control-Request-Method") != null &&
		    request.getMethod()                                != null &&
		    request.getMethod().equals("OPTIONS")                         )
		    
			return PREFLIGHT;
			
		else
			return ACTUAL;
	}
}
