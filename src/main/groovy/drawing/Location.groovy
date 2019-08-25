package drawing

import state.IStateChangedChecker
import state.StateChangedCheckerImpl
import state.IStateManaged

class Location implements IStateManaged {
	int x = 0
	int y = 0

	@Delegate
	IStateChangedChecker stateChangedChecker

	Location(int x = 0, int y = 0) {
		this.x = x
		this.y = y
		this.stateChangedChecker = new StateChangedCheckerImpl(this)
	}

	@Override
	Map getStateProperties() {
		[x: x, y: y]
	}
}
