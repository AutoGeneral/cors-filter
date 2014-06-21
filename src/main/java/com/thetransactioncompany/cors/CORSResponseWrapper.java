package com.thetransactioncompany.cors;


import java.util.HashMap;
import java.util.Map;
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

		for (String headerName : this.getHeaderNames()) {
			if (headerName.startsWith("Access-Control-")) {
				// it's a CORS header
				corsHeaders.put(headerName, getHeader(headerName));
			}
		}

		super.reset();

		for (String headerName : corsHeaders.keySet()) {
			setHeader(headerName, corsHeaders.get(headerName));
		}
	}
}
