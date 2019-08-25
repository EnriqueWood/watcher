package state

class StateChangedCheckerImpl implements IStateChangedChecker {

	Map initialStateProperties
	IStateManaged stateManaged

	StateChangedCheckerImpl(IStateManaged stateManaged) {
		this.stateManaged = stateManaged
		this.initialStateProperties = stateManaged.stateProperties
	}

	boolean stateChanged() {
		initialStateProperties != stateManaged.stateProperties
	}
}
