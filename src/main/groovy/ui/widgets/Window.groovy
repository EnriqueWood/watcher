package ui.widgets

import java.awt.Color
import java.awt.Dimension
import java.awt.Point

interface Window {

	static final String WINDOW_TITLE = 'Watcher 0.1'

	static final Dimension INITIAL_DIMENSIONS = new Dimension(640, 800)

	static final Point INITIAL_LOCATION = new Point(2, 200)

	static final Color WINDOW_INITIAL_COLOR = Color.decode('#ff0000')

	void show()

	void hide()

	void close()
}
