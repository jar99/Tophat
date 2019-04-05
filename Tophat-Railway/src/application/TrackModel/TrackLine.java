package application.TrackModel;

import java.util.Collections;
import java.util.Map;

//NOTE: don't need to clone, just create multiple instances during import and send those.

public class TrackLine {

	final private String lineName;

	final private Map<Character, TrackSection> sections;
	final private Map<Integer, TrackBlock> blocks;
	final private Map<String, TrackStation> stations;
	final private Map<Integer, TrackSwitch> switches;

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

	public TrackSection getSection(char sectionID) {
		if (sections.containsKey(sectionID)) {
			return sections.get(sectionID);
		} else
			throw new IllegalArgumentException(lineName + " line does not contain Section: " + sectionID);
	}

	public TrackStation getStation(String stationName) {
		if (stations.containsKey(stationName)) {
			return stations.get(stationName);
		} else
			throw new IllegalArgumentException(lineName + " line does not contain station: " + stationName);
	}

	public TrackSwitch getSwitch(int switchID) {
		if (switches.containsKey(switchID)) {
			return switches.get(switchID);
		} else
			throw new IllegalArgumentException(lineName + " line does not contain switch: " + switchID);

	}

	public TrackBlock getBlock(int blockID) {
		if (blocks.containsKey(blockID)) {
			return blocks.get(blockID);
		} else
			throw new IllegalArgumentException(lineName + " line does not contain block: " + blockID);

	}

	public TrackSection getRailYard() {
		return getSection('0'); // NOTE: 0 will represent both the yard section and block
	}

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

	public void calculateCoordinates(TrainLocation train) {
		// Just call the section calculation method
		char sectionID = train.getSectionID();
		getSection(sectionID).calculateCoordinates(train);

	}

	public String getLineName() {
		return lineName;
	}

}
