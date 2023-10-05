package com.thetransactioncompany.cors;


import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * Mock servlet response.
 *
 * @author Vladimir Dzhuvinov
 */
public class MockServletResponse implements HttpServletResponse {


	private int status = 200;


	private final Map<String,String> headers = new HashMap<String, String>();


	@Override
	public void addCookie(Cookie cookie) {
	}

	@Override
	public boolean containsHeader(String s) {
		return false;
	}

	@Override
	public String encodeURL(String s) {
		return null;
	}

	@Override
	public String encodeRedirectURL(String s) {
		return null;
	}

	@Override
	public void sendError(int i, String s) throws IOException {
	}

	@Override
	public void sendError(int i) throws IOException {
	}

	@Override
	public void sendRedirect(String s) throws IOException {
	}

	@Override
	public void setDateHeader(String s, long l) {
	}

	@Override
	public void addDateHeader(String s, long l) {
	}


	@Override
	public Collection<String> getHeaders(String s) {
		return null;
	}


	@Override
	public Collection<String> getHeaderNames() {
		return null;
	}


	public String getHeader(final String name) {

		return headers.get(name);
	}


	public Map<String,String> getHeaders() {

		return headers;
	}


	@Override
	public void setHeader(String name, String value) {

		if (value == null)
			headers.remove(name);
		else
			headers.put(name, value);
	}

	@Override
	public void addHeader(String name, String value) {

		headers.put(name, value);
	}

	@Override
	public void setIntHeader(String s, int i) {
	}

	@Override
	public void addIntHeader(String s, int i) {
	}

	public int getStatus() {

		return status;
	}

	@Override
	public void setStatus(int i) {
	}



	@Override
	public String getCharacterEncoding() {
		return null;
	}

	@Override
	public String getContentType() {
		return null;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return null;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return null;
	}

	@Override
	public void setCharacterEncoding(String s) {
	}

	@Override
	public void setContentLength(int i) {
	}

	@Override
	public void setContentLengthLong(long l) {

	}

	@Override
	public void setContentType(String s) {
	}

	@Override
	public void setBufferSize(int i) {
	}

	@Override
	public int getBufferSize() {
		return 0;
	}

	@Override
	public void flushBuffer() throws IOException {
	}

	@Override
	public void resetBuffer() {
	}

	@Override
	public boolean isCommitted() {
		return false;
	}

	@Override
	public void reset() {
	}

	@Override
	public void setLocale(Locale locale) {
	}

	@Override
	public Locale getLocale() {
		return null;
	}
}
