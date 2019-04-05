package application.TrackModel;

import java.util.HashMap;
import java.util.Map;

import application.CTC.CTCInterface;
import application.CTC.CTCSingleton;
import application.TrackController.TrackControllerInterface;
import application.TrackController.TrackControllerSingleton;
import application.TrainModel.TrainModelInterface;
import application.TrainModel.TrainModelSingleton;
import application.MBO.MBOInterface;
import application.MBO.MBOSingleton;

public class TrackModelSingleton implements TrackModelInterface {

	// Singleton Functions (NO TOUCHY!!)
	private static TrackModelSingleton instance = null;

	private TrackModelSingleton() {

	}

	public static TrackModelSingleton getInstance() {
		if (instance == null) {
			instance = new TrackModelSingleton();
		}

		return instance;
	}

	// ===============DATA / VARIABLES======================

	final private HashMap<String, TrackLine> track = new HashMap<String, TrackLine>();
	final private HashMap<Integer, TrainLocation> trainLocations = new HashMap<Integer, TrainLocation>();
	private int currentBlockID = -1;
	private String currentLineName = null;

	// ===============UPDATE METHOD======================
	public void update() {
		// TODO: generate a ticket randomly each second (chance of no ticket - 1/10)
		// increment
		// alighting/boarding on two different Stations

	}

	// ===============INTERFACE METHODS======================

	// ====CTC Methods====
	@Override
	public int getScheduledBoarding(String lineName, String stationName) {
		if (track.containsKey(lineName)) {
			return track.get(lineName).getStation(stationName).getBoarding();
		} else
			throw new IllegalArgumentException("Track does not contain line: " + lineName);
	}

	@Override
	public int getScheduledAlighting(String lineName, String stationName) {
		if (track.containsKey(lineName)) {
			return track.get(lineName).getStation(stationName).getAlighting();
		} else
			throw new IllegalArgumentException("Track does not contain line: " + lineName);
	}

	// ====Track Controller Methods====
	@Override
	public void createTrain(String lineName, int trainID) {
		if (!track.containsKey(lineName))
			throw new IllegalArgumentException("Track does not contain line: " + lineName);
		else if (trainLocations.containsKey(trainID))
			throw new IllegalArgumentException("Train: " + trainID + " already exists");
		else {
			TrackSection yard = track.get(lineName).getRailYard();
			TrainLocation newTrain = new TrainLocation(trainID, lineName, yard.getSectionID(), yard.getFirstBlockID(),
					yard.getStartX(), yard.getStartY());
			trainLocations.put(trainID, newTrain);
			yard.getBlock(yard.getFirstBlockID()).setOccupied(true);

			// TODO: call the Train Model create train method
		}
	}

	@Override
	public void setSwitch(String lineName, int switchID, boolean straight) {
		if (track.containsKey(lineName)) {
			track.get(lineName).getSwitch(switchID).setSwitchStraight(straight);
		} else
			throw new IllegalArgumentException("Track does not contain line: " + lineName);

	}

	@Override
	public void setSuggestedSpeed(String lineName, int blockID, double suggestedSpeed)
			throws TrackCircuitFailureException {
		if (suggestedSpeed < 0.0)
			throw new IllegalArgumentException("Suggested Speed cannot be negative");
		else if (!track.containsKey(lineName))
			throw new IllegalArgumentException("Track does not contain line: " + lineName);
		else if (track.get(lineName).getBlock(blockID).isFailCircuit())
			throw new TrackCircuitFailureException("Track Circuit is Failing for Block: " + blockID);
		else
			track.get(lineName).getBlock(blockID).setSuggestedSpeed(suggestedSpeed);

	}

	@Override
	public void setAuthority(String lineName, int blockID, int authority) throws TrackCircuitFailureException {
		if (authority < 0)
			throw new IllegalArgumentException("Authority cannot be negative");
		else if (!track.containsKey(lineName))
			throw new IllegalArgumentException("Track does not contain line: " + lineName);
		else if (track.get(lineName).getBlock(blockID).isFailCircuit())
			throw new TrackCircuitFailureException("Track Circuit is Failing for Block: " + blockID);
		else
			track.get(lineName).getBlock(blockID).setAuthority(authority);

	}

