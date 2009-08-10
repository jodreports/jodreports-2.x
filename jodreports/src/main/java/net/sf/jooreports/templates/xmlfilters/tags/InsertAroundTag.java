package net.sf.jooreports.templates.xmlfilters.tags;

import net.sf.jooreports.templates.xmlfilters.AbstractInsertTag;
import nu.xom.Element;
import nu.xom.Comment;

public class InsertAroundTag extends AbstractInsertTag {

	public void process(Element scriptElement, Element tagElement) {
		Comment beforeComment = new Comment(tagElement.getChild(0).getValue());
		Comment afterComment = new Comment(tagElement.getChild(2).getValue());
		insertBefore(scriptElement, tagElement, (Comment) beforeComment.copy());
		insertAfter(scriptElement, tagElement, (Comment) afterComment.copy());
	}
}
