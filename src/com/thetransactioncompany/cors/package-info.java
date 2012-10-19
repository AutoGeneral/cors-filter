/**
 * <h1>Cross-Origin Resource Sharing (CORS) Filter</h1>
 *
 * <p>This package provides a Java servlet filter that implements the 
 * <a href="http://www.w3.org/TR/cors/">Cross-Origin Resource Sharing (CORS)</a>
 * mechanism for making cross-site HTTP requests from web browsers. The CORS W3C
 * working draft stabilised in 2009 and as of 2010 CORS is supported by major
 * browsers such as Firefox, Safari, Chrome and IE.
 *
 * <p>To enable CORS for a particular HTTP resource, such as a servlet, JSP or
 * plain HTML file, attach a {@link com.thetransactioncompany.cors.CORSFilter} 
 * to it via a {@code <filter-mapping>} element in the {@code web.xml} 
 * descriptor file. The default CORS filter policy is to allow any origin 
 * (including credentials). To impose a stricter access policy configure the 
 * filter using the supported {@code <init-param>} elements. See the 
 * {@link com.thetransactioncompany.cors.CORSFilter} JavaDoc for configuration 
 * details.
 *
 * <p>The CORS Filter also tags the allowed CORS HTTP requests with
 * {@code HttpServletRequest.addAttribute()} to provide notification to 
 * downstream handlers:
 *
 * <ul>
 *     <li>{@code cors.isCorsRequest} {Boolean} Indicates if the HTTP request
 *         is CORS.
 *     <li>{@code cors.origin} {String} the value of the "Origin" header,
 *         {@code null} if undefined.
 *     <li>{@code cors.requestType} {String} If the request is CORS, indicates
 *         its type - "actual" for simple / actual or "preflight".
 *     <li>{@code cors.requestHeaders} {String} if the request is CORS 
 *         preflight, the value of the "Access-Control-Request-Headers" header, 
 *         {@code null} if undefined.
 * </ul>
 *
 * <p>This CORS filter version implements the W3C 
 * <a href="http://www.w3.org/TR/2012/WD-cors-20120403/">working draft</a> from 
 * 2012-04-03.</p>
 *
 * <p>Supported CORS request types:</p>
 * 		    
 * <ul>
 *         <li><a href="http://www.w3.org/TR/2012/WD-cors-20120403/#resource-requests">Simple / actual requests</a></li>
 *         <li><a href="http://www.w3.org/TR/2012/WD-cors-20120403/#resource-preflight-requests">Preflight requests</a></li>
 * 
 * </ul>
 * 
 * <p>Supported CORS headers:</p>
 * 
 * <ul>
 *     <li>Request headers:
 *         <ul>
 *             <li><a href="http://www.w3.org/TR/2012/WD-cors-20120403/#origin-request-header">Origin</a></li>
 *             <li><a href="http://www.w3.org/TR/2012/WD-cors-20120403/#access-control-request-method-request-header">Access-Control-Request-Method</a></li>
 *             <li><a href="http://www.w3.org/TR/2012/WD-cors-20120403/#access-control-request-headers-request-header">Access-Control-Request-Headers</a></li>
 *         </ul>
 *     </li>
 *
 *     <li>Response headers:
 *         <ul>
 *             <li><a href="http://www.w3.org/TR/2012/WD-cors-20120403/#access-control-allow-origin-response-header">Access-Control-Allow-Origin</a></li>
 *             <li><a href="http://www.w3.org/TR/2012/WD-cors-20120403/#access-control-allow-credentials-response-header">Access-Control-Allow-Credentials</a></li>
 *             <li><a href="http://www.w3.org/TR/2012/WD-cors-20120403/#access-control-allow-methods-response-header">Access-Control-Allow-Methods</a></li>
 *             <li><a href="http://www.w3.org/TR/2012/WD-cors-20120403/#access-control-allow-headers-response-header">Access-Control-Allow-Headers</a></li>
 * 
 *             <li><a href="http://www.w3.org/TR/2012/WD-cors-20120403/#access-control-expose-headers-response-header">Access-Control-Expose-Headers</a></li>
 *             <li><a href="http://www.w3.org/TR/2012/WD-cors-20120403/#access-control-max-age-response-header">Access-Control-Max-Age</a></li>
 *         </ul>
 *     </li>
 * </ul>
 * 
 * 
 * <p>Package dependencies:
 *
 * <ul>
 *     <li>{@code com.thetransactioncompany.util} provides parsing of the filter
 *         init parameters (included in the CORS filter distribution).</li>
 * </ul>
 *
 * @author Vladimir Dzhuvinov
 * @version $version$ ($version-date$)
 */
package com.thetransactioncompany.cors;