	@Override
	public void setControlAuthority(String lineName, int blockID, boolean ctrlAuthority)
			throws TrackCircuitFailureException {

		if (!track.containsKey(lineName))
			throw new IllegalArgumentException("Track does not contain line: " + lineName);
		else if (track.get(lineName).getBlock(blockID).isFailCircuit())
			throw new TrackCircuitFailureException("Track Circuit is Failing for Block: " + blockID);
		else
			track.get(lineName).getBlock(blockID).setControlAuthority(ctrlAuthority);
	}

	@Override
	public void setLightStatus(String lineName, int blockID, boolean green) {
		if (!track.containsKey(lineName))
			throw new IllegalArgumentException("Track does not contain line: " + lineName);
		else if (!track.get(lineName).getBlock(blockID).hasLight())
			throw new IllegalArgumentException("Block: " + blockID + " does not have a light");
		else if (green)
			track.get(lineName).getBlock(blockID).setGreen();
		else
			track.get(lineName).getBlock(blockID).setRed();

	}

	@Override
	public boolean getOccupancy(String lineName, int blockID) throws TrackCircuitFailureException {
		if (!track.containsKey(lineName))
			throw new IllegalArgumentException("Track does not contain line: " + lineName);
		else if (track.get(lineName).getBlock(blockID).isFailCircuit())
			throw new TrackCircuitFailureException("Track Circuit is Failing for Block: " + blockID);
		else if (track.get(lineName).getBlock(blockID).isFailRail())
			return true;
		else
			return track.get(lineName).getBlock(blockID).isOccupied();

	}

	@Override
	public void setHeating(String lineName, int blockID, boolean heated) {
		if (track.containsKey(lineName)) {
			track.get(lineName).getBlock(blockID).setHeated(heated);
		} else
			throw new IllegalArgumentException("Track does not contain line: " + lineName);

	}

