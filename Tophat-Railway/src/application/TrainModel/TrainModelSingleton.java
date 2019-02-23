package application.TrainModel;

import application.CTC.CTCSingleton;
import application.MBO.MBOSingleton;
import application.TrackController.TrackControllerSingleton;
import application.TrackModel.TrackModelSingleton;
import application.TrainController.TrainControllerSingleton;

public class TrainModelSingleton {

	// Singleton Functions (NO TOUCHY!!)
	private static TrainModelSingleton instance = null;

	private TrainModelSingleton() {
	}

	public static TrainModelSingleton getInstance() {
		if (instance == null) {
			instance = new TrainModelSingleton();
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
		TrackModelSingleton tckModSin = TrackModelSingleton.getInstance();
		int t_count = tckModSin.getCount();
		if (count < t_count)
			count = t_count;

	}

}
