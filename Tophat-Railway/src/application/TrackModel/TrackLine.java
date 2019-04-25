package application.TrackModel;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * <h1>Track Line</h1> Contains all the blocks, stations, sections, and switches
 * for a train line.
 *
 * @author Cory Cizauskas
 * @version 1.0
 * @since 2019-04-13
 */
public class TrackLine {

	final private String lineName;

	final private Map<Character, TrackSection> sections;
	final private Map<Integer, TrackBlock> blocks;
	final private Map<String, TrackStation> stations;
	final private Map<Integer, TrackSwitch> switches;

	private int totalBoarders = 0;

	// ===============CONSTRUCTORS======================
	public TrackLine(String lineName, Map<Character, TrackSection> sections, Map<Integer, TrackBlock> blocks,
			Map<String, TrackStation> stations, Map<Integer, TrackSwitch> switches) {
		this.lineName = lineName;
		this.sections = Collections.unmodifiableMap(sections);
		this.blocks = Collections.unmodifiableMap(blocks);
		this.stations = Collections.unmodifiableMap(stations);
		this.switches = Collections.unmodifiableMap(switches);
	}

	// ===============GET METHODS=======================

	/**
	 * Gets a specific section of the line
	 * 
	 * @param sectionID - a character identifying the chosen section
	 * @return the section requested
	 */
	public TrackSection getSection(char sectionID) {
		if (sections.containsKey(sectionID)) {
			return sections.get(sectionID);
		} else
			throw new IllegalArgumentException(lineName + " line does not contain Section: " + sectionID);
	}

	/**
	 * Gets a specific station on the line
	 * 
	 * @param stationName - the name of the chosen station
	 * @return the station requested
	 */
	public TrackStation getStation(String stationName) {
		if (stations.containsKey(stationName)) {
			return stations.get(stationName);
		} else
			throw new IllegalArgumentException(lineName + " line does not contain station: " + stationName);
	}

	/**
	 * Gets a specific switch on the line
	 * 
	 * @param switchID - an integer identifying the chosen switch
	 * @return the switch requested
	 */
	public TrackSwitch getSwitch(int switchID) {
		if (switches.containsKey(switchID)) {
			return switches.get(switchID);
		} else
			throw new IllegalArgumentException(lineName + " line does not contain switch: " + switchID);

	}

	/**
	 * Gets a specific block on the line
	 * 
	 * @param blockID - an integer identifying the chosen block
	 * @return the block requested
	 */
	public TrackBlock getBlock(int blockID) {
		if (blocks.containsKey(blockID)) {
			return blocks.get(blockID);
		} else
			throw new IllegalArgumentException(lineName + " line does not contain block: " + blockID);

	}

	/**
	 * Gets a Junction for the next block <i>connected</i> to the given block
	 * 
	 * @param isDirectionAB  - boolean indicating which direction along the block to
	 *                       travel
	 * @param currentBlockID - ID for the block currently being traveled on
	 * @return a Track Junction pointing to the next block if traveling in the given
	 *         direction
	 * @throws SwitchStateException - if the current block leads to a switch that
	 *                              cannot be crossed in its current position
	 */
	public TrackJunction getNextBlockJunction(boolean isDirectionAB, int currentBlockID) throws SwitchStateException {
		// return the next BLOCK junction. use switch first if necessary
		TrackJunction nextJunction;
		if (isDirectionAB) { // B if going A to B
			nextJunction = blocks.get(currentBlockID).getJunctionB();
		}

		else { // A if going B to A
			nextJunction = blocks.get(currentBlockID).getJunctionA();
		}

		while (nextJunction.isSwitch()) { // Need to get next junction if this one is a switch
			nextJunction = switches.get(nextJunction.getID()).getNextJunction(nextJunction.getEntryPoint());
		}

		return nextJunction;
	}

	/**
	 * Calls the correct section coordinate calculation method based on given train
	 * location
	 * 
	 * @param train - a Train Location object with an ID for the section the train
	 *              is on
	 */
	public void calculateCoordinates(TrainLocation train) {
		// Just call the section calculation method
		char sectionID = train.getSectionID();
		getSection(sectionID).calculateCoordinates(train);

	}

	/**
	 * Gets the name of this line, which is also the key used to store it.
	 * 
	 * @return the name of this line in lowercase letters
	 */
	public String getLineName() {
		return lineName;
	}

	/**
	 * Gets the number of blocks in this line
	 * 
	 * @return the number of blocks in this line
	 */
	public int getNumBlocks() {
		return blocks.size();
	}

	/**
	 * Gets every block on this line
	 * 
	 * @return a Collection of all blocks on this line
	 */
	public Collection<TrackBlock> getBlocks() {
		return blocks.values();
	}

	/**
	 * Gets every station on this line
	 * 
	 * @return a Collection of all stations on this line
	 */
	public Collection<TrackStation> getStations() {
		return stations.values();
	}

	/**
	 * Gets every switch on this line
	 * 
	 * @return a Collection of all switches on this line
	 */
	public Collection<TrackSwitch> getSwitches() {
		return switches.values();
	}

	/**
	 * Gets every section on this line
	 * 
	 * @return a Collection of all sections on this line
	 */
	public Collection<TrackSection> getSections() {
		return sections.values();
	}

	/**
	 * Gets the block a train will start on if it leaves the yard
	 * 
	 * @return the block a new train will start on
	 */
	public TrackBlock getEntrance() throws SwitchStateException {
		TrackSwitch entranceSwitch = switches.get(1);
		TrackJunction gate;
		if (entranceSwitch.getMainJunction().getID() == -1)
			gate = entranceSwitch.getNextJunction(0);
		else if (entranceSwitch.getStraightJunction().getID() == -1)
			gate = entranceSwitch.getNextJunction(1);
		else if (entranceSwitch.getDivergingJunction().getID() == -1)
			gate = entranceSwitch.getNextJunction(2);
		else
			throw new IllegalStateException("Line: " + this.lineName + " does not have an entrance at switch 1");

		return blocks.get(gate.getID());

	}

	/**
	 * Gets the total number of boarders on this line
	 * 
	 * @return the total number of boarders for this line
	 */
	public int getTotalBoarders() {
		return totalBoarders;
	}

	/**
	 * Adds more boarding passengers to the running total
	 * 
	 * @param moreBoarders - the number of boarders to be added to the total
	 */
	public void addBoarders(int moreBoarders) {
		totalBoarders += moreBoarders;
	}

}
