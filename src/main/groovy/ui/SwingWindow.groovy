package ui

import drawing.Dimension
import drawing.IPaintingCanvas
import drawing.IWatchSpecification
import drawing.IWindow
import drawing.SwingPaintingCanvas
import state.StateManager
import toolkit.Helper
import toolkit.ResourceManager

import javax.swing.JFrame
import java.awt.Graphics

class SwingWindow implements IWindow {

	public static final int TICK_PERIOD_IN_MILLIS = 100
	JFrame frame
	IPaintingCanvas paintingCanvas
	Timer tickTimer
	File widgetFolder
	IWatchSpecification watchSpecification
	private StateManager stateManager

	SwingWindow() {
		this.widgetFolder = Helper.widgetFolder
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
		timer.scheduleAtFixedRate(tickAndRepaintTask(), TICK_PERIOD_IN_MILLIS, TICK_PERIOD_IN_MILLIS)
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
		IWatchSpecification watchSpecification = ResourceManager.parseSpecification(specificationFolderPath)
		initFrame(watchSpecification.dimension)
		paintingCanvas = new SwingPaintingCanvas(frame, frame.width, frame.height)
		paintingCanvas.addScreens(watchSpecification.screens)
		paintingCanvas.updateScreens(true)
		paintingCanvas.paint()
		paintingCanvas.show()
		this.tickTimer = scheduleTickAndRepaint()
		frame.setVisible(true)
		stateManager = new StateManager(watchSpecification)
	}

	void saveSpecificationFile(File file) {
		FileWriter fileWriter = new FileWriter(file)
		fileWriter.write(stateManager.getStateAsJsonString())
		fileWriter.close()
	}

	@Override
	String getSpecificationFolderPath() {
		widgetFolder.path
	}

	void hide() {
		frame.setVisible(false)
	}

	void close() {
		tickTimer.cancel()
		frame.dispose()
	}
}
