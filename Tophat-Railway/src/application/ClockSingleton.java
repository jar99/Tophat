package application;

/**
 * <h1>System Clock</h1> Increments a counter ever second and returns time data
 * based on that counter
 *
 * @author Cory Cizauskas
 * @version 1.0
 * @since 2019-04-13
 */
public class ClockSingleton {
	// Singleton Functions (NO TOUCHY!!)
	private static ClockSingleton instance = null;

	/**
	 * Private Constructor
	 */
	private ClockSingleton() {

	}

	/**
	 * Gives the caller an instance of the Clock Singleton
	 * 
	 * @return a reference to the Clock Singleton
	 */
	public static ClockSingleton getInstance() {
		if (instance == null) {
			instance = new ClockSingleton();
		}

		return instance;
	}

	// ===============UPDATE METHOD======================
	/**
	 * Advances the clock forward based on current speed
	 */
	public void update() {
		currentTimeSeconds += ratio;
	}

	// ===============DATA / VARIABLES======================
	private int currentTimeSeconds = 0;
	private int ratio = 1;

	// ===============METHODS======================

	/**
	 * Gets the number of seconds leading to the next minute
	 * 
	 * @return seconds
	 */
	public int getCurrentTimeSeconds() {
		return ((currentTimeSeconds % 86400) % 3600) % 60;
	}

	/**
	 * Gets the number of minutes leading to the next hour
	 * 
	 * @return minutes
	 */
	public int getCurrentTimeMinutes() {
		return ((currentTimeSeconds % 86400) % 3600) / 60;
	}

	/**
	 * Gets the number of hours leading to the next day
	 * 
	 * @return hours
	 */
	public int getCurrentTimeHours() {
		return (currentTimeSeconds % 86400) / 3600;

	}

	/**
	 * Gets a string representation of the current time (HH:MM:SS)
	 * 
	 * @return String - (HH:MM:SS)
	 */
	public String getCurrentTimeString() {
		return getCurrentTimeHours() + ":" + getCurrentTimeMinutes() + ":" + getCurrentTimeSeconds();
	}

	/**
	 * Gets the ratio of system time : real time
	 * 
	 * @return the number of system seconds advancing every real second
	 */
	public int getRatio() {
		return ratio;
	}

	/**
	 * Sets the system time to a new value
	 * 
	 * @param newTime - the new system time in seconds
	 */
	public void setCurrentTime(int newTime) {
		currentTimeSeconds = newTime;
	}

	/**
	 * Sets the system speed. 0 pauses the system.
	 * 
	 * @param newRatio - the new ratio of system seconds to real seconds
	 */
	public void setRatio(int newRatio) {
		ratio = newRatio;
	}

}
