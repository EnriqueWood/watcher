package toolkit

import drawing.Dimension
import drawing.Drawable
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
	//TODO: improve resource loading
	static final Map resourceMap = [
			dimension: [
					width : 483,
					height: 697,
			],
			assets   : [
					[name: 'watch-skin', type: 'image', path: './assets/watch-skin.png'],
					[name: 'time-font', type: 'font', path: './assets/font.ttf'],
			],
			screens  : [
					[active: true,
					 layers: [
							 [visible  : true,
							  opacity  : 100,
							  drawables: [
									  [type     : 'image',
									   image    : 'watch-skin',
									   locationX: 0,
									   locationY: 0,
									  ],
									  [type     : 'text',
									   locationX: 145,
									   locationY: 360,
									   text     : '88:88',
									   font     : 'time-font',
									   textColor: '#bbbbbc',
									   fontSize : 80f
									  ],
									  [type     : 'text',
									   locationX: 310,
									   locationY: 378,
									   text     : '88',
									   font     : 'time-font',
									   textColor: '#bbbbbc',
									   fontSize : 56f
									  ],
									  [type     : 'text',
									   text     : 'SA',
									   font     : 'time-font',
									   textColor: '#bbbbbc',
									   fontSize : 42f,
									   locationX: 220,
									   locationY: 320,
									  ],
									  [type     : 'text',
									   text     : '88',
									   font     : 'time-font',
									   fontSize : 42f,
									   textColor: '#bbbbbc',
									   locationX: 320,
									   locationY: 320,
									  ],
									  [type      : 'date',
									   font      : 'time-font',
									   fontSize  : 42f,
									   dateFormat: 'dd',
									   timeZone  : 'America/Santiago',
									   textColor : '#000000',
									   locationX : 320,
									   locationY : 320,
									  ],
									  [type      : 'date',
									   font      : 'time-font',
									   fontSize  : 80f,
									   dateFormat: 'HH',
									   timeZone  : 'America/Santiago',
									   textColor : '#000000',
									   locationX : 145,
									   locationY : 360,
									  ],
									  [type      : 'date',
									   font      : 'time-font',
									   fontSize  : 80f,
									   dateFormat: ':mm',
									   timeZone  : 'America/Santiago',
									   textColor : '#000000',
									   locationX : 215,
									   locationY : 360,
									  ],
									  [type      : 'date',
									   font      : 'time-font',
									   fontSize  : 56f,
									   locationX : 310,
									   locationY : 378,
									   dateFormat: 'ss',
									   timeZone  : 'America/Santiago',
									   textColor : '#000000',
									  ],
							  ]
							 ]
					 ]
					]
			]
	]

	static IWatchSpecification parseSpecification(Map resourceMap) {
		Dimension dimension = parseDimension(resourceMap)
		ResourceBox resourceBox = new ResourceBox()
		resourceBox.addAllAssets(resourceMap.assets.collect { loadAsset(it as Map) })
		List<IScreen> screens = resourceMap.screens.collect {
			parseScreen(it as Map, dimension, resourceBox)
		}
		new WatchSpecification(dimension, screens)
	}

	protected static Dimension parseDimension(Map resourceMap) {
		new Dimension(resourceMap.dimension.width as int, resourceMap.dimension.height as int)
	}

	static Asset loadAsset(Map assetsSpecification) {
		switch (assetsSpecification.type) {
			case 'image':
				return new Asset<BufferedImage>(assetsSpecification.name as String, loadImage(assetsSpecification.path as String))
				break
			case 'font':
				return new Asset<Font>(assetsSpecification.name as String, loadFont(assetsSpecification.path as String))
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
				return new TextDrawable(drawableSpecification.text as String,
						drawableSpecification.textColor as String,
						resourceBox.getFont(drawableSpecification.font as String, drawableSpecification.fontSize as float),
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
		Font customFont = null
		try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File(path))
			GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(customFont)
		} catch (FontFormatException e) {
			e.printStackTrace()
		}
		customFont
	}
}


