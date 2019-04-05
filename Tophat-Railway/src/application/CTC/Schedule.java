package application.CTC;
import java.util.*;
public class Schedule{
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
			//tmp.add("	"+Station[i]+" "+"Arrival at "+ArrivalTime[i]+". Leave at "+LeaveTime[i]);//TODO 
			tmp.add("	"+Station[i]+" "+"Arrival at "+"10:00"+". Leave at "+"10:05");//TODO 
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