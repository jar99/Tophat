package application.TrackModel;

public interface TrackModelInterface {

	
	// This takes a double for the distance traveled over 1 second (update time)
	// It returns a Track Train object that contains the X and Y coordinates.
	// Methods are available in this object
	
	public TrackTrain getTrainLocation(double distance_traveled);
}
