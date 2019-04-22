package application.TrainModel;
/**
 * This is the TrainModel object class this contains all the data and functions
 * to simulate the train. This should not be called directly since it contains 
 * some unsafe function to display information better. 
 * 
 * @author jar254
 * @version 1.0
 *
 */

import java.util.LinkedList;
import java.util.Queue;

import application.ClockSingleton;
import application.MBO.MBOInterface;
import application.TrackModel.TrackCircuitFailureException;
import application.TrackModel.TrackModelInterface;
import application.TrackModel.TrainCrashedException;

class TrainModel extends JPhysics implements TrainInterface, TrainModelTrackInterface {

	// These are some constants that should change
	private static final double AVERAGEPASSENGERMASS = 75; // Mass of a passenger
	private static final int BEACONSIZE = 126;
	private static final double POEWRSCALE = 1000.0;
	private final static double MINVELOCITY = 0.0;

	protected static double MAXVELOCITY = kmhToms(70); // 70 kmh
	protected static double MAXACCELERATION = 0.5; // 0.5 m/s^2
	protected static double STDFRICTION = 0.002;

	private static double TRAINWAIGHT = 40900.0;
	private static double MAXPOWER = 120e3; // 120kw
	private static double SERVICEBRAKEFORCE = 6307.724082;
	private static double EMERGENCYBRAKEFORCE = 12089.80449;

	private static int PASSENGERCAP = 222;

	private static double LENGTH = 100.05;
	private static double WIDTH = 10.0;
	private static double HEIGHT = 15.0;
	private static int CARCOUNT = 6;

	static void setMaxVelocity(double maxvelocity) {
		MAXVELOCITY = kmhToms(maxvelocity);
	}

	static void setMaxAcceleration(double maxacceleration) {
		MAXACCELERATION = maxacceleration;
	}

	static void setSTDFriction(double stdfriction) {
		STDFRICTION = stdfriction;
	}

	static void setTrainWaight(double trainwaight) {
		TRAINWAIGHT = trainwaight;
	}

	static void setMaxPower(double maxpower) {
		MAXPOWER = maxpower;
	}

	static void setServiceBrakeForce(double servicebrakeforce) {
		SERVICEBRAKEFORCE = servicebrakeforce;
	}

	static void setEmergencyBrakeForce(double emergencybrakeforce) {
		EMERGENCYBRAKEFORCE = emergencybrakeforce;
	}

	static void setPassengerCap(int passengercap) {
		PASSENGERCAP = passengercap;
	}

	static void setTrainWaight(int trainWaight) {
		TRAINWAIGHT = trainWaight;
	}

	static void setLength(double length) {
		LENGTH = length;
	}

	static void setWidth(double width) {
		WIDTH = width;
	}

	static void setHeight(double height) {
		HEIGHT = height;
	}

	static void setCarCount(int carcount) {
		CARCOUNT = carcount;
	}

	private static double kmhToms(double kmh) {
		return 0.277778 * kmh;
	}

	private static double msTokmh(double ms) {
		return 3.60000288 * ms;
	}

	// These are basic information on the train
	private int trainID;
	private boolean isActive = false;
	private boolean hasCrashed = false;

	private int passengers = 0;
	private int crewCount = 1;

	private double power;

	// These are the actual position read from the block this should change at some
	// point
	private double x, y;

	private double temperature = 20;
	private boolean lights = false;
	private boolean interiorLights = false;
	private boolean leftDoorState = false;
	private boolean rightDoorState = false;

	private String beaconData = "";

	private boolean emergencyBrake = false;
	private boolean serviceBrake = false;

	private int mboAuthority = 0;
	private double mboSuggestedSpeed = 0;

	// These are fault variables
	private boolean mboConnection = true;
	private boolean railSignalConnection = true;
	private boolean brakeOperationState = true;
	private boolean engineOperationState = true;

	private double passengerWeight = 0.0;

	// These are the train simulation metrics
	private int passengerCap = PASSENGERCAP;

