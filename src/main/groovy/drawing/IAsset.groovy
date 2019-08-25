package drawing

import state.IStateManaged

interface IAsset extends IStateManaged {

	String getType()

	String getName()

	String getPath()

	Object getResource()
}
