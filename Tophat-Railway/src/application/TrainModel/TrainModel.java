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

class TrainModel implements TrainInterface {

	//These are some constants that should change
	private final double AVERAGEPASSENGERMASS = 75; // Mass of a passenger
	private static final int BEACONSIZE = 126;
	
	private static final double GRAVITY = 9.8;
	
	private static final double MINFORCE = 100.0;
	
	
	//These are basic information on the train
	private int trainID;
	private boolean isActive = false;
	private boolean hasCrashed = false;
	
	private int passengers = 0;
	private int crewCount = 1;
	
	private double power;
    private double acceleration;
    private double speed;
    private double displacement;
    private double possition;
    
    //These are the actual position read from the block this should change at some point
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
    
    //These are fault variables
    private boolean mboConnection = true;
    private boolean railSignalConnection = true;
    private boolean brakeOperationState = true;
    private boolean engineOperationState = true;
    
    
    private double passengerWeight = 0.0;

    
    //These are the train simulation metrics
    private int passengerCap = 222;
    
    private double maxPower = 120e3; // 120kw
    private double speedLimit = 70; // 70 kmh
    private double acelerationLimit = 0.5; // 0.5 m/s^2
    
    private double trainWaight = 40900.0;
    private double length = 100.05; //Need to load in from database
    private double width = 10.0;
    private double height = 15.0;
    private int axelCount = 6;
    private int carCount = 5;
    
    private double serviceBrakeForce = 6307.724082; 
    private double emergencyBrakeForce = 12089.80449; 
    
    
    private Queue<String> trainLog = new LinkedList<>();
    
    private TrackModelInterface trModSin;
	private MBOInterface mboSin;
    
    public TrainModel(int trainID, TrackModelInterface trModSin, MBOInterface mboSin) {
    	this.mboSin = mboSin;
    	this.trModSin = trModSin;
        this.trainID = trainID;
        isActive = false;
    }

