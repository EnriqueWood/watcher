package ui.widgets

import drawing.Dimension

import java.awt.Color

interface Window {

	static final String WINDOW_TITLE = 'Watcher 0.1'

	static final Dimension INITIAL_DIMENSIONS = new Dimension(640, 800)

	static final Location INITIAL_LOCATION = new Location(2, 200)

	void show()

	void hide()

	void close()
}
