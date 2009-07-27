package net.sf.jooreports.templates.image;

import java.io.IOException;
import java.io.OutputStream;

public interface ImageSource {

	public void write(OutputStream outputStream) throws IOException;
	public double getWidth();
	public double getHeight();
}
