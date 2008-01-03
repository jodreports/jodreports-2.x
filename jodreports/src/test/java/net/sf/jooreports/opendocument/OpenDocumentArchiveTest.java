package net.sf.jooreports.opendocument;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import junit.framework.TestCase;

import net.sf.jooreports.opendocument.OpenDocumentArchive;

import org.apache.commons.io.IOUtils;

public class OpenDocumentArchiveTest extends TestCase {

	public void testCopy() throws IOException {
		OpenDocumentArchive archive = new OpenDocumentArchive();
		
		Writer mimetypeWriter = archive.getEntryWriter("mimetype");
		mimetypeWriter.write("application/x-test");
		mimetypeWriter.close();
		
		Writer contentWriter = archive.getEntryWriter("content.xml");
		contentWriter.write("test content");
		contentWriter.close();
		
		OpenDocumentArchive archiveCopy = archive.createCopy();
		assertNotNull(archiveCopy);
		Set entryNames = archiveCopy.getEntryNames();
		assertNotNull(entryNames);
		assertEquals(2, entryNames.size());
		assertTrue(entryNames.contains("mimetype"));
		assertTrue(entryNames.contains("content.xml"));
		
		assertEquals("test content", IOUtils.toString(archiveCopy.getEntryReader("content.xml")));
		
		// now modify the content
		Writer contentCopyWriter = archiveCopy.getEntryWriter("content.xml");
		contentCopyWriter.write("modified content");
		contentCopyWriter.close();

		assertEquals("test content", IOUtils.toString(archive.getEntryReader("content.xml")));
		assertEquals("modified content", IOUtils.toString(archiveCopy.getEntryReader("content.xml")));
	}
}
