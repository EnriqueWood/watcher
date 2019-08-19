package drawing

import java.awt.Image

interface ILayer {

	boolean isVisible()

	void addDrawable(Drawable drawable)

	void addDrawables(List<Drawable> drawables)

	void removeDrawable(Drawable drawable)

	void removeAllDrawables()

	Image getImage()

	void draw()

	int updateElements(boolean force)
}
