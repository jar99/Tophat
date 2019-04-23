package application.TrackModel;

/**
 * <h1>Switch State Exception</h1> Occurs if Train attempts to cross a switch
 * while it is in a bad position
 *
 * @author Cory Cizauskas
 * @version 1.0
 * @since 2019-04-13
 */
public class SwitchStateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5282755349010986723L;

	public SwitchStateException() {
	}

	public SwitchStateException(String message) {
		super(message);
	}
}
