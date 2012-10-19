package com.thetransactioncompany.cors;


import junit.framework.TestCase;


/**
 * Tests the validated origin class.
 *
 * @author Vladimir Dzhuvinov
 * @author Jared Ottley
 * @version $version$ (2012-10-19)
 */
public class ValidatedOriginTest extends TestCase {
	
	
	public void testHTTPOrigin() {
	
		String uri = "http://example.com";
		
		ValidatedOrigin o = null;
		
		try {
			o = new ValidatedOrigin(uri);
		
		} catch (OriginException e) {
			fail(e.getMessage());
		}
		
		assertEquals(uri, o.toString());
		
		assertEquals("http", o.getScheme());
		assertEquals("example.com", o.getHost());
		assertEquals(-1, o.getPort());
		assertEquals("example.com", o.getSuffix());
	}
	
	
	public void testHTTPSOrigin() {
	
		String uri = "https://example.com";
		
		ValidatedOrigin o = null;
		
		try {
			o = new ValidatedOrigin(uri);
		
		} catch (OriginException e) {
			fail(e.getMessage());
		}
		
		assertEquals(uri, o.toString());
		
		assertEquals("https", o.getScheme());
		assertEquals("example.com", o.getHost());
		assertEquals(-1, o.getPort());
		assertEquals("example.com", o.getSuffix());
	}
	
	
	public void testUnsupportedSchemeException() {
	
		String uri = "file:///data/file.xml";
		
		try {
			new ValidatedOrigin(uri);
		
			fail("Failed to raise exception");
			
		} catch (OriginException e) {
			
			// ok
		}
	}
	
	
	public void testIPAddressHost() {
	
		String uri = "http://192.168.0.1:8080";
		
		ValidatedOrigin o = null;
		
		try {
			o = new ValidatedOrigin(uri);
		
		} catch (OriginException e) {
			
			fail(e.getMessage());
		}
		
		assertEquals(uri, o.toString());
		
		assertEquals("http", o.getScheme());
		assertEquals("192.168.0.1", o.getHost());
		assertEquals(8080, o.getPort());
		assertEquals("192.168.0.1:8080", o.getSuffix());
	}
	

//      Path+query+fragment checking not implemented at present
//
// 	public void testOriginURIWithPath() {
// 	
// 		String uri = "https://LOCALHOST:8080/my-app/upload.php";
// 		
// 		try {
// 			new ValidatedOrigin(uri);
// 		
// 			fail("Failed to raise exception");
// 			
// 		} catch (OriginException e) {
// 			
// 			// ok
// 		}
// 	}
}
