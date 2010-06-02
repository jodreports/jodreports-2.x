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

import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Nodes;
import nu.xom.ParentNode;
import nu.xom.Text;

/**
 * OpenDocument XML file filter that replaces text-input elements with FreeMarker expressions.<p> 
 * For example:<p>
 * <tt>&lt;text:text-input text-description="JOOScript"&gt;$user.name&lt;/text:text-input&gt;</tt>
 * becomes <tt>${user.name}</tt>.<p>
 * <tt>&lt;text:text-input text-description="JOOScript"&gt;[#assign title='Mr.']&lt;/text:text-input&gt;</tt>
 * becomes <tt>[#assign title='Mr.']</tt>.
 */
public class TextInputTagFilter extends XmlEntryFilter {

	public void doFilter(Document document) {
		Nodes textInputNodes = document.query("//text:text-input", XPATH_CONTEXT);
		for (int nodeIndex = 0; nodeIndex < textInputNodes.size(); nodeIndex++) {
			Element textInputElement = (Element) textInputNodes.get(nodeIndex);
			String expression = textInputElement.getAttributeValue("description", 
					textInputElement.getNamespaceURI()).trim();
			if (expression.equalsIgnoreCase("jooscript")) {
				String value = textInputElement.getValue();
				if (value.startsWith("${")) {
					textInputElement.getParent().replaceChild(textInputElement, new Text(value));
				} else {
					ParentNode childNode = textInputElement;
					while (childNode.getParent().getChildCount()==1) {
						childNode = childNode.getParent();
					}
					childNode.getParent().replaceChild(childNode, newNode(value));
				}
			} else {
				if (expression.length()>0 && !expression.startsWith("${")) {
					expression = "${" + expression + "}";
				}
				textInputElement.getParent().replaceChild(textInputElement, new Text(expression));
			}
		}
	}

}
