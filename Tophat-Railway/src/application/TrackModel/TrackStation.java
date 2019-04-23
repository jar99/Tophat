package application.TrackModel;

/**
 * <h1>Track Station</h1> A block of the track which also serves as a station.
 * Models the boarding and alighting of passengers.
 *
 * @author Cory Cizauskas
 * @version 1.0
 * @since 2019-04-13
 */
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

	/**
	 * Simulates passengers boarding and alighting the train
	 * 
	 * @param currentPassengers - the current number of passengers on the train
	 * @param capacity          - the passenger limit for the train
	 * @return the new number of passengers
	 */
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

	/**
	 * Simulates the train leaving the station
	 */
	public void departure() {
		this.docked = false;
		this.alighting = 0;
		this.boarding = 0;
	}

}
