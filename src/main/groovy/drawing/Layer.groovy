package drawing

import java.awt.AlphaComposite
import java.awt.Graphics2D
import java.awt.image.BufferedImage

class Layer implements ILayer {
	boolean visible
	int opacity
	BufferedImage image
	List<Drawable> drawables
	Dimension dimension

	Layer(Dimension dimension, boolean visible = true, int opacity = 100, List<Drawable> drawables = []) {
		image = Helper.resetImage(dimension)
		this.dimension = dimension
		this.visible = visible
		this.opacity = opacity
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
		Graphics2D graphics = image.graphics as Graphics2D
		graphics.composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (opacity as float) / 100f as float)
		drawables.each {
			graphics.drawImage(it.image, it.location.x, it.location.y, null)
		}
		graphics.dispose()
	}

	@Override
	int updateElements(boolean force = false) {
		List<Updateable> updateables = drawables.findAll { force || it.shouldUpdate() } as List<Updateable>
		updateables*.update()
		updateables.size()
	}
}