    public TrainModel(int trainID, TrackModelInterface trModSin, MBOInterface mboSingleton, int passangers, double speed) {
		this(trainID, trModSin, mboSingleton);
		this.speed = speed;
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
    void update(){
    	if(!isActive || hasCrashed) return;
    	ClockSingleton clock = ClockSingleton.getInstance();
    	
//    	System.out.println(this + " train runs at " + System.nanoTime());
    	
    	double dt = clock.getRatio(); // The amount of seconds between updates
    	if(dt == 0) return; // If no time has passed there will not be an update to the train physics
    	
    	
    	double v = kmhToms(speed);
    	
    	double grade = trModSin.getTrainBlockGrade(trainID);
    	double angle = 0.0;
    	if(grade != 0) {
    		angle = Math.atan(grade);
    	}
    	
    	double f = gravity(angle);
    	
    	if(trModSin.trainHasPower(trainID) && brakeF() == 0.0) { // The train should have power
//    		Calculate how much force is applied by power
    		f+=powerF();
    	}
    	
//    	System.out.printf("angle= %f\tgravity= %f\tbrakeforce= %f\n",angle, gravity(angle), brakeF());
    	
//    	Calculate the acceleration
    	double an = 0;
    	if (Math.abs(f) > MINFORCE) {
    		an = Math.min(acelerationLimit, f/mass());
    	}
    	
    	if(v > 0) {		
			an -= brakeF()/mass();
    	}
    	
    	
    	double vn = Math.min(laplace(dt, acceleration, an, v), kmhToms(speedLimit)); //This should prevent negative movement.
    	
    	vn = Math.max(0, vn); // Prevents negative movements
    	
		double xn = laplace(dt, v, vn, possition);
    	
		
//		System.out.printf("p= %f\tf= %f\ta= %f\tv= %f\tx= %f\n", power, f, an, vn, xn);
	
		displacement = xn - possition;
		
		possition = xn;
		speed = msTokmh(vn);
		acceleration = an;

//    	Update train location
		try {
			trModSin.updateTrainDisplacement(trainID, displacement);
		} catch (TrainCrashedException e) {
			trainCrashed();
		}
		
		if(trModSin.trainBlockHasBeacon(trainID)) {
			String beaconData = trModSin.getTrainBlockBeaconData(trainID);
			setBeaconData(beaconData);
		}
		
		x = trModSin.getTrainXCoordinate(trainID);
		y = trModSin.getTrainYCoordinate(trainID);
    	
//    	Update everyone else
		callMBO();   
    }
    
    private double kmhToms(double kmh) {
    	return 0.277778*kmh;
    }
    
    private double msTokmh(double ms) {
    	return 3.60000288*ms;
    }
    
    private void trainCrashed() {
		System.out.println(trainID + ": The train has crashed.");
		this.addTrainInformation("The train has crashed.");
		hasCrashed = true;
	}

	private double powerF() {
		if(!engineOperationState) return 0.0;
//		if(speed == 0.0) return power;
		return power/speed;
	}

	private double brakeF() {
		if(!brakeOperationState) return 0.0;
		if(emergencyBrake) return emergencyBrakeF();
		return serviceBrakeF();
	}
	
	private double serviceBrakeF() {
		if(!serviceBrake) return 0.0;
		return serviceBrakeForce;
	}
	
	private double emergencyBrakeF() {
		if(!emergencyBrake) return 0.0;
		return emergencyBrakeForce;
	}
   
	private double gravity(double angle) {
		return GRAVITY*Math.sin(angle)*mass();
	}
	
    private double mass() {
    	return getWeight()/GRAVITY;
    }
    
    private double laplace(double deltaT, double a, double an, double b) {
		return b+((deltaT)/2)*(an + a);
	}

    private void callMBO(){
        //TODO This needs to change i don't know how it should work.
//        mboSin.getLocation(x, y);
    }
    
   
	String poptrainInformation() {
		if(trainLog.isEmpty()) return null;
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
	
	//Getters and setters	
	public double getPower(){
		return power;
		
    }
	
	public void setPower(double power){
		if(power < 0) {
			this.power = 0.0;
			return;
		}
		else if(power > maxPower) {
			this.power = maxPower;
			return;
		}
		this.power = power;
    }
	
	
	public boolean hasPower() {
		if(!isActive) return false;
		return trModSin.trainHasPower(trainID);
	}

    
    public double getWeight() {
        return trainWaight+passengerWeight;
    }
    

    public double getSpeed() {
        return speed;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
    
    /**
     * Simple function to get a string value of location
     * @return
     */
    public String getCoordinates() {
    	return "[" + x + ", " + y + "]";
    }

    @Override
	public int getPassengers() {
		return passengers;
	}


	@Override
	public int boardPassengers(int numPassengers) {
		int remainingPassangers = 0;
		if(numPassengers > passengerCap) {
			remainingPassangers = numPassengers - passengerCap;
			numPassengers -= remainingPassangers;
		}
		passengerWeight += AVERAGEPASSENGERMASS*numPassengers;
		passengers+=numPassengers;
		System.out.println("The train now waights " + passengerWeight + " after " + numPassengers + " boarded. The train now has " + passengers + " people onboard. People were left behind " + remainingPassangers + " passangers.");
		return remainingPassangers;
	}
	
	@Override
	public int alightPassengers(int numPassengers) {
		//TODO check for edge cases
		int exessPassangers = 0;
		if(numPassengers > passengers) {
			exessPassangers = passengers - numPassengers;
			numPassengers -= exessPassangers;
		}
		
		passengerWeight -= AVERAGEPASSENGERMASS*numPassengers;
		passengers-=numPassengers;
		System.out.println("The train now waights " + passengerWeight + " after " + numPassengers + " boarded. The train now has " + passengers + " people onboard. People were left behind " + exessPassangers + " passangers.");
		return exessPassangers;
	}
	
	@Override
	public int getTrackAuthority() {
		if(!isActive) return -9;
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
		if(!isActive) return -9.9;
		try {
			return trModSin.getTrainSuggestedSpeed(trainID);
		} catch (TrackCircuitFailureException e) {
			return Double.NaN;
		}
	}
	
	@Override
	public int getMBOAuthority() {
		// TODO add the mbo connection
		if(!isActive) return -8;
		if(mboSin == null) return Integer.MIN_VALUE;
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
		if(!isActive) return -8.8;
		if(mboSin == null) return Double.NaN;
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
		if(leftDoorState) exchangePassangers();
		return true;
	}

	@Override
	public boolean toggleRightDoors() {
		rightDoorState = !rightDoorState;
		if(rightDoorState) exchangePassangers();
		return true;
	}
	
	private void exchangePassangers() {
		if(!trModSin.trainBlockIsStation(trainID)) return;
		int newPassengers = trModSin.stationPassengerExchange(trainID, passengers, passengerCap);
		System.out.println("Number of passangers " + newPassengers);
		int deltaPassengers = newPassengers-passengers;
		if(deltaPassengers > 0) {
			boardPassengers(deltaPassengers);
		} else {
			alightPassengers(deltaPassengers);
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
		if(!emergencyBrake) return false;
		emergencyBrake = false;
		return true;
		
	}

	@Override
	public boolean triggerEmergencyBrake() {
		if(emergencyBrake) return false;
		emergencyBrake = true;
		return true;
	}

	@Override
	public boolean getServiceBrake() {
		return serviceBrake;
	}
	
	@Override
	public boolean setServiceBrake() {
		if(serviceBrake) return false;
		serviceBrake = true;
		return true;
	}

	@Override
	public boolean unsetServiceBrake() {
		if(!serviceBrake) return false;
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
		if(newData != this.beaconData) {
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
	
    public String toString(){
        return "Train_" + getTrainID();
    }

	public void setEngineFailureState(boolean isFailure) {
		if(isFailure) addTrainInformation("The trains engine stopped working.");
		else addTrainInformation("The trains engine has been fixed.");
		engineOperationState = !isFailure;
	}

	public void setMBOConnectionState(boolean isFailure) {
		if(isFailure) addTrainInformation("The trains lost connection to the MBO.");
		else addTrainInformation("The trains regained the MBO signal.");
		mboConnection = !isFailure;
	}
	
	public void setRailSignalConnectionState(boolean isFailure) {
		if(isFailure) addTrainInformation("The trains lost connection to the rail signal.");
		else addTrainInformation("The trains regained the rail signal.");
		railSignalConnection = !isFailure;
	}
	
	public void setBrakeOperationState(boolean isFailure) {
		if(isFailure) addTrainInformation("The trains brakes stopped working.");
		else addTrainInformation("The trains brakes are fixed.");
		brakeOperationState = !isFailure;
		
	}

	@Override
	public int getID() {
		return trainID;
	}
}
