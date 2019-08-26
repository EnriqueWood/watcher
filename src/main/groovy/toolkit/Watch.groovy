package toolkit

import drawing.Dimension
import drawing.IAsset
import drawing.IScreen
import drawing.IWatch
import state.StateChangedCheckerImpl

class Watch implements IWatch {
	Dimension dimension
	List<IAsset> assets
	List<IScreen> screens

	@Delegate
	StateChangedCheckerImpl stateChangedChecker

	Watch(Dimension dimension, List<IAsset> assets, List<IScreen> screens) {
		this.dimension = dimension
		this.assets = assets
		this.screens = screens
		this.stateChangedChecker = new StateChangedCheckerImpl(this)
	}

	@Override
	Map getStateProperties() {
		[dimension: dimension.stateProperties,
		 assets   : assets*.stateProperties,
		 screens  : screens*.stateProperties]
	}
}
