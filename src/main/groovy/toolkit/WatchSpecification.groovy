package toolkit

import drawing.Dimension
import drawing.IScreen
import drawing.IWatchSpecification

class WatchSpecification implements IWatchSpecification {
	Dimension dimension
	List<IScreen> screens

	WatchSpecification(Dimension dimension, List<IScreen> screens) {
		this.dimension = dimension
		this.screens = screens
	}
}
