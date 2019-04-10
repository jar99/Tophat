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
	private double blockListSpeed[] = {};
	
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


		//get map<Integer,Train> 
		Map<Integer, Train> trains = ctcModSin.viewtrains();

		
		 Iterator<Entry<Integer, Train>> iterator = trains.entrySet().iterator();
		 while (iterator.hasNext()) { 
			 Entry<Integer, Train> entry = iterator.next();
			 Train value = entry.getValue(); 
			 suggestedSpeed = value.getSuggestedSpeed();
		 trainID = value.getID();
		 authority = value.getAuthority();
		 for(int i = 0; i < authority; i++) {
			 //TODO fill authority
		 }
		  
		  if (!sent_train) { trackModSin.createTrain("green", trainID); sent_train = !sent_train; }
		  
		 }
		 

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
	 * Speed Limit: 55 mph in blocks 1-12, 86-101
	 * 60 mph in blocks 17-20, 58-62, 69-76, 117-121
	 * 70 mph in blocks 13-16, 21-57, 63-68, 77-85, 110-116, 122-150
	 * Suggested speed absolutely can NOT exceed speed limit in a block
	 */
	void setSuggestedSpeed(double suggestedSpeed, int blockID) {
		if (((blockID >= 1 && blockID <=12) || (blockID >= 86 && blockID <= 101))
				&& suggestedSpeed > 55)
			throw new IllegalArgumentException("The suggested speed is over the speed limit for block: " + blockID);
		if (((blockID >= 17 && blockID <= 20)  || (blockID >= 58 && blockID <= 62)
				|| (blockID >= 69 && blockID <= 76) || (blockID >= 117 && blockID <= 121))
				&& suggestedSpeed > 60)
			throw new IllegalArgumentException("The suggested speed is over the speed limit for block: " + blockID);
		if (((blockID >= 13 && blockID <= 16)  || (blockID >= 21 && blockID <= 57)
				|| (blockID >= 63 && blockID <= 68) || (blockID >= 77 && blockID <= 85)
				|| (blockID >= 110 && blockID <= 116) || (blockID >= 122 && blockID <= 150))
				&& suggestedSpeed > 70)
			throw new IllegalArgumentException("The suggested speed is over the speed limit for block: " + blockID);
		else
			blockListSpeed[blockID-1] = suggestedSpeed;
	}
	
//	String getSpeed(int controllerID, int blockID) {
//		if (controllerID == 1) {
//			for (int i = 0; i < blockListG1.length; i++) {
//				if(i+1 == blockID)
//					return Double.toString(blockListSpeed[i]);
//			}
//		}
//		return Double.toString(suggestedSpeed);
//
//	}

//	String getAuthority() {
//		if (CBIDG1 == 0) {
//			return Integer.toString(authorityA1);
//		}
//		return Integer.toString(authorityA2);
//	}
	
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
		if (track.isEmpty() == true)
			return;
		if (track.get("green").getBlock(12).isOccupied()) {
			track.get("green").getSwitch(5).setSwitchStraight(true);
			switchStraightG5 = true;
			track.get("green").getBlock(13).setGreen();
			track.get("green").getBlock(13).setControlAuthority(true);
			track.get("green").getBlock(1).setRed();
			track.get("green").getBlock(1).setControlAuthority(false);
		}
		if (track.get("green").getBlock(2).isOccupied()) {
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
		if(track.isEmpty())
			return;
		if (track.get("green").getBlock(27).isOccupied()) {
			track.get("green").getSwitch(4).setSwitchStraight(true);
			switchStraightG4 = true;
			track.get("green").getBlock(28).setGreen();
			track.get("green").getBlock(28).setControlAuthority(true);
			track.get("green").getBlock(150).setRed();
			track.get("green").getBlock(150).setControlAuthority(false);
		}
		if (track.get("green").getBlock(149).isOccupied()) {
			track.get("green").getSwitch(4).setSwitchStraight(false);
			switchStraightG4 = false;
			track.get("green").getBlock(150).setGreen();
			track.get("green").getBlock(150).setControlAuthority(true);
			track.get("green").getBlock(28).setRed();
			track.get("green").getBlock(28).setControlAuthority(false);
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
		if(track.isEmpty())
			return;
		if (track.get("green").getBlock(75).isOccupied()) {
			track.get("green").getSwitch(2).setSwitchStraight(true);
			switchStraightG2 = true;
			track.get("green").getBlock(76).setGreen();
			track.get("green").getBlock(76).setControlAuthority(true);
			track.get("green").getBlock(77).setRed();
			track.get("green").getBlock(77).setControlAuthority(false);
		}
		if (track.get("green").getBlock(78).isOccupied()) {
			track.get("green").getSwitch(2).setSwitchStraight(false);
			switchStraightG2 = false;
			track.get("green").getBlock(77).setGreen();
			track.get("green").getBlock(77).setControlAuthority(true);
			track.get("green").getBlock(76).setRed();
			track.get("green").getBlock(76).setControlAuthority(false);
		}
	}

	public void setSwitchG3() {
		if(track.isEmpty())
			return;
		if (track.get("green").getBlock(84).isOccupied()) {
			track.get("green").getSwitch(3).setSwitchStraight(true);
			switchStraightG3 = true;
			track.get("green").getBlock(85).setGreen();
			track.get("green").getBlock(85).setControlAuthority(true);
			track.get("green").getBlock(100).setRed();
			track.get("green").getBlock(100).setControlAuthority(false);
		}
		if (track.get("green").getBlock(99).isOccupied()) {
			track.get("green").getSwitch(3).setSwitchStraight(false);
			switchStraightG3 = false;
			track.get("green").getBlock(100).setGreen();
			track.get("green").getBlock(100).setControlAuthority(true);
			track.get("green").getBlock(85).setRed();
			track.get("green").getBlock(85).setControlAuthority(false);
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
}
