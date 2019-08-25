package state

import groovy.json.JsonBuilder

class StateManager {

	IStateManaged stateManagedObject

	StateManager(IStateManaged stateManagedObject) {
		this.stateManagedObject = stateManagedObject
	}

	boolean stateChanged(){
		stateManagedObject.stateChanged()
	}

	String getStateAsJsonString() {
		new JsonBuilder(stateManagedObject.stateProperties).toPrettyString()
	}

}
