package application.TrackModel;

import java.util.Collections;
import java.util.Map;

/**
 * <h1>Track Section</h1> A collection of block objects joined together.
 * Contains functions and data related to grid position.
 *
 * @author Cory Cizauskas
 * @version 1.0
 * @since 2019-04-13
 */
public abstract class TrackSection {
	// Identification
	final protected String lineName;
	final protected char sectionID;

	// Block Contents
	final protected int firstBlockID;
	final protected int lastBlockID;
	final protected int numBlocks;

	// Measurable Attributes
	final protected double length;

	// Base Coordinates
	final protected double startX;
	final protected double startY;
	final protected double endX;
	final protected double endY;

	// Junctions (block/switch at each end of section)
	final protected TrackJunction junctionA;
	final protected TrackJunction junctionB;

	// Block objects
	final protected Map<Integer, TrackBlock> blocks;

	// =======CONSTRUCTORS===========================
	public TrackSection(String lineName, char sectionID, int firstBlockID, int lastBlockID, double startX,
			double startY, double endX, double endY, Map<Integer, TrackBlock> blocks) {
		this.lineName = lineName;
		this.sectionID = sectionID;
		this.firstBlockID = firstBlockID;
		this.lastBlockID = lastBlockID;
		this.numBlocks = blocks.size();
		double t_length = 0.0;
		for (TrackBlock block : blocks.values()) {
			t_length += block.getLength();
		}
		this.length = t_length;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.junctionA = blocks.get(firstBlockID).getJunctionA();
		this.junctionB = blocks.get(lastBlockID).getJunctionB();
		this.blocks = Collections.unmodifiableMap(blocks);
	}

	// ==========GET METHODS============================

	// Identification
	final public String getLineName() {
		return lineName;
	}

	final public char getSectionID() {
		return sectionID;
	}

	final public int getFirstBlockID() {
		return firstBlockID;
	}

	final public int getLastBlockID() {
		return lastBlockID;
	}

	final public int getNumBlocks() {
		return numBlocks;
	}

	// Measurable Attributes
	final public double getLength() {
		return length;
	}

	// Boolean Attributes
	final public boolean isBidirectional() {
		return blocks.get(firstBlockID).isBidirectional();
	}

	// Base Coordinates
	final public double getStartX() {
		return startX;
	}

	final public double getStartY() {
		return startY;
	}

	final public double getEndX() {
		return endX;
	}

	final public double getEndY() {
		return endY;
	}

	// Junctions (section ends)
	final public TrackJunction getJunctionA() {
		return junctionA;
	}

	final public TrackJunction getJunctionB() {
		return junctionB;
	}

	// Objects and Coordination
	/**
	 * Gets a block from this sections
	 * 
	 * @param blockID - the requested block ID
	 * @return the requested block
	 */
	final public TrackBlock getBlock(int blockID) {
		if (!containsBlock(blockID))
			throw new IllegalArgumentException("Section: " + sectionID + " does not contain block: " + blockID);
		else
			return blocks.get(blockID);
	}

	/**
	 * Checks that this section contains a specific block
	 * 
	 * @param blockID - the requested block ID
	 * @return true - if the section contains the block
	 */
	final public boolean containsBlock(int blockID) {
		return firstBlockID <= blockID && blockID <= lastBlockID;
	}

	// ========CALCULATIONS============================
	/**
	 * Calculates the grid coordinates for a given train
	 * 
	 * @param trainLocation - the train containing updated block and block
	 *                      displacement details
	 */
	abstract void calculateCoordinates(TrainLocation trainLocation);

}