	// ====Train Model Methods====
	@Override
	public void updateTrainDisplacement(int trainID, double displacement) throws TrainCrashedException {
		if (displacement < 0.0)
			throw new IllegalArgumentException("Displacement cannot be negative");
		else if (!trainLocations.containsKey(trainID))
			throw new IllegalArgumentException("Train: " + trainID + " not found");

		TrainLocation train = trainLocations.get(trainID);
		TrackLine line = track.get(train.getLineName());

		if (trainLocations.get(trainID).hasCrashed()) {
			line.calculateCoordinates(train);
			throw new TrainCrashedException("Train: " + trainID + " has crashed");
		}

		// Keep going until train has moved full distance
		while (displacement > 0.0) {

			// Periodically refresh train / block data
			int currentBlockID = train.getBlockID();
			double currentBlockDisplacement = train.getBlockDisplacement();
			boolean isDirectionAB = train.isDirectionAB();

			double currentBlockLength = line.getBlock(currentBlockID).getLength();

			// check that train does not run over broken rail (block)
			if (line.getBlock(currentBlockID).isFailRail()) {
				train.setCrashed();
				throw new TrainCrashedException("Train: " + trainID + " has crashed on a broken rail");
			}

			// Direction on a block affects calculations
			if (isDirectionAB) { // Train is going from A to B

				// Check to see if we stay on this block
				if (currentBlockLength > currentBlockDisplacement + displacement) {

					// Check that a train isn't blocking us
					if (trainIsBlocking(train, displacement, currentBlockLength))
						throw new TrainCrashedException(
								"Train: " + trainID + " has crashed on Block: " + currentBlockID);

					// END CASE: Staying on this block requires a simple displacement update
					train.setBlockDisplacement(currentBlockDisplacement + displacement);
					displacement -= displacement;

				}

				// If we are going to the next block
				else {

					// Calculate the difference
					double blockDifference = currentBlockLength - currentBlockDisplacement;

					// Check that a train isn't blocking us
					if (trainIsBlocking(train, blockDifference, currentBlockLength))
						throw new TrainCrashedException(
								"Train: " + trainID + " has crashed on Block: " + currentBlockID);

					// Need to remove the difference before going to next block
					displacement -= blockDifference;

					// Get next block ID
					try {
						TrackJunction nextBlockJunction = line.getNextBlockJunction(isDirectionAB, currentBlockID);
						if (nextBlockJunction.getID() == -1) { // If enter yard (leaving track)
							// TODO: Call Track Controller Remove Train option
							// TODO: Call Train Model Remove Train option
							train.delete();
							checkBlockOccupancy(line.getLineName(), currentBlockID);
						} else {
							placeTrainOnNextBlock(train, nextBlockJunction);
						}
					} catch (SwitchStateException e) {
						// Train Crashed at end of current block due to switch issue
						train.setCrashed();
						train.setBlockDisplacement(currentBlockLength);
						throw new TrainCrashedException(
								"Train: " + trainID + " has crashed on Block: " + currentBlockID + " at it's switch");
					}

				}

			}

			// the other direction (B to A) method
			else {
				// Check to see if we stay on this block
				if (currentBlockDisplacement - displacement > 0.0) {

					// Check that a train isn't blocking us
					if (trainIsBlocking(train, displacement, currentBlockLength))
						throw new TrainCrashedException(
								"Train: " + trainID + " has crashed on Block: " + currentBlockID);

					// END CASE: Staying on this block requires a simple displacement update
					train.setBlockDisplacement(currentBlockDisplacement - displacement);
					displacement -= displacement;

				}

				// If we are going to the next block
				else {

					// Calculate the difference
					double blockDifference = currentBlockDisplacement;

					// Check that a train isn't blocking us
					if (trainIsBlocking(train, blockDifference, currentBlockLength))
						throw new TrainCrashedException(
								"Train: " + trainID + " has crashed on Block: " + currentBlockID);

					// Need to remove the difference before going to next block
					displacement -= blockDifference;

					// Get next block ID
					try {
						TrackJunction nextBlockJunction = line.getNextBlockJunction(isDirectionAB, currentBlockID);
						if (nextBlockJunction.getID() == -1) { // If enter yard (leaving track)
							// TODO: Call Track Controller Remove Train option
							// TODO: Call Train Model Remove Train option
							train.delete();
							checkBlockOccupancy(line.getLineName(), currentBlockID);
						} else {
							placeTrainOnNextBlock(train, nextBlockJunction);
						}
					} catch (SwitchStateException e) {
						// Train Crashed at end of current block due to switch issue
						train.setCrashed();
						train.setBlockDisplacement(0.0);
						throw new TrainCrashedException(
								"Train: " + trainID + " has crashed on Block: " + currentBlockID + " at it's switch");
					}

				}
			}
		}

		// calculate train's new coordinates
		line.calculateCoordinates(train);

	}

	private void placeTrainOnNextBlock(TrainLocation train, TrackJunction nextBlockJunction) {
		if (nextBlockJunction.isSwitch())
			throw new IllegalArgumentException("Cannot place a train on a switch");

		String lineName = train.getLineName();
		TrackBlock currentBlock = track.get(lineName).getBlock(train.getBlockID());
		TrackBlock nextBlock = track.get(lineName).getBlock(nextBlockJunction.getID());

		// Set the following according to the junction and block data directionAB
		if (nextBlockJunction.getEntryPoint() == 0)
			train.setDirectionAB(true);
		else
			train.setDirectionAB(false);

		// blockID
		train.setBlockID(nextBlock.getBlockID());
		// sectionID
		train.setSectionID(nextBlock.getSectionID());

		// blockDisplacement
		if (nextBlockJunction.getEntryPoint() == 0)
			train.setBlockDisplacement(0.0);
		else
			train.setBlockDisplacement(nextBlock.getLength());

		// update occupancy (current and next)
		checkBlockOccupancy(lineName, currentBlock.getBlockID());
		checkBlockOccupancy(lineName, nextBlock.getBlockID());

	}

