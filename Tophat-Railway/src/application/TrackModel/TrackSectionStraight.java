package application.TrackModel;

import java.util.Map;

public class TrackSectionStraight extends TrackSection {

	public TrackSectionStraight(String lineName, char sectionID, int firstBlockID, double startX, double startY,
			double endX, double endY, TrackJunction junctionA, TrackJunction junctionB,
			Map<Integer, TrackBlock> blocks) {
		super(lineName, sectionID, firstBlockID, startX, startY, endX, endY, junctionA, junctionB, blocks);
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

}
