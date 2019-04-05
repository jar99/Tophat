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
import application.TrackModel.TrackBlock;
import application.TrackModel.TrackModelSingleton;
import application.TrackModel.TrackTrain;
import application.TrainController.TrainControllerSingleton;

class TrainModel implements TrainInterface {

	//These are some constants that should change
	private final double AVERAGEPASSENGERMASS = 75; // Mass of a passenger
	private static final int BEACONSIZE = 126;
	
	//These are basic information on the train
	private int trainID;
	private boolean isActive = false;
	
	private int passengers = 0;
	private int crewCount = 1;
	
	private double power;
    private double speed;
    
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
    
    //These are fault variables
    private boolean mboConnection = true;
    private boolean railSignalConnection = true;
    private boolean doorOperationState = true;
    private boolean engineOperationState = true;
    
    //These are the train simulation metrics
    private int passengerCap = 200;
    
    private double mass = 10000.0;
    private double length = 100.05; //Need to load in from database
    private double width = 10.0;
    private double height = 15.0;
    
    
    private Queue<String> trainLog = new LinkedList<>();
    

    private TrackBlock currentBlock;
    
    private TrackModelSingleton trackModelSingleton = TrackModelSingleton.getInstance();
    private TrainControllerSingleton trainControllerSingleton = TrainControllerSingleton.getInstance();
	private MBOSingleton mboSingleton = MBOSingleton.getInstance();
    
    public TrainModel(int trainID) {
        this.trainID = trainID;
        isActive = true;
    }

    public TrainModel(int trainID, double x, double y, TrackBlock currentBlock) {
        this(trainID);
        this.x = x;
        this.y = y;
        this.currentBlock = currentBlock;
        this.currentBlock.getAuthority();
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
//    	System.out.println(this + " train runs at " + System.nanoTime());
        callTrainController();
        double distance = speed;
    	callTrackModel(speed);
        callMBO();
        
    }
    
    private void callTrainController() {
    	// TODO fix the train controller program
    	if(!emergencyBrake && trainControllerSingleton.getnumPower() >= 0) {
    		speed = 5.0;
    	}
    	else 
    	{
    		speed = 0.0;
    	}
    	
    }
    
    private void callTrackModel(double distance) {
    	// TODO This needs to be fixed so it works on it's own
    	TrackTrain trackTrain = trackModelSingleton.getTrainLocation(distance);
    	if(trackTrain == null) return;
    	x = trackTrain.getX();
    	y = trackTrain.getY();
    	
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
		this.power = power;
    }
	
	
	public boolean hasPower() {
		return true; //TODO add block power
//		return currentBlock.hasPower();
	}

    
    public double getWeight() {
        return mass;
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
		mass += AVERAGEPASSENGERMASS*numPassengers;
		return passengers+=numPassengers;
	}
	
	@Override
	public int alightPassengers(int numPassengers) {
		//TODO check for edge cases
		mass -= AVERAGEPASSENGERMASS*numPassengers;
		return passengers-=numPassengers;
	}
	
	@Override
	public int getTrackAuthority() {
		if(currentBlock == null) return Integer.MIN_VALUE;
		return currentBlock.getAuthority();
	}

	@Override
	public double getTrackSpeed() {
		if(currentBlock == null) return Double.NaN;
		return currentBlock.getSpeed();
	}
	
	@Override
	public int getMBOAuthority() {
		// TODO add the mbo connection
		if(null == null) return Integer.MIN_VALUE;
		return 0;
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
