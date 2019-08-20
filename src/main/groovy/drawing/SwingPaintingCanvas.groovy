package drawing

import javax.swing.JPanel
import java.awt.Color
import java.awt.Container
import java.awt.Graphics
import java.awt.Image

/* Usage: Create a painting canvas, make some painting */
/* in each layer and when it's ready call show method  */

class SwingPaintingCanvas implements PaintingCanvas {
	Dimension dimension
	List<IScreen> screens
	Image image
	JPanel panel = new JPanel() {
		@Override
		void paint(Graphics graphics) {
			graphics.drawImage(screens.find { it.active }.image, 0, 0, null)
		}
	}

	SwingPaintingCanvas(Container parent, int width, int height) {
		dimension = new Dimension(width, height)
		screens = []
		panel.preferredSize.width = width
		panel.preferredSize.height = height
		parent.add(panel)
	}

	@Override
	void paint() {
		image = Helper.resetImage(dimension)
		IScreen activeScreen = screens.find { it.active }
		activeScreen.draw()
		image.graphics.drawImage(activeScreen.image, 0, 0, null)
	}

	@Override
	void show() {
		panel.repaint()
	}

	@Override
	List<IScreen> updateScreens(boolean force = false) {
		screens.findAll { it.updateElements(force) > 0 }
	}

	@Override
	void addScreen(IScreen screen) {
		this.screens.add(screen)
	}

	@Override
	void addScreens(List<IScreen> screens) {
		this.screens.addAll(screens)
	}
}
