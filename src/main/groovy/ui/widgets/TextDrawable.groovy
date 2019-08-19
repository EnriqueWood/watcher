package ui.widgets

import drawing.Drawable
import drawing.Helper

import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage

class TextDrawable implements Drawable {

	BufferedImage image
	Location location
	int width, height
	String text
	String hexColor
	Font font

	TextDrawable(String text, String hexColor = '#000000', Font font = new Font('Arial', Font.PLAIN, 50), Location location = new Location()) {
		this.text = text
		this.hexColor = hexColor
		this.font = font
		this.location = location
	}

	@Override
	boolean shouldUpdate() {
		false
	}

	@Override
	void update() {
		FontMetrics fontMetrics = Helper.getFontMetrics(font)
		Dimension fontDimension = new Dimension(fontMetrics.stringWidth(this.text), fontMetrics.height)
		image = new BufferedImage(fontDimension.width as int, fontDimension.height as int, BufferedImage.TYPE_INT_ARGB)
		this.width = image.width
		this.height = image.height
		Graphics2D graphics2D = getGraphics(image)
		graphics2D.color = Color.decode(hexColor)
		graphics2D.font = font
		graphics2D.drawString(this.text, 0, fontMetrics.height - fontMetrics.descent)
		graphics2D.dispose()
	}

	protected static Graphics2D getGraphics(BufferedImage image) {
		Graphics2D graphics2D = image.createGraphics()
		graphics2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY)
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
		graphics2D.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY)
		graphics2D.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE)
		graphics2D.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON)
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)
		graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
		graphics2D.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE)
		graphics2D
	}
}
