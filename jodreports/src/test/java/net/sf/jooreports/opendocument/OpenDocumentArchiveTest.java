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
		
		Writer mimetypeWriter = archive.getEntryWriter(OpenDocumentArchive.ENTRY_MIMETYPE);
		mimetypeWriter.write("application/x-test");
		mimetypeWriter.close();
		
		Writer contentWriter = archive.getEntryWriter(OpenDocumentArchive.ENTRY_CONTENT);
		contentWriter.write("test content");
		contentWriter.close();
		
		OpenDocumentArchive archiveCopy = archive.createCopy();
		assertNotNull(archiveCopy);
		Set entryNames = archiveCopy.getEntryNames();
		assertNotNull(entryNames);
		assertEquals(2, entryNames.size());
		assertTrue(entryNames.contains(OpenDocumentArchive.ENTRY_MIMETYPE));
		assertTrue(entryNames.contains(OpenDocumentArchive.ENTRY_CONTENT));
		
		assertEquals("test content", IOUtils.toString(archiveCopy.getEntryReader(OpenDocumentArchive.ENTRY_CONTENT)));
		
		// now modify the content
		Writer contentCopyWriter = archiveCopy.getEntryWriter(OpenDocumentArchive.ENTRY_CONTENT);
		contentCopyWriter.write("modified content");
		contentCopyWriter.close();

		assertEquals("test content", IOUtils.toString(archive.getEntryReader(OpenDocumentArchive.ENTRY_CONTENT)));
		assertEquals("modified content", IOUtils.toString(archiveCopy.getEntryReader(OpenDocumentArchive.ENTRY_CONTENT)));
	}
}
