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
import application.TrainController.TrainControllerSingleton;
import application.TrainModel.TrainModelSingleton;
import application.TrackModel.*;
import application.TrackController.*;
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
	private HashMap<Integer,Schedule> myschedule=new HashMap<Integer,Schedule>();
	private HashMap<String, TrackLine> track = new HashMap<String, TrackLine>();
	//private String[] Stations=TrackModelSingleton.getInstance().getBlockNameList().stream().toArray(String[]::new);
	//TODO: need a function, getBlockName, return array/list of strings of all block names and stations
	private String[] Stations={"B0","B1 FIXME","B2 StationA","B3"};
	private String[] Sections= {"A"};
	private String[] RealStations;
	private int[] blocks=new int[Stations.length];
	private int[] distance=new int[Stations.length];
	private boolean isSectionClose[];
	// NOTE: Put some functions here
	public String[] getStations(){
		Stations=new String[1];
		Stations[0]="";
		for(String key:track.keySet()) {
			TrackLine tmp=track.get(key);
			Collection<TrackBlock> myBlocks=tmp.getBlocks();
			int mySize=myBlocks.size()+1;
			Stations=new String[mySize];
			int i=0;
			for (TrackBlock block:myBlocks){
				String lala=block.getStationName();
				if (lala==null) lala="";
				Stations[i]="Block "+block.getBlockID()+" "+lala;
				i++;
			}
			Stations[mySize-1]="yard";
		}
		blocks=new int[Stations.length];
		distance=new int[Stations.length];
		return Stations;
	}
	public String[] getSections() {
		for(String key:track.keySet()) {
			TrackLine tmp=track.get(key);
			Collection<TrackSection> mySections=tmp.getSections();
			int mySize=mySections.size();
			Sections=new String[mySize];
			int i=0;
			for (TrackSection section:mySections){
				Sections[i]=String.valueOf(section.getSectionID());
				i++;
			}
		}
		isSectionClose=new boolean[Sections.length];
		return Sections;
	}
	public String[] getOnlyStations() {
		for(String key:track.keySet()) {
			TrackLine tmp=track.get(key);
			Collection<TrackStation> myStation=tmp.getStations();
			int mySize=myStation.size();
			RealStations=new String[mySize];
			int i=0;
			for (TrackStation astation:myStation){
				RealStations[i]=astation.getStationName()+" Alighting:"+astation.getAlighting()+" Boarding:"+astation.getBoarding();
				i++;
			}
		}
		return RealStations;
	}
	public int[] getBlocks(){
		for (int i=0;i<blocks.length;i++){
			blocks[i]=1;
		}
		return blocks;
	}
	public int[] getDistance(){
		for(String key:track.keySet()) {
			int i=0;
			TrackLine tmp=track.get(key);
			Collection<TrackBlock> myBlocks=tmp.getBlocks();
			for (TrackBlock block:myBlocks) {
				distance[distance.length-1-i]=(int)block.getLength();
				i++;
			}
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
		trains.put(Integer.valueOf(ID), new Train(Integer.parseInt(ID), (int)(Integer.parseInt(Speed)*0.448)));
		return true;
		
	}
	public boolean addSchedule(int ID, String myLine, String[] myStation, Integer[] mydistance, int myDeparturetime, int suggestedSpeed){
		boolean flag=false;
		if (myschedule.containsKey(ID)) flag=true;
		if (!flag){
			Schedule tmp=new Schedule(ID, myLine, myStation,mydistance,myDeparturetime,suggestedSpeed,track);
			myschedule.put(ID,tmp);
			return true;
		}
		else{
			Schedule tmp1=myschedule.get(ID);
			Schedule tmp2=new Schedule(ID, myLine, myStation,mydistance,myDeparturetime,suggestedSpeed,track);
			tmp1.mergeSchedule(tmp2);
			TrackControllerInterface TCInterface=TrackControllerSingleton.getInstance();
			TCInterface.createTrain("green",ID);
			return true;
		}
		
	}
	public ArrayList<String> tolist(){
		ArrayList<String> tmp=new ArrayList<String>();
		for (Integer key:myschedule.keySet()){
			tmp.addAll(myschedule.get(key).printschedule());
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
	public HashMap<Integer,Schedule> viewSchedule(){
		return myschedule;
	}
	public boolean[] getifSectionClose() {
		return isSectionClose;
	}
	public void openSection(int ID) {
		isSectionClose[ID]=false;
	}
	public void closeSection(int ID) {
		isSectionClose[ID]=true;
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