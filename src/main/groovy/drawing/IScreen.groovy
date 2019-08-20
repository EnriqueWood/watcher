package drawing

import java.awt.Image

interface IScreen {

	boolean isActive()

	Image getImage()

	void addLayers(List<ILayer> layers)

	void draw()

	int updateElements(boolean force)
}
