package drawing

import state.IStateManaged
import ui.widgets.IWidget

import java.awt.Image

interface ILayer extends IStateManaged {

	boolean isVisible()

	int getOpacity()

	void addWidget(IWidget widget)

	void addWidgets(List<IWidget> widgets)

	void removeWidget(IWidget widgets)

	void removeAllWidgets()

	Image getImage()

	void draw()

	int updateElements(boolean force)
}
