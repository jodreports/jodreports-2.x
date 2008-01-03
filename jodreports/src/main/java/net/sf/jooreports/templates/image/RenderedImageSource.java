package net.sf.jooreports.templates.image;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

public class RenderedImageSource implements ImageSource {
	
	private final RenderedImage image;

	public RenderedImageSource(RenderedImage image) {
		this.image = image;
	}

	public void write(OutputStream outputStream) throws IOException {
		ImageIO.write(image, "png", outputStream);
	}
}
