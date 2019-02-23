package application.TrackModel;

import application.CTC.CTCSingleton;
import application.MBO.MBOSingleton;
import application.TrackController.TrackControllerSingleton;
import application.TrainController.TrainControllerSingleton;
import application.TrainModel.TrainModelSingleton;

public class TrackModelSingleton {

	// Singleton Functions (NO TOUCHY!!)
	private static TrackModelSingleton instance = null;

	private TrackModelSingleton() {
	}

	public static TrackModelSingleton getInstance() {
		if (instance == null) {
			instance = new TrackModelSingleton();
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
		TrackControllerSingleton tckCtrlSin = TrackControllerSingleton.getInstance();
		int t_count = tckCtrlSin.getCount();
		if (count < t_count)
			count = t_count;

	}

}
