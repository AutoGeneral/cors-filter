package com.thetransactioncompany.cors.demo;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;


/**
 * Simple servlet for testing CORS requests.
 *
 * @author Vladimir Dzhuvinov
 */
public class HelloWorldServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
	
		doHelloWorld(request, response);
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
		
		doHelloWorld(request, response);
	}
	
	
	private void doHelloWorld(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
	
		response.setContentType("text/plain");
		
		response.addHeader("X-Test-1", "Hello world!");
		response.addHeader("X-Test-2", "1, 2, 3");
			
		PrintWriter out = response.getWriter();
		
		out.println("[HTTP " + request.getMethod() + "] Hello world!");
		
		out.println("");
		
		out.println("CORS Filter request tags: ");
		out.println("\tcors.isCorsRequest: " + request.getAttribute("cors.isCorsRequest"));
		out.println("\tcors.origin: " + request.getAttribute("cors.origin"));
		out.println("\tcors.requestType: " + request.getAttribute("cors.requestType"));
		out.println("\tcors.requestHeaders: " + request.getAttribute("cors.requestHeaders"));
	}
}
