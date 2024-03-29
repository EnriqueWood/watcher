package drawing

import state.IStateChangedChecker
import state.StateChangedCheckerImpl
import state.IStateManaged

class Dimension implements IStateManaged {
	int width
	int height

	@Delegate
	IStateChangedChecker stateChangedChecker

	Dimension(int width, int height) {
		this.width = width
		this.height = height
		this.stateChangedChecker = new StateChangedCheckerImpl(this)
	}

	@Override
	Map getStateProperties() {
		[width : width,
		 height: height]
	}
}
