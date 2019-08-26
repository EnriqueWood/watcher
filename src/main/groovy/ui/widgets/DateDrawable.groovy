package ui.widgets

import drawing.Location
import state.StateChangedCheckerImpl

import java.awt.Font
import java.awt.Image

class DateDrawable implements IWidget {

	static final DEFAULT_FONT = new Font('Arial', Font.PLAIN, 50)

	String dateFormat
	String timeZone
	TextDrawable textDrawable

	@Delegate
	StateChangedCheckerImpl stateChangedChecker

	DateDrawable(String fontName, String dateFormat, String timeZone = "UTC", String textColor = '#000000', Font font, Location location) {
		this.dateFormat = dateFormat
		this.timeZone = timeZone
		this.textDrawable = new TextDrawable(fontName, font ?: DEFAULT_FONT, time, textColor, location ?: new Location(0, 0))
		this.stateChangedChecker = new StateChangedCheckerImpl(this)
	}

	protected String getTime() {
		new Date().format(dateFormat, SimpleTimeZone.getTimeZone(timeZone))
	}

	@Override
	Location getLocation() {
		textDrawable.location
	}

	@Override
	Image getImage() {
		textDrawable.image
	}

	@Override
	boolean shouldUpdate() {
		time != textDrawable.text
	}

	@Override
	void update() {
		textDrawable.text = time
		textDrawable.update()
	}

	@Override
	Map getStateProperties() {
		["type"      : "date",
		 "font"      : textDrawable.stateProperties.font,
		 "fontSize"  : textDrawable.stateProperties.fontSize,
		 "textColor" : textDrawable.stateProperties.textColor,
		 "location"  : textDrawable.stateProperties.location,
		 "dateFormat": dateFormat,
		 "timeZone"  : timeZone]
	}
}
