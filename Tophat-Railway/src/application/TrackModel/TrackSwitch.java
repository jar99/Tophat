package application.TrackModel;

/**
 * <h1>Track Switch</h1> Models a simple railway switch with a main, straight,
 * and diverging track.
 *
 * @author Cory Cizauskas
 * @version 1.0
 * @since 2019-04-13
 */
public class TrackSwitch {

	final private int switchID;

	final private TrackJunction mainJunction;
	final private TrackJunction straightJunction;
	final private TrackJunction divergingJunction;

	// NOTE: true for straight, false for diverging
	private boolean switchStraight = true;

	// ========CONSTRUCTOR====================
	// TODO: MEH - check arguments (construction)
	public TrackSwitch(int switchID, TrackJunction mainJunction, TrackJunction straightJunction,
			TrackJunction divergingJunction) {

		this.switchID = switchID;
		this.mainJunction = mainJunction;
		this.straightJunction = straightJunction;
		this.divergingJunction = divergingJunction;

	}

	// ========GET METHODS=====================
	public int getSwitchID() {
		return switchID;
	}

	public TrackJunction getMainJunction() {
		return mainJunction;
	}

	public TrackJunction getStraightJunction() {
		return straightJunction;
	}

	public TrackJunction getDivergingJunction() {
		return divergingJunction;
	}

	public boolean isSwitchStraight() {
		return switchStraight;
	}

	// =============SET METHODS=======================
	public void setSwitchStraight(boolean switchStraight) {
		this.switchStraight = switchStraight;
	}

	// =============CALCULATION======================
	/**
	 * Get the next Junction based on entry point and switch state
	 * 
	 * @param entryPoint - 0 = main / 1 = straight / 2 = diverging.
	 * @return The next Junction (which identifies a block)
	 * @throws SwitchStateException - when entering the switch while it's set the
	 *                              other way
	 */
	public TrackJunction getNextJunction(int entryPoint) throws SwitchStateException {
		if (entryPoint == 0) { // main
			if (switchStraight)
				return straightJunction;
			else
				return divergingJunction;

		} else if (entryPoint == 1) { // straight
			if (switchStraight)
				return mainJunction;
			else
				throw new SwitchStateException("Cannot enter straight while switch is diverging");

		} else if (entryPoint == 2) { // diverging
			if (switchStraight)
				throw new SwitchStateException("Cannot enter diverging while switch is straight");
			else
				return mainJunction;

		} else
			throw new IllegalArgumentException("Illegal EntryPoint value: 0 = main, 1 = straight, 2 = diverging");

	}

}
