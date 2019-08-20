package drawing

import groovy.json.JsonSlurper
import ui.widgets.Window

import javax.swing.JFileChooser
import java.awt.Container
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

	static Map parseJson(String path) {
		new JsonSlurper().parse(new File(path)) as Map
	}

	static File getWidgetFolder() {
		List<File> widgetFoldersInDefaultFolder = widgetFoldersInDefaultFolder
		if (widgetFoldersInDefaultFolder.empty) {
			return pickFolder()
		}
		if (widgetFoldersInDefaultFolder.size() == 1) {
			return widgetFoldersInDefaultFolder.first()
		}
		pickFolder(null, Window.DEFAULT_WIDGETS_FOLDER)
	}

	static File pickFolder(Container parent = null, File initialFolder = Window.DEFAULT_INITIAL_FOLDER) {
		JFileChooser chooser = new JFileChooser(initialFolder)
		chooser.setVisible(true)
		chooser.multiSelectionEnabled = false
		chooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
		chooser.showOpenDialog(parent)
		chooser.selectedFile
	}

	static List<File> getWidgetFoldersInDefaultFolder() {
		List<File> directories = Window.DEFAULT_WIDGETS_FOLDER.listFiles().findAll { it.directory }
		directories.empty ? [] : directories
	}
}
