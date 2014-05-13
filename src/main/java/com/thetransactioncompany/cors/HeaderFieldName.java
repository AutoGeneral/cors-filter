package com.thetransactioncompany.cors;


import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;


/**
 * Represents an HTTP header field name. Provides an {@link #equals} method to
 * compare two header names using case-insensitive matching (RFC 2616, section
 * 4.2).
 *
 * <p>Header field name examples:
 *
 * <ul> <li>Content-Type <li>User-Agent <li>X-Requested-With </ul>
 *
 * @author Vladimir Dzhuvinov
 * @author Chris Mountford
 */
public class HeaderFieldName {


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

		// Check for valid syntax
		if (!VALID.matcher(nameTrimmed).matches())
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
	public HeaderFieldName(final String name) {

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

		return object instanceof HeaderFieldName && name.equals(object.toString());
	}
}
