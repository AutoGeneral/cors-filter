Java Cross-Origin Resource Sharing (CORS) Filter

Copyright (c) Vladimir Dzhuvinov, 2010 - 2014


README

This package provides a Java servlet filter that implements the Cross-Origin 
Resource Sharing (CORS) mechanism for allowing cross-domain HTTP requests from 
web browsers. The CORS W3C working draft settled in 2009 and as of 2010 CORS is 
supported by all major browsers such as Firefox, Safari, Chrome and IE.

To enable CORS for a particular HTTP resource, such as a servlet, JSP or plain 
HTML file, attach a CORSFilter to it via a <filter-mapping> element in the 
web.xml descriptor file. The default CORS filter policy is to allow any origin 
(including credentials). To impose a stricter access policy configure the 
filter using the supported <init-param> elements. See the CORSFilter JavaDoc 
for configuration details. 

This CORS filter version implements the W3C recommendation from 16 January
2014:

	http://www.w3.org/TR/2014/REC-cors-20140116/


For installation instructions, usage and other information visit the CORS 
Filter website:

	http://software.dzhuvinov.com/cors-filter.html


Change log:

version 1.0 (2010-09-29)
	* First official release.

version 1.1 (2010-10-10)
	* Tags CORS requests for downstream notification using 
	  HttpServletRequest.addAttribute().

version 1.2 (2010-12-13)
	* Released under the Apache Open Source License 2.0.

version 1.2.1 (2011-07-29)
	* Updates Property Util JAR to 1.4.
	* Updates documentation to reflect the latest W3C CORS terminology.

version 1.3 (2011-12-02)
	* Fixes improper detection of actual HTTP OPTIONS CORS requests.
	* Updates Property Util JAR to 1.5.

version 1.3.1 (2011-12-02)
	* Removes improper filter chain for preflight HTTP OPTIONS CORS 
	  requests.

version 1.3.2 (2012-07-31)
	* Updates Property Util JAR to 1.6.
	* Adds Maven POM.
	* Updates Ant build.xml script.

version 1.4 (2012-10-19)
	* Adds new optional cors.allowSubdomains configuration parameter to
	  enable domain suffix matching (contributed by Jared Ottley and Luis
	  Sala of Alfresco).
	* Removes support of file:// CORS scheme.
	* Refactors Origin processing.

version 1.5 (2012-10-19)
	* Removes support of multivalued Origin headers according to the
	  latest CORS spec from 2012-04-03.

version 1.5.1 (2013-04-03)
	* Updates CORS spec reference.
	* Switches build to Apache Maven and enabled publishing to Maven 
	  Central.

version 1.6 (2013-04-18)
	* Permits any URI scheme, not just http and https, according to RFC 
	  6454.

version 1.7 (2013-05-20)
	* Corrects handling of same-origin requests that specify an Origin 
	  header.
	* Augments the cors.supportedHeader configuration option to enable
	  specification of an allow-any-header policy.
	* Upgrades to Property Utils JAR 1.9.

version 1.7.1 (2013-06-18)
	* Fixes a NullPointerException bug in CORSRequestHandler.

version 1.8 (2013-11-05)
    * Switches to ServletContext.getResourceAsStream(String) for loading the
      configuration properties file in a more reliable manner.
    * Updates the CORS specification references.

version 1.9 (2013-12-29)
    * Adds cors.tagRequests configuration option to enable / disable tagging
      of handled HTTP requests with CORS information, to be passed to
      downstream filters and servlets.
    * Adds support for loading the configuration properties file relative to
      the application classpath.
    * Upgrades to Property Utils JAR 1.9.1.
    
version 1.9.1 (2014-01-10)
    * Makes '*' the preferred value of Access-Control-Allow-Origin response 
      headers (see issue #16).

version 1.9.2 (2014-01-13)
    * Adds Vary: Origin header to preflight and actual requests if credentials
      are allowed or the allowed origin list is bounded (see issue #16).

version 1.9.3 (2014-05-13)
    * Fixes validation of HTTP Header names (issue #19).

version 2.0 (2014-06-21)
    * Upgrades to Servlet API 3.0.
    * Annotates CORSFilter to enable asynchronous requests.
    * Adds HttpServletResponseWrapper to preserve CORS response headers on
      HttpServletResponse.reset().

[EOF]
