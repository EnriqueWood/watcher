package ui.widgets

import drawing.Dimension
import drawing.IWatchSpecification
import drawing.PaintingCanvas
import drawing.SwingPaintingCanvas
import toolkit.ResourceLoader

import javax.swing.JFrame
import java.awt.Graphics

class SwingWindow implements Window {

	public static final int TICK_PERDIOD_IN_MILLIS = 100
	JFrame frame
	PaintingCanvas paintingCanvas
	Timer tickTimer

	SwingWindow(String speficationFolderPath) {
		IWatchSpecification watchSpecification = ResourceLoader.parseSpecification(speficationFolderPath)
		initFrame(watchSpecification.dimension)
		paintingCanvas = new SwingPaintingCanvas(frame, frame.width, frame.height)
		paintingCanvas.addScreens(watchSpecification.screens)
		paintingCanvas.updateScreens(true)
		paintingCanvas.paint()
		paintingCanvas.show()
		this.tickTimer = scheduleTickAndRepaint()
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

	protected Timer scheduleTickAndRepaint() {
		Timer timer = new Timer('tickAndRepaint', true)
		timer.scheduleAtFixedRate(tickAndRepaintTask(), TICK_PERDIOD_IN_MILLIS, TICK_PERDIOD_IN_MILLIS)
		timer
	}

	protected TimerTask tickAndRepaintTask() {
		new TimerTask() {
			@SuppressWarnings("GroovyAssignabilityCheck")
			@Override
			void run() {
				// if at least one layer was updated call to repaint
				if (!paintingCanvas.updateScreens().empty) {
					frame.repaint()
				}
			}
		}
	}

	void show() {
		frame.setVisible(true)
	}

	void hide() {
		frame.setVisible(false)
	}

	void close() {
		tickTimer.cancel()
		frame.dispose()
	}
}
