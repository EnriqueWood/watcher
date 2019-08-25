package toolkit

import drawing.IAsset
import state.StateChangedCheckerImpl

class Asset<T> implements IAsset {
	String name
	String path
	String type
	T resource

	@Delegate
	StateChangedCheckerImpl stateChangedChecker

	Asset(String name, String type, String path, T resource) {
		this.name = name
		this.path = path
		this.resource = resource
		this.type = type
		stateChangedChecker = new StateChangedCheckerImpl(this)
	}

	@Override
	Map getStateProperties() {
		[type: type,
		 name: name,
		 path: path]
	}
}
