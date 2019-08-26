package drawing

import state.IStateManaged

interface IWatch extends IStateManaged {
	Dimension getDimension()

	List<IAsset> getAssets()

	List<IScreen> getScreens()
}
