package application.TrackModel;

/**
 * <h1>Track Junction</h1> Exists at each end of blocks, switches, and sections.
 * Either points to a block or a switch.
 * <p>
 * Entry Point is represented by an integer. For blocks, 0 indicates the side
 * with Junction A, while 1 indicates the side with Junction B. For switches, 0
 * indicates the main track, 1 indicates the straight track, and 2 indicates the
 * diverging track.
 * </p>
 *
 * @author Cory Cizauskas
 * @version 1.0
 * @since 2019-04-13
 */
public class TrackJunction {
	final private boolean isSwitch; // true = switch / false = block
	final private int ID;
	final private int entryPoint; // switch: 0 = main / 1 = straight / 2 = diverging
									// block: 0 = JunctionA / 1 = JunctionB

	// ========CONSTRUCTOR====================
	public TrackJunction(boolean isSwitch, int ID, int entryPoint) {

		if (ID < -1)
			throw new IllegalArgumentException("ID cannot be negative");

		if (isSwitch) {
			if (entryPoint != 0 && entryPoint != 1 && entryPoint != 2)
				throw new IllegalArgumentException(
						"Illegal Switch EntryPoint value: 0 = main, 1 = straight, 2 = diverging");
		} else {
			if (entryPoint != 0 && entryPoint != 1)
				throw new IllegalArgumentException("Illegal Block EntryPoint value: 0 = Junction A, 1 = Junction B");

		}
		this.isSwitch = isSwitch;
		this.ID = ID;
		this.entryPoint = entryPoint;

	}

	// ========GET METHODS=====================
	public boolean isSwitch() {
		return isSwitch;
	}

	public int getID() {
		return ID;
	}

	public int getEntryPoint() {
		return entryPoint;
	}

}
