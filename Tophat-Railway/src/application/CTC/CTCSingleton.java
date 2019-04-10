package application.CTC;
import application.TrackModel.*;
import java.util.*;
import java.util.stream.IntStream;

import com.sun.media.jfxmedia.track.Track;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Map.Entry;
import application.MBO.MBOSingleton;
import application.TrackController.TrackControllerSingleton;
import application.TrackModel.TrackModelSingleton;
import application.TrainController.TrainControllerSingleton;
import application.TrainModel.TrainModelSingleton;
import application.TrackModel.TrackLine;
import application.TrackModel.*;
public class CTCSingleton implements CTCInterface {

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
	private HashMap<Integer,Train> trains =new HashMap<Integer,Train>();
	private ArrayList<Schedule> myschedule=new ArrayList<Schedule>();
	final private HashMap<String, TrackLine> track = new HashMap<String, TrackLine>();
	//private String[] Stations=TrackModelSingleton.getInstance().getBlockNameList().stream().toArray(String[]::new);
	//TODO: need a function, getBlockName, return array/list of strings of all block names and stations
	private String[] Stations={"B0","B1 FIXME","B2 StationA"};
	private int[] blocks=new int[Stations.length];
	private int[] distance=new int[Stations.length];
	// NOTE: Put some functions here
	public String[] getStations(){
		Stations=null;
		TrackLine tmp=track.get("Green");//TODO:fix later
		Stations=new String[150];
		for (int i=0;i<150;i++){
			Stations[i]="Block "+(i+1)+" StationnameFIXME";//+tmp.getBlock(i).getStationName();
		}
		Stations=new String[3];
		Stations[0]="B0";
		Stations[1]="B1 FIXME";
		Stations[2]="B2 StationA";
		blocks=new int[Stations.length];
		distance=new int[Stations.length];
		return Stations;
	}
	public int[] getBlocks(){
		for (int i=0;i<blocks.length;i++){
			blocks[i]=1;
		}
		return blocks;
	}
	public int[] getDistance(){
		/*TrackBlock[] tmp=TrackModelSingleton.getInstance().getBlockList().stream().toArray(TrackBlock[]::new);
		
		for (int i=0;i<distance.length;i++){
			distance[i]=(int)tmp[i].getLength();
		}*/

		//TODO need a function to return an array of int, the distance
		TrackLine tmp=track.get("Green");//TODO: fix later
		for (int i=0;i<distance.length;i++){
			//distance[i]=(int)tmp.getBlock(i).getLength();
			distance[i]=1000;//TODO:fix later
		} 
		return distance;
	}
	public boolean addTrain(String ID,String Speed){
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum1 = pattern.matcher(ID);
		Matcher isNum2 = pattern.matcher(Speed);
		if((!isNum1.matches())||(!isNum2.matches())){
			System.out.println("you should input an integer for ID and Speed!!");//TODO change this into UI
			return false;
		}
		if (trains.containsKey(Integer.valueOf(ID))){
			System.out.println("Duplicated train ID!!");//TODO change this into UI
		}
		trains.put(Integer.valueOf(ID), new Train(Integer.parseInt(ID), Integer.parseInt(Speed)));
		return true;
		
	}
	public boolean addSchedule(int ID, String myLine, String[] myStation, Integer[] mydistance, int myDeparturetime, int suggestedSpeed){
		boolean flag=false;
		for (Schedule m:myschedule) {
			if (m.getID()==ID) {
				flag=true;
			}
		}
		if (!flag){
			Schedule tmp=new Schedule(ID, myLine, myStation,mydistance,myDeparturetime,suggestedSpeed);
			myschedule.add(tmp);
			return true;
		}
		else{
			Schedule tmp1=myschedule.get(ID);
			Schedule tmp2=new Schedule(ID, myLine, myStation,mydistance,myDeparturetime,suggestedSpeed);
			tmp1.mergeSchedule(tmp2);
			return true;
		}
		
	}
	public ArrayList<String> tolist(){
		ArrayList<String> tmp=new ArrayList<String>();
		for (Schedule s:myschedule){
			tmp.addAll(s.printschedule());
		}
		return tmp;
	}
	public ArrayList<String> tolistTrains(){
		ArrayList<String> tmp=new ArrayList<String>();
		Iterator<Entry<Integer, Train>> iterator = trains.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<Integer, Train> entry = iterator.next();
			Train value = entry.getValue();
			tmp.addAll(value.printTrain());
		}
		return tmp;
	}
	public void ModifyTrain(Integer ID, int Authority, int Speed){
		Train tmp=trains.get(ID);
		tmp.set2(Speed, Authority);
	}
	public Map<Integer,Train> viewtrains(){
		return trains;
	}
	public ArrayList<Schedule> viewSchedule(){
		return myschedule;
	}

	// NOTE: Singleton Connections (Put changes reads, gets, sets that you want to
	// occur here)
	// WARNING: This Only changes the singleton, not your UI. UI updates occur in
	// your UI controller
	public void update() {
		/*
		MBOSingleton mboModSin=MBOSingleton.getInstance();
		TrackModelSingleton tkmModSin=TrackModelSingleton.getInstance();
		TrackControllerSingletom TkCtrlSin=TrackControllerSingletom.getInstance();

		*/


	}
	@Override
	public void importLine(TrackLine trackLine) {
		track.put(trackLine.getLineName(), trackLine);
	}

}