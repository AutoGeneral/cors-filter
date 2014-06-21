package com.thetransactioncompany.cors;


import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;


/**
 * Represents an HTTP header name. Provides an {@link #equals} method to
 * compare two header names using case-insensitive matching (RFC 2616, section
 * 4.2).
 *
 * <p>Header field name examples:
 *
 * <ul>
 *     <li>Content-Type
 *     <li>User-Agent
 *     <li>X-Requested-With
 * </ul>
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
	 * The header field name, formatted as {@code Aaa-Bbb-Ccc}.
	 */
	private final String name;


	/**
	 * Applies a {@code Aaa-Bbb-Ccc} format to a header field name.
	 *
	 * @param name The header field name to format, must not be an empty
	 *             string or {@code null}.
	 *
	 * @return The formatted header field name.
	 *
	 * @throws IllegalArgumentException On a empty or invalid header field
	 *                                  name.
	 */
	protected static String formatCanonical(final String name) {

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
	 * Creates a new header field name from the specified string.
	 *
	 * @param name The header field name, must not be an empty strings or
	 *             {@code null}.
	 *
	 * @throws IllegalArgumentException On a empty or invalid header field
	 *                                  name.
	 */
	public HeaderName(final String name) {

		this.name = formatCanonical(name);
	}


	/**
	 * Returns a string representation of a header field name in {@code
	 * Aaa-Bbb-Ccc} format.
	 *
	 * @return The header field name as string.
	 */
	@Override
	public String toString() {

		return name;
	}


	/**
	 * Overrides {@code Object.hashCode}.
	 *
	 * @return The object hash code.
	 */
	@Override
	public int hashCode() {

		return name.hashCode();
	}


	/**
	 * Overrides {@code Object.equals()}.
	 *
	 * @param object The object to compare to.
	 *
	 * @return {@code true} if the objects have the same value, otherwise
	 * {@code false}.
	 */
	@Override
	public boolean equals(Object object) {

		return object instanceof HeaderName && name.equalsIgnoreCase(object.toString());
	}
}
