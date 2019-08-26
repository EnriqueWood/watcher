package timing

import drawing.IUpdatable

class Timer {

	int tickPeriod
	List<IUpdatable> updatables

	Timer(int tickPeriod) {
		this.tickPeriod = tickPeriod
		this.updatables = []
	}

	void start() {
		java.util.Timer timer = new java.util.Timer('tickAndRepaint', true)
		timer.scheduleAtFixedRate(timerTask, tickPeriod, tickPeriod)
		timer
	}

	private TimerTask timerTask = new TimerTask() {
		@Override
		void run() {
			updatables*.update()
		}
	}

	void cancel() {
		timerTask.cancel()
	}
}
