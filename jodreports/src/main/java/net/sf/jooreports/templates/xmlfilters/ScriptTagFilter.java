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

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import net.sf.jooreports.templates.DocumentTemplateException;
import net.sf.jooreports.templates.xmlfilters.tags.InsertAfterTag;
import net.sf.jooreports.templates.xmlfilters.tags.InsertAroundTag;
import net.sf.jooreports.templates.xmlfilters.tags.InsertBeforeTag;
import net.sf.jooreports.templates.xmlfilters.tags.JooScriptTag;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Nodes;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * OpenDocument XML entry filter that replaces <tt>script</tt> elements with FreeMarker directives.
 * <p>
 * Scripts can contain FreeMarker directives to be placed at the location of the script itself, or
 * at some enclosing tag. For example a script inside a table cell can contain a
 * <tt>[#list items as item]</tt> directive to be inserted at the enclosing table row so that the
 * entire row will be repeated for each item in the list.
 */
public class ScriptTagFilter extends XmlEntryFilter {

	private static final Log log = LogFactory.getLog(ScriptTagFilter.class);
	
	private final Map/*<String,JooScriptTag>*/ tags;
	
	public ScriptTagFilter() {
		tags = new HashMap();
		tags.put("insert-after", new InsertAfterTag());
		tags.put("insert-around", new InsertAroundTag());
		tags.put("insert-before", new InsertBeforeTag());
	}

	public void doFilter(Document document) throws DocumentTemplateException {
		// bloody xpath... no easier way to do a case-insentive match than translate()
		Nodes scriptNodes = document.query("//text:script[translate(@script:language, 'CIJOPRST', 'cijoprst')='jooscript']", XPATH_CONTEXT);
		for (int nodeIndex = 0; nodeIndex < scriptNodes.size(); nodeIndex++) {
			Element scriptElement = (Element) scriptNodes.get(nodeIndex);
			Elements scriptTags = parseScriptText(scriptElement.getValue());
			for (int tagIndex = 0; tagIndex < scriptTags.size(); tagIndex++) {
				Element tagElement = scriptTags.get(tagIndex);
				String tagName = tagElement.getLocalName();
				if (tags.containsKey(tagName)) {
					JooScriptTag tag = (JooScriptTag) tags.get(tagName);
					tag.process(scriptElement, tagElement);
				} else {
					log.error("unknown script tag: " + tagName + "; ignoring");
				}
			}
			scriptElement.detach();
		}
	}

	private Elements parseScriptText(String scriptText) throws DocumentTemplateException {
		new StringReader(scriptText);
		Builder builder = new Builder();
		StringReader reader = new StringReader(scriptText);
		Document document;
		try {
			document = builder.build(reader);
		} catch (Exception exception) {
			throw new DocumentTemplateException("invalid script: " + scriptText, exception);
		}
		reader.close();
		return document.getRootElement().getChildElements();
	}
}
