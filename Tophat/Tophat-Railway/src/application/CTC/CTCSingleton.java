package application.CTC;

import application.MBO.MBOSingleton;
import application.TrackController.TrackControllerSingleton;
import application.TrackModel.TrackModelSingleton;
import application.TrainController.TrainControllerSingleton;
import application.TrainModel.TrainModelSingleton;

public class CTCSingleton {

	// Singleton Functions (NO TOUCHY!!)
	private static CTCSingleton instance = null;

	private CTCSingleton() {
	}

	public static CTCSingleton getInstance() {
		if (instance == null) {
			instance = new CTCSingleton();
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
		MBOSingleton mboModSin = MBOSingleton.getInstance();
		int t_count = mboModSin.getCount();
		if (count < t_count)
			count = t_count;

	}

	public void getAuthority() {
		// TODO Auto-generated method stub
		
	}

	public void getSpeed() {
		// TODO Auto-generated method stub
		
	}

	public void getSwitchTrackState() {
		// TODO Auto-generated method stub
		
	}

}
