package net.sf.jooreports.templates.xmlfilters.tags;

import net.sf.jooreports.templates.xmlfilters.AbstractInsertTag;
import nu.xom.Element;
import nu.xom.Node;

public class InsertAfterTag extends AbstractInsertTag {

	public void process(Element scriptElement, Element tagElement) {
		Node node = newNode(tagElement.getValue());
		insertAfter(scriptElement, tagElement, node);
	}
}