	private void checkBlockOccupancy(String lineName, int currentBlockID) {
		// Check to see if any trains on this block
		// If there are set it to Occupied, otherwise, set it to Unoccupied.
		// Ignore 'deleted' trains
		for (TrainLocation train : trainLocations.values()) {
			if (!train.mustDelete() && lineName.equals(train.getLineName()) && train.getBlockID() == currentBlockID) {
				track.get(lineName).getBlock(currentBlockID).setOccupied(true);
				return;
			}
		}

		track.get(lineName).getBlock(currentBlockID).setOccupied(false);

	}

	private boolean trainIsBlocking(TrainLocation train, double distanceTraveled, double currentblockLength) {
		// NOTE: depends on direction

		double currentBlockDisplacement = train.getBlockDisplacement();
		boolean isDirectionAB = train.isDirectionAB();

		// Check to see if any OTHER train is on the same block
		for (TrainLocation otherTrain : trainLocations.values()) {
			if (train.getTrainID() == otherTrain.getTrainID())
				continue;
			if (train.mustDelete())
				continue;

			// If so, check that that train is not between the displacement and an end point
			if (train.getBlockID() == otherTrain.getBlockID()) {
				double otherBlockDisplacement = otherTrain.getBlockDisplacement();

				// If so, set both train locations to that of the OTHER train, mark them as
				// crashed, and return true
				if (isDirectionAB) {
					if (otherBlockDisplacement >= currentBlockDisplacement
							&& otherBlockDisplacement <= currentBlockDisplacement + distanceTraveled) {
						train.setCrashed();
						otherTrain.setCrashed();
						train.setBlockDisplacement(otherBlockDisplacement);
						return true;
					}
				} else {
					if (otherBlockDisplacement <= currentBlockDisplacement
							&& otherBlockDisplacement >= currentBlockDisplacement - distanceTraveled) {
						train.setCrashed();
						otherTrain.setCrashed();
						train.setBlockDisplacement(otherBlockDisplacement);
						return true;
					}
				}

			}
		}

		return false;
	}

	@Override
	public double getTrainXCoordinate(int trainID) {
		if (trainLocations.containsKey(trainID)) {
			return trainLocations.get(trainID).getCoordX();
		} else
			throw new IllegalArgumentException("Train: " + trainID + " not found");
	}

	@Override
	public double getTrainYCoordinate(int trainID) {
		if (trainLocations.containsKey(trainID)) {
			return trainLocations.get(trainID).getCoordY();
		} else
			throw new IllegalArgumentException("Train: " + trainID + " not found");
	}

	@Override
	public int stationPassengerExchange(int trainID, int currentPassengers, int capacity) {
		if (currentPassengers < 0)
			throw new IllegalArgumentException("Current Passengers cannot be negative");
		else if (capacity < 0)
			throw new IllegalArgumentException("Capacity cannot be negative");
		if (currentPassengers > capacity)
			throw new IllegalArgumentException("Current Passengers cannot exceed Capacity");
		else if (!trainLocations.containsKey(trainID))
			throw new IllegalArgumentException("Train: " + trainID + " not found");
		else if (!track.get(trainLocations.get(trainID).getLineName())
				.getBlock(trainLocations.get(trainID).getBlockID()).isStation())
			throw new IllegalStateException("Train: " + trainID + " is not at a station");
		else {
			TrainLocation dockedTrain = trainLocations.get(trainID);
			TrackLine stationLine = track.get(dockedTrain.getLineName());
			TrackBlock stationBlock = stationLine.getBlock(dockedTrain.getBlockID());
			TrackStation station = stationLine.getStation(stationBlock.getStationName());

			int alighting = station.getAlighting();
			int boarding = station.getBoarding();

			// Alight them
			if (currentPassengers >= alighting) {
				currentPassengers -= alighting;
				station.alighted(alighting);
			} else {
				currentPassengers -= currentPassengers;
				station.alighted(currentPassengers);
			}

			// Board them
			if (currentPassengers < capacity) {
				if (currentPassengers + boarding <= capacity) {
					currentPassengers += boarding;
					station.boarded(boarding);
				} else {
					currentPassengers = capacity;
					station.boarded(capacity - currentPassengers);
				}
			}

			return currentPassengers;
		}

	}

