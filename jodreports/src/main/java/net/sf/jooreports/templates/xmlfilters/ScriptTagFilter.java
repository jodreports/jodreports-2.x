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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jooreports.templates.DocumentTemplateException;
import net.sf.jooreports.templates.xmlfilters.tags.InsertAfterTag;
import net.sf.jooreports.templates.xmlfilters.tags.InsertAroundTag;
import net.sf.jooreports.templates.xmlfilters.tags.InsertBeforeTag;
import net.sf.jooreports.templates.xmlfilters.tags.JooScriptTag;
import nu.xom.Attribute;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Nodes;
import nu.xom.ParentNode;

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
			if (scriptElement.getValue().toLowerCase().startsWith("<jooscript>")) {
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
			} else {
				try {
					String script = addScriptDirectives(scriptElement);
					scriptElement.getParent().replaceChild(scriptElement, newNode(script));
				} catch (IOException ioException) {
					log.error("unable to parse script: '"+scriptElement.getValue()+"'; ignoring", ioException);
					scriptElement.detach();
				}
				
			}
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
	
	private static class ScriptPart {

		private StringBuffer text = new StringBuffer();
		private String location;
		private Boolean isEndTag;

		public ScriptPart() {
			// no location
		}

		public ScriptPart(String location, Boolean isEndTag) {
			this.location = location;
			this.isEndTag = isEndTag;
		}

		public void appendText(String line) {
			text.append(line + "\n");
		}

		public String getText() {
			return text.toString().trim();
		}

		public String getLocation() {
			return location;
		}
		
		public boolean afterEndTag(){
			boolean afterEndTag = false;
			if (isEndTag != null && isEndTag == Boolean.TRUE) {
				afterEndTag = true;
			}
			return afterEndTag;
		}
		
		public boolean isTagAttribute(){
			boolean insideTag = false;
			if (isEndTag != null && isEndTag == Boolean.FALSE) {
				insideTag = true;
			}
			return insideTag;
		}
	}

	/**
	 * @param scriptElement
	 * @return the text that should replace the input field
	 * @throws DocumentTemplateException 
	 */
	private static String addScriptDirectives(Element scriptElement) throws IOException, DocumentTemplateException {
		String scriptReplacement = "";
		
		List scriptParts = parseScriptParts(scriptElement.getValue());
		for (int index = 0; index < scriptParts.size(); index++) {
			ScriptPart scriptPart = (ScriptPart) scriptParts.get(index);
			if (scriptPart.getLocation() == null) {
				scriptReplacement = scriptPart.getText();
			} else {
				Element enclosingElement = findEnclosingElement(scriptElement, scriptPart.getLocation());
				if (scriptPart.isTagAttribute()) {
					String[] nameValue = scriptPart.getText().split("=", 2);
					if (nameValue.length != 2) {
						throw new DocumentTemplateException("script error: # attribute name=value not found");
					}
					enclosingElement.addAttribute(new Attribute(nameValue[0], enclosingElement.getNamespaceURI(), nameValue[1]));
				} else {
					ParentNode parent = enclosingElement.getParent();
					int parentIndex = parent.indexOf(enclosingElement);
					if (scriptPart.afterEndTag()) {
						parentIndex++;
					}
					parent.insertChild(newNode(scriptPart.getText()), parentIndex);
				}
			}
		}
		
		return scriptReplacement;		
	}

	private static List/*<ScriptPart>*/ parseScriptParts(String scriptText) throws IOException, DocumentTemplateException {
		List scriptParts = new ArrayList();
		BufferedReader stringReader = new BufferedReader(new StringReader(scriptText));
		ScriptPart scriptPart = new ScriptPart();
		scriptParts.add(scriptPart);
		for (String line; (line = stringReader.readLine()) != null;) {
			line = line.trim();
			if (line.startsWith("@")) {
				String location = line.substring(1);
				if (location.startsWith("/")) {
					scriptPart = new ScriptPart(location.substring(1), Boolean.TRUE);
				} else if (location.startsWith("#")) {
					scriptPart = new ScriptPart(location.substring(1), Boolean.FALSE);
				} else {
					scriptPart = new ScriptPart(location, null);
				}
				scriptParts.add(scriptPart);
			} else {
            	scriptPart.appendText(line.replaceFirst("^\\[#--", "[#comment]").replaceFirst("--\\]$", "[/#comment]"));
			}
		}
		return scriptParts;
	}

	private static Element findEnclosingElement(Element element, String enclosingTagName) throws DocumentTemplateException {
		Nodes ancestors = element.query("ancestor::" + enclosingTagName, XPATH_CONTEXT);
		if (ancestors.size() == 0) {
			ancestors = element.query("preceding::" + enclosingTagName, XPATH_CONTEXT);
			if (ancestors.size() == 0) {
				throw new DocumentTemplateException("script error: no such enclosing tag named '" + enclosingTagName +"'");
			}
		}
		return (Element) ancestors.get(ancestors.size() - 1);
	}
}
