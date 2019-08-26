package ui.widgets

import drawing.Dimension
import drawing.IDrawable
import drawing.ILayer
import drawing.IUpdatable
import state.StateChangedCheckerImpl
import toolkit.Helper

import java.awt.AlphaComposite
import java.awt.Graphics2D
import java.awt.image.BufferedImage

class Layer implements ILayer {
	boolean visible
	int opacity
	BufferedImage image
	List<IWidget> widgets
	Dimension dimension

	@Delegate
	StateChangedCheckerImpl stateChangedChecker

	Layer(Dimension dimension, boolean visible = true, int opacity = 100, List<IWidget> widgets = []) {
		image = Helper.resetImage(dimension)
		this.dimension = dimension
		this.visible = visible
		this.opacity = opacity
		this.widgets = widgets
		this.stateChangedChecker = new StateChangedCheckerImpl(this)
	}

	@Override
	void addWidget(IWidget widget) {
		widgets.add(widget)
	}

	@Override
	void addWidgets(List<IWidget> widgets) {
		this.widgets.addAll(widgets)
	}

	@Override
	void removeWidget(IWidget widgets) {
		this.widgets.removeAll(widgets)
	}

	@Override
	void removeAllWidgets() {
		widgets.clear()
	}

	@Override
	void draw() {
		image = Helper.resetImage(dimension)
		if (!visible) {
			return
		}
		Graphics2D graphics = image.graphics as Graphics2D
		graphics.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (opacity as float) / 100f as float)
		widgets.each {
			graphics.drawImage(it.image, it.location.x, it.location.y, null)
		}
		graphics.dispose()
	}

	@Override
	int updateElements(boolean force = false) {
		List<IUpdatable> updatables = widgets.findAll { force || it.shouldUpdate() } as List<IUpdatable>
		updatables*.update()
		updatables.size()
	}

	@Override
	Map getStateProperties() {
		[visible  : visible,
		 opacity  : opacity,
		 drawables: widgets*.stateProperties
		]
	}
}
