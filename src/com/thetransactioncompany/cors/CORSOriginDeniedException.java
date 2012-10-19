package com.thetransactioncompany.cors;


/**
 * CORS origin denied (not allowed) exception.
 *
 * @author Vladimir Dzhuvinov
 * @version $version$ (2012-10-19)
 */
public class CORSOriginDeniedException extends CORSException {

	
	/**
	 * The request origin.
	 */
	private final Origin requestOrigin;
	
	
	/**
	 * Creates a new CORS origin denied exception with the specified 
	 * message.
	 *
	 * @param message The message.
	 */
	public CORSOriginDeniedException(final String message) {
	
		this(message, null);
	}
	
	
	/**
	 * Creates a new CORS origin denied exception with the specified 
	 * message and request origins.
	 *
	 * @param message       The message.
	 * @param requestOrigin The request origin, {@code null} if unknown.
	 */
	public CORSOriginDeniedException(final String message, final Origin requestOrigin) {
	
		super(message);
		
		this.requestOrigin = requestOrigin;
	}
	
	
	/**
	 * Gets the request origin.
	 *
	 * @return The request origin, {@code null} if unknown or not set.
	 */
	public Origin getRequestOrigin() {
	
		return requestOrigin;
	}
}
