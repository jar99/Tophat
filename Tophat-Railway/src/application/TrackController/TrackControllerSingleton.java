package application.TrackController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import application.TrackModel.*;
import application.CTC.*;

import application.CTC.CTCSingleton;
import application.CTC.CTCInterface;
import application.MBO.MBOSingleton;
import application.TrainController.TrainControllerSingleton;
import application.TrainModel.TrainModelSingleton;

public class TrackControllerSingleton implements TrackControllerInterface {

	// Singleton Functions (NO TOUCHY!!)
	private static TrackControllerSingleton instance = null;

	//private ArrayList<TrackBlock> blockList = null;
	final private HashMap<String, TrackLine> track = new HashMap<String, TrackLine>();

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
	private int CBIDG1 = 1;
	private double speedA1 = 0;
	private int authorityA1 = 0;
	private int authorityA2 = 0;
	private boolean switchStraightG1;
	private boolean switchStraightG2;
	private boolean switchStraightG3;
	private boolean switchStraightG4;
	private boolean switchStraightG5;
	private boolean switchStraightG6;
	private int blockListG1[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
	private int g1Index = 0;
	private int blockListG2[] = {21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,144,145,146,147,148,149,150};
	private int g2Index = 0;
	private int blockListG3[] = {36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68};
	private int g3Index = 0;
	private int blockListG4[] = {69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104};
	private int g4Index = 0;
	private int blockList65[] = {105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143};
	private int g5Index = 0;

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


		// get map<Integer,Train>
		//Map<Integer, Train> trains = ctcModSin.viewtrains();

		/*
		 * Iterator<Entry<Integer, Train>> iterator = trains.entrySet().iterator();
		 * while (iterator.hasNext()) { Entry<Integer, Train> entry = iterator.next();
		 * Train value = entry.getValue(); speedA1 = value.getSuggestedSpeed();
		 * //blockList.get(0).setSpeed(speedA1); authorityA1 = value.getAuthority();
		 * authorityA2 = value.getAuthority()-1;
		 * blockList.get(0).setAuthority(authorityA1);
		 * 
		 * blockList.get(0).setControlAuthority(true); //ID = value.getID();
		 * 
		 * if (!sent_train) { //trackModSin.dispatchTrain(); sent_train = !sent_train; }
		 * 
		 * }
		 */

	}

	void shiftBlockLeftG1() {
		if (g1Index == 0)
		{
			CBIDG1 = blockListG1[blockListG1.length-1];
			g1Index = blockListG1.length;
		}
		else
		{
			g1Index--;
			CBIDG1 = blockListG1[g1Index];
		}
	}

	void shiftBlockRightG1() {
		if (g1Index == blockListG1[blockListG1.length-1])
		{
			CBIDG1 = blockListG1[0];
			g1Index = 0;
		}
		else
		{
			g1Index++;
			CBIDG1 = blockListG1[g1Index];
		}
	}

	 String getCBNameG1() {
		if (track.isEmpty() == true)
			return "1";
		else
			return Integer.toString(track.get("green").getBlock(CBIDG1).getBlockID());

	}
	 
	 void setLightsOnG1() {
		 if (track.isEmpty())
			 throw new IllegalArgumentException("There is no line to set the lights.");
		 else if (CBIDG1 == 1 || CBIDG1 == 13) {
			 if (track.get("green").getSwitch(5).isSwitchStraight() && CBIDG1 == 1)
				 throw new IllegalArgumentException("Light can not be changed to green as the switch is in the wrong position.");
			 else if (!track.get("green").getSwitch(5).isSwitchStraight() && CBIDG1 == 13)
				 throw new IllegalArgumentException("Light can not be changed to green as the switch is in the wrong position.");
			 else {
				 track.get("green").getBlock(CBIDG1).setGreen();
				 track.get("green").getBlock(CBIDG1).setControlAuthority(true);
			 }
		 }
		 else
			 throw new IllegalArgumentException("There are no lights on this block.");
	 }
	 
	 void setLightsOffG1() {
		 if (track.isEmpty())
			 throw new IllegalArgumentException("There is no line to set the lights.");
		 else if (CBIDG1 == 1 || CBIDG1 == 13) {
			 track.get("green").getBlock(CBIDG1).setRed();
			 track.get("green").getBlock(CBIDG1).setControlAuthority(false);
		 }
		 else
			 throw new IllegalArgumentException("There are no lights on this block.");
	 }
	 
	 void setCrossingOnG1() {
		 if(track.isEmpty())
			 throw new IllegalArgumentException("There is no line to set the crossings.");
		 else if (CBIDG1 == 19) {
			 //TODO: manually toggle a crossing
		 }
		 else
			 throw new IllegalArgumentException("There is no crossing on this block."); 
	 }

