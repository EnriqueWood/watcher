package drawing

import state.IStateManaged

interface IWatchSpecification extends IStateManaged {
	Dimension getDimension()

	List<IAsset> getAssets()

	List<IScreen> getScreens()
}
