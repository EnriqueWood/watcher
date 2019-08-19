package ui.widgets

import drawing.PaintingCanvas
import drawing.SwingPaintingCanvas
import toolkit.ResourceLoader

import javax.swing.JFrame
import java.awt.Graphics
import java.awt.Rectangle

class SwingWindow implements Window {

	public static final int TICK_PERDIOD_IN_MILLIS = 100
	JFrame frame
	PaintingCanvas paintingCanvas
	Timer tickTimer

	SwingWindow() {
		initFrame()
		paintingCanvas = new SwingPaintingCanvas(frame, frame.width, frame.height)
		paintingCanvas.addLayers(ResourceLoader.load(ResourceLoader.@resourceMap))
		paintingCanvas.updateLayers(true)
		paintingCanvas.paintLayers()
		paintingCanvas.show()
		this.tickTimer = scheduleTickAndRepaint()
	}

	void initFrame() {
		frame = new JFrame() {
			@Override
			void paint(Graphics g) {
				super.paint(g)
				paintingCanvas.paintLayers()
			}
		}
		frame.bounds = new Rectangle(size: INITIAL_DIMENSIONS, location: INITIAL_LOCATION)
		frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
	}

	protected Timer scheduleTickAndRepaint() {
		new Timer('tickAndRepaint', true)
				.scheduleAtFixedRate(tickAndRepaintTask(), TICK_PERDIOD_IN_MILLIS, TICK_PERDIOD_IN_MILLIS)
	}

	protected TimerTask tickAndRepaintTask() {
		new TimerTask() {
			@SuppressWarnings("GroovyAssignabilityCheck")
			@Override
			void run() {
				// if at least one layer was updated call to repaint
				if (!paintingCanvas.updateLayers().empty) {
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