	 void setCrossingOffG1() {
		 if(track.isEmpty())
			 throw new IllegalArgumentException("There is no line to set the crossings.");
		 else if (CBIDG1 == 19) {
			 //TODO: manually toggle a crossing
		 }
		 else
			 throw new IllegalArgumentException("There is no crossing on this block."); 
	 }
	 
	 void setSwitchStraightG1() {
		 if(track.isEmpty())
			 throw new IllegalArgumentException("There is no line to set the switch.");
		 else if (CBIDG1 == 13) {
			 track.get("green").getSwitch(5).setSwitchStraight(true);
			 track.get("green").getBlock(13).setGreen();
			 track.get("green").getBlock(1).setRed();
		 }
		 else if (CBIDG1 == 1) {
			 throw new IllegalArgumentException("The switch can not be set straight from this block.");
		 }
		 else
			 throw new IllegalArgumentException("There is no switch connected to this block.");
	 }
	 
	 void setSwitchDivergeG1() {
		 if(track.isEmpty())
			 throw new IllegalArgumentException("There is no line to set the switch.");
		 else if (CBIDG1 == 13) {
			throw new IllegalArgumentException("The switch can not be set diverging from this block."); 
		 }
		 else if (CBIDG1 == 1) {
			 track.get("green").getSwitch(5).setSwitchStraight(false);
			 track.get("green").getBlock(1).setGreen();
			 track.get("green").getBlock(13).setRed();
		 }
		 else
			 throw new IllegalArgumentException("There is no switch connected to this block.");
	 }
	 
	/*
	 * Speed Limit: 55 mph in blocks 1-12, 86-101
	 * 60 mph in blocks 17-20, 58-62, 69-76, 117-121
	 * 70 mph in blocks 13-16, 21-57, 63-68, 77-85, 110-116, 122-150
	 * Suggested speed absolutely can NOT exceed speed limit in a block
	 */
	String getSpeed() {

		return Double.toString(speedA1);

	}

	String getAuthority() {
		if (CBIDG1 == 0) {
			return Integer.toString(authorityA1);
		}
		return Integer.toString(authorityA2);
	}
	
	int getCurrentBlockIDG1() {
		return CBIDG1;
	}
	
	void checkOccupancyG1() {
		for(g1Index = 0; g1Index < blockListG1.length; g1Index++) {
			if (track.get("green").getBlock(blockListG1[g1Index]).isOccupied()) {
				//TODO: figure out how to send occupancy to CTC
			}
		}
	}

	boolean isCBOccupied() {
		if (track.isEmpty() == true)
			return false;
		else
			return track.get("green").getBlock(CBIDG1).isOccupied();
	}

	boolean isLightGreen1() {
		if (track.isEmpty() == true)
			return false;
		else if (track.get("green").getBlock(1).isLightGreen() == true)
			return true;
		else
			return false;
	}
	
	boolean isLightGreen13() {
		if (track.isEmpty() == true)
			return false;
		else if (track.get("green").getBlock(13).isLightGreen() == true)
			return true;
		else
			return false;
	}
	
	
	boolean isTrackEmpty() {
		return track.isEmpty();
	}
	
	boolean isCrossingOnG1() {
		if (track.isEmpty() == true)
			return false;
		else if(track.get("green").getBlock(19).isOccupied())
			//TODO: implement crossing
			return true;
		else
			return false;
	}
	
	void setSwitchG5() {
		if (track.get("green").getBlock(13).isOccupied()) {
			track.get("green").getSwitch(5).setSwitchStraight(true);
			switchStraightG5 = true;
			track.get("green").getBlock(13).setGreen();
			track.get("green").getBlock(13).setControlAuthority(true);
			track.get("green").getBlock(1).setRed();
			track.get("green").getBlock(1).setControlAuthority(false);
		}
		if (track.get("green").getBlock(1).isOccupied()) {
			track.get("green").getSwitch(5).setSwitchStraight(false);
			switchStraightG5 = false;
			track.get("green").getBlock(1).setGreen();
			track.get("green").getBlock(1).setControlAuthority(true);
			track.get("green").getBlock(13).setRed();
			track.get("green").getBlock(13).setControlAuthority(false);
		}
		
	}
	
	boolean isSwitchG5Straight() {
		return switchStraightG5;
	}
	
	@Override
	public double setSuggestedSpeed(String lineName, int blockID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int setAuthority(String lineName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void importLine(TrackLine trackLine) {
		track.put(trackLine.getLineName(), trackLine);
	}

	@Override
	public void removeTrain(int trainID) {
		// TODO Auto-generated method stub
		
	}
}
