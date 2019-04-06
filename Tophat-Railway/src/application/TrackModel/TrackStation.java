package application.TrackModel;

public class TrackStation extends TrackBlock {

	// Ticket sale information
	private int boarding = 0;
	private int alighting = 0;

	// ================CONSTRUCTOR===================
	public TrackStation(String lineName, char sectionID, int blockID, TrackJunction junctionA, TrackJunction junctionB,
			double length, double grade, double spdLmt, double elev, double totElev, String stationName,
			String beaconData, String cardinalDirection, boolean isUnderground, boolean isCrossing, boolean hasLight,
			boolean isBidirectional) {
		super(lineName, sectionID, blockID, junctionA, junctionB, length, grade, spdLmt, elev, totElev, stationName,
				beaconData, cardinalDirection, isUnderground, isCrossing, hasLight, isBidirectional);
	}

	// ================GET METHODS===================

	public int getBoarding() {
		return boarding;
	}

	public int getAlighting() {
		return alighting;
	}

	// ================SET METHODS===================
	// adjusted when people meet train
	public void boarded(int boarded) {
		if (boarded > boarding)
			throw new IllegalArgumentException("Too many boarding train");
		this.boarding -= boarded;
	}

	public void alighted(int alighted) {
		if (alighted > alighting)
			throw new IllegalArgumentException("Too many alighting train");
		this.alighting -= alighted;
	}

	// adjusted when tickets sell
	public void addBoarder(int boarders) {
		if (boarders < 0)
			throw new IllegalArgumentException("Boarders cannot be less than 0");
		this.boarding += boarders;
	}

	public void addAlighters(int alighters) {
		if (alighters < 0)
			throw new IllegalArgumentException("Alighters cannot be less than 0");
		this.alighting += alighters;
	}

}
