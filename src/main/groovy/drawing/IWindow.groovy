package drawing

interface IWindow {

	String WINDOW_TITLE = 'Watcher 0.1'

	Dimension INITIAL_DIMENSIONS = new Dimension(640, 800)

	Location INITIAL_LOCATION = new Location(2, 200)

	File DEFAULT_WIDGETS_FOLDER = new File('./assets')

	File DEFAULT_INITIAL_FOLDER = new File('.')

	void show()

	void hide()

	void close()
}
