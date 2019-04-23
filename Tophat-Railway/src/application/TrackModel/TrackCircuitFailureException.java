package application.TrackModel;

/**
 * <h1>Track Circuit Exception</h1> Occurs if a module interacts with a track
 * circuit while that circuit is failing
 *
 * @author Cory Cizauskas
 * @version 1.0
 * @since 2019-04-13
 */
public class TrackCircuitFailureException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4232827666710963350L;

	public TrackCircuitFailureException() {
	}

	public TrackCircuitFailureException(String message) {
		super(message);
	}
}
