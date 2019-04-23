package application.TrackModel;

/**
 * <h1>Track Power Exception</h1> Occurs if a module tries to set/get the switch
 * light values while they are not powered.
 *
 * @author Cory Cizauskas
 * @version 1.0
 * @since 2019-04-13
 */
public class TrackPowerFailureException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4232827666710963350L;

	public TrackPowerFailureException() {
	}

	public TrackPowerFailureException(String message) {
		super(message);
	}
}
