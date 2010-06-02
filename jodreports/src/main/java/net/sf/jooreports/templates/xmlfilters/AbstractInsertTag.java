package net.sf.jooreports.templates.xmlfilters;

import net.sf.jooreports.opendocument.OpenDocumentNamespaces;
import net.sf.jooreports.templates.xmlfilters.tags.JooScriptTag;
import nu.xom.Element;
import nu.xom.Node;
import nu.xom.Nodes;
import nu.xom.ParentNode;

public abstract class AbstractInsertTag implements JooScriptTag {

	public abstract void process(Element scriptElement, Element tagElement);

	protected void insertBefore(Element scriptElement, Element tagElement, Node node) {
		String tag = tagElement.getAttributeValue("element");
		Element targetElement = findEnclosingElement(scriptElement, tag);
		ParentNode parentNode = targetElement.getParent();
		int parentIndex = parentNode.indexOf(targetElement);
		parentNode.insertChild(node, parentIndex);
	}

	protected void insertAfter(Element scriptElement, Element tagElement, Node node) {
		String tag = tagElement.getAttributeValue("element");
		Element targetElement = findEnclosingElement(scriptElement, tag);
		ParentNode parentNode = targetElement.getParent();
		int parentIndex = parentNode.indexOf(targetElement);
		parentNode.insertChild(node, parentIndex + 1);		
	}

    private Element findEnclosingElement(Element element, String enclosingTagName) {
        Nodes ancestors = element.query("ancestor::" + enclosingTagName, OpenDocumentNamespaces.XPATH_CONTEXT);
        if (ancestors.size() == 0) {
        	return null;
        }
        return (Element) ancestors.get(ancestors.size() - 1);
    }

    protected static Node newNode(String script){
    	return XmlEntryFilter.newNode(script);
    }
}
