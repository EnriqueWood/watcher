package ui.widgets

import drawing.Drawable

import java.awt.image.BufferedImage

class ImageDrawable implements Drawable {
	BufferedImage image
	Location location
	int width, height

	ImageDrawable(BufferedImage image, Location location = new Location()) {
		this.image = image
		this.width = image.width
		this.height = image.height
		this.location = location
	}

	@Override
	boolean shouldUpdate() {
		false
	}

	@Override
	void update() {
	}
}
