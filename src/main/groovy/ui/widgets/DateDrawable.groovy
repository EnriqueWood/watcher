package ui.widgets

import drawing.Drawable

import java.awt.Font
import java.awt.Image

class DateDrawable implements Drawable {
	String dateFormat
	TextDrawable textDrawable
	Font font
	Location location
	String hexColor
	boolean valid

	DateDrawable(String dateFormat, String textColor = '#000000', Font font = new Font('Lucida', Font.PLAIN, 50), Location location = new Location()) {
		this.font = font
		this.location = location
		this.hexColor = textColor
		this.dateFormat = dateFormat
		textDrawable = new TextDrawable(time, textColor, font, location)
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
		new Date().format(dateFormat)
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