	private static double trainWaight = TRAINWAIGHT;
	private double length = LENGTH; // Need to load in from database
	private double width = WIDTH;
	private double height = HEIGHT;
	private int carCount = CARCOUNT;

	private double maxPower = MAXPOWER; // 120kw
	private double serviceBrakeForce = SERVICEBRAKEFORCE;
	private double emergencyBrakeForce = EMERGENCYBRAKEFORCE;

	private Queue<String> trainLog = new LinkedList<>();

	private TrackModelInterface trModSin;
	private MBOInterface mboSin;

	private boolean isDark;

	TrainModel(int trainID, TrackModelInterface trModSin, MBOInterface mboSin) {
		super(STDFRICTION, 1, MINVELOCITY, MAXVELOCITY, MAXACCELERATION);
		super.setMass(mass());
		super.setAngle(0.0);

		this.mboSin = mboSin;
		this.trModSin = trModSin;
		this.trainID = trainID;
		isActive = false;
	}

	TrainModel(int trainID, TrackModelInterface trModSin, MBOInterface mboSingleton, int passangers, double speed) {
		this(trainID, trModSin, mboSingleton);
		super.setVelocity(kmhToms(speed));

		boardPassengers(passangers);

	}

	/**
	 * This function is called when the train should be removed.
	 */
	void remove() {
		System.out.println(this + " train was removed at " + System.nanoTime());
		isActive = false;
	}

	public boolean dispatch() {
		isActive = true;
		return isActive;
	}

	boolean isActive() {
		return isActive;
	}

	/**
	 * To update the train model this function is called.
	 * 
	 */
	void update() {
		if (!isActive || hasCrashed)
			return;
		ClockSingleton clock = ClockSingleton.getInstance();

//    	System.out.println(this + " train runs at " + System.nanoTime());

		long dt = clock.getRatio(); // The amount of seconds between updates
		if (dt == 0)
			return; // If no time has passed there will not be an update to the train physics

		double grade = trModSin.getTrainBlockGrade(trainID);
		double angle = 0.0;
		if (grade != 0) {
			angle = Math.atan(grade);
		}
		super.setAngle(angle);
		super.setMass(mass());

		if (trModSin.trainHasPower(trainID) && brakeF() == 0.0) { // The train should have power
			super.seteIn(engineOperationState ? power : 0.0);
		}

		super.seteOut(brakeF());

		super.update(dt);

//    	Update train location
		try {
			trModSin.updateTrainDisplacement(trainID, displacement);
		} catch (TrainCrashedException e) {
			trainCrashed();
		}

		if (trModSin.trainBlockHasBeacon(trainID)) {
			String beaconData = trModSin.getTrainBlockBeaconData(trainID);
			setBeaconData(beaconData);
		}

		x = trModSin.getTrainXCoordinate(trainID);
		y = trModSin.getTrainYCoordinate(trainID);

		isDark = trModSin.trainBlockIsUnderground(trainID);

//    	Update everyone else
		callMBO();
	}

	private void trainCrashed() {
		System.out.println(trainID + ": The train has crashed.");
		this.addTrainInformation("The train has crashed.");
		hasCrashed = true;
	}

	private double brakeF() {
		if (!brakeOperationState)
			return 0.0;
		if (emergencyBrake)
			return emergencyBrakeF();
		return serviceBrakeF();
	}

	private double serviceBrakeF() {
		if (!serviceBrake)
			return 0.0;
		return serviceBrakeForce;
	}

	private double emergencyBrakeF() {
		if (!emergencyBrake)
			return 0.0;
		return emergencyBrakeForce;
	}

	private double mass() {
		return getWeight() / GRAVITY;
	}

	private void callMBO() {
		// TODO This needs to change i don't know how it should work.
//        mboSin.getLocation(x, y);
	}

	String poptrainInformation() {
		if (trainLog.isEmpty())
			return null;
		return trainLog.remove();
	}

	boolean trainLogEmpty() {
		return trainLog.isEmpty();
	}

