package application.TrainController;

import application.CTC.CTCSingleton;
import application.MBO.MBOSingleton;
import application.TrackController.TrackControllerSingleton;
import application.TrackModel.TrackModelSingleton;
import application.TrainModel.TrainModelSingleton;

public class TrainControllerSingleton {

	// Singleton Functions (NO TOUCHY!!)
	private static TrainControllerSingleton instance = null;

	private TrainControllerSingleton() {
	}

	public static TrainControllerSingleton getInstance() {
		if (instance == null) {
			instance = new TrainControllerSingleton();
		}

		return instance;
	}

	// =====================================

	// NOTE: Put your data objects here
	private String speed, power, temperature;
	private int count = 0;
	// NOTE: Put some functions here
	
	
	public String getSpeed() {
		return speed;
	}
	
	public void setSpeed(String speed) {
		this.speed = speed;
	}
	
	public String getPower() {
		return power;
	}
	
	public void setPower(String power) {
		this.power = power;
	}
	
	public String getTemperature() {
		return temperature;
	}
	
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	
	// NOTE: Singleton Connections (Put changes reads, gets, sets that you want to
	// occur here)
	// WARNING: This Only changes the singleton, not your UI. UI updates occur in
	// your UI controller
	public void update() {
		
		// Example: get the count from a singleton and replace yours with the largest
		TrainModelSingleton trnModSin = TrainModelSingleton.getInstance();
		int t_count = trnModSin.getCount();
			count = t_count;
		/*if (count < t_count)
			count = t_count;
		else
			count = t_count;*/
		

	}

}
