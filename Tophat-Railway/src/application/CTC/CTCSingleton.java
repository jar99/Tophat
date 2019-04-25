package application.CTC;
import application.TrackModel.*;
import application.ClockSingleton;
import java.util.*;
import java.util.stream.IntStream;

import com.sun.media.jfxmedia.track.Track;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Map.Entry;

import application.ClockSingleton;
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
		for (boolean lala:isSectionClose){
			lala=true;
		}
		return Sections;
	}
	public String[] getOnlyStations() {
		TrackModelInterface aTest = TrackModelSingleton.getInstance();
		for(String key:track.keySet()) {
			TrackLine tmp=track.get(key);
			Collection<TrackStation> myStation=tmp.getStations();
			int mySize=myStation.size();
			RealStations=new String[mySize+1];
			int i=0;
			for (TrackStation astation:myStation){
				RealStations[i+1]=astation.getStationName()+" Alighting:"+aTest.getScheduledAlighting("green",astation.getStationName())+" Boarding:"+aTest.getScheduledBoarding("green",astation.getStationName());
				i++;
			}
		}
		if (!track.isEmpty()) {
			ClockSingleton myClock=ClockSingleton.getInstance();
			int myTime=myClock.getCurrentTimeHours()*3600+myClock.getCurrentTimeMinutes()*60+myClock.getCurrentTimeSeconds();
			double myHour=(double)myTime/3600.0;
			RealStations[0]="Total throughput: "+aTest.getTotalBoarders("green")/myHour;
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
			TrackControllerInterface TCInterface=TrackControllerSingleton.getInstance();
			TCInterface.createTrain("green",ID);
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
			int ID=value.getID();
			tmp.add(String.valueOf(ID));
			if (myschedule.get(ID).getIfSpdLmt())
				tmp.add("	Suggested Speed is the max speed: "+(int)(myschedule.get(ID).getSpdlmt()/0.5));
			else
				tmp.add("	Suggested Speed is the suggested speed: "+myschedule.get(ID).getspdprint());
			//tmp.add("	Current Position:"+ CurrentPosition);
			//tmp.add("	Authority: "+myschedule.get(ID).getAuthority());
		}
		return tmp;
	}
	public void ModifyTrain(Integer ID, int Authority, int Speed){
		Train tmp=trains.get(ID);
		if(Speed*0.448>myschedule.get(ID).getSpdlmt()) {
			myschedule.get(ID).setIfLspdlmt(true);
		}
		else {
			myschedule.get(ID).setIfLspdlmt(false);
			myschedule.get(ID).setSpeed((int)(Speed*0.448));
			myschedule.get(ID).setspdprint(Speed);
		}
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
	public HashMap<String, TrackLine> viewTrack(){
		return track;
	}
	public String viewLine(int ID) {
		return myschedule.get(ID).getLine();
	}
	public String[] switchstuff() {
		Collection<TrackSwitch> switchIDs = new TreeSet<TrackSwitch>();
		for(String key:track.keySet()) {
			TrackLine tmp=track.get(key);
			switchIDs=tmp.getSwitches();
		}
		String[] result=new String[switchIDs.size()];
		int a=0;
		for (TrackSwitch i:switchIDs) {
			result[a]="switch ID:"+i.getSwitchID();
			a++;
		}
		return result;
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

	@Override
	public boolean getSectionMaintenance(String lineName, int blockID) {
		// TODO Implement function to tell Track Controller what blocks need maintenance
		if (blockID==1||blockID==2||blockID==3)
			return isSectionClose[0];
		else if (blockID==4||blockID==5||blockID==6)
			return isSectionClose[1];
		else if (blockID>=7&&blockID<=12)
			return isSectionClose[2];
		else if (blockID>=13&&blockID<=16)
			return isSectionClose[3];
		else if (blockID>=17&&blockID<=20)
			return isSectionClose[4];
		else if (blockID>=21&&blockID<=28)
			return isSectionClose[5];
		else if (blockID>=29&&blockID<=32)
			return isSectionClose[6];
		else if (blockID>=33&&blockID<=35)
			return isSectionClose[7];
		else if (blockID>=36&&blockID<=57)
			return isSectionClose[8];
		else if (blockID>=58&&blockID<=62)
			return isSectionClose[9];
		else if (blockID>=63&&blockID<=68)
			return isSectionClose[10];
		else if (blockID>=69&&blockID<=73)
			return isSectionClose[11];
		else if (blockID>=74&&blockID<=76)
			return isSectionClose[12];
		else if (blockID>=77&&blockID<=85)
			return isSectionClose[13];
		else if (blockID>=86&&blockID<=88)
			return isSectionClose[14];
		else if (blockID>=89&&blockID<=97)
			return isSectionClose[15];
		else if (blockID>=98&&blockID<=100)
			return isSectionClose[16];
		else if (blockID>=101&&blockID<=101)
			return isSectionClose[17];
		else if (blockID>=102&&blockID<=104)
			return isSectionClose[18];
		else if (blockID>=105&&blockID<=109)
			return isSectionClose[19];
		else if (blockID>=110&&blockID<=116)
			return isSectionClose[20];
		else if (blockID>=117&&blockID<=121)
			return isSectionClose[21];
		else if (blockID>=122&&blockID<=143)
			return isSectionClose[22];
		else if (blockID>=144&&blockID<=146)
			return isSectionClose[23];
		else if (blockID>=147&&blockID<=149)
			return isSectionClose[24];
		else 
			return isSectionClose[25];
		
	}

}


