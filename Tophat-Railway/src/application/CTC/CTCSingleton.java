package application.CTC;
import application.TrackModel.*;
import java.util.*;
import java.util.stream.IntStream;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.Map.Entry;
import application.MBO.MBOSingleton;
import application.TrackController.TrackControllerSingleton;
import application.TrackModel.TrackModelSingleton;
import application.TrainController.TrainControllerSingleton;
import application.TrainModel.TrainModelSingleton;

public class CTCSingleton {

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
	private Map<Integer,Train> trains =new HashMap<Integer,Train>();
	private ArrayList<Schedule> myschedule=new ArrayList<Schedule>();
	private String[] Stations=TrackModelSingleton.getInstance().getBlockNameList().stream().toArray(String[]::new);
	private int[] blocks=new int[Stations.length];
	private int[] distance=new int[Stations.length];
	// NOTE: Put some functions here
	public String[] getStations(){
		return Stations;
	}
	public int[] getBlocks(){
		for (int i=0;i<blocks.length;i++){
			blocks[i]=1;
		}
		return blocks;
	}
	public int[] getDistance(){
		TrackBlock[] tmp=TrackModelSingleton.getInstance().getBlockList().stream().toArray(TrackBlock[]::new);
		for (int i=0;i<distance.length;i++){
			distance[i]=(int)tmp[i].getLength();
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
			return false;
		}
		trains.put(Integer.valueOf(ID), new Train(Integer.parseInt(ID), Integer.parseInt(Speed)));
		return true;
		
	}
	public boolean addSchedule(int ID, String myLine, String[] myStation, Integer[] mydistance, int myDeparturetime, int suggestedSpeed){
		Schedule tmp=new Schedule(ID, myLine, myStation,mydistance,myDeparturetime,suggestedSpeed);
		myschedule.add(tmp);
		return true;
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

}

class Schedule{
	private int ID;
	private String Line;
	private String[] Station;
	private int Departuretime;
	private int[] ArrivalTime;
	private int[] LeaveTime;
	private int[] distance;
	private int speed;
	Schedule(int TrainID, String myLine, String[] myStation, Integer[] myDistance,int myDeparturetime,int suggestedSpeed){
		ID=TrainID;
		Line=myLine;
		Station=myStation;
		distance=Arrays.stream(myDistance).mapToInt(Integer::intValue).toArray();
		Departuretime=myDeparturetime;
		speed=suggestedSpeed;
		ArrivalTime=new int[myDistance.length+1];
		LeaveTime=new int[myDistance.length+1];
		ArrivalTime[0]=Departuretime-30*60;
		LeaveTime[0]=Departuretime;
		for (int i=1;i<ArrivalTime.length;i++){
			ArrivalTime[i]=LeaveTime[i-1]+distance[i-1]/speed;
			LeaveTime[i]=ArrivalTime[i]+5*60;
		}
	}
	public ArrayList<String> printschedule(){
		
		ArrayList<String> tmp=new ArrayList<String>();
		tmp.add(String.valueOf(ID));
		tmp.add("	"+Line);
		for (int i=0;i<Station.length;i++){
			tmp.add("	"+Station[i]+" "+"Arrival at "+ArrivalTime[i]+". Leave at "+LeaveTime[i]);//TODO 
		}
		return tmp;
	}
	public int getID(){
		return ID;
	}
	public String getLine(){
		return Line;
	}
	public String[] getStation(){
		return Station;
	}
	public int[] getArrivalTime(){
		return ArrivalTime;
	}
	public int[] getLeaveTime(){
		return LeaveTime;
	}
}
