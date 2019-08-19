package toolkit

import drawing.Drawable
import drawing.ILayer
import drawing.Layer
import ui.widgets.DateDrawable
import ui.widgets.ImageDrawable
import ui.widgets.Location
import ui.widgets.TextDrawable

import javax.imageio.ImageIO
import java.awt.Dimension
import java.awt.Font
import java.awt.FontFormatException
import java.awt.GraphicsEnvironment
import java.awt.image.BufferedImage

class ResourceLoader {
	//TODO: improve resource loading
	static final Map resourceMap = [
			width    : 600,
			height   : 600,
			resources: [
					[name: 'casio-skin', type: 'image', path: './resources/casio-skin.png'],
					[name: 'time-font', type: 'font', path: './resources/technology_font/Technology-Bold.ttf'],
			],
			layers   : [
					[visible  : true,
					 drawables: [
							 [type     : 'image',
							  image    : 'casio-skin',
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
							 [type      : 'date',
							  font      : 'time-font',
							  fontSize  : 42f,
							  dateFormat: 'dd',
							  textColor : '#000000',
							  locationX : 320,
							  locationY : 320,
							 ],
							 [type      : 'date',
							  font      : 'time-font',
							  fontSize  : 80f,
							  dateFormat: 'HH',
							  textColor : '#000000',
							  locationX : 145,
							  locationY : 360,
							 ],
							 [type      : 'date',
							  font      : 'time-font',
							  fontSize  : 80f,
							  dateFormat: ':mm',
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
							  textColor : '#000000',
							 ],
					 ]

					]
			]
	]

	static List<ILayer> load(Map resourceMap) {
		ResourceBox resourceBox = new ResourceBox()
		resourceBox.addAllResources(resourceMap.resources.collect { loadResource(it as Map) })
		resourceMap.layers.collect {
			parseLayer(it as Map, new Dimension(resourceMap.width as int, resourceMap.height as int), resourceBox)
		}
	}

	static Resource loadResource(Map resourceSpecification) {
		switch (resourceSpecification.type) {
			case 'image':
				return new Resource<BufferedImage>(resourceSpecification.name as String, loadImage(resourceSpecification.path as String))
				break
			case 'font':
				return new Resource<Font>(resourceSpecification.name as String, loadFont(resourceSpecification.path as String))
				break
			default:
				throw new IllegalArgumentException("Type ${resourceSpecification.type} is not a known resource type")
		}
	}

	static ILayer parseLayer(Map<String, String> layerSpecification, Dimension layerDimension, ResourceBox resourceBox) {
		ILayer layer = new Layer(layerDimension, layerSpecification.visible as boolean)
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


