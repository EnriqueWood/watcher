package toolkit

import drawing.Dimension
import drawing.IAsset
import drawing.ILayer
import drawing.IScreen
import drawing.IWatch
import drawing.Location
import drawing.Screen
import ui.widgets.DateDrawable
import ui.widgets.IWidget
import ui.widgets.ImageDrawable
import ui.widgets.Layer
import ui.widgets.TextDrawable

import javax.imageio.ImageIO
import java.awt.Font
import java.awt.FontFormatException
import java.awt.GraphicsEnvironment
import java.awt.image.BufferedImage

class ResourceManager {

	static final String SPECIFICATION_FILENAME = 'specification.json'
	private String specificationFolderPath

	ResourceManager(String specificationFolderPath) {
		this.specificationFolderPath = specificationFolderPath
	}

	IWatch parseSpecification() {
		Map specification = Helper.parseJson("${specificationFolderPath}${File.separator}${SPECIFICATION_FILENAME}")

		ResourceBox resourceBox = new ResourceBox()
		Dimension dimension = parseDimension(specification.dimension as Map<String, Integer>)
		resourceBox.addAllAssets(specification.assets.collect { loadAsset(specificationFolderPath, it as Map) })
		List<IScreen> screens = specification.screens.collect {
			parseScreen(it as Map, dimension, resourceBox)
		}
		new Watch(dimension, resourceBox.assetList, screens)
	}

	private static Dimension parseDimension(Map<String, Integer> dimensionSpecification) {
		new Dimension(dimensionSpecification.width, dimensionSpecification.height)
	}

	private static Location parseLocation(Map<String, Integer> locationSpecification) {
		new Location(locationSpecification.x, locationSpecification.y)
	}

	private static IAsset loadAsset(String basePath, Map assetsSpecification) {
		String assetPath = "${basePath}${File.separator}${assetsSpecification.path}"
		switch (assetsSpecification.type) {
			case 'image':
				return new Asset<BufferedImage>(assetsSpecification.name as String, assetsSpecification.type as String, assetsSpecification.path as String, loadImage(assetPath))
				break
			case 'font':
				return new Asset<Font>(assetsSpecification.name as String, assetsSpecification.type as String, assetsSpecification.path as String, loadFont(assetPath))
				break
			default:
				throw new IllegalArgumentException("Type ${assetsSpecification.type} is not a known resource type")
		}
	}

	private static IScreen parseScreen(Map<String, String> screenSpecification, Dimension dimension, ResourceBox resourceBox) {
		IScreen screen = new Screen(dimension, screenSpecification.active as boolean)
		screen.addLayers(screenSpecification.layers.collect { parseLayer(it as Map, dimension, resourceBox) })
		screen
	}

	private static ILayer parseLayer(Map<String, String> layerSpecification, Dimension layerDimension, ResourceBox resourceBox) {
		ILayer layer = new Layer(layerDimension, layerSpecification.visible as boolean, layerSpecification.opacity as int)
		layer.addWidgets(layerSpecification.drawables.collect { parseWidgets(it as Map, resourceBox) })
		layer
	}

	private static IWidget parseWidgets(Map drawableSpecification, ResourceBox resourceBox) {
		Location location = parseLocation(drawableSpecification.location as Map<String, Integer>)
		switch (drawableSpecification.type) {
			case 'text':
				new TextDrawable(drawableSpecification.font as String,
						resourceBox.getFont(drawableSpecification.font as String, drawableSpecification.fontSize as float),
						drawableSpecification.text as String,
						drawableSpecification.textColor as String,
						location)
				break
			case 'image':
				new ImageDrawable(drawableSpecification.image as String, resourceBox.getImage(drawableSpecification.image as String), location)
				break
			case 'date':
				new DateDrawable(drawableSpecification.font as String,
						drawableSpecification.dateFormat as String,
						drawableSpecification.timeZone as String,
						drawableSpecification.textColor as String,
						resourceBox.getFont(drawableSpecification.font as String, drawableSpecification.fontSize as float),
						location)
				break
			default:
				throw new IllegalArgumentException("Type ${drawableSpecification.type} is not a known resource type")
		}
	}

	private static BufferedImage loadImage(String path) {
		try {
			ImageIO.read(new File(path))
		} catch (IOException ioException) {
			throw new IllegalArgumentException("Cannot load image in <${path}>", ioException)
		}
	}

	private static Font loadFont(String path) {
		try {
			Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File(path))
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(customFont)
			customFont
		} catch (FontFormatException formatException) {
			throw new IllegalArgumentException("Cannot load font in <${path}>", formatException)
		}
	}
}


