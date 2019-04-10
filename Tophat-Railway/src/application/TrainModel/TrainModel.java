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

import application.MBO.MBOInterface;
import application.TrackModel.TrackCircuitFailureException;
import application.TrackModel.TrackModelInterface;
import application.TrackModel.TrainCrashedException;

class TrainModel implements TrainInterface {

	//These are some constants that should change
	private final double AVERAGEPASSENGERMASS = 75; // Mass of a passenger
	private static final int BEACONSIZE = 126;
	
	private static final double GRAVITY = 9.8;
	private static final double TIMESCALE = 1e9;
	
	private static final double MINFORCE = 10.0;
	
	
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
    
    private double temperature;
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
     * @param deltaTime
     */
    void update(long deltaTime){
    	if(!isActive || hasCrashed) return;
    	
//    	System.out.println(this + " train runs at " + System.nanoTime());
    	double dt = deltaTime/TIMESCALE;
    	
    	double grade = trModSin.getTrainBlockGrade(trainID);
    	double angle = 0.0;
    	if(grade != 0) {
    		angle = Math.atan(grade);
    	}
    	
    	double f = gravity(angle);
    	
    	if(trModSin.trainHasPower(trainID)) { // The train should have power
//    		Calculate how much force is applied by power
    		f+=powerF();
    	}
    	
//    	System.out.printf("angle= %f\tgravity= %f\tbrakeforce= %f\n",angle, gravity(angle), brakeF());
    	
//    	Calculate the acceleration
    	double an = 0;
    	if (Math.abs(f) > MINFORCE) {
    		an = Math.min(acelerationLimit, f/mass());
    	}
    	
    	if(speed > 0) {		
			an -= brakeF()/mass();
    	}
    	
    	
    	double vn = Math.min(laplace(dt, acceleration, an, speed), speedLimit); //This should prevent negative movement.
    	
    	vn = Math.max(0, vn); // Prevents negative movements
    	
		double xn = laplace(dt, speed, vn, possition);
    	
		
//		System.out.printf("p= %f\tf= %f\ta= %f\tv= %f\tx= %f\n", power, f, an, vn, xn);
		
		
		possition = xn;
		speed = vn;
		acceleration = an;
		displacement = xn - possition;
		
//    	Update train location
		try {
			trModSin.updateTrainDisplacement(trainID, displacement);
		} catch (TrainCrashedException e) {
			trainCrashed();
		}
		
		x = trModSin.getTrainXCoordinate(trainID);
		y = trModSin.getTrainYCoordinate(trainID);
//		
//		
//    	
////    	Update everyone else
//		callMBO();
//        
    }
    
    private void trainCrashed() {
		System.out.println(trainID + ": The train has crashed.");
		hasCrashed = true;
	}

	private double powerF() {
		if(!engineOperationState) return 0.0;
		if(speed == 0.0) return 0.0; // prevents NaN
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
	
	//Getters and setters
	
	public double getPower(){
		return power;
		
    }
	
	public void setPower(double power){
		if(power < 0) this.power = 0;
		else if(power > maxPower) this.power = maxPower;
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
		//TODO check for edge cases
		passengerWeight += AVERAGEPASSENGERMASS*numPassengers;
		System.out.println("The train now waights " + passengerWeight + " after " + numPassengers + " boarded.");
		return passengers+=numPassengers;
	}
	
	@Override
	public int alightPassengers(int numPassengers) {
		//TODO check for edge cases
		passengerWeight -= AVERAGEPASSENGERMASS*numPassengers;
		return passengers-=numPassengers;
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
		return 0;
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
		return 0;
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
		return true;
	}

	@Override
	public boolean toggleRightDoors() {
		rightDoorState = !rightDoorState;
		return true;
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
	public void setBeacon(String beaconData) {   	
		this.beaconData = beaconData.substring(0, BEACONSIZE); 	
	}

	@Override
	public String getBeacon() {
		return beaconData;
	}
	
	public void addTrainInformation(String message) {
		trainLog.add(System.nanoTime() + ": " + message);
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
}