	@Override
	public double getTrainBlockLength(int trainID) {
		if (trainLocations.containsKey(trainID)) {
			String lineName = trainLocations.get(trainID).getLineName();
			int blockID = trainLocations.get(trainID).getBlockID();
			return track.get(lineName).getBlock(blockID).getLength();
		} else
			throw new IllegalArgumentException("Train: " + trainID + " not found");
	}

	@Override
	public double getTrainBlockGrade(int trainID) {
		if (trainLocations.containsKey(trainID)) {
			String lineName = trainLocations.get(trainID).getLineName();
			int blockID = trainLocations.get(trainID).getBlockID();
			return track.get(lineName).getBlock(blockID).getGrade();
		} else
			throw new IllegalArgumentException("Train: " + trainID + " not found");
	}

	@Override
	public double getTrainBlockSpeedLimit(int trainID) {
		if (trainLocations.containsKey(trainID)) {
			String lineName = trainLocations.get(trainID).getLineName();
			int blockID = trainLocations.get(trainID).getBlockID();
			return track.get(lineName).getBlock(blockID).getSpdLmt();
		} else
			throw new IllegalArgumentException("Train: " + trainID + " not found");
	}

	@Override
	public double getTrainBlockElevation(int trainID) {
		if (trainLocations.containsKey(trainID)) {
			String lineName = trainLocations.get(trainID).getLineName();
			int blockID = trainLocations.get(trainID).getBlockID();
			return track.get(lineName).getBlock(blockID).getElev();
		} else
			throw new IllegalArgumentException("Train: " + trainID + " not found");
	}

	@Override
	public double getTrainBlockTotalElevation(int trainID) {
		if (trainLocations.containsKey(trainID)) {
			String lineName = trainLocations.get(trainID).getLineName();
			int blockID = trainLocations.get(trainID).getBlockID();
			return track.get(lineName).getBlock(blockID).getTotElev();
		} else
			throw new IllegalArgumentException("Train: " + trainID + " not found");
	}

	@Override
	public boolean trainBlockHasBeacon(int trainID) {
		if (trainLocations.containsKey(trainID)) {
			String lineName = trainLocations.get(trainID).getLineName();
			int blockID = trainLocations.get(trainID).getBlockID();
			return track.get(lineName).getBlock(blockID).hasBeacon();
		} else
			throw new IllegalArgumentException("Train: " + trainID + " not found");
	}

	@Override
	public String getTrainBlockBeaconData(int trainID) {
		if (trainLocations.containsKey(trainID)) {
			String lineName = trainLocations.get(trainID).getLineName();
			int blockID = trainLocations.get(trainID).getBlockID();
			return track.get(lineName).getBlock(blockID).getBeaconData();
		} else
			throw new IllegalArgumentException("Train: " + trainID + " not found");
	}

	@Override
	public boolean trainBlockIsStation(int trainID) {
		if (trainLocations.containsKey(trainID)) {
			String lineName = trainLocations.get(trainID).getLineName();
			int blockID = trainLocations.get(trainID).getBlockID();
			return track.get(lineName).getBlock(blockID).isStation();
		} else
			throw new IllegalArgumentException("Train: " + trainID + " not found");
	}

	@Override
	public boolean trainBlockIsUnderground(int trainID) {
		if (trainLocations.containsKey(trainID)) {
			String lineName = trainLocations.get(trainID).getLineName();
			int blockID = trainLocations.get(trainID).getBlockID();
			return track.get(lineName).getBlock(blockID).isUnderground();
		} else
			throw new IllegalArgumentException("Train: " + trainID + " not found");
	}

