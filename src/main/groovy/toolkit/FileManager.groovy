package toolkit

import drawing.IWindow

import javax.swing.JFileChooser

class FileManager {

	static File pickFolder(File initialFolder = IWindow.DEFAULT_INITIAL_FOLDER) {
		JFileChooser chooser = new JFileChooser(initialFolder)
		chooser.setVisible(true)
		chooser.multiSelectionEnabled = false
		chooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
		chooser.showOpenDialog(null)
		chooser.selectedFile
	}

	static File getWidgetFolder() {
		List<File> widgetFoldersInDefaultFolder = widgetFoldersInDefaultFolder
		if (widgetFoldersInDefaultFolder.empty) {
			return pickFolder()
		}
		if (widgetFoldersInDefaultFolder.size() == 1) {
			return widgetFoldersInDefaultFolder.first()
		}
		pickFolder(IWindow.DEFAULT_WIDGETS_FOLDER)
	}

	static List<File> getWidgetFoldersInDefaultFolder() {
		List<File> directories = IWindow.DEFAULT_WIDGETS_FOLDER.listFiles().findAll { it.directory }
		directories.empty ? [] : directories
	}

	private static void writeTextToFile(String text, File file) {
		FileWriter fileWriter = new FileWriter(file)
		fileWriter.write(text)
		fileWriter.close()
	}
}
