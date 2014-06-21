package com.thetransactioncompany.cors;


import java.util.*;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;


/**
 * HTTP response wrapper that preserves the CORS response headers on
 * {@link javax.servlet.ServletResponse#reset()}. Some web applications and
 * frameworks (e.g. RestEasy) reset the servlet response when a HTTP 4xx error
 * is produced; this wrapper ensures previously set CORS headers survive such a
 * reset.
 *
 * @author Gervasio Amy
 */
public class CORSResponseWrapper extends HttpServletResponseWrapper {


	/**
	 * The names of the CORS response headers to preserve.
	 */
	public static final Set<String> RESPONSE_HEADER_NAMES;


	static {
		Set<String> headerNames = new HashSet<String>();
		headerNames.add(HeaderName.ACCESS_CONTROL_ALLOW_ORIGIN);
		headerNames.add(HeaderName.ACCESS_CONTROL_ALLOW_CREDENTIALS);
		headerNames.add(HeaderName.ACCESS_CONTROL_EXPOSE_HEADERS);
		headerNames.add(HeaderName.VARY);
		RESPONSE_HEADER_NAMES = Collections.unmodifiableSet(headerNames);
	}


	/**
	 * Creates a new CORS response wrapper for the specified HTTP servlet
	 * response.
	 *
	 * @param response The HTTP servlet response.
	 */
	public CORSResponseWrapper(final HttpServletResponse response) {

		super(response);
	}

	@Override
	public void reset() {

		Map<String,String> corsHeaders = new HashMap<String,String>();

		for (String headerName : getHeaderNames()) {

			if (RESPONSE_HEADER_NAMES.contains(headerName)) {
				// save
				corsHeaders.put(headerName, getHeader(headerName));
			}
		}

		super.reset();

		for (String headerName : corsHeaders.keySet()) {
			setHeader(headerName, corsHeaders.get(headerName));
		}
	}
}
