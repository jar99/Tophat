package application.TrackController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
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
	private int CBIDG2 = 21;
	private int CBIDG3 = 36;
	private int CBIDG4 = 69;
	private int CBIDG5 = 105;
	private double suggestedSpeed = 0;
	private int authority = 0;
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
	private int blockListG5[] = {105,106,107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143};
	private int g5Index = 0;
	private int trainID = 0;
	private double[] blockListSpeed = new double[200];
	private int[] blockListAuthority = new int[200];
	private int[] storedTrainID = new int [50];
	private int storedTrainIDIndex = 0;
	
	final private Hashtable<Integer, TrainAuthority> trainAuthority = new Hashtable<Integer, TrainAuthority>();
	
	// NOTE: Put some functions here

	// NOTE: Singleton Connections (Put changes reads, gets, sets that you want to
	// occur here)
	// WARNING: This Only changes the singleton, not your UI. UI updates occur in
	// your UI controller

	boolean sent_train = false;

	public void update() {
		// Example: get the count from a singleton and replace yours with the largest
		CTCSingleton ctcModSin = CTCSingleton.getInstance();
		TrackModelInterface trackModInt = TrackModelSingleton.getInstance();
		
		for(TrackLine line : track.values()) {
			for(TrackBlock block : line.getBlocks()) {
				try {
					block.setOccupied(trackModInt.getOccupancy(line.getLineName(), block.getBlockID()));
					if(block.isFailCircuit())
						block.toggleFailCircuit();
				} catch (TrackCircuitFailureException e) {
					if(!block.isFailCircuit())
						block.toggleFailCircuit();
					e.printStackTrace();
				}
			}
		}

		//get map<Integer,Train> 
//		Map<Integer, Train> trains = ctcModSin.viewtrains();
//
//		
//		 Iterator<Entry<Integer, Train>> iterator = trains.entrySet().iterator();
//		 while (iterator.hasNext()) { 
//			 Entry<Integer, Train> entry = iterator.next();
//			 Train value = entry.getValue(); 
//			 suggestedSpeed = value.getSuggestedSpeed();
//			 trainID = value.getID();
//			 authority = value.getAuthority();
//			 if(authority > 63) { 
//				 for(int i = 62; i < authority; i++) {
//				 	blockListAuthority[i] = authority - i;
//				 	blockListSpeed[i] = suggestedSpeed;
//				 	track.get("green").getBlock(i+1).setSuggestedSpeed(blockListSpeed[i]);
//				 	track.get("green").getBlock(i+1).setAuthority(blockListAuthority[i]);
//				 	track.get("green").getBlock(i+1).setControlAuthority(true);
//			 	}
//			 }
//			 if(authority < 63) {
//				 for(int i = 62; i < 150; i++) {
//					 blockListAuthority[i] = 150-i+authority;
//					 blockListSpeed[i] = suggestedSpeed;
//					 track.get("green").getBlock(i+1).setSuggestedSpeed(blockListSpeed[i]);
//					 track.get("green").getBlock(i+1).setAuthority(blockListAuthority[i]);
//					 track.get("green").getBlock(i+1).setControlAuthority(true);
//
//				 }
//				 for(int i = 0; i < authority; i++) {
//					 blockListAuthority[i] = authority-i;
//					 blockListSpeed[i] = suggestedSpeed;
//					 track.get("green").getBlock(i+1).setSuggestedSpeed(blockListSpeed[i]);
//					 track.get("green").getBlock(i+1).setAuthority(blockListAuthority[i]);
//					 track.get("green").getBlock(i+1).setControlAuthority(true);
//				 }
//			 }
//		  
//		  if (!sent_train) { try {
//			trackModSin.createTrain("green", trainID);
//		} catch (SwitchStateException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} sent_train = !sent_train; }
//		  
//		 }
//		 

	}

	void shiftBlockLeftG1() {
		if (g1Index == 0)
		{
			CBIDG1 = blockListG1[blockListG1.length-1];
			g1Index = blockListG1.length-1;
		}
		else
		{
			g1Index--;
			CBIDG1 = blockListG1[g1Index];
		}
	}

	void shiftBlockRightG1() {
		if (g1Index == blockListG1.length-1)
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
		 if (CBIDG1 == 1 || CBIDG1 == 13) {
			 if (track.get("green").getSwitch(5).isSwitchStraight() && CBIDG1 == 1)
				 throw new IllegalArgumentException("Light can not be changed to green as the switch is in the wrong position.");
			 if (!track.get("green").getSwitch(5).isSwitchStraight() && CBIDG1 == 13)
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
		 if (CBIDG1 == 19) {
			 //TODO: manually toggle a crossing
		 }
		 else
			 throw new IllegalArgumentException("There is no crossing on this block."); 
	 }

	 void setCrossingOffG1() {
		 if(track.isEmpty())
			 throw new IllegalArgumentException("There is no line to set the crossings.");
		 if (CBIDG1 == 19) {
			 //TODO: manually toggle a crossing
		 }
		 else
			 throw new IllegalArgumentException("There is no crossing on this block."); 
	 }
	 
	 void setSwitchStraightG1() {
		 if(track.isEmpty())
			 throw new IllegalArgumentException("There is no line to set the switch.");
		 if (CBIDG1 == 13) {
			 track.get("green").getSwitch(5).setSwitchStraight(true);
			 track.get("green").getBlock(13).setGreen();
			 track.get("green").getBlock(1).setRed();
			 track.get("green").getBlock(13).setControlAuthority(true);
			 track.get("green").getBlock(1).setControlAuthority(false);
			 switchStraightG5 = true;
		 }
		 if (CBIDG1 == 1) {
			 throw new IllegalArgumentException("The switch can not be set straight from this block.");
		 }
		 else
			 throw new IllegalArgumentException("There is no switch connected to this block.");
	 }
	 
	 void setSwitchDivergeG1() {
		 if(track.isEmpty())
			 throw new IllegalArgumentException("There is no line to set the switch.");
		 if (CBIDG1 == 13) {
			throw new IllegalArgumentException("The switch can not be set diverging from this block."); 
		 }
		 if (CBIDG1 == 1) {
			 track.get("green").getSwitch(5).setSwitchStraight(false);
			 track.get("green").getBlock(1).setGreen();
			 track.get("green").getBlock(13).setRed();
			 track.get("green").getBlock(1).setControlAuthority(true);
			 track.get("green").getBlock(13).setControlAuthority(false);
			 switchStraightG5 = false;
		 }
		 else
			 throw new IllegalArgumentException("There is no switch connected to this block.");
	 }
	 
	/*
	 * Speed Limit: 55 mph (24.5872) in blocks 1-12, 86-101
	 * 60 mph (26.8224) in blocks 17-20, 58-62, 69-76, 117-121
	 * 70 mph (31.2928) in blocks 13-16, 21-57, 63-68, 77-85, 110-116, 122-150
	 * Suggested speed absolutely can NOT exceed speed limit in a block
	 */
	void checkSuggestedSpeed() {
		for(int i = 0; i < 150; i++) {
			if((i >= 0 && i <= 12) || (i >= 85 && i <= 100)) {
				if(blockListSpeed[i] > 55)
					throw new IllegalArgumentException("Suggested speed is too high for the block: " +(i+1));
			}
			if((i >= 16 && i <= 19) || (i >= 57 && i <= 61) || (i >= 68 && i <= 75) || (i >= 116 && i <= 120)) {
				if(blockListSpeed[i] > 60)
					throw new IllegalArgumentException("Suggested speed is too high for the block: " +(i+1));
			}
			if((i >= 12 && i <= 15) || (i >= 20 && i <= 56) || (i >= 62 && i <= 67) || (i >= 76 && i <= 84) || (i >= 109 && i <= 115) || (i >= 121 || i <= 149)) {
				if(blockListSpeed[i] > 70)
					throw new IllegalArgumentException("Suggested speed is too high for the block: " +(i+1));
			}
		}
	}
	
	String getSpeed(int controllerID, int blockID) {
		if(track.isEmpty())
			return null;
		if (controllerID == 1) {
			return Double.toString(track.get("green").getBlock(blockID).getSuggestedSpeed());
		}
		if (controllerID == 2) {
			return Double.toString(track.get("green").getBlock(blockID).getSuggestedSpeed());
		}
		if (controllerID == 3) {
			return Double.toString(track.get("green").getBlock(blockID).getSuggestedSpeed());
		}
		if (controllerID == 4) {
			return Double.toString(track.get("green").getBlock(blockID).getSuggestedSpeed());
		}
		if (controllerID == 5) {
			return Double.toString(track.get("green").getBlock(blockID).getSuggestedSpeed());
		}
		else
			return null;
	}

	String getAuthority(int controllerID, int blockID) {
		if(track.isEmpty())
			return null;
		if(controllerID == 1) {
			return Integer.toString(track.get("green").getBlock(blockID).getAuthority());
		}
		if(controllerID == 2) {
			return Integer.toString(track.get("green").getBlock(blockID).getAuthority());
		}
		if(controllerID == 3) {
			return Integer.toString(track.get("green").getBlock(blockID).getAuthority());
		}
		if(controllerID == 4) {
			return Integer.toString(track.get("green").getBlock(blockID).getAuthority());
		}
		if(controllerID == 5) {
			return Integer.toString(track.get("green").getBlock(blockID).getAuthority());
		}
		else
			return null;
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

	boolean isCBOccupied(int controllerID) {
		TrackModelInterface trackModInt = TrackModelSingleton.getInstance();
		if (track.isEmpty() == true)
			return false;
		if(controllerID == 1) {
			try {
				return trackModInt.getOccupancy("green", CBIDG1);
			} catch (TrackCircuitFailureException e) {
				return true;
			}
		}
		if(controllerID == 2) {
			try {
				return trackModInt.getOccupancy("green", CBIDG2);
			} catch (TrackCircuitFailureException e) {
				return true;
			}
		}
		if(controllerID == 3) {
			try {
				return trackModInt.getOccupancy("green", CBIDG3);
			} catch (TrackCircuitFailureException e) {
				return true;
			}
		}
		if(controllerID == 4) {
			try {
				return trackModInt.getOccupancy("green", CBIDG4);
			} catch (TrackCircuitFailureException e) {
				return true;
			}
		}
		if(controllerID == 5) {
			try {
				return trackModInt.getOccupancy("green", CBIDG5);
			} catch (TrackCircuitFailureException e) {
				return true;
			}
		}
		else
			return false;
	}

	boolean isLightGreen1() {
		if (track.isEmpty() == true)
			return false;
		if (track.get("green").getBlock(1).isLightGreen() == true)
			return true;
		else
			return false;
	}
	
	boolean isLightGreen13() {
		if (track.isEmpty() == true)
			return false;
		if (track.get("green").getBlock(13).isLightGreen() == true)
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
		if(track.get("green").getBlock(19).isOccupied())
			//TODO: implement crossing
			return true;
		else
			return false;
	}
	
	void setSwitchG5() {
		TrackModelInterface trackModInt = TrackModelSingleton.getInstance();
		if (track.isEmpty() == true)
			return;
		try {
			if (trackModInt.getOccupancy("green", 12)) {
				trackModInt.setSwitch("green", 5, true);
				try {
					trackModInt.setLightStatus("green", 13, true);
				} catch (TrackPowerFailureException e) {
					trackModInt.setControlAuthority("green", 13, false);
				}
				trackModInt.setControlAuthority("green", 13, true);
				try {
					trackModInt.setLightStatus("green", 1, false);
				} catch (TrackPowerFailureException e) {
					trackModInt.setControlAuthority("green", 1, false);
				}
				trackModInt.setControlAuthority("green", 1, false);
				switchStraightG5 = true;
			}
		} catch (TrackCircuitFailureException e) {
			e.printStackTrace();
			return;
		}
		try {
			if (trackModInt.getOccupancy("green", 2)) {
				trackModInt.setSwitch("green", 5, false);
				try {
					trackModInt.setLightStatus("green", 1, true);
				} catch (TrackPowerFailureException e) {
					trackModInt.setControlAuthority("green", 1, false);
				}
				trackModInt.setControlAuthority("green", 1, true);
				try {
					trackModInt.setLightStatus("green", 13, false);
				} catch (TrackPowerFailureException e) {
					trackModInt.setControlAuthority("green", 13, false);
				}
				trackModInt.setControlAuthority("green", 13, false);
				switchStraightG5 = false;
			}
		} catch (TrackCircuitFailureException e) {
			e.printStackTrace();
			return; }
	}
	
	boolean isSwitchG5Straight() {
		return switchStraightG5;
	}
	

	@Override
	public void importLine(TrackLine trackLine) {
		track.put(trackLine.getLineName(), trackLine);
	}


	void shiftBlockLeftG2() {
		if (g2Index == 0)
		{
			CBIDG2 = blockListG2[blockListG2.length-1];
			g2Index = blockListG2.length-1;
		}
		else
		{
			g2Index--;
			CBIDG2 = blockListG2[g2Index];
		}
		
	}

	void shiftBlockRightG2() {
		if (g2Index == blockListG2.length-1)
		{
			CBIDG2 = blockListG2[0];
			g2Index = 0;
		}
		else
		{
			g2Index++;
			CBIDG2 = blockListG2[g2Index];
		}
		
	}

	void setLightsOnG2() {
		if (track.isEmpty())
			 throw new IllegalArgumentException("There is no line to set the lights.");
		 if (CBIDG2 == 28 || CBIDG2 == 150) {
			 if (track.get("green").getSwitch(4).isSwitchStraight() && CBIDG2 == 150)
				 throw new IllegalArgumentException("Light can not be changed to green as the switch is in the wrong position.");
			 if (!track.get("green").getSwitch(4).isSwitchStraight() && CBIDG2 == 28)
				 throw new IllegalArgumentException("Light can not be changed to green as the switch is in the wrong position.");
			 else {
				 track.get("green").getBlock(CBIDG2).setGreen();
				 track.get("green").getBlock(CBIDG2).setControlAuthority(true);
			 }
		 }
		 else
			 throw new IllegalArgumentException("There are no lights on this block.");
		
	}

	void setLightsOffG2() {
		if (track.isEmpty())
			 throw new IllegalArgumentException("There is no line to set the lights.");
		 if (CBIDG2 == 28 || CBIDG2 == 150) {
			 track.get("green").getBlock(CBIDG2).setRed();
			 track.get("green").getBlock(CBIDG2).setControlAuthority(false);
		 }
		 else
			 throw new IllegalArgumentException("There are no lights on this block.");
		
	}

	void setCrossingOnG2() {
		throw new IllegalArgumentException("There is no crossing on this block.");
		
	}

	void setCrossingOffG2() {
		throw new IllegalArgumentException("There is no crossing on this block.");		
	}

	void setSwitchStraightG2() {
		if(track.isEmpty())
			 throw new IllegalArgumentException("There is no line to set the switch.");
		 if (CBIDG2 == 28) {
			 track.get("green").getSwitch(4).setSwitchStraight(true);
			 track.get("green").getBlock(28).setGreen();
			 track.get("green").getBlock(150).setRed();
			 track.get("green").getBlock(28).setControlAuthority(true);
			 track.get("green").getBlock(150).setControlAuthority(false);
			 switchStraightG4 = true;
		 }
		 if (CBIDG2 == 150) {
			 throw new IllegalArgumentException("The switch can not be set straight from this block.");
		 }
		 else
			 throw new IllegalArgumentException("There is no switch connected to this block.");
		
	}

	void setSwitchDivergeG2() {
		if(track.isEmpty())
			 throw new IllegalArgumentException("There is no line to set the switch.");
		 if (CBIDG2 == 28) {
			throw new IllegalArgumentException("The switch can not be set diverging from this block."); 
		 }
		 if (CBIDG2 == 150) {
			 track.get("green").getSwitch(4).setSwitchStraight(false);
			 track.get("green").getBlock(150).setGreen();
			 track.get("green").getBlock(28).setRed();
			 track.get("green").getBlock(150).setControlAuthority(true);
			 track.get("green").getBlock(28).setControlAuthority(false);
			 switchStraightG4 = false;
		 }
		 else
			 throw new IllegalArgumentException("There is no switch connected to this block.");
		
	}

	String getCBNameG2() {
		if (track.isEmpty() == true)
			return "21";
		else
			return Integer.toString(track.get("green").getBlock(CBIDG2).getBlockID());
	}

	boolean isLightGreen28() {
		if (track.isEmpty() == true)
			return false;
		if (track.get("green").getBlock(28).isLightGreen() == true)
			return true;
		else
			return false;
	}

	boolean isLightGreen150() {
		if (track.isEmpty() == true)
			return false;
		if (track.get("green").getBlock(150).isLightGreen() == true)
			return true;
		else
			return false;
	}

	boolean isCrossingOnG2() {
		return false;
	}

	boolean isSwitchG4Straight() {
		return switchStraightG4;
	}

	int getCurrentBlockIDG2() {
		return CBIDG2;
	}
	
	void setSwitchG4() {
		TrackModelInterface trackModInt = TrackModelSingleton.getInstance();
		if (track.isEmpty() == true)
			return;
		try {
			if (trackModInt.getOccupancy("green", 27)) {
				trackModInt.setSwitch("green", 4, true);
				try {
					trackModInt.setLightStatus("green", 28, true);
				} catch (TrackPowerFailureException e) {
					trackModInt.setControlAuthority("green", 28, false);
				}
				trackModInt.setControlAuthority("green", 28, true);
				try {
					trackModInt.setLightStatus("green", 150, false);
				} catch (TrackPowerFailureException e) {
					trackModInt.setControlAuthority("green", 150, false);
				}
				trackModInt.setControlAuthority("green", 150, false);
				switchStraightG4 = true;
			}
		} catch (TrackCircuitFailureException e) {
			e.printStackTrace();
			return;
		}
		try {
			if (trackModInt.getOccupancy("green", 149)) {
				trackModInt.setSwitch("green", 4, false);
				try {
					trackModInt.setLightStatus("green", 150, true);
				} catch (TrackPowerFailureException e) {
					trackModInt.setControlAuthority("green", 150, false);
				}
				trackModInt.setControlAuthority("green", 150, true);
				try {
					trackModInt.setLightStatus("green", 28, false);
				} catch (TrackPowerFailureException e) {
					trackModInt.setControlAuthority("green", 28, false);
				}
				trackModInt.setControlAuthority("green", 28, false);
				switchStraightG4 = false;
			}
		} catch (TrackCircuitFailureException e) {
			e.printStackTrace();
			return;
		}
	}

	void shiftBlockLeftG3() {
		if (g3Index == 0)
		{
			CBIDG3 = blockListG3[blockListG3.length-1];
			g3Index = blockListG3.length-1;
		}
		else
		{
			g3Index--;
			CBIDG3 = blockListG3[g3Index];
		}
		
	}

	public void shiftBlockRightG3() {
		if (g3Index == blockListG3.length-1)
		{
			CBIDG3 = blockListG3[0];
			g3Index = 0;
		}
		else
		{
			g3Index++;
			CBIDG3 = blockListG3[g3Index];
		}
		
	}

	public void setLightsOnG3() {
		if (track.isEmpty())
			 throw new IllegalArgumentException("There is no line to set the lights.");
		if (CBIDG3 == 57 || CBIDG3 == 62) {
			 if (track.get("green").getSwitch(1).isSwitchStraight() && CBIDG3 == 62)
				 throw new IllegalArgumentException("Light can not be changed to green as the switch is in the wrong position.");
			 else {
				 track.get("green").getBlock(CBIDG3).setGreen();
				 track.get("green").getBlock(CBIDG3).setControlAuthority(true);
			 }
		 }
		 else
			 throw new IllegalArgumentException("There are no lights on this block.");
		
	}

	public void setLightsOffG3() {
		if (track.isEmpty())
			 throw new IllegalArgumentException("There is no line to set the lights.");
		 if (CBIDG3 == 57 || CBIDG3 == 62) {
			 track.get("green").getBlock(CBIDG3).setRed();
			 track.get("green").getBlock(CBIDG3).setControlAuthority(false);
		 }
		 else
			 throw new IllegalArgumentException("There are no lights on this block.");
		
	}

	public void setCrossingOnG3() {
		throw new IllegalArgumentException("There is no crossing on this block.");
		
	}

	public void setCrossingOffG3() {
		throw new IllegalArgumentException("There is no crossing on this block.");
		
	}

	public void setSwitchStraightG3() {
		if(track.isEmpty())
			 throw new IllegalArgumentException("There is no line to set the switch.");
		 if (CBIDG3 == 57) {
			 track.get("green").getSwitch(6).setSwitchStraight(true);
			 track.get("green").getBlock(57).setGreen();
			 track.get("green").getBlock(57).setControlAuthority(true);
			 switchStraightG1 = true;
		 }
		 if (CBIDG3 == 62) {
			 throw new IllegalArgumentException("The switch can not be set straight from this block.");
		 }
		 else
			 throw new IllegalArgumentException("There is no switch connected to this block.");
		
	}

	public void setSwitchDivergeG3() {
		if(track.isEmpty())
			 throw new IllegalArgumentException("There is no line to set the switch.");
		if (CBIDG3 == 57) {
			 track.get("green").getSwitch(6).setSwitchStraight(false);
			 track.get("green").getBlock(57).setGreen();	
			 track.get("green").getBlock(57).setControlAuthority(true);
			 switchStraightG1 = true;
			 }
		if(CBIDG2 == 62) {
			 track.get("green").getSwitch(1).setSwitchStraight(false);
			 track.get("green").getBlock(62).setGreen();
			 track.get("green").getBlock(62).setControlAuthority(true);
			 switchStraightG1 = false;
		 }
		else
			throw new IllegalArgumentException("There is no switch connected to this block.");
		
	}

	public String getCBNameG3() {
		if (track.isEmpty() == true)
			return "36";
		else
			return Integer.toString(track.get("green").getBlock(CBIDG3).getBlockID());
	}

	public boolean isLightGreen57() {
		if (track.isEmpty() == true)
			return false;
		if (track.get("green").getBlock(57).isLightGreen() == true)
			return true;
		else
			return false;
	}

	public boolean isLightGreen62() {
		if (track.isEmpty() == true)
			return false;
		if (track.get("green").getBlock(62).isLightGreen() == true)
			return true;
		else
			return false;
	}

	public boolean isCrossingOnG3() {
		return false;
	}

	public boolean isSwitchG6Straight() {
		return switchStraightG6;
	}

	public boolean isSwitchG1Straight() {
		return switchStraightG1;
	}

	public int getCurrentBlockIDG3() {
		return CBIDG3;
	}

	public void shiftBlockLeftG4() {
		if (g4Index == 0)
		{
			CBIDG4 = blockListG4[blockListG4.length-1];
			g4Index = blockListG4.length-1;
		}
		else
		{
			g4Index--;
			CBIDG4 = blockListG4[g4Index];
		}
		
	}

	public void shiftBlockRightG4() {
		if (g4Index == blockListG4.length-1)
		{
			CBIDG4 = blockListG4[0];
			g4Index = 0;
		}
		else
		{
			g4Index++;
			CBIDG4 = blockListG4[g4Index];
		}
		
	}

	public void setLightsOnG4() {
		if (track.isEmpty())
			 throw new IllegalArgumentException("There is no line to set the lights.");
		if (CBIDG4 == 76 || CBIDG4 == 77) {
			 if (track.get("green").getSwitch(2).isSwitchStraight() && CBIDG4 == 77)
				 throw new IllegalArgumentException("Light can not be changed to green as the switch is in the wrong position.");
			 if (!track.get("green").getSwitch(2).isSwitchStraight() && CBIDG4 == 76)
				 throw new IllegalArgumentException("Light can not be changed to green as the switch is in the wrong position.");
			 else {
				 track.get("green").getBlock(CBIDG4).setGreen();
				 track.get("green").getBlock(CBIDG4).setControlAuthority(true);
			 }
		 }
		if (CBIDG4 == 85 || CBIDG4 == 100) {
			 if (track.get("green").getSwitch(3).isSwitchStraight() && CBIDG4 == 100)
				 throw new IllegalArgumentException("Light can not be changed to green as the switch is in the wrong position.");
			 if (!track.get("green").getSwitch(3).isSwitchStraight() && CBIDG4 == 85)
				 throw new IllegalArgumentException("Light can not be changed to green as the switch is in the wrong position.");
			 else {
				 track.get("green").getBlock(CBIDG4).setGreen();
				 track.get("green").getBlock(CBIDG4).setControlAuthority(true);
			 }
		 }
		 else
			 throw new IllegalArgumentException("There are no lights on this block.");
		
	}

	public void setLightsOffG4() {
		if (track.isEmpty())
			 throw new IllegalArgumentException("There is no line to set the lights.");
		 if (CBIDG4 == 76 || CBIDG4 == 77 || CBIDG4 == 85 || CBIDG4 == 100) {
			 track.get("green").getBlock(CBIDG4).setRed();
			 track.get("green").getBlock(CBIDG4).setControlAuthority(false);
		 }
		 else
			 throw new IllegalArgumentException("There are no lights on this block.");
	}

	public void setCrossingOnG4() {
		throw new IllegalArgumentException("There is no crossing on this block.");		
	}

	public void setCrossingOffG4() {
		throw new IllegalArgumentException("There is no crossing on this block.");		
	}

	public void setSwitchStraightG4() {
		if(track.isEmpty())
			 throw new IllegalArgumentException("There is no line to set the switch.");
		 if (CBIDG4 == 76) {
			 track.get("green").getSwitch(2).setSwitchStraight(true);
			 track.get("green").getBlock(76).setGreen();
			 track.get("green").getBlock(77).setRed();
			 track.get("green").getBlock(76).setControlAuthority(true);
			 track.get("green").getBlock(77).setControlAuthority(false);
			 switchStraightG2 = true;
		 }
		 if (CBIDG4 == 77) {
			 throw new IllegalArgumentException("The switch can not be set straight from this block.");
		 }
		 if (CBIDG4 == 85) {
			 track.get("green").getSwitch(3).setSwitchStraight(true);
			 track.get("green").getBlock(85).setGreen();
			 track.get("green").getBlock(100).setRed();
			 track.get("green").getBlock(85).setControlAuthority(true);
			 track.get("green").getBlock(100).setControlAuthority(false);
			 switchStraightG3 = true;
		 }
		 if (CBIDG4 == 100) {
			 throw new IllegalArgumentException("The switch can not be set straight from this block.");
		 }
		 else
			 throw new IllegalArgumentException("There is no switch connected to this block.");
		
	}

	public void setSwitchDivergeG4() {
		if(track.isEmpty())
			 throw new IllegalArgumentException("There is no line to set the switch.");
		 if (CBIDG4 == 76) {
			throw new IllegalArgumentException("The switch can not be set diverging from this block."); 
		 }
		 if (CBIDG4 == 77) {
			 track.get("green").getSwitch(2).setSwitchStraight(false);
			 track.get("green").getBlock(77).setGreen();
			 track.get("green").getBlock(76).setRed();
			 track.get("green").getBlock(77).setControlAuthority(true);
			 track.get("green").getBlock(76).setControlAuthority(false);
			 switchStraightG2 = false;
		 }
		 if (CBIDG4 == 85) {
			throw new IllegalArgumentException("The switch can not be set diverging from this block."); 
		 }
		 if (CBIDG4 == 100) {
			 track.get("green").getSwitch(3).setSwitchStraight(false);
			 track.get("green").getBlock(100).setGreen();
			 track.get("green").getBlock(85).setRed();
			 track.get("green").getBlock(100).setControlAuthority(true);
			 track.get("green").getBlock(85).setControlAuthority(false);
			 switchStraightG3 = false;
		 }
		 else
			 throw new IllegalArgumentException("There is no switch connected to this block.");
		
	}

	public void setSwitchG2() {
		TrackModelInterface trackModInt = TrackModelSingleton.getInstance();
		if (track.isEmpty() == true)
			return;
		try {
			if (trackModInt.getOccupancy("green", 75)) {
				trackModInt.setSwitch("green", 2, true);
				try {
					trackModInt.setLightStatus("green", 76, true);
				} catch (TrackPowerFailureException e) {
					trackModInt.setControlAuthority("green", 76, false);
				}
				trackModInt.setControlAuthority("green", 76, true);
				try {
					trackModInt.setLightStatus("green", 77, false);
				} catch (TrackPowerFailureException e) {
					trackModInt.setControlAuthority("green", 77, false);
				}
				trackModInt.setControlAuthority("green", 77, false);
				switchStraightG2 = true;
			}
		} catch (TrackCircuitFailureException e) {
			e.printStackTrace();
			return;
		}
		try {
			if (trackModInt.getOccupancy("green", 78)) {
				trackModInt.setSwitch("green", 2, false);
				try {
					trackModInt.setLightStatus("green", 77, true);
				} catch (TrackPowerFailureException e) {
					trackModInt.setControlAuthority("green", 77, false);
				}
				trackModInt.setControlAuthority("green", 77, true);
				try {
					trackModInt.setLightStatus("green", 76, false);
				} catch (TrackPowerFailureException e) {
					trackModInt.setControlAuthority("green", 77, false);
				}
				trackModInt.setControlAuthority("green", 76, false);
				switchStraightG2 = false;
			}
		} catch (TrackCircuitFailureException e) {
			e.printStackTrace();
			return;
		}
	}

	public void setSwitchG3() {
		TrackModelInterface trackModInt = TrackModelSingleton.getInstance();
		if (track.isEmpty() == true)
			return;
		try {
			if (trackModInt.getOccupancy("green", 84)) {
				trackModInt.setSwitch("green", 3, true);
				try {
					trackModInt.setLightStatus("green", 85, true);
				} catch (TrackPowerFailureException e) {
					trackModInt.setControlAuthority("green", 85, false);
				}
				trackModInt.setControlAuthority("green", 85, true);
				try {
					trackModInt.setLightStatus("green", 100, false);
				} catch (TrackPowerFailureException e) {
					trackModInt.setControlAuthority("green", 100, false);
				}
				trackModInt.setControlAuthority("green", 100, false);
				switchStraightG3 = true;
			}
		} catch (TrackCircuitFailureException e) {
			e.printStackTrace();
			return;
		}
		try {
			if (trackModInt.getOccupancy("green", 99)) {
				trackModInt.setSwitch("green", 3, false);
				try {
					trackModInt.setLightStatus("green", 100, true);
				} catch (TrackPowerFailureException e) {
					trackModInt.setControlAuthority("green", 100, false);
				}
				trackModInt.setControlAuthority("green", 100, true);
				try {
					trackModInt.setLightStatus("green", 85, false);
				} catch (TrackPowerFailureException e) {
					trackModInt.setControlAuthority("green", 85, false);
				}
				trackModInt.setControlAuthority("green", 85, false);
				switchStraightG5 = false;
			}
		} catch (TrackCircuitFailureException e) {
			e.printStackTrace();
			return;
		}
	}

	public boolean isLightGreen76() {
		if (track.isEmpty() == true)
			return false;
		if (track.get("green").getBlock(76).isLightGreen() == true)
			return true;
		else
			return false;
	}

	public boolean isLightGreen77() {
		if (track.isEmpty() == true)
			return false;
		if (track.get("green").getBlock(77).isLightGreen() == true)
			return true;
		else
			return false;
	}

	public boolean isLightGreen85() {
		if (track.isEmpty() == true)
			return false;
		if (track.get("green").getBlock(85).isLightGreen() == true)
			return true;
		else
			return false;
	}

	public boolean isLightGreen100() {
		if (track.isEmpty() == true)
			return false;
		if (track.get("green").getBlock(100).isLightGreen() == true)
			return true;
		else
			return false;
	}

	public boolean isCrossingOnG4() {
		return false;
	}

	public boolean isSwitchG2Straight() {
		return switchStraightG2;
	}

	public boolean isSwitchG3Straight() {
		return switchStraightG3;
	}

	public int getCurrentBlockIDG4() {
		return CBIDG4;
	}

	public String getCBNameG4() {
		if (track.isEmpty() == true)
			return "69";
		else
			return Integer.toString(track.get("green").getBlock(CBIDG4).getBlockID());
	}

	public String getCBNameG5() {
		if (track.isEmpty() == true)
			return "105";
		else
			return Integer.toString(track.get("green").getBlock(CBIDG5).getBlockID());
	}

	public void shiftBlockLeftG5() {
		if (g5Index == 0)
		{
			CBIDG5 = blockListG5[blockListG5.length-1];
			g5Index = blockListG5.length-1;
		}
		else
		{
			g5Index--;
			CBIDG5 = blockListG5[g5Index];
		}
		
	}

	public void shiftBlockRightG5() {
		if (g5Index == blockListG5.length-1)
		{
			CBIDG5 = blockListG5[0];
			g5Index = 0;
		}
		else
		{
			g5Index++;
			CBIDG5 = blockListG5[g5Index];
		}
		
	}

	public int getCurrentBlockIDG5() {
		return CBIDG5;
	}
	
	@Override
	public void removeTrain(int trainID) {
		//TODO
	}

	@Override
	public void createTrain(String lineName, int trainID) {
		if(track.isEmpty())
			return;
		for(int i = 0; i < storedTrainID.length; i++) {
			if(trainID == storedTrainID[i])
				return;
		}
		TrainAuthority newTrain = new TrainAuthority(trainID, 63, 0);
		trainAuthority.put(trainID, newTrain);
		storedTrainID[storedTrainIDIndex] = trainID;
		storedTrainIDIndex++;
		TrackModelSingleton trackModSin = TrackModelSingleton.getInstance();
		if (!sent_train) { try {
			trackModSin.createTrain(lineName, trainID);
		} catch (SwitchStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} sent_train = !sent_train; }
		
	}

	@Override
	public void sendTrainToBlock(int trainID, int blockID, double suggestedSpeed) {
		TrackModelInterface trackModInt = TrackModelSingleton.getInstance();
		if(track.isEmpty())
			return;
		int blockIDOccupied = 63;
		for(TrackLine line : track.values()) {
			for(TrackBlock block : line.getBlocks()) {
				try {
					if(trackModInt.getOccupancy(line.getLineName(), block.getBlockID())) {
						blockIDOccupied = block.getBlockID();
						break;
					}
				} catch (TrackCircuitFailureException e) {
					e.printStackTrace();
					return;
				}
			}
		}
		
		trainAuthority.get(trainID).setAuthority(blockID);
		trainAuthority.get(trainID).setSuggestedSpeed(suggestedSpeed);
		trainAuthority.get(trainID).calculateBlockDisplacement(blockID, blockIDOccupied);
		int authorityDisplacement = trainAuthority.get(trainID).getAuthorityDisplacement();
		trainAuthority.get(trainID).setSuggestedSpeedEachBlock(suggestedSpeed, blockIDOccupied, authorityDisplacement);
		trainAuthority.get(trainID).setAuthorityEachBlock(blockID, blockIDOccupied, authorityDisplacement);
		
//		for(TrackLine line : track.values()) {
//			for(TrackBlock block : line.getBlocks()) {
//				try {
//					trackModInt.setAuthority(line.getLineName(), block.getBlockID(), authority);
//					if(block.isFailCircuit())
//						block.toggleFailCircuit();
//				} catch (TrackCircuitFailureException e) {
//					if(!block.isFailCircuit())
//						block.toggleFailCircuit();
//					e.printStackTrace();
//				}
//			}
//				
//		}
		for(TrackLine line: track.values()) {
			for(TrackBlock block : line.getBlocks()) {
				try {
					trackModInt.setSuggestedSpeed(line.getLineName(), block.getBlockID(), suggestedSpeed);
					if(block.isFailCircuit())
						block.toggleFailCircuit();
				} catch (TrackCircuitFailureException e) {
					if(!block.isFailCircuit())
						block.toggleFailCircuit();
					e.printStackTrace();
				}
			}
		}
	}

	
	@Override
	public boolean getOccupancyCTC(String lineName, int blockID) {
		TrackModelInterface trackModInt = TrackModelSingleton.getInstance();
		try {
			return trackModInt.getOccupancy(lineName, blockID);
		} catch (TrackCircuitFailureException e) {
			e.printStackTrace();
			return false;
		}
	}
	

	@Override
	public boolean isBlockBroken(String lineName, int blockID) {
		// TODO Write method to return if a block is broken.
		return false;
	}

	@Override
	public int getAuthorityCTC(String lineName, int blockID) {
		// TODO Auto-generated method stub
		return 0;
	}	
}
