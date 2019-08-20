package toolkit

import drawing.Dimension
import drawing.Drawable
import drawing.Helper
import drawing.ILayer
import drawing.IScreen
import drawing.IWatchSpecification
import drawing.Layer
import drawing.Screen
import ui.widgets.DateDrawable
import ui.widgets.ImageDrawable
import ui.widgets.Location
import ui.widgets.TextDrawable

import javax.imageio.ImageIO
import java.awt.Font
import java.awt.FontFormatException
import java.awt.GraphicsEnvironment
import java.awt.image.BufferedImage

class ResourceLoader {

	static final String SPECIFICATION_FILENAME = 'specification.json'

	static IWatchSpecification parseSpecification(String specificationFolderPath) {
		Map specification = Helper.parseJson("${specificationFolderPath}${File.separator}${SPECIFICATION_FILENAME}")

		ResourceBox resourceBox = new ResourceBox()
		Dimension dimension = parseDimension(specification.dimension as Map<String, Integer>)
		resourceBox.addAllAssets(specification.assets.collect { loadAsset(specificationFolderPath, it as Map) })
		List<IScreen> screens = specification.screens.collect {
			parseScreen(it as Map, dimension, resourceBox)
		}
		new WatchSpecification(dimension, screens)
	}

	protected static Dimension parseDimension(Map<String, Integer> dimensionSpecification) {
		new Dimension(dimensionSpecification.width, dimensionSpecification.height)
	}

	static Asset loadAsset(String basePath, Map assetsSpecification) {
		String assetPath = "${basePath}${File.separator}${assetsSpecification.path}"
		switch (assetsSpecification.type) {
			case 'image':
				return new Asset<BufferedImage>(assetsSpecification.name as String, loadImage(assetPath))
				break
			case 'font':
				return new Asset<Font>(assetsSpecification.name as String, loadFont(assetPath))
				break
			default:
				throw new IllegalArgumentException("Type ${assetsSpecification.type} is not a known resource type")
		}
	}

	static IScreen parseScreen(Map<String, String> screenSpecification, Dimension dimension, ResourceBox resourceBox) {
		IScreen screen = new Screen(dimension, screenSpecification.active as boolean)
		screen.addLayers(screenSpecification.layers.collect { parseLayer(it as Map, dimension, resourceBox) })
		screen
	}

	static ILayer parseLayer(Map<String, String> layerSpecification, Dimension layerDimension, ResourceBox resourceBox) {
		ILayer layer = new Layer(layerDimension, layerSpecification.visible as boolean, layerSpecification.opacity as int)
		layer.addDrawables(layerSpecification.drawables.collect { parseDrawable(it as Map, resourceBox) })
		layer
	}

	static Drawable parseDrawable(Map drawableSpecification, ResourceBox resourceBox) {
		Location location = new Location(drawableSpecification.locationX as int, drawableSpecification.locationY as int)
		switch (drawableSpecification.type) {
			case 'text':
				return new TextDrawable(resourceBox.getFont(drawableSpecification.font as String, drawableSpecification.fontSize as float), drawableSpecification.text as String,
						drawableSpecification.textColor as String
						,
						location)
				break
			case 'image':
				return new ImageDrawable(resourceBox.getImage(drawableSpecification.image as String), location)
				break
			case 'date':
				return new DateDrawable(drawableSpecification.dateFormat as String,
						drawableSpecification.timeZone as String,
						drawableSpecification.textColor as String,
						resourceBox.getFont(drawableSpecification.font as String, drawableSpecification.fontSize as float),
						location)
				break
			default:
				throw new IllegalArgumentException("Type ${drawableSpecification.type} is not a known resource type")
		}
	}

	static BufferedImage loadImage(String path) {
		ImageIO.read(new File(path))
	}

	static Font loadFont(String path) {
		try {
			Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(path))
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(customFont)
			customFont
		} catch (FontFormatException formatException) {
			throw new IllegalArgumentException("Cannot load font in <${path}>", formatException)
		}
	}
}


