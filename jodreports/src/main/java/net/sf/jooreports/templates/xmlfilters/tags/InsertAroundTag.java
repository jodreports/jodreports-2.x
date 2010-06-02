package net.sf.jooreports.templates.xmlfilters.tags;

import net.sf.jooreports.templates.xmlfilters.AbstractInsertTag;
import nu.xom.Element;
import nu.xom.Node;

public class InsertAroundTag extends AbstractInsertTag {

	public void process(Element scriptElement, Element tagElement) {
		Node beforeNode = newNode(tagElement.getChild(0).getValue());
		Node afterNode = newNode(tagElement.getChild(2).getValue());
		insertBefore(scriptElement, tagElement, (Node) beforeNode.copy());
		insertAfter(scriptElement, tagElement, (Node) afterNode.copy());
	}
}