	@Override
	public boolean trainBlockHasLight(int trainID) {
		if (trainLocations.containsKey(trainID)) {
			String lineName = trainLocations.get(trainID).getLineName();
			int blockID = trainLocations.get(trainID).getBlockID();
			return track.get(lineName).getBlock(blockID).hasLight();
		} else
			throw new IllegalArgumentException("Train: " + trainID + " not found");
	}

	@Override
	public boolean trainBlockLightIsGreen(int trainID) {
		if (trainLocations.containsKey(trainID)) {
			String lineName = trainLocations.get(trainID).getLineName();
			int blockID = trainLocations.get(trainID).getBlockID();
			return track.get(lineName).getBlock(blockID).isLightGreen();
		} else
			throw new IllegalArgumentException("Train: " + trainID + " not found");
	}

	@Override
	public double getTrainSuggestedSpeed(int trainID) throws TrackCircuitFailureException {
		if (trainLocations.containsKey(trainID))
			throw new IllegalArgumentException("Train: " + trainID + " not found");

		String lineName = trainLocations.get(trainID).getLineName();
		int blockID = trainLocations.get(trainID).getBlockID();
		if (track.get(lineName).getBlock(blockID).isFailCircuit())
			throw new TrackCircuitFailureException("Track Circuit is Failing for Block: " + blockID);
		else if (!track.get(lineName).getBlock(blockID).hasControlAuthority())
			return 0.0;
		else
			return track.get(lineName).getBlock(blockID).getSuggestedSpeed();

	}

	@Override
	public int getTrainBlockAuthority(int trainID) throws TrackCircuitFailureException {
		if (trainLocations.containsKey(trainID))
			throw new IllegalArgumentException("Train: " + trainID + " not found");

		String lineName = trainLocations.get(trainID).getLineName();
		int blockID = trainLocations.get(trainID).getBlockID();
		if (track.get(lineName).getBlock(blockID).isFailCircuit())
			throw new TrackCircuitFailureException("Track Circuit is Failing for Block: " + blockID);
		else if (!track.get(lineName).getBlock(blockID).hasControlAuthority())
			return 0;
		else
			return track.get(lineName).getBlock(blockID).getAuthority();
	}

	@Override
	public boolean trainHasPower(int trainID) {
		if (trainLocations.containsKey(trainID)) {
			String lineName = trainLocations.get(trainID).getLineName();
			int blockID = trainLocations.get(trainID).getBlockID();
			return !track.get(lineName).getBlock(blockID).isFailPower();
		} else
			throw new IllegalArgumentException("Train: " + trainID + " not found");
	}

	// ====MBO Methods====
	@Override
	public boolean getSwitchState(String lineName, int switchID) {
		if (track.containsKey(lineName)) {
			return track.get(lineName).getSwitch(switchID).isSwitchStraight();
		} else
			throw new IllegalArgumentException("Track does not contain line: " + lineName);
	}

	// ===============UI CONTROLLER METHODS======================
	public void shiftBlockLeft() {
		// TODO Auto-generated method stub

	}

	public void shiftBlockRight() {
		// TODO Auto-generated method stub

	}

	public void toggleCBFailRail() {
		track.get(currentLineName).getBlock(currentBlockID).toggleFailRail();

	}

	public void toggleCBFailCircuit() {
		track.get(currentLineName).getBlock(currentBlockID).toggleFailCircuit();

	}

	public void toggleCBFailPower() {
		track.get(currentLineName).getBlock(currentBlockID).toggleFailPower();

	}

	public boolean hasALine() {
		return currentBlockID != -1 && currentLineName != null;
	}

	public TrackBlock getCurrentBlock() {
		return track.get(currentLineName).getBlock(currentBlockID);
	}

	public Map<Integer, TrainLocation> getTrainMap() {
		return trainLocations;
	}

}
