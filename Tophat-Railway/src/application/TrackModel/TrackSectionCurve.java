package application.TrackModel;

import java.util.Map;

public class TrackSectionCurve extends TrackSection {

	// TODO: add center, radius, clockwise/counterclockwise

	public TrackSectionCurve(String lineName, char sectionID, int firstBlockID, double startX, double startY,
			double endX, double endY, TrackJunction junctionA, TrackJunction junctionB,
			Map<Integer, TrackBlock> blocks) {
		super(lineName, sectionID, firstBlockID, startX, startY, endX, endY, junctionA, junctionB, blocks);
		// TODO Auto-generated constructor stub
	}

	@Override
	void calculateCoordinates(TrainLocation trainlocation) {
		// TODO Auto-generated method stub

	}

}