	double getAcelleration() {
		return acceleration;
	}

	double getCrew() {
		return crewCount;
	}

	// Getters and setters

	/**
	 * Returns power in watts
	 */
	public double getPower() {
		return power / POEWRSCALE;

	}

	/**
	 * Sets power in kw
	 */
	public void setPower(double power) {
		if (power < 0) {
			this.power = 0.0;
			return;
		}

		// Sets power to watts
		power *= POEWRSCALE;
		if (power > maxPower) {
			power = maxPower;
		}
		this.power = power;
	}

	public boolean hasPower() {
		if (!isActive)
			return false;
		return trModSin.trainHasPower(trainID);
	}

	public double getWeight() {
		return trainWaight + passengerWeight;
	}

	public double getSpeed() {
		return msTokmh(velocity);
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	/**
	 * Simple function to get a string value of location
	 * 
	 * @return
	 */
	public String getCoordinates() {
		return String.format("[ %.3f, %.3f]", x, y);
	}

	@Override
	public int getPassengers() {
		return passengers;
	}

	@Override
	public int boardPassengers(int numPassengers) {
		int remainingPassangers = 0;
		int space = passengerCap - passengers;
		if (numPassengers > space) {
			remainingPassangers = numPassengers - space;
			numPassengers -= remainingPassangers;
		}
		passengerWeight += AVERAGEPASSENGERMASS * numPassengers;
		passengers += numPassengers;
		System.out.println("The train now waights " + passengerWeight + " after " + numPassengers
				+ " boarded. The train now has " + passengers + " people onboard. People were left behind "
				+ remainingPassangers + " passangers.");
		return remainingPassangers;
	}

	@Override
	public int alightPassengers(int numPassengers) {
		// TODO check for edge cases
		int exessPassangers = 0;
		if (numPassengers > passengers) {
			exessPassangers = passengers - numPassengers;
			numPassengers -= exessPassangers;
		}

		passengerWeight -= AVERAGEPASSENGERMASS * numPassengers;
		passengers -= numPassengers;
		System.out.println(
				"The train now waights " + passengerWeight + " after " + numPassengers + " boarded. The train now has "
						+ passengers + " people onboard. People were left behind " + exessPassangers + " passangers.");
		return exessPassangers;
	}

	@Override
	public int getTrackAuthority() {
		if (!isActive)
			return -9;
		try {
			return trModSin.getTrainBlockAuthority(trainID);
		} catch (TrackCircuitFailureException e) {
			return Integer.MIN_VALUE;
		}
	}

	double getSpeedLimit() {
		return trModSin.getTrainBlockSpeedLimit(trainID);
	}

	@Override
	public double getTrackSpeed() {
		if (!isActive)
			return -9.9;
		try {
			return trModSin.getTrainSuggestedSpeed(trainID);
		} catch (TrackCircuitFailureException e) {
			return Double.NaN;
		}
	}

	@Override
	public int getMBOAuthority() {
		// TODO add the mbo connection
		if (!isActive)
			return -8;
		if (mboSin == null)
			return Integer.MIN_VALUE;
		return mboAuthority;
	}

	void setMBOAuthority(int authority) {
		mboAuthority = authority;

	}

	void setMBOSuggestedSpeed(double speed) {
		mboSuggestedSpeed = speed;

	}

	@Override
	public double getMBOSpeed() {
		// TODO add the mbo connection
		if (!isActive)
			return -8.8;
		if (mboSin == null)
			return Double.NaN;
		return mboSuggestedSpeed;
	}

	@Override
	public boolean getLeftDoorState() {
		return leftDoorState;
	}

	@Override
	public boolean getRightDoorState() {
		return rightDoorState;
	}

	@Override
	public boolean toggleLeftDoors() {
		leftDoorState = !leftDoorState;
		if (leftDoorState)
			exchangePassangers();
		return true;
	}

	@Override
	public boolean toggleRightDoors() {
		rightDoorState = !rightDoorState;
		if (rightDoorState)
			exchangePassangers();
		return true;
	}

	private void exchangePassangers() {
		if (!trModSin.trainBlockIsStation(trainID))
			return;
		int newPassengers = trModSin.stationPassengerExchange(trainID, passengers, passengerCap);
		System.out.println("Number of passangers " + newPassengers);
		int deltaPassengers = newPassengers - passengers;
		if (deltaPassengers > 0) {
			boardPassengers(deltaPassengers);
		} else {
			alightPassengers(-deltaPassengers);
		}
	}

	@Override
	public boolean getLightState() {
		return lights;
	}

	@Override
	public boolean toggleLights() {
		lights = !lights;
		return true;
	}

	@Override
	public boolean getInterierLightState() {
		return interiorLights;
	}

	@Override
	public boolean toggleInterierLight() {
		interiorLights = !interiorLights;
		return true;
	}

	@Override
	public boolean getEmergencyBrake() {
		return emergencyBrake;
	}

	@Override
	public boolean resetEmergencyBrake() {
		if (!emergencyBrake)
			return false;
		emergencyBrake = false;
		return true;

	}

	@Override
	public boolean triggerEmergencyBrake() {
		if (emergencyBrake)
			return false;
		emergencyBrake = true;
		return true;
	}

	@Override
	public boolean getServiceBrake() {
		return serviceBrake;
	}

	@Override
	public boolean setServiceBrake() {
		if (serviceBrake)
			return false;
		serviceBrake = true;
		return true;
	}

	@Override
	public boolean unsetServiceBrake() {
		if (!serviceBrake)
			return false;
		serviceBrake = false;
		return true;
	}

	@Override
	public boolean toggleServiceBrake() {
		return serviceBrake = !serviceBrake;
	}

	@Override
	public boolean engineState() {
		return engineOperationState;
	}

	@Override
	public boolean mboConnectionState() {
		return mboConnection;
	}

	@Override
	public boolean railSignalState() {
		return railSignalConnection;
	}

	@Override
	public boolean brakeOperationState() {
		return brakeOperationState;
	}

	/**
	 * This might be removed later.
	 */
	@Override
	public void setBeaconData(String beaconData) {

		String newData = beaconData.substring(0, Math.min(beaconData.length(), BEACONSIZE));
		if (newData != this.beaconData) {
			this.beaconData = newData;
			addTrainInformation("Beacon Data: " + newData);
		}
	}

	@Override
	public String getBeaconData() {
		return beaconData;
	}

	public void addTrainInformation(String message) {
		ClockSingleton clock = ClockSingleton.getInstance();

		trainLog.add(clock.getCurrentTimeString() + ": " + message);
	}

	@Override
	public void trainDerails() {
		addTrainInformation("Train has crashed.");
	}

	public int getTrainID() {
		return trainID;
	}

	public String toString() {
		return "Train_" + getTrainID();
	}

	public void setEngineFailureState(boolean isFailure) {
		if (isFailure)
			addTrainInformation("The trains engine stopped working.");
		else
			addTrainInformation("The trains engine has been fixed.");
		engineOperationState = !isFailure;
	}

	public void setMBOConnectionState(boolean isFailure) {
		if (isFailure)
			addTrainInformation("The trains lost connection to the MBO.");
		else
			addTrainInformation("The trains regained the MBO signal.");
		mboConnection = !isFailure;
	}

	public void setRailSignalConnectionState(boolean isFailure) {
		if (isFailure)
			addTrainInformation("The trains lost connection to the rail signal.");
		else
			addTrainInformation("The trains regained the rail signal.");
		railSignalConnection = !isFailure;
	}

	public void setBrakeOperationState(boolean isFailure) {
		if (isFailure)
			addTrainInformation("The trains brakes stopped working.");
		else
			addTrainInformation("The trains brakes are fixed.");
		brakeOperationState = !isFailure;
	}

	@Override
	public int getID() {
		return trainID;
	}

	@Override
	public boolean isDark() {
		return isDark;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
}
