package drawing

import java.awt.Dimension
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Graphics2D
import java.awt.image.BufferedImage

class Helper {
	static BufferedImage resetImage(Dimension dimension) {
		new BufferedImage(dimension.width as int, dimension.height as int, BufferedImage.TYPE_INT_ARGB)
	}

	static FontMetrics getFontMetrics(Font font) {
		Graphics2D graphics2D = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics()
		graphics2D.font = font
		graphics2D.fontMetrics
	}
}
