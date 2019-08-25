package drawing

import state.IStateManaged

import java.awt.Image

interface IScreen extends IStateManaged {

	boolean isActive()

	Image getImage()

	void addLayers(List<ILayer> layers)

	void draw()

	int updateElements(boolean force)
}
