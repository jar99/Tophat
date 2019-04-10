package application;

public class ClockSingleton {
	// Singleton Functions (NO TOUCHY!!)
	private static ClockSingleton instance = null;

	private ClockSingleton() {

	}

	public static ClockSingleton getInstance() {
		if (instance == null) {
			instance = new ClockSingleton();
		}

		return instance;
	}

	// ===============UPDATE METHOD======================
	public void update() {
		currentTimeSeconds += ratio;
	}

	// ===============DATA / VARIABLES======================
	private int currentTimeSeconds = 0;
	private int ratio = 1;

	// ===============METHODS======================

	public int getCurrentTimeSeconds() {
		return ((currentTimeSeconds % 86400) % 3600) % 60;
	}

	public int getCurrentTimeMinutes() {
		return ((currentTimeSeconds % 86400) % 3600) / 60;
	}

	public int getCurrentTimeHours() {
		return (currentTimeSeconds % 86400) / 3600;

	}

	public String getCurrentTimeString() {
		return getCurrentTimeHours() + ":" + getCurrentTimeMinutes() + ":" + getCurrentTimeSeconds();
	}

	public int getRatio() {
		return ratio;
	}

	public void setCurrentTime(int newTime) {
		currentTimeSeconds = newTime;
	}

	public void setRatio(int newRatio) {
		ratio = newRatio;
	}

}
