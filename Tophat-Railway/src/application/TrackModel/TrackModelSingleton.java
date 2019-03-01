package application.TrackModel;

import java.util.ArrayList;

import application.CTC.CTCSingleton;
import application.MBO.MBOSingleton;
import application.TrackController.TrackControllerSingleton;
import application.TrainController.TrainControllerSingleton;
import application.TrainModel.TrainModelSingleton;

public class TrackModelSingleton implements TrackModelInterface{

	// Singleton Functions (NO TOUCHY!!)
	private static TrackModelSingleton instance = null;

	private TrackModelSingleton() {
		//: Initialize temporary block list
		TrackBlock block1 = new TrackBlock('A', 1, 100.0, 0.1, 10.0, 21.0, 00.0, 
												150, 60, 150, 110);
		
		  TrackBlock block2 = new TrackBlock('A', 2, 200.0, 0.2, 20.0, 22.0, 21.0, 150,
		  110, 150, 210); 
		/*
		 * TrackBlock block3 = new TrackBlock('A', 3, 300.0, 0.3, 30.0, 23.0, 43.0, 150,
		 * 210, 150, 360);
		 */
		 
		//block2.setOccupied();
		t_BlockList.add(block1);
		t_BlockList.add(block2);
		//t_BlockList.add(block3);
		
	}

	public static TrackModelSingleton getInstance() {
		if (instance == null) {
			instance = new TrackModelSingleton();
		}

		return instance;
	}

	// =====================================
	
	// NOTE: Put your data objects here
	//private ArrayList<TrackLine> track = new ArrayList<TrackLine>();
	private int CBID = 0;	// Current Block ID
	private ArrayList<TrackBlock> t_BlockList = new ArrayList<TrackBlock>();
	
	//: store list of trains and locations
	private ArrayList<TrackTrain> t_TrainList = new ArrayList<TrackTrain>();
	
	// NOTE: Put some functions here
	

	// NOTE: Singleton Connections (Put changes reads, gets, sets that you want to
	// occur here)
	// WARNING: This Only changes the singleton, not your UI. UI updates occur in
	// your UI controller
	public void update() {
		TrackControllerSingleton tckCtrlSin = TrackControllerSingleton.getInstance();
		TrainModelSingleton trnModSin = TrainModelSingleton.getInstance();
		
		
		if (t_TrainList.size() > 0) {
			TrackTrain train = t_TrainList.get(0);
			if (train.getY() > 210) {
				t_TrainList.get(0).delete();
				trnModSin.removeTrain(0);
				t_BlockList.get(1).unsetOccupied();
			} else if (train.getY() > 110) {
				t_BlockList.get(0).unsetOccupied();
				t_BlockList.get(1).setOccupied();
			}
		}
		
		//tckCtrlSin.getSwitchStates();
		//tckCtrlSin.getAuthority();
		//tckCtrlSin.getSpeed();
		
		//TODO: get speed
		
		//TODO: get authority
		//TODO: get control authority
		
		/*
		 * TrackTrain train1 = t_TrainList.get(0); train1.changeCoord(train1.getX(),
		 * train1.getY() + 5.0);
		 */

	}

	
	//===UI Controller Methods======================================
	void shiftBlockLeft() {
		if (CBID == 0) CBID = t_BlockList.size() - 1;
		else CBID = CBID - 1;
	}

	void shiftBlockRight() {
		if (CBID == t_BlockList.size() - 1) CBID = 0;
		else CBID = CBID + 1;
	}

	String getCBName() {
		return t_BlockList.get(CBID).getName();
	}

	String getCBLength() {
		return Double.toString(t_BlockList.get(CBID).getLength());
	}

	String getCBGrade() {
		return Double.toString(t_BlockList.get(CBID).getGrade());
	}

	String getCBSpdLmt() {
		return Double.toString(t_BlockList.get(CBID).getSpdLmt());
	}

	String getCBElev() {
		return Double.toString(t_BlockList.get(CBID).getElev());
	}

	String getCBTotElev() {
		return Double.toString(t_BlockList.get(CBID).getTotElev());
	}

	boolean isCBOccupied() {
		return t_BlockList.get(CBID).isOccupied();
	}

	boolean isCBUnderground() {
		return t_BlockList.get(CBID).isUnderground();
	}

	boolean isCBStation() {
		return t_BlockList.get(CBID).isStation();
	}

	boolean isCBCrossing() {
		return t_BlockList.get(CBID).isCrossing();
	}

	boolean isCBBeacon() {
		return t_BlockList.get(CBID).isBeacon();
	}

	boolean isCBHeated() {
		return t_BlockList.get(CBID).isHeated();
	}

	boolean isCBSwitch() {
		return t_BlockList.get(CBID).isSwitch();
	}

	//: Create 3 methods for returning Failure status
	public boolean isCBFailRail() {
		return t_BlockList.get(CBID).isFailRail();
	}

	public boolean isCBFailCircuit() {
		return t_BlockList.get(CBID).isFailCircuit();
	}

	public boolean isCBFailPower() {
		return t_BlockList.get(CBID).isFailPower();
	}
	
	
	//: Create 3 methods for toggeling Failure status
	public void toggleCBFailRail() {
		t_BlockList.get(CBID).toggleFailRail();
	}

	public void toggleCBFailCircuit() {
		t_BlockList.get(CBID).toggleFailCircuit();
	}

	public void toggleCBFailPower() {
		t_BlockList.get(CBID).toggleFailPower();
	}
	
	public ArrayList<String> getBlockNameList() {
		ArrayList<String> blockList = new ArrayList<String>();
		for (TrackBlock block : t_BlockList) {
			blockList.add(block.getName());
		}
		return blockList;
	}

	//NOTE: This method assumes the block id is based on block number
	public void setCB(int newCB) {
		CBID = newCB - 1;
	}


	//: Create method to return list of blocks
	public ArrayList<TrackBlock> getBlockList() {
		return t_BlockList;
	}
	
	//TODO: Create method to calculate train GPS coords and return the next block
	
	
	//TODO: Create a method to just calculate train GPS coords and return them
	
	//: Create a method to return a list of trains
	public ArrayList<TrackTrain> getTrainList() {
		return t_TrainList;
	}
	
	//: Create a method to make a new train
	public boolean dispatchTrain() {
		TrackTrain train = new TrackTrain(1, 150, 60);
		t_TrainList.add(train);
		
		t_BlockList.get(0).setOccupied();
		
		TrainModelSingleton trnModSin = TrainModelSingleton.getInstance();
		
		trnModSin.makeTrain(1, 150, 60, t_BlockList.get(0), t_BlockList.get(1));
		
		return true;
		
	}
	
	//: Create method to return blocklist (arraylist of booleans)

	//: Return random ticket sales
	public ArrayList<Integer> getTickets(){
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(100);
		list.add(200);
		return list;
	}

	@Override
	public TrackTrain getTrainLocation(double distance_traveled) {
		TrackTrain train = t_TrainList.get(0);
		train.changeCoord(train.getX(), train.getY() + distance_traveled);
		return train;
	}
}
