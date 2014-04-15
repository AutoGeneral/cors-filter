package com.thetransactioncompany.cors;


import junit.framework.TestCase;


/**
 * Tests the header field name class.
 *
 * @author Vladimir Dzhuvinov
 */
public class HeaderFieldNameTest extends TestCase {
        
        
        public void testFormatCanonical1() {
		
		assertEquals(HeaderFieldName.formatCanonical("content-type"), "Content-Type");
        }
	
	
	public void testFormatCanonical2() {
		
		assertEquals(HeaderFieldName.formatCanonical("CONTENT-TYPE"), "Content-Type");
        }
	
	
	public void testFormatCanonical3() {
		
		assertEquals(HeaderFieldName.formatCanonical("X-type"), "X-Type");
        }
	
	
	public void testFormatCanonical4() {
		
		assertEquals(HeaderFieldName.formatCanonical("Origin"), "Origin");
        }
	
	
	public void testFormatCanonical5() {
		
		assertEquals(HeaderFieldName.formatCanonical("A"), "A");
        }
	
	
	public void testFormatCanonical6() {
		
		try {
			assertEquals(HeaderFieldName.formatCanonical(""), "");
			fail("Failed to raise IllegalArgumentException on empty string");
			
		} catch (IllegalArgumentException e) {
			// ok
		}
        }
	
	
	public void testConstructor1() {
	
		HeaderFieldName n = new HeaderFieldName("content-type");
		
		assertEquals(n.toString(), "Content-Type");
	}
	
	
	public void testConstructor2() {
	
		HeaderFieldName n = new HeaderFieldName("X-ABC");
		
		assertEquals(n.toString(), "X-Abc");
	}
	
	
	public void testEquality1() {
	
		HeaderFieldName n1 = new HeaderFieldName("content-type");
		HeaderFieldName n2 = new HeaderFieldName("CONTENT-TYPE");
		
		assertTrue(n1.equals(n2));
	}
	
	
	public void testEquality2() {
	
		HeaderFieldName n1 = new HeaderFieldName("content-type");
		HeaderFieldName n2 = new HeaderFieldName("CONTENT");
		
		assertFalse(n1.equals(n2));
	}

    public void testTrim() {
        String expected = "Content-Type";
        String n1 = HeaderFieldName.formatCanonical("content-type\n");
        String n2 = HeaderFieldName.formatCanonical(" CONTEnt-Type ");

        assertEquals("All whitespace should be trimmed", expected, n1);
        assertEquals("All whitespace should be trimmed", expected, n2);
    }

    public void testInvalid1() {
        assertInvalid("X-r@b");
    }


    public void testInvalid2() {
        assertInvalid("1=X-r");
    }


    public void testInvalid3() {
        assertInvalid("Aaa Bbb");
    }


    public void testInvalid4() {
        assertInvalid("less<than");
    }


    public void testInvalid5() {
        assertInvalid("alpha1>");
    }


    public void testInvalid6() {
        assertInvalid("X-Forwarded-By-{");
    }


    public void testInvalid7() {
        assertInvalid("a}");
    }


    public void testInvalid8() {
        assertInvalid("separator:");
    }


    public void testInvalid9() {
        assertInvalid("asd\"f;");
    }


    public void testInvalid10() {
        assertInvalid("rfc@w3c.org");
    }


    public void testInvalid11() {
        assertInvalid("bracket[");
    }


    public void testInvalid12() {
        assertInvalid("control\u0002header");
    }


    public void testInvalid13() {
        assertInvalid("control\nembedded");
    }


    public void testInvalid14() {
        assertInvalid("uni╚(•⌂•)╝");
    }


    public void testInvalid15() {
        assertInvalid("uni\u3232_\u3232");
    }


    public void testUnusualButValid() {
        new HeaderFieldName("__2");
        new HeaderFieldName("$%.%");
        new HeaderFieldName("`~'&#*!^|");
        new HeaderFieldName("Original_Name");
    }


    private void assertInvalid(String header) {
        try {
            new HeaderFieldName(header);

            fail("Failed to raise exeption on bad header name");

        } catch (IllegalArgumentException e) {
            // ok
        }

    }

}
