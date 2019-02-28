package application.TrackController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
	private double speedA1 = 0;
	private int authorityA1 = 0;
	private int authorityA2 = 0;

	private int ID = 0;
	// NOTE: Put some functions here

	// NOTE: Singleton Connections (Put changes reads, gets, sets that you want to
	// occur here)
	// WARNING: This Only changes the singleton, not your UI. UI updates occur in
	// your UI controller

	boolean sent_train = false;

	public void update() {
		// Example: get the count from a singleton and replace yours with the largest
		CTCSingleton ctcModSin = CTCSingleton.getInstance();
		TrackModelSingleton trackModSin = TrackModelSingleton.getInstance();

		if (blockList == null)
			blockList = trackModSin.getBlockList();

		// get map<Integer,Train>
		Map<Integer, Train> trains = ctcModSin.viewtrains();

		Iterator<Entry<Integer, Train>> iterator = trains.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Integer, Train> entry = iterator.next();
			Train value = entry.getValue();
			speedA1 = value.getSuggestedSpeed();
			blockList.get(0).setSpeed(speedA1);
			authorityA1 = value.getAuthority();
			authorityA2 = value.getAuthority();
			blockList.get(0).setAuthority(authorityA1);

			blockList.get(0).setControlAuthority(true);
			ID = value.getID();

			if (!sent_train) {
				trackModSin.dispatchTrain();
			}

		}

	}

	void shiftBlockLeft() {
		if (CBID == 0)
			CBID = blockList.size() - 1;
		else
			CBID = CBID - 1;
	}

	void shiftBlockRight() {
		if (CBID == blockList.size() - 1)
			CBID = 0;
		else
			CBID = CBID + 1;
	}

	String getCBName() {
		if (blockList == null)
			return "A1";
		else
			return blockList.get(CBID).getName();

	}

	String getSpeed() {

		return Double.toString(speedA1);

	}

	String getAuthority() {
		if (CBID == 0) {
			return Integer.toString(authorityA1);
		}
		return Integer.toString(authorityA2);
	}

	boolean isCBOccupied() {
		if (blockList == null)
			return false;
		else
			return blockList.get(CBID).isOccupied();
	}
}
