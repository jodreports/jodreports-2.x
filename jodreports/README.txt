This is JODReports version 2.1, released on 2009-11-27

Please see

  http://sourceforge.net/projects/jodreports/
  
for the latest and up to date documentation.

Requirements
============

JODReports requires Java 1.4 or later (although with minor modifications
it can be made to work with 1.3 as well).

To create documents in formats other than OpenDocument Text (odt) an
OpenOffice.org instance running as a service is also required
See http://www.artofsolving.com/node/7 for more info.

To run the webapp a servlet 2.3 container such as Apache Tomcat 4.1
or later is also required.

Licenses
========

The JODReports library is distributed under the terms of the LGPL.
This basically means that you are free to use it in both open source
and commercial projects.

If you modify the library itself you are required to contribute
your changes back, so JODReports can be improved.

(You are free to modify the sample webapp as a starting point for your
own webapp without restrictions.)

JODReports includes the following third-party libraries so you must
agree to their respective licenses as well

 * OpenOffice.org Java/UNO bridge - http://www.openoffice.org
   GNU LGPL

 * FreeMarker - http://www.freemarker.org
   BSD-style

 * Jakarta Commons - IO, Logging and FileUpload - http://jakarta.apache.org/commons/
   Apache License 2.0

 * XOM - http://xom.nu
   GNU LGPL

 * slf4j - http://slf4j.org
   optional (used by JODConverter) - MIT License
 
 * Spring Framework - http://www.springframework.org
   Apache License 2.0

 * XStream - http://xstream.codehaus.org
   only if you use XmlDocumentFormatRegistry - BSD-style (revised) 

 * JFreeChart - http://www.jfree.org/jfreechart/
   optional (used by report sample) - GNU LGPL

 * PDFBox - http://www.pdfbox.org
   optional (used by unit tests) - BSD-style (revised)

Third-party licenses are included in lib/licenses.

In compliance to some of the above licenses I also need to state here
that JODReports includes software developed by

 * the Visigoth Software Society (FreeMarker - http://www.visigoths.org/)
 * the Apache Software Foundation (http://www.apache.org)
 * the Spring Framework project (http://www.springframework.org)

-- Mirko Nasato (mirko at artofsolving.com)
-- Terry Liang (terry at polonious.com.au)
