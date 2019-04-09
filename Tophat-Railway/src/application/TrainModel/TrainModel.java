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

import application.MBO.MBOSingleton;
import application.TrackModel.TrackCircuitFailureException;
import application.TrackModel.TrackModelSingleton;
import application.TrackModel.TrainCrashedException;

class TrainModel implements TrainInterface {

	//These are some constants that should change
	private final double AVERAGEPASSENGERMASS = 75; // Mass of a passenger
	private static final int BEACONSIZE = 126;
	private static final double GRAVITY = 9.8;
	
	private static final double STDFRICTION = 0.002;
	private static final double TIMESCALE = 1e9;
	
	//The minimum force to have acelleration
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
    private boolean serviceBrake = true;
    
    private int mboAuthority = 0;
    private double mboSuggestedSpeed = 0;
    
    //These are fault variables
    private boolean mboConnection = true;
    private boolean railSignalConnection = true;
    private boolean doorOperationState = true;
    private boolean engineOperationState = true;
    
    
    private double passengerWeight = 0.0;

    
    //These are the train simulation metrics
    private int passengerCap = 222;
    
    private double maxPower = 120e3; // 120kw
    private double speedLimit = 70; // 70 kmh
    private double acelerationLimit = 0.5; // 0.5 m/s^2
    
    private double trainWaight = 100000;
    private double length = 100.05; //Need to load in from database
    private double width = 10.0;
    private double height = 15.0;
    private int axelCount = 6;
    private int carCount = 5;
    
    private double crf = 0.0020; // The rolling resistance of the train
    
    private Queue<String> trainLog = new LinkedList<>();
    
    private TrackModelSingleton trModSin = TrackModelSingleton.getInstance();
	private MBOSingleton mboSingleton = MBOSingleton.getInstance();
    
    public TrainModel(int trainID) {
        this.trainID = trainID;
        isActive = true;
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
     * @param delaTime
     */
    void update(int delaTime){
    	if(!isActive || hasCrashed) return;
    	
    	System.out.println(this + " train runs at " + System.nanoTime());
    	double dt = delaTime/TIMESCALE;
    	
    	double angle = Math.atan(trModSin.getTrainBlockGrade(trainID));
    	
    	double f = force(angle);
    	if(trModSin.trainHasPower(trainID)) { // The train should have power
//    		Calculate how much force is applied by power
    		f+=powerF();
    	}
    	
//    	Calculate the acceleration
    	double an = 0;
    	if (f > MINFORCE) {
    		an = f/mass();
    	}
    	
    	double vn = laplace(dt, acceleration, an, speed);
		double xn = laplace(dt, speed, vn, possition);
    	
		
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
		
		
    	
//    	Update everyone else
		callMBO();
        
    }
    
    private void trainCrashed() {
		System.out.println(trainID + ": The train has crashed.");
		hasCrashed = true;
	}

	private double powerF() {
		return power/speed;
	}

	private double force(double angle) {
    	return gravity(angle) - staticF(angle);
	}
    
    private double staticF(double angle) {
		return crf*normal(angle)/axelCount;
	}

	private double gravity(double angle) {
		return GRAVITY*Math.sin(angle)*mass();
	}
	
	private double normal(double angle) {
		return GRAVITY*Math.cos(angle)*mass();
	}
    
    private double mass() {
    	return getWeight()/GRAVITY;
    }
    
    private double laplace(double deltaT, double a, double an, double b) {
		return b+((deltaT)/2)*(an + a);
	}

    private void callMBO(){
        //TODO This needs to change i don't know how it should work.
        mboSingleton.getLocation(x, y);
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

    public void setTemperature(float temperature) {
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
		try {
			return trModSin.getTrainBlockAuthority(trainID);
		} catch (TrackCircuitFailureException e) {
			return Integer.MIN_VALUE;
		}
	}

	@Override
	public double getTrackSpeed() {
		try {
			return trModSin.getTrainSuggestedSpeed(trainID);
		} catch (TrackCircuitFailureException e) {
			return Double.NaN;
		}
	}
	
	@Override
	public int getMBOAuthority() {
		// TODO add the mbo connection
		if(null == null) return Integer.MIN_VALUE;
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
		if(null == null) return Double.NaN;
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
		if(doorOperationState) return false;
		leftDoorState = !leftDoorState;
		return true;
	}

	@Override
	public boolean toggleRightDoors() {
		if(doorOperationState) return false;
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
	public boolean doorOperationState() {
		return doorOperationState;
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

}
