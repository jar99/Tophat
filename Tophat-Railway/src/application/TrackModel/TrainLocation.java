package application.TrackModel;

class TrainLocation {

	// Train Information
	final private int trainID;
	private boolean directionAB = true; // Note: True - Junction A to B. False - Junction B to A.
	private boolean hasCrashed = false;

	// Location Information
	final private String lineName;
	private char sectionID;
	private int blockID;
	private double blockDisplacement = 0.0;

	// Coordinate Location
	private double coordX;
	private double coordY;

	// UI Synchronization
	private boolean add = true;
	private boolean delete = false;

	// ================CONSTRUCTOR===================
	public TrainLocation(int trainID, String lineName, char sectionID, int blockID, double coordX, double coordY) {
		this.trainID = trainID;
		this.lineName = lineName;
		this.sectionID = sectionID;
		this.blockID = blockID;
		this.coordX = coordX;
		this.coordY = coordY;
	}

	// ================GET METHODS===================
	public int getTrainID() {
		return trainID;
	}

	public boolean isDirectionAB() {
		return directionAB;
	}

	public boolean hasCrashed() {
		return hasCrashed;
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

	public double getBlockDisplacement() {
		return blockDisplacement;
	}

	public double getCoordX() {
		return coordX;
	}

	public double getCoordY() {
		return coordY;
	}
	// ================SET METHODS===================

	public void setDirectionAB(boolean directionAB) {
		this.directionAB = directionAB;
	}

	public void setCrashed() {
		this.hasCrashed = true;
	}

	public void setSectionID(char sectionID) {
		this.sectionID = sectionID;
	}

	public void setBlockID(int blockID) {
		if (blockID < -1)
			throw new IllegalArgumentException("Block ID cannot be less than 0");
		this.blockID = blockID;
	}

	public void setBlockDisplacement(double blockDisplacement) {
		if (blockDisplacement < 0)
			throw new IllegalArgumentException("Block Displacement cannot be less than 0");
		this.blockDisplacement = blockDisplacement;
	}

	// Change train coordinates
	void setCoordinates(double newX, double newY) {
		if (newX < 0 || newY < 0)
			throw new IllegalArgumentException("Coordinates cannot be less than 0");
		this.coordX = newX;
		this.coordY = newY;
	}

	// ================UI SYNC FUNCTIONS===================
	void added() {
		add = false;
	}

	void delete() {
		delete = true;
	}

	boolean mustDelete() {
		return delete;
	}

	boolean mustAdd() {
		return add;
	}

}
