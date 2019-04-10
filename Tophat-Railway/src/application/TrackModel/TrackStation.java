package application.TrackModel;

public class TrackStation extends TrackBlock {

	// Ticket sale information
	private int scheduledBoarders = 0;
	private int scheduledAlighters = 0;
	private int boarding = 0;
	private int alighting = 0;

	private boolean docked = false;

	// ================CONSTRUCTOR===================
	public TrackStation(String lineName, char sectionID, int blockID, TrackJunction junctionA, TrackJunction junctionB,
			double length, double grade, double spdLmt, double elev, double totElev, String stationName,
			String beaconData, String cardinalDirection, boolean isUnderground, boolean isCrossing, boolean hasLight,
			boolean isBidirectional) {
		super(lineName, sectionID, blockID, junctionA, junctionB, length, grade, spdLmt, elev, totElev, stationName,
				beaconData, cardinalDirection, isUnderground, isCrossing, hasLight, isBidirectional);
	}

	// ================GET METHODS===================
	public int getScheduledBoarders() {
		return scheduledBoarders;
	}

	public int getScheduledAlighters() {
		return scheduledAlighters;
	}

	public int getBoarding() {
		return boarding;
	}

	public int getAlighting() {
		return alighting;
	}

	public boolean isDocked() {
		return docked;
	}

	// ================SET METHODS===================

	// Adjusted when tickets sell
	public void addScheduledBoarders(int newBoarders) {
		if (newBoarders < 0)
			throw new IllegalArgumentException("New Boarders cannot be less than 0");
		this.scheduledBoarders += newBoarders;
	}

	public void addScheduledAlighters(int newAlighters) {
		if (newAlighters < 0)
			throw new IllegalArgumentException("New Alighters cannot be less than 0");
		this.scheduledAlighters += newAlighters;
	}

	public int arrival(int currentPassengers, int capacity) {
		docked = true;

		// Alight them
		if (currentPassengers >= this.scheduledAlighters) {
			this.alighting = this.scheduledAlighters;
		} else {
			this.alighting = currentPassengers;
		}
		this.scheduledAlighters -= this.alighting;
		currentPassengers -= this.alighting;

		// Board them
		if (currentPassengers < capacity) {
			if (currentPassengers + scheduledBoarders <= capacity) {
				this.boarding = scheduledBoarders;
			} else {
				this.boarding = capacity - currentPassengers;
			}
			this.scheduledBoarders -= this.boarding;
			currentPassengers += this.boarding;
		}

		return currentPassengers;
	}

	public void departure() {
		this.docked = false;
		this.alighting = 0;
		this.boarding = 0;
	}

}
