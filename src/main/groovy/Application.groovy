import drawing.IWindow
import ui.SwingWindow

class Application {
	static void main(String[] args) {
		IWindow mainFrame = new SwingWindow()
		mainFrame.show()
	}
}
