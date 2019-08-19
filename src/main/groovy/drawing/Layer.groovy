package drawing

import java.awt.Dimension
import java.awt.image.BufferedImage

class Layer implements ILayer {
	boolean visible
	BufferedImage image
	List<Drawable> drawables
	Dimension dimension

	Layer(Dimension dimension, boolean visible = true, List<Drawable> drawables = []) {
		image = Helper.resetImage(dimension)
		this.dimension = dimension
		this.visible = visible
		this.drawables = drawables
	}

	@Override
	void addDrawable(Drawable drawable) {
		drawables.add(drawable)
	}

	@Override
	void addDrawables(List<Drawable> drawables) {
		this.drawables.addAll(drawables)
	}

	@Override
	void removeDrawable(Drawable drawable) {
		drawables.removeAll(drawable)
	}

	@Override
	void removeAllDrawables() {
		drawables.clear()
	}

	@Override
	void draw() {
		image = Helper.resetImage(dimension)
		if (!visible) {
			return
		}
		drawables.each {
			image.graphics.drawImage(it.image, it.location.x, it.location.y, null)
		}
		image.graphics.dispose()
	}

	@Override
	int updateElements(boolean force = false) {
		List<Updateable> updateables = drawables.findAll { force || it.shouldUpdate() } as List<Updateable>
		updateables*.update()
		updateables.size()
	}
}
