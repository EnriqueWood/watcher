package drawing

import javax.swing.JPanel
import java.awt.Container
import java.awt.Dimension
import java.awt.Graphics
import java.awt.Image

/* Usage: Create a painting canvas, make some painting */
/* in each layer and when it's ready call show method  */

class SwingPaintingCanvas implements PaintingCanvas {
	Dimension dimension
	List<ILayer> layers
	Image image
	JPanel panel = new JPanel() {
		@Override
		void paint(Graphics graphics) {
			layers.findAll { it.visible } each {
				graphics.drawImage(it.image, 0, 0, null)
			}
		}
	}

	SwingPaintingCanvas(Container parent, int width, int height) {
		dimension = new Dimension(width, height)
		layers = []
		panel.preferredSize = dimension
		parent.add(panel)
	}

	@Override
	void paintLayers() {
		image = Helper.resetImage(dimension)
		layers.findAll { it.visible }.each {
			it.draw()
			image.graphics.drawImage(it.image, 0, 0, null)
		}
	}

	@Override
	void show() {
		panel.repaint()
	}

	@Override
	List<ILayer> updateLayers(boolean force = false) {
		layers.findAll { it.updateElements(force) > 0 }
	}

	@Override
	void addLayer(ILayer layer) {
		layers.add(layer)
	}

	@Override
	void addLayers(List<ILayer> layers) {
		this.layers.addAll(layers)
	}
}
