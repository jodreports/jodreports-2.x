package net.sf.jooreports.templates.xmlfilters.tags;

import net.sf.jooreports.templates.xmlfilters.AbstractInsertTag;
import nu.xom.Element;
import nu.xom.Comment;

public class InsertAfterTag extends AbstractInsertTag implements JooScriptTag {

	public void process(Element scriptElement, Element tagElement) {
		Comment comment = new Comment(tagElement.getValue());
		insertAfter(scriptElement, tagElement, comment);
	}
}
