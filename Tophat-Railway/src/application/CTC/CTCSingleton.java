package application.CTC;
import java.util.*;
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
	// NOTE: Put some functions here
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
	public boolean addSchedule(int ID, String myLine, String[] myStation){
		Schedule tmp=new Schedule(ID, myLine, myStation);
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
class Train{
	private int ID;
	private int SuggestedSpeed;
	private int authority=0;
	private double CurrentSpeed=0;
	private double CurrentPosition=0;
	Train(int anID,int aSpeed){
		ID=anID;
		SuggestedSpeed=aSpeed;
	}
	public void set(double aSpeed, double aPosition){
		CurrentSpeed=aSpeed;
		CurrentPosition=aPosition;
	}
	public void set2(int speed, int anauthority){
		SuggestedSpeed=speed;
		authority=anauthority;
	}
	public int getID(){
		return ID;
	}
	public int getSuggestedSpeed(){
		return SuggestedSpeed;
	}
	public double getCurrentSpeed(){
		return CurrentSpeed;
	}
	public double getCurrentPosition(){
		return CurrentPosition;
	}
	public int getAuthority(){
		return authority;
	}
	public ArrayList<String> printTrain(){
		ArrayList<String> tmp=new ArrayList<String>();
		tmp.add(String.valueOf(ID));
		tmp.add("	Current Speed:"+CurrentSpeed);
		tmp.add("	Current Position:"+ CurrentPosition);
		tmp.add("	Authority:"+authority);
		return tmp;
	}
}
class Schedule{
	private int ID;
	private String Line;
	private String[] Station;
	private int[] ArrivalTime;
	private int[] LeaveTime;
	Schedule(int TrainID, String myLine, String[] myStation){
		ID=TrainID;
		Line=myLine;
		Station=myStation;
	}
	public ArrayList<String> printschedule(){
		ArrayList<String> tmp=new ArrayList<String>();
		tmp.add(String.valueOf(ID));
		tmp.add("	"+Line);
		for (String s:Station){
			tmp.add("	"+s+" "+"time1 "+"time2");//TODO 
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