package ui.widgets

import drawing.Drawable

import java.awt.Font
import java.awt.Image

class DateDrawable implements Drawable {

	static final DEFAULT_FONT = new Font('Arial', Font.PLAIN, 50)

	boolean valid
	String dateFormat
	String timeZone
	TextDrawable textDrawable

	DateDrawable(String dateFormat, String timeZone = "UTC", String textColor = '#000000', Font font, Location location) {
		this.dateFormat = dateFormat
		this.timeZone = timeZone
		this.textDrawable = new TextDrawable(font ?: DEFAULT_FONT, time, textColor, location ?: new Location(0, 0))

		//TODO: Make updatable drawables subscribe to external timer
		TimerTask timerTask = new TimerTask() {
			@Override
			void run() {
				if (time != textDrawable.text) {
					valid = false
					println "DateDrawable - with format ${dateFormat} needs update..."
				}
			}
		}
		Timer timer = new Timer('dateUpdater', true)
		timer.schedule(timerTask, 100, 100)
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
	int getWidth() {
		textDrawable.width
	}

	@Override
	int getHeight() {
		textDrawable.height
	}

	@Override
	boolean shouldUpdate() {
		!valid
	}

	@Override
	void update() {
		textDrawable.text = time
		textDrawable.update()
		valid = true
	}
}
