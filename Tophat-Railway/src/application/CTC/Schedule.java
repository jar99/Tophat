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
	public void mergeSchedule(Schedule tmp2){
		int tmpsize=this.getStation().length+tmp2.getStation().length-1;
		int[] distancetmp=new int[tmpsize];
		String[] stationtmp=new String[tmpsize];
		int[] arrivaltmp=new int[tmpsize];
		int[] leavetmp=new int[tmpsize];
		distancetmp[0]=distance[0];
		stationtmp[0]=Station[0];
		arrivaltmp[0]=ArrivalTime[0];
		leavetmp[0]=LeaveTime[0];
		stationtmp[0]=Station[0];
		for (int i=1;i<this.getStation().length;i++){
			distancetmp[i]=distance[i-1];
			stationtmp[i]=Station[i];
			arrivaltmp[i]=ArrivalTime[i];
			leavetmp[i]=LeaveTime[i];
			stationtmp[i]=Station[i];
		}
		distancetmp[this.getStation().length-1]=tmp2.getDistance()[0];
		leavetmp[this.getStation().length-1]=tmp2.getDepartureTime();
		for (int i=this.getStation().length;i<tmpsize;i++){
			arrivaltmp[i]=leavetmp[i-1]+distancetmp[i-1]/tmp2.getSpeed();
			leavetmp[i]=arrivaltmp[i]+5*60;
			distancetmp[i]=tmp2.getDistance()[i-this.getStation().length];
			stationtmp[i]=tmp2.getStation()[i-this.getStation().length+1];
		}
		Station=stationtmp;
		ArrivalTime=arrivaltmp;
		LeaveTime=leavetmp;
		distance=distancetmp;
		
	}
	public ArrayList<String> printschedule(){
		
		ArrayList<String> tmp=new ArrayList<String>();
		tmp.add(String.valueOf(ID));
		tmp.add("	"+Line);
		for (int i=0;i<Station.length;i++) {
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
	public int[] getDistance(){
		return distance;
	}
	public int getDepartureTime(){
		return Departuretime;
	}
	public int getSpeed(){
		return speed;
	}
}