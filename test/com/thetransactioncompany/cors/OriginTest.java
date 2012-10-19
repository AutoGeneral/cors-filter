package com.thetransactioncompany.cors;


import junit.framework.TestCase;


/**
 * Tests the base origin class.
 *
 * @author Vladimir Dzhuvinov
 * @author Jared Ottley
 * @version $version$ (2012-10-19)
 */
public class OriginTest extends TestCase {
        
        
	public void testOrigin() {
	
		String uri = "http://example.com";
		
		Origin o = new Origin(uri);
		
		assertEquals(uri, o.toString());
		assertEquals(uri.hashCode(), o.hashCode());
	}
	
	
	public void testOriginEquality() {
	
		String uri = "http://example.com";
		
		Origin o1 = new Origin(uri);
		Origin o2 = new Origin(uri);
		
		assertTrue(o1.equals(o2));
	}
	
	
	public void testOriginInequality() {
	
		String uri1 = "http://example.com";
		String uri2 = "HTTP://EXAMPLE.COM";
		
		Origin o1 = new Origin(uri1);
		Origin o2 = new Origin(uri2);
		
		assertFalse(o1.equals(o2));
	}
	
	
	public void testValidation() {
	
		String uri = "http://example.com";
		
		ValidatedOrigin validatedOrigin = null;
		
		try {
			validatedOrigin = new Origin(uri).validate();
			
		} catch (OriginException e) {
		
			fail(e.getMessage());
		}
		
		assertNotNull(validatedOrigin);
		
		assertEquals(uri, validatedOrigin.toString());
	}
}
