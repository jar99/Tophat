package application.TrackModel;

import java.util.Map;

/**
 * <h1>Track Section</h1> Implements the Track Section abstract class for curved
 * sections of track.
 *
 * @author Cory Cizauskas
 * @version 1.0
 * @since 2019-04-13
 */
public class TrackSectionCurve extends TrackSection {

	final private double radius;
	final private double centerX;
	final private double centerY;
	final private boolean isClockwise;

	public TrackSectionCurve(String lineName, char sectionID, int firstBlockID, int lastBlockID, double startX,
			double startY, double endX, double endY, Map<Integer, TrackBlock> blocks, double radius, double centerX,
			double centerY, boolean isClockwise) {
		super(lineName, sectionID, firstBlockID, lastBlockID, startX, startY, endX, endY, blocks);
		this.radius = radius;
		this.centerX = centerX;
		this.centerY = centerY;
		this.isClockwise = isClockwise;
	}

	@Override
	void calculateCoordinates(TrainLocation trainLocation) {
		if (trainLocation.getSectionID() != sectionID)
			throw new IllegalArgumentException(
					"Train: " + trainLocation.getTrainID() + " is not on Section: " + sectionID);

		int currentBlockID = trainLocation.getBlockID();
		if (!blocks.containsKey(currentBlockID))
			throw new IllegalArgumentException(
					"Section: " + sectionID + " does not contain Block: " + trainLocation.getBlockID());

		TrackBlock currentBlock = getBlock(trainLocation.getBlockID());
		double blockDisplacement = trainLocation.getBlockDisplacement();
		if (currentBlock.getLength() < blockDisplacement)
			throw new IllegalArgumentException("displacement cannot be larger than block length");
		else if (blockDisplacement < 0.0)
			throw new IllegalArgumentException("displacement cannot be negative");

		double sectionDisplacement = 0.0;
		for (int blockID = firstBlockID; blockID < currentBlockID; blockID++) {
			sectionDisplacement += getBlock(blockID).getLength();
		}
		sectionDisplacement += blockDisplacement;

		double angle = Math.atan2((-1) * (startY - centerY), startX - centerX);

		if (isClockwise)
			angle = angle - sectionDisplacement / radius;
		else
			angle = angle + sectionDisplacement / radius;

		double coordX = centerX + radius * Math.cos(angle);
		double coordY = centerY - radius * Math.sin(angle);
		trainLocation.setCoordinates(coordX, coordY);

	}

	public double getRadius() {
		return radius;
	}

	public double getCenterX() {
		return centerX;
	}

	public double getCenterY() {
		return centerY;
	}

	public boolean isClockwise() {
		return isClockwise;
	}

	public double getStartAngle() {
		return Math.atan2((-1) * (startY - centerY), startX - centerX);
	}

	public double getLengthAngle() {
		if (isClockwise)
			return (-1) * getLength() / radius;
		else
			return getLength() / radius;
	}

	/**
	 * Calculates the starting angle for a block
	 * 
	 * @param cBlockID - the block requested
	 * @return the blocks starting angle (radians)
	 */
	public double getBlockStartAngle(int cBlockID) {
		double sectionDisplacement = 0.0;
		for (int blockID = firstBlockID; blockID < cBlockID; blockID++) {
			sectionDisplacement += getBlock(blockID).getLength();
		}

		double angle = Math.atan2((-1) * (startY - centerY), startX - centerX);

		if (isClockwise)
			return angle - sectionDisplacement / radius;
		else
			return angle + sectionDisplacement / radius;

	}

	/**
	 * Calculates the length of the angle for a block
	 * 
	 * @param cBlockID - the block requested
	 * @return the blocks angle length (radians)
	 */
	public double getBlockLengthAngle(int cBlockID) {
		if (isClockwise)
			return (-1) * getBlock(cBlockID).getLength() / radius;
		else
			return getBlock(cBlockID).getLength() / radius;
	}

}
