package ui.widgets

import toolkit.Helper
import drawing.Location
import state.StateChangedCheckerImpl

import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage

class TextDrawable implements IWidget {

	String fontName
	BufferedImage image
	Location location
	int width, height
	String text
	String hexColor
	Font font

	@Delegate
	StateChangedCheckerImpl stateChangedChecker

	TextDrawable(String fontName, Font font, String text, String hexColor = '#000000', Location location = new Location()) {
		this.fontName = fontName
		this.text = text
		this.hexColor = hexColor
		this.location = location
		this.font = font ?: new Font('Arial', Font.PLAIN, 50)
		this.stateChangedChecker = new StateChangedCheckerImpl(this)
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

	@Override
	Map getStateProperties() {
		["type"     : "text",
		 "text"     : text,
		 "font"     : fontName,
		 "fontSize" : font.size,
		 "textColor": hexColor,
		 "location" : location.stateProperties]
	}
}
