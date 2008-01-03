package net.sf.jooreports.templates.image;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

public abstract class AbstractInputStreamImageSource implements ImageSource {

	protected abstract InputStream getInputStream() throws IOException;

	public void write(OutputStream outputStream) throws IOException {
		InputStream inputStream = null;
		try {
			inputStream = getInputStream();
			IOUtils.copy(inputStream, outputStream);
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
	}
}
