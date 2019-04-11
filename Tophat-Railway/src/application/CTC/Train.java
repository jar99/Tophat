package application.CTC;

import java.util.ArrayList;

public class Train{
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
		//tmp.add("	Current Speed:"+CurrentSpeed);
		//tmp.add("	Current Position:"+ CurrentPosition);
		tmp.add("	Authority:"+authority);
		return tmp;
	}
}
