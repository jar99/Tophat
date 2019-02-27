package application.TrackModel;

public class TrackBlock {

	private char line;
	private int ID;
	
	private double length;
	private double grade;
	private double spdLmt;
	private double elev;
	private double totElev;
	
	private boolean isOccupied = false;
	private boolean isUnderground = false;
	private boolean isStation = false;
	private boolean isCrossing = false;
	private boolean isBeacon = false;
	private boolean isHeated = false;
	private boolean isSwitch = false;
	
	
	public TrackBlock(char line, int ID, double length, double grade, double spdLmt, double elev, double totElev) {
		this.line = line;
		this.ID = ID;		
		this.length = length;
		this.grade = grade;
		this.spdLmt = spdLmt;
		this.elev = elev;
		this.totElev = totElev;
	}

	public String getName() {
		StringBuilder sb = new StringBuilder();
		sb.append(line);
		sb.append(ID);
		return sb.toString();
	}

	public double getLength() {
		return length;
	}

	public double getGrade() {
		return grade;
	}

	public double getSpdLmt() {
		return spdLmt;
	}

	public double getElev() {
		return elev;
	}

	public double getTotElev() {
		return totElev;
	}

	public boolean isOccupied() {
		return isOccupied;
	}

	public boolean isUnderground() {
		return isUnderground;
	}

	public boolean isStation() {
		return isStation;
	}

	public boolean isCrossing() {
		return isCrossing;
	}

	public boolean isBeacon() {
		return isBeacon;
	}

	public boolean isHeated() {
		return isHeated;
	}

	public boolean isSwitch() {
		return isSwitch;
	}

	public void setOccupied() {
		isOccupied = true;
	}
	
	public void unsetOccupied() {
		isOccupied = false;
	}
	
}
