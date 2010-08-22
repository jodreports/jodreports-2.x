//
// JOOReports - The Open Source Java/OpenOffice Report Engine
// Copyright (C) 2004-2006 - Mirko Nasato <mirko@artofsolving.com>
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
// http://www.gnu.org/copyleft/lesser.html
//
package net.sf.jooreports.templates.xmlfilters;

import net.sf.jooreports.templates.DocumentTemplateException;
import net.sf.jooreports.templates.TemplateFreemarkerNamespace;
import nu.xom.Comment;
import nu.xom.Document;
import nu.xom.Node;
import nu.xom.XPathContext;

/**
 * Abstract class for filters that operate on an OpenDocument XML entry
 * (e.g. <tt>content.xml</tt>).
 * <p>
 * XML manipulations use the <a href="http://xom.nu">XOM</a> API. 
 */
public abstract class XmlEntryFilter {

	protected static final String DRAW_NAMESPACE = "urn:oasis:names:tc:opendocument:xmlns:drawing:1.0";
	protected static final String SCRIPT_NAMESPACE =  "urn:oasis:names:tc:opendocument:xmlns:script:1.0";
	protected static final String TABLE_NAMESPACE = "urn:oasis:names:tc:opendocument:xmlns:table:1.0";
	protected static final String TEXT_NAMESPACE = "urn:oasis:names:tc:opendocument:xmlns:text:1.0";
	protected static final String STYLE_NAMESPACE = "urn:oasis:names:tc:opendocument:xmlns:style:1.0";
	protected static final String XLINK_NAMESPACE = "http://www.w3.org/1999/xlink";
	
	protected static final XPathContext XPATH_CONTEXT = new XPathContext();
	static {
		XPATH_CONTEXT.addNamespace("draw", DRAW_NAMESPACE);
		XPATH_CONTEXT.addNamespace("script", SCRIPT_NAMESPACE);
		XPATH_CONTEXT.addNamespace("table", TABLE_NAMESPACE);
		XPATH_CONTEXT.addNamespace("text", TEXT_NAMESPACE);
		XPATH_CONTEXT.addNamespace("style", STYLE_NAMESPACE);
		XPATH_CONTEXT.addNamespace("xlink", XLINK_NAMESPACE);
	}

	public abstract void doFilter(Document document) throws DocumentTemplateException;
	
	protected static Node newNode(String script){
		return new Comment(" ${"+TemplateFreemarkerNamespace.NAME+".doubleHyphen}>" + 
				script.replace("--", "\\x002d\\x002d") +
				"<!${"+TemplateFreemarkerNamespace.NAME+".doubleHyphen} ");
	}

}
