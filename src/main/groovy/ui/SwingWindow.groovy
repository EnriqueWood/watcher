package ui

import drawing.Dimension
import drawing.IPaintingCanvas
import drawing.IUpdatable
import drawing.IWatch
import drawing.IWindow
import drawing.SwingPaintingCanvas
import state.StateManager
import timing.Timer
import toolkit.FileManager
import toolkit.ResourceManager

import javax.swing.JFrame
import java.awt.Graphics

class SwingWindow implements IWindow, IUpdatable {

	public static final int TICK_PERIOD_IN_MILLIS = 100

	JFrame frame
	IPaintingCanvas paintingCanvas
	IWatch watch
	ResourceManager resourceManager
	StateManager stateManager
	Timer ticker

	SwingWindow() {
		resourceManager = new ResourceManager(FileManager.widgetFolder.path)
		watch = resourceManager.parseSpecification()
		stateManager = new StateManager(watch)
		ticker = new Timer(TICK_PERIOD_IN_MILLIS)
		ticker.updatables.add(this)
	}

	void initFrame(Dimension dimension = new Dimension(INITIAL_DIMENSIONS.width as int, INITIAL_DIMENSIONS.height as int)) {
		frame = new JFrame() {
			@Override
			void paint(Graphics g) {
				super.paint(g)
				paintingCanvas.paint()
			}
		}
		frame.title = WINDOW_TITLE
		frame.setBounds(INITIAL_LOCATION.x, INITIAL_LOCATION.y, dimension.width, dimension.height)
		frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
	}

	void show() {
		initFrame(watch.dimension)
		paintingCanvas = new SwingPaintingCanvas(frame, frame.width, frame.height)
		paintingCanvas.addScreens(watch.screens)
		paintingCanvas.update(true)
		paintingCanvas.paint()
		paintingCanvas.show()
		ticker.start()
		frame.setVisible(true)
	}

	void hide() {
		frame.setVisible(false)
	}

	void close() {
		ticker.cancel()
		frame.dispose()
	}

	@Override
	boolean shouldUpdate() {
		true
	}

	@Override
	void update() {
		paintingCanvas.update(false)
		frame.repaint()
	}
}
