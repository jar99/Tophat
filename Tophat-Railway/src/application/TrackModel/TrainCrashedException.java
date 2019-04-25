package application.TrackModel;

/**
 * <h1>Train Crashed Exception</h1> Occurs if a train derails or hits another
 * train.
 *
 * @author Cory Cizauskas
 * @version 1.0
 * @since 2019-04-13
 */
public class TrainCrashedException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8720230795366774418L;

	public TrainCrashedException() {
	}

	public TrainCrashedException(String message) {
		super(message);
	}
}
