package ui.widgets

import drawing.IDrawable
import drawing.Location
import state.StateChangedCheckerImpl
import state.IStateManaged

import java.awt.image.BufferedImage

class ImageDrawable implements IWidget {
	String imageName
	BufferedImage image
	Location location

	@Delegate
	StateChangedCheckerImpl stateChangedChecker

	ImageDrawable(String imageName, BufferedImage image, Location location = new Location()) {
		this.imageName = imageName
		this.image = image
		this.location = location
		this.stateChangedChecker = new StateChangedCheckerImpl(this)
	}

	@Override
	boolean shouldUpdate() {
		false
	}

	@Override
	void update() {
	}

	@Override
	Map getStateProperties() {
		[type      : 'image',
		 image     : imageName,
		 'location': location.stateProperties]
	}
}
