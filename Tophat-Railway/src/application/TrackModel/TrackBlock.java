package application.TrackModel;

public class TrackBlock {
	// Block Identification
	final private String lineName;
	final private char sectionID;
	final private int blockID;

	// Junctions
	final private TrackJunction junctionA;
	final private TrackJunction junctionB;

	// Measurable Attributes
	final private double length;
	final private double grade;
	final private double spdLmt;
	final private double elev;
	final private double totElev;

	// String Attributes
	final private String stationName;
	final private String beaconData;

	// Boolean Attributes
	final private boolean isStation;
	final private boolean hasBeacon;
	final private boolean isUnderground;
	final private boolean isCrossing;
	final private boolean hasLight;
	final private boolean isBidirectional; //Note: A -> B is default

	// Boolean Controls
	private boolean isOccupied = false;
	private boolean isHeated = false;
	private boolean isLightGreen = true;

	// Speed, Authority, and Control Values
	private double suggestedSpeed = 0.0;
	private int authority = 0;
	private boolean controlAuthority = false;

	// Active Failure Booleans
	private boolean failRail = false;
	private boolean failCircuit = false;
	private boolean failPower = false;

	// ===========CONSTRUCTORS============================

	//TODO: MEH - check arguments (construction)
	public TrackBlock(String lineName, char sectionID, int blockID, TrackJunction junctionA, TrackJunction junctionB,
			double length, double grade, double spdLmt, double elev, double totElev, String stationName,
			String beaconData, boolean isUnderground, boolean isCrossing, boolean hasLight, boolean isBidirectional) {
		
		this.lineName = lineName;
		this.sectionID = sectionID;
		this.blockID = blockID;
		this.junctionA = junctionA;
		this.junctionB = junctionB;
		this.length = length;
		this.grade = grade;
		this.spdLmt = spdLmt;
		this.elev = elev;
		this.totElev = totElev;

		if (stationName == null && beaconData == null) {
			this.isStation = false;
			this.hasBeacon = false;
		} else if (stationName != null && beaconData != null) {
			this.isStation = true;
			this.hasBeacon = true;
		} else
			throw new IllegalArgumentException("Only a Station can have a Beacon");

		this.stationName = stationName;
		this.beaconData = beaconData;
		this.isUnderground = isUnderground;
		this.isCrossing = isCrossing;
		this.hasLight = hasLight;
		this.isBidirectional = isBidirectional;
	}

	// ==========GET METHODS=========================

	// Naming
	public String getName() {
		StringBuilder sb = new StringBuilder();
		sb.append(sectionID);
		sb.append(blockID);
		return sb.toString();
	}

	public String getLineName() {
		return lineName;
	}

	public char getSectionID() {
		return sectionID;
	}

	public int getBlockID() {
		return blockID;
	}

	// Junctions
	public TrackJunction getJunctionA() {
		return junctionA;
	}

	public TrackJunction getJunctionB() {
		return junctionB;
	}

	// Measurable Attributes
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

	// String Attributes
	public String getStationName() {
		return stationName;
	}

	public String getBeaconData() {
		return beaconData;
	}

	// Boolean Attributes
	public boolean isStation() {
		return isStation;
	}

	public boolean hasBeacon() {
		return hasBeacon;
	}

	public boolean isUnderground() {
		return isUnderground;
	}

	public boolean isCrossing() {
		return isCrossing;
	}

	public boolean hasLight() {
		return hasLight;
	}
	
	public boolean isBidirectional() {
		return isBidirectional;
	}

	// Boolean Controls
	public boolean isOccupied() {
		return isOccupied;
	}

	public boolean isHeated() {
		return isHeated;
	}

	public boolean isLightGreen() {
		return isLightGreen;
	}

	// Speed, Authority, and Control Values
	public double getSuggestedSpeed() {
		return suggestedSpeed;
	}

	public int getAuthority() {
		return authority;
	}

	public boolean hasControlAuthority() {
		return controlAuthority;
	}

	// Active Failure Booleans
	public boolean isFailRail() {
		return failRail;
	}

	public boolean isFailCircuit() {
		return failCircuit;
	}

	public boolean isFailPower() {
		return failPower;
	}

	// ============SET METHODS==============================

	// Boolean Controls
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}

	public void setHeated(boolean isHeated) {
		this.isHeated = isHeated;
	}

	public void setGreen() {
		isLightGreen = true;
	}

	public void setRed() {
		isLightGreen = false;
	}

	// Speed, Authority, and Control Values
	public void setSuggestedSpeed(double suggestedSpeed) {
		if (suggestedSpeed < 0.0)
			throw new IllegalArgumentException("Suggested Speed cannot be negative");
		else
			this.suggestedSpeed = suggestedSpeed;
	}

	public void setAuthority(int authority) {
		if (authority < 0)
			throw new IllegalArgumentException("Authority cannot be negative");
		else
			this.authority = authority;
	}

	public void setControlAuthority(boolean controlAuthority) {
		this.controlAuthority = controlAuthority;
	}

	// Active Failure Booleans
	public void toggleFailRail() {
		failRail = !failRail;
	}

	public void toggleFailCircuit() {
		failCircuit = !failCircuit;
	}

	public void toggleFailPower() {
		failPower = !failPower;
	}
}
