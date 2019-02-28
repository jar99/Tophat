package application.TrackController;

import java.util.ArrayList;
import application.TrackModel.*;
import application.CTC.*;

import application.CTC.CTCSingleton;
import application.MBO.MBOSingleton;
import application.TrackModel.TrackModelSingleton;
import application.TrainController.TrainControllerSingleton;
import application.TrainModel.TrainModelSingleton;

public class TrackControllerSingleton {

	// Singleton Functions (NO TOUCHY!!)
	private static TrackControllerSingleton instance = null;
	
	private ArrayList<TrackBlock> blockList = null;
	

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
	private int CBID = 0;
	private int speed = 0;
	private int authority = 0;
	private int ID = 0;
	// NOTE: Put some functions here
	
	

	


	// NOTE: Singleton Connections (Put changes reads, gets, sets that you want to
	// occur here)
	// WARNING: This Only changes the singleton, not your UI. UI updates occur in
	// your UI controller
	public void update() {
		// Example: get the count from a singleton and replace yours with the largest
		CTCSingleton ctcModSin = CTCSingleton.getInstance();
		TrackModelSingleton trackModSin = TrackModelSingleton.getInstance();

		
		if(blockList == null)
			blockList = trackModSin.getBlockList();
		
		//get map<Integer,Train>
		/*ctcModSin.viewtrains();
		
		Iterator<Entry<Integer, Train>> iterator = trains.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Integer, Train> entry = iterator.next();
			Train value = entry.getValue();
			speed = value.getSuggestedSpeed();
			authority = value.getAuthority();
			ID = value.getID();
		}*/
		

	}
	
	void shiftBlockLeft() {
		if (CBID == 0) CBID = blockList.size() - 1;
		else CBID = CBID - 1;
	}

	void shiftBlockRight() {
		if (CBID == blockList.size() - 1) CBID = 0;
		else CBID = CBID + 1;
	}

	String getCBName() {
		return blockList.get(CBID).getName();
		
	
	}

	String getSpeed() {
		return Integer.toString(speed);
	}

	String getAuthority() {
		return Integer.toString(authority);
	}
	
}
