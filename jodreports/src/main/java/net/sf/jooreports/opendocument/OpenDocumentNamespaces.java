package net.sf.jooreports.opendocument;

import nu.xom.XPathContext;

public abstract class OpenDocumentNamespaces {

	public static final String URI_DRAW 	= "urn:oasis:names:tc:opendocument:xmlns:drawing:1.0";
	public static final String URI_MANIFEST = "urn:oasis:names:tc:opendocument:xmlns:manifest:1.0";
	public static final String URI_CONFIG 	= "urn:oasis:names:tc:opendocument:xmlns:config:1.0";
	public static final String URI_SCRIPT 	= "urn:oasis:names:tc:opendocument:xmlns:script:1.0";
	public static final String URI_TABLE 	= "urn:oasis:names:tc:opendocument:xmlns:table:1.0";
	public static final String URI_TEXT 	= "urn:oasis:names:tc:opendocument:xmlns:text:1.0";
	public static final String URI_SVG 		= "urn:oasis:names:tc:opendocument:xmlns:svg-compatible:1.0";
	public static final String URI_XLINK 	= "http://www.w3.org/1999/xlink";

	public static final XPathContext XPATH_CONTEXT = new XPathContext();
	static {
		XPATH_CONTEXT.addNamespace("draw", URI_DRAW);
		XPATH_CONTEXT.addNamespace("manifest", URI_MANIFEST);
		XPATH_CONTEXT.addNamespace("config", URI_CONFIG);
		XPATH_CONTEXT.addNamespace("script", URI_SCRIPT);
		XPATH_CONTEXT.addNamespace("table", URI_TABLE);
		XPATH_CONTEXT.addNamespace("text", URI_TEXT);
		XPATH_CONTEXT.addNamespace("svg", URI_SVG);
		XPATH_CONTEXT.addNamespace("xlink", URI_XLINK);
	}
}
