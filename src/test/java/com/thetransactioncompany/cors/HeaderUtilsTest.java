package com.thetransactioncompany.cors;


import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Tests the header utilities.
 *
 * @author Vladimir Dzhuvinov
 */
public class HeaderUtilsTest {


	@Test
    public void testSerialize() {

		Set<String> values = new LinkedHashSet<String>();
		values.add("apples");
		values.add("pears");
		values.add("oranges");

		String out = HeaderUtils.serialize(values, ", ");

		assertEquals("apples, pears, oranges", out);

		out = HeaderUtils.serialize(values, " ");

		assertEquals("apples pears oranges", out);

		out = HeaderUtils.serialize(values, null);

		assertEquals("applesnullpearsnulloranges", out);
	}


	@Test
    public void testParseMultipleHeaderValues() {

		String[] out = HeaderUtils.parseMultipleHeaderValues(null);

		assertEquals(0, out.length);

		out = HeaderUtils.parseMultipleHeaderValues("apples, pears, oranges");

		assertEquals("apples", out[0]);
		assertEquals("pears", out[1]);
		assertEquals("oranges", out[2]);
		assertEquals(3, out.length);

		out = HeaderUtils.parseMultipleHeaderValues("apples,pears,oranges");

		assertEquals("apples", out[0]);
		assertEquals("pears", out[1]);
		assertEquals("oranges", out[2]);
		assertEquals(3, out.length);

		out = HeaderUtils.parseMultipleHeaderValues("apples pears oranges");

		assertEquals("apples", out[0]);
		assertEquals("pears", out[1]);
		assertEquals("oranges", out[2]);
		assertEquals(3, out.length);
	}

}
