package drawing

import ui.widgets.Location

import java.awt.Image

interface Drawable extends Updatable {

	Location getLocation()

	Image getImage()

	int getWidth()

	int getHeight()
}
