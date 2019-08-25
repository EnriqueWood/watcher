package toolkit

import drawing.Dimension
import drawing.IAsset
import drawing.IScreen
import drawing.IWatchSpecification
import state.StateChangedCheckerImpl

class WatchSpecification implements IWatchSpecification {
	Dimension dimension
	List<IAsset> assets
	List<IScreen> screens

	@Delegate
	StateChangedCheckerImpl stateChangedChecker

	WatchSpecification(Dimension dimension, List<IAsset> assets, List<IScreen> screens) {
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
