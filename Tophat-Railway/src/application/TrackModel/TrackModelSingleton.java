package application.TrackModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



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

	final private Hashtable<String, TrackLine> track = new Hashtable<String, TrackLine>();
	final private Hashtable<Integer, TrainLocation> trainLocations = new Hashtable<Integer, TrainLocation>();

	private int currentBlockID = -1;
	private String currentLineName = null;

	private double temperature = 70.00;
	
	private int chanceTicket = 0;
	private Random rand = new Random();

	// ===============UPDATE METHOD======================
	public void update() {

		if(hasALine()) {
			generateTicket(1.0); //TODO: get time ratio
		}

	}

	private void generateTicket(double timeRatio) {
		if(rand.nextInt(100) < chanceTicket) {
			chanceTicket = 0;
			for(TrackLine line : track.values()) {
				Object[] stations = line.getStations().toArray();
				TrackStation boardStation = (TrackStation) stations[rand.nextInt(stations.length)];
				TrackStation alightStation;
				do {
					alightStation = (TrackStation) stations[rand.nextInt(stations.length)];
				}while(alightStation.getBlockID() == boardStation.getBlockID());
				boardStation.addScheduledBoarders(1);
				alightStation.addScheduledAlighters(1);
			
			}
		}else {
			chanceTicket += 2 * timeRatio;
		}
		
	}
	
	// ===============INTERFACE METHODS======================

	// ====CTC Methods====
	@Override
	public int getScheduledBoarding(String lineName, String stationName) {
		if (track.containsKey(lineName)) {
			return track.get(lineName).getStation(stationName).getScheduledBoarders();
		} else
			throw new IllegalArgumentException("Track does not contain line: " + lineName);
	}

	@Override
	public int getScheduledAlighting(String lineName, String stationName) {
		if (track.containsKey(lineName)) {
			return track.get(lineName).getStation(stationName).getScheduledAlighters();
		} else
			throw new IllegalArgumentException("Track does not contain line: " + lineName);
	}

	// ====Track Controller Methods====
	@Override
	public void createTrain(String lineName, int trainID) throws SwitchStateException {
		if (!track.containsKey(lineName))
			throw new IllegalArgumentException("Track does not contain line: " + lineName);
		else if (trainLocations.containsKey(trainID))
			throw new IllegalArgumentException("Train: " + trainID + " already exists");
		else {

			TrackBlock firstBlock = track.get(lineName).getEntrance();
			TrackSection firstSection = track.get(lineName).getSection(firstBlock.getSectionID());
			
			TrainLocation newTrain = new TrainLocation(trainID, lineName, firstSection.getSectionID(), firstSection.getFirstBlockID(),
					firstSection.getStartX(), firstSection.getStartY());
			
			trainLocations.put(trainID, newTrain);
			firstSection.getBlock(firstSection.getFirstBlockID()).setOccupied(true);

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
	// TODO: NOTE: could the train deletion method impair ID checks?
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

		//: Consider this to be the depart function from stations (update block
		// boarding values)
		int currentBlockID = train.getBlockID();
		TrackBlock currentBlock = line.getBlock(currentBlockID);
		if(currentBlock.isStation()) {
			TrackStation station = line.getStation(currentBlock.getStationName());
			if(station.isDocked() && displacement > 0) {
				station.departure();
			}
		}

		// Keep going until train has moved full distance
		while (displacement > 0.0) {

			// Periodically refresh train / block data
			currentBlockID = train.getBlockID();
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

			//: Consider this to be the dock function from stations (update block
			// boarding values)

			TrainLocation dockedTrain = trainLocations.get(trainID);
			TrackLine stationLine = track.get(dockedTrain.getLineName());
			TrackBlock stationBlock = stationLine.getBlock(dockedTrain.getBlockID());
			TrackStation station = stationLine.getStation(stationBlock.getStationName());

			return station.arrival(currentPassengers, capacity);

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
		if (!trainLocations.containsKey(trainID))
			throw new IllegalArgumentException("Train: " + trainID + " not found");

		TrainLocation train = trainLocations.get(trainID);
		String lineName = train.getLineName();
		int blockID = train.getBlockID();
		double grade = track.get(lineName).getBlock(blockID).getGrade();

		if (train.isDirectionAB())
			return grade;
		else
			return grade * (-1);

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
		if (!trainLocations.containsKey(trainID))
			throw new IllegalArgumentException("Train: " + trainID + " not found");

		TrainLocation train = trainLocations.get(trainID);
		String lineName = train.getLineName();
		int blockID = train.getBlockID();
		double elevationChange = track.get(lineName).getBlock(blockID).getElev();

		if (train.isDirectionAB())
			return elevationChange;
		else
			return elevationChange * (-1);

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
		if (hasALine()) {
			if (currentBlockID == 1)
				currentBlockID = track.get(currentLineName).getNumBlocks();
			else
				currentBlockID = currentBlockID - 1;
		}

	}

	public void shiftBlockRight() {
		if (hasALine()) {
			if (currentBlockID == track.get(currentLineName).getNumBlocks())
				currentBlockID = 1;
			else
				currentBlockID = currentBlockID + 1;
		}
	}

	public void toggleCBFailRail() {
		if (hasALine())
			track.get(currentLineName).getBlock(currentBlockID).toggleFailRail();

	}

	public void toggleCBFailCircuit() {
		if (hasALine())
			track.get(currentLineName).getBlock(currentBlockID).toggleFailCircuit();

	}

	public void toggleCBFailPower() {
		if (hasALine())
			track.get(currentLineName).getBlock(currentBlockID).toggleFailPower();

	}

	public boolean hasALine() {
		return currentBlockID != -1 && currentLineName != null;
	}

	public TrackBlock getCurrentBlock() {
		return track.get(currentLineName).getBlock(currentBlockID);
	}

	public TrackStation getCurrentStation() {
		TrackLine currentLine = track.get(currentLineName);
		TrackBlock currentBlock = currentLine.getBlock(currentBlockID);
		String stationName = currentBlock.getStationName();
		return currentLine.getStation(stationName);
	}
	
	public Map<Integer, TrainLocation> getTrainMap() {
		return trainLocations;
	}

	public void importLine(String fileName) {
		File excelFile = new File(fileName);

		FileInputStream fis;
		XSSFWorkbook workbook;

		try {
			fis = new FileInputStream(excelFile);
			workbook = new XSSFWorkbook(fis);

			// Add Track Model Line
			TrackLine myLine = readLineFile(workbook);

			track.put(myLine.getLineName(), myLine);
			currentBlockID = 1;
			currentLineName = myLine.getLineName();

			// Call CTC importLine Method
			TrackLine ctcLine = readLineFile(workbook);
			CTCInterface ctcInt = CTCSingleton.getInstance();
			ctcInt.importLine(ctcLine);

			// Call Track Controller importLine Method
			TrackLine tckCtrlLine = readLineFile(workbook);
			TrackControllerInterface tckCtrlInt = TrackControllerSingleton.getInstance();
			tckCtrlInt.importLine(tckCtrlLine);

			// Call MBO importLine Method
			TrackLine mboLine = readLineFile(workbook);
			MBOInterface mboInt = MBOSingleton.getInstance();
			mboInt.importLine(mboLine);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private TrackLine readLineFile(XSSFWorkbook workbook) {
		String lineName = null;

		// Import Block Sheet
		XSSFSheet blockSheet = workbook.getSheetAt(0);

		Iterator<Row> blockRowIt = blockSheet.iterator();
		Hashtable<Integer, TrackBlock> blocks = new Hashtable<Integer, TrackBlock>();
		Hashtable<String, TrackStation> stations = new Hashtable<String, TrackStation>();
		Hashtable<Character, Hashtable<Integer, TrackBlock>> sectionBlocksTable = new Hashtable<Character, Hashtable<Integer, TrackBlock>>();

		blockRowIt.next();

		while (blockRowIt.hasNext()) {

			Row blockRow = blockRowIt.next();

			lineName = blockRow.getCell(0).getStringCellValue();
			char sectionID = blockRow.getCell(1).getStringCellValue().charAt(0);
			int blockID = (int) blockRow.getCell(2).getNumericCellValue();

			boolean AisSwitch = blockRow.getCell(4).getBooleanCellValue();
			int AID = (int) blockRow.getCell(5).getNumericCellValue();
			int Aentry = (int) blockRow.getCell(6).getNumericCellValue();
			boolean BisSwitch = blockRow.getCell(7).getBooleanCellValue();
			int BID = (int) blockRow.getCell(8).getNumericCellValue();
			int Bentry = (int) blockRow.getCell(9).getNumericCellValue();

			TrackJunction junctionA = new TrackJunction(AisSwitch, AID, Aentry);
			TrackJunction junctionB = new TrackJunction(BisSwitch, BID, Bentry);

			double length = blockRow.getCell(11).getNumericCellValue();
			double grade = blockRow.getCell(12).getNumericCellValue();
			double speedLimit = 0.277778 * blockRow.getCell(13).getNumericCellValue();
			double elevation = blockRow.getCell(14).getNumericCellValue();
			double cmElev = blockRow.getCell(15).getNumericCellValue();

			String cardinalDirection = blockRow.getCell(19).getStringCellValue();

			boolean isStation = blockRow.getCell(21).getBooleanCellValue();
			boolean hasBeacon = blockRow.getCell(22).getBooleanCellValue();
			boolean isUnderground = blockRow.getCell(23).getBooleanCellValue();
			boolean isCrossing = blockRow.getCell(24).getBooleanCellValue();
			boolean hasLight = blockRow.getCell(25).getBooleanCellValue();
			boolean isBidirectional = blockRow.getCell(26).getBooleanCellValue();

			if (!sectionBlocksTable.containsKey(sectionID)) {
				Hashtable<Integer, TrackBlock> sectionBlocks = new Hashtable<Integer, TrackBlock>();
				sectionBlocksTable.put(sectionID, sectionBlocks);
			}

			if (isStation) {

				String stationName = blockRow.getCell(17).getStringCellValue();
				String beaconData = blockRow.getCell(18).getStringCellValue();

				TrackStation station = new TrackStation(lineName, sectionID, blockID, junctionA, junctionB, length,
						grade, speedLimit, elevation, cmElev, stationName, beaconData, cardinalDirection, isUnderground,
						isCrossing, hasLight, isBidirectional);
				blocks.put(blockID, station);
				stations.put(stationName, station);
				sectionBlocksTable.get(sectionID).put(blockID, station);
			} else {
				TrackBlock block = new TrackBlock(lineName, sectionID, blockID, junctionA, junctionB, length, grade,
						speedLimit, elevation, cmElev, null, null, cardinalDirection, isUnderground, isCrossing,
						hasLight, isBidirectional);
				blocks.put(blockID, block);
				sectionBlocksTable.get(sectionID).put(blockID, block);
			}

		}

		// Import Switch Sheet
		XSSFSheet switchSheet = workbook.getSheetAt(1);
		Iterator<Row> switchRowIt = switchSheet.iterator();
		Hashtable<Integer, TrackSwitch> switches = new Hashtable<Integer, TrackSwitch>();

		switchRowIt.next();

		while (switchRowIt.hasNext()) {

			Row switchRow = switchRowIt.next();

			int switchID = (int) switchRow.getCell(0).getNumericCellValue();

			boolean MisSwitch = switchRow.getCell(2).getBooleanCellValue();
			int MID = (int) switchRow.getCell(3).getNumericCellValue();
			int Mentry = (int) switchRow.getCell(4).getNumericCellValue();

			TrackJunction mainJunction = new TrackJunction(MisSwitch, MID, Mentry);

			boolean SisSwitch = switchRow.getCell(6).getBooleanCellValue();
			int SID = (int) switchRow.getCell(7).getNumericCellValue();
			int Sentry = (int) switchRow.getCell(8).getNumericCellValue();

			TrackJunction straightJunction = new TrackJunction(SisSwitch, SID, Sentry);

			boolean DisSwitch = switchRow.getCell(10).getBooleanCellValue();
			int DID = (int) switchRow.getCell(11).getNumericCellValue();
			int Dentry = (int) switchRow.getCell(12).getNumericCellValue();

			TrackJunction divergingJunction = new TrackJunction(DisSwitch, DID, Dentry);

			TrackSwitch switchy = new TrackSwitch(switchID, mainJunction, straightJunction, divergingJunction);

			switches.put(switchID, switchy);
		}

		// Import Section Sheet
		XSSFSheet sectionSheet = workbook.getSheetAt(2);
		Iterator<Row> sectionRowIt = sectionSheet.iterator();
		Hashtable<Character, TrackSection> sections = new Hashtable<Character, TrackSection>();

		sectionRowIt.next();

		while (sectionRowIt.hasNext()) {
			Row sectionRow = sectionRowIt.next();

			lineName = sectionRow.getCell(0).getStringCellValue();
			char sectionID = sectionRow.getCell(1).getStringCellValue().charAt(0);

			int firstBlockID = (int) sectionRow.getCell(3).getNumericCellValue();
			int lastBlockID = (int) sectionRow.getCell(4).getNumericCellValue();

			double startX = sectionRow.getCell(6).getNumericCellValue();
			double startY = sectionRow.getCell(7).getNumericCellValue();
			double endX = sectionRow.getCell(8).getNumericCellValue();
			double endY = sectionRow.getCell(9).getNumericCellValue();

			boolean isCurved = sectionRow.getCell(11).getBooleanCellValue();

			if (isCurved) {
				double centerX = sectionRow.getCell(12).getNumericCellValue();
				double centerY = sectionRow.getCell(13).getNumericCellValue();
				double radius = sectionRow.getCell(14).getNumericCellValue();
				boolean isClockwise = sectionRow.getCell(15).getBooleanCellValue();

				TrackSectionCurve sectionCurve = new TrackSectionCurve(lineName, sectionID, firstBlockID, lastBlockID,
						startX, startY, endX, endY, sectionBlocksTable.get(sectionID), centerX, centerY, radius,
						isClockwise);
				sections.put(sectionID, sectionCurve);
			} else {
				TrackSectionStraight sectionStraight = new TrackSectionStraight(lineName, sectionID, firstBlockID,
						lastBlockID, startX, startY, endX, endY, sectionBlocksTable.get(sectionID));
				sections.put(sectionID, sectionStraight);
			}

		}

		// Create Line
		return new TrackLine(lineName, sections, blocks, stations, switches);
	}

	public String getSwitchConnection(TrackJunction switchJunction) {
		if (!switchJunction.isSwitch())
			throw new IllegalArgumentException("Junction with ID: " + switchJunction.getID() + " is not a switch");
		try {
			TrackJunction blockJunction = track.get(currentLineName).getSwitch(switchJunction.getID())
					.getNextJunction(switchJunction.getEntryPoint());

			if (blockJunction.getID() == -1)
				return "Rail Yard";
			else
				return track.get(currentLineName).getBlock(blockJunction.getID()).getName();

		} catch (SwitchStateException e) {
			return "Disconnected";
		}
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double newTemperature) {
		if (newTemperature <= 39.00) {
			setHeatersOn(true);
		} else {
			setHeatersOn(false);
		}
		temperature = newTemperature;

	}

	private void setHeatersOn(boolean heaterOn) {
		for (TrackLine line : track.values()) {
			for (TrackBlock block : line.getBlocks()) {
				block.setHeated(heaterOn);
			}
		}

	}

	// ===============DEBUG METHODS======================
	public void debugJunction(TrackJunction junction) {
		System.out.println("ID: " + junction.getID() + " Entry: " + junction.getEntryPoint() + " isSwitch: "
				+ junction.isSwitch());
	}



}
