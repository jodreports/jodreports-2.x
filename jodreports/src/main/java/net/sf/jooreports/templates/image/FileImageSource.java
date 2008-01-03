package net.sf.jooreports.templates.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileImageSource extends AbstractInputStreamImageSource implements ImageSource {

	private File file;

	public FileImageSource(String fileName) {
		this(new File(fileName));
	}

	public FileImageSource(File file) {
		this.file = file;
	}

	protected InputStream getInputStream() throws IOException {
		return new FileInputStream(file);
	}
}
