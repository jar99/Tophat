package application.TrackModel;

class TrackStation {

	// Ticket sale information
	final private String stationName;
	private int boarding = 0;
	private int alighting = 0;

	// ================CONSTRUCTOR===================
	public TrackStation(String stationName) {
		this.stationName = stationName;
	}

	// ================GET METHODS===================
	public String getStationName() {
		return stationName;
	}

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
