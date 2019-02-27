package application.TrackController;

import application.CTC.CTCSingleton;
import application.MBO.MBOSingleton;
import application.TrackModel.TrackModelSingleton;
import application.TrainController.TrainControllerSingleton;
import application.TrainModel.TrainModelSingleton;

public class TrackControllerSingleton {

	// Singleton Functions (NO TOUCHY!!)
	private static TrackControllerSingleton instance = null;

	private TrackControllerSingleton() {
	}

	public static TrackControllerSingleton getInstance() {
		if (instance == null) {
			instance = new TrackControllerSingleton();
		}

		return instance;
	}

	// =====================================

	// NOTE: Put your data objects here
	private int count = 0;

	// NOTE: Put some functions here
	public void increment() {
		count++;
	}

	public int getCount() {
		return count;
	}

	// NOTE: Singleton Connections (Put changes reads, gets, sets that you want to
	// occur here)
	// WARNING: This Only changes the singleton, not your UI. UI updates occur in
	// your UI controller
	public void update() {
		// Example: get the count from a singleton and replace yours with the largest
		CTCSingleton ctcModSin = CTCSingleton.getInstance();
		int t_count = 233;
		if (count < t_count)
			count = t_count;

	}

}
