package com.thetransactioncompany.cors;


import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;


/**
 * HTTP header name constants and utilities.
 *
 * @author Vladimir Dzhuvinov
 * @author Chris Mountford
 */
public class HeaderName {


	/**
	 * Must match "token", 1 or more of any US-ASCII char except control
	 * chars or specific "separators", see:
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec4.html#sec4.2
	 * and
	 * http://www.w3.org/Protocols/rfc2616/rfc2616-sec2.html#sec2
	 *
	 * Note use of regex character class subtraction and character class
	 * metacharacter rules.
	 */
	private static final Pattern VALID = compile("^[\\x21-\\x7e&&[^]\\[}{()<>@,;:\\\\\"/?=]]+$");


	/**
	 * "Origin" header name.
	 */
	public static final String ORIGIN = "Origin";


	/**
	 * "Access-Control-Request-Method" header name.
	 */
	public static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method";


	/**
	 * "Access-Control-Request-Headers" header name.
	 */
	public static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers";


	/**
	 * "Access-Control-Allow-Origin" header name.
	 */
	public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";


	/**
	 * "Access-Control-Allow-Methods" header name.
	 */
	public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";


	/**
	 * "Access-Control-Allow-Headers" header name.
	 */
	public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";


	/**
	 * "Access-Control-Allow-Credentials" header name.
	 */
	public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";


	/**
	 * "Access-Control-Expose-Headers" header name.
	 */
	public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";


	/**
	 * "Access-Control-Max-Age" header name.
	 */
	public static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";


	/**
	 * "Vary" header name.
	 */
	public static final String VARY = "Vary";


	/**
	 * "Host" header name.
	 */
	public static final String HOST = "Host";


	/**
	 * Applies a {@code Aaa-Bbb-Ccc} format to a header name.
	 *
	 * @param name The header name to format, must not be an empty string
	 *             or {@code null}.
	 *
	 * @return The formatted header name.
	 *
	 * @throws IllegalArgumentException On a empty or invalid header name.
	 */
	public static String formatCanonical(final String name) {

		String nameTrimmed = name.trim();

		if (nameTrimmed.isEmpty())
			throw new IllegalArgumentException("The header field name must not be an empty string");

		assert(VALID != null);

		// Check for valid syntax
		if (! VALID.matcher(nameTrimmed).matches())
			throw new IllegalArgumentException("Invalid header field name syntax (see RFC 2616)");


		String[] tokens = nameTrimmed.toLowerCase().split("-");

		String out = "";

		for (int i = 0; i < tokens.length; i++) {

			char[] c = tokens[i].toCharArray();

			// Capitalise first char
			c[0] = Character.toUpperCase(c[0]);

			if (i >= 1)
				out = out + "-";

			out = out + new String(c);
		}

		return out;
	}


	/**
	 * Prevents public instantiation.
	 */
	private HeaderName() {

	}
}
