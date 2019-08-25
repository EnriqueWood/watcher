package drawing

import state.IStateChangedChecker
import state.StateChangedCheckerImpl
import toolkit.Helper

import java.awt.Graphics2D
import java.awt.Image
import java.awt.image.BufferedImage

class Screen implements IScreen {
	boolean active
	List<ILayer> layers

	BufferedImage image

	Dimension dimension

	@Delegate
	IStateChangedChecker stateChangedChecker

	Screen(Dimension dimension, boolean active) {
		this.layers = []
		this.dimension = dimension
		this.active = active
		this.stateChangedChecker = new StateChangedCheckerImpl(this)
	}

	@Override
	Image getImage() {
		image
	}

	void addLayers(List<ILayer> layers) {
		this.layers.addAll(layers)
	}

	@Override
	void draw() {
		image = Helper.resetImage(dimension)
		Graphics2D graphics = image.graphics as Graphics2D
		layers.each {
			it.draw()
			graphics.drawImage(it.image, 0, 0, null)
		}
		graphics.dispose()
	}

	@Override
	int updateElements(boolean force) {
		layers.collect { it.updateElements(force) }.sum() as int
	}

	@Override
	Map getStateProperties() {
		[active: active,
		 layers: layers*.stateProperties
		]
	}
}
