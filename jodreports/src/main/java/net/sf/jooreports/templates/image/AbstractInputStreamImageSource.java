package net.sf.jooreports.templates.image;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

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
	
	public double getWidth() {
		try {
			return (double)(ImageIO.read(getInputStream()).getWidth()/36);
		} catch (Exception e) {}
		return 0;
	}

	public double getHeight() {
		try {
			return (double)(ImageIO.read(getInputStream()).getHeight()/36);
		} catch (Exception e) {}
		return 0;
	}

}
