package application.MBO;

public class Operator {
	private String name;
	private int startTime;
	private int stopTime;
	
	public Operator(String name, int startTime, int stopTime) {
		this.name = name;
		this.startTime = startTime;
		this.stopTime = stopTime;
	}
	
	public String getName() {
		return name;
	}
	public int getStartTime() {
		return startTime;
	}
	public int getStopTime() {
		return stopTime;
	}
	public void setID(String name) {
		this.name = name;
	}
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	public void setStopTime(int stopTime) {
		this.stopTime = stopTime;
	}
}
