package application.TrackModel;

import java.util.Map;

public class TrackSectionStraight extends TrackSection {

	public TrackSectionStraight(String lineName, char sectionID, int firstBlockID, int lastBlockID, double startX,
			double startY, double endX, double endY, Map<Integer, TrackBlock> blocks) {
		super(lineName, sectionID, firstBlockID, lastBlockID, startX, startY, endX, endY, blocks);
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

		double coordX = (sectionDisplacement / length) * (endX - startX) + startX;
		double coordY = (sectionDisplacement / length) * (endY - startY) + startY;
		trainLocation.setCoordinates(coordX, coordY);
	}

	public double getBlockStartX(int chosenBlockID) {
		double sectionDisplacement = 0.0;
		for (int blockID = firstBlockID; blockID < chosenBlockID; blockID++) {
			sectionDisplacement += getBlock(blockID).getLength();
		}
		
		return (sectionDisplacement / length) * (endX - startX) + startX;
	}

	public double getBlockStartY(int chosenBlockID) {
		double sectionDisplacement = 0.0;
		for (int blockID = firstBlockID; blockID < chosenBlockID; blockID++) {
			sectionDisplacement += getBlock(blockID).getLength();
		}
		
		return (sectionDisplacement / length) * (endY - startY) + startY;
	}

	public double getBlockEndX(int chosenBlockID) {
		double sectionDisplacement = 0.0;
		for (int blockID = firstBlockID; blockID <= chosenBlockID; blockID++) {
			sectionDisplacement += getBlock(blockID).getLength();
		}
		
		return (sectionDisplacement / length) * (endX - startX) + startX;
	}

	public double getBlockEndY(int chosenBlockID) {
		double sectionDisplacement = 0.0;
		for (int blockID = firstBlockID; blockID <= chosenBlockID; blockID++) {
			sectionDisplacement += getBlock(blockID).getLength();
		}
		
		return (sectionDisplacement / length) * (endY - startY) + startY;
	}

}
