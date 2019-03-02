package application.TrainModel;

import application.MBO.MBOSingleton;
import application.TrackModel.TrackBlock;
import application.TrackModel.TrackModelSingleton;
import application.TrackModel.TrackTrain;
import application.TrainController.TrainControllerSingleton;

class TrainModel implements TrainInterface {
	//These are some constants that should change
	private final double AVGMASS = 75; // Mass of a passenger
	
	//These are basic information on the train
	private int trainID;
	private int passangers = 0;
	private int crewCount = 1;
	
    private double speed;
    private double x, y;
    
    private double temperature;
    private boolean lights = false;
    private boolean interierLights = false;
    private boolean leftDoorState = false;
    private boolean rightDoorState = false;
    
    //These are fault variables
    private boolean mboConnection = true;
    private boolean railSignalConnection = true;
    private boolean doorOperationState = true;
    private boolean engineOperatioState = true;
    
    //These are the train simulation metrics
    private int passangerCap = 200;
    
    private double mass = 10000.0;
    private double length = 100.05; //Need to load in
    private double width = 10.0;
    private double height = 15.0;
    
    
    
    
    

    private TrackBlock currentBlock;
    
    private TrainControllerSingleton tainControllerSingleton = TrainControllerSingleton.getInstance();

    public TrainModel(int trainID) {
        this.trainID = trainID;
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
    public void remove() {
//    	System.out.println(this + " train was removed at " + System.nanoTime());
    }
    
    public boolean dispatch() {
		return true;
	}
    /**
     * To update the train model this function is called.
     * @param delaTime
     */
    public void update(int delaTime){
//    	System.out.println(this + " train runs at " + System.nanoTime());
        callTrainController();
        double distance = speed;
    	callTrackModel(speed);
        callMBO();
    }
    
    private void callTrainController() {
    	
    	if(tainControllerSingleton.getnumPower() >= 0) {
    		speed = 5.0;
    	}
    	else 
    	{
    		speed = 0.0;
    	}
    	
    }
    
    private void callTrackModel(double distance) {
    	TrackModelSingleton trackModelSingleton = TrackModelSingleton.getInstance();
    	// TODO This needs to be fixed so it works on it's own
    	TrackTrain trackTrain = trackModelSingleton.getTrainLocation(distance);
    	x = trackTrain.getX();
    	y = trackTrain.getY();
    	
    }

    private void callMBO(){
        MBOSingleton mboSingleton = MBOSingleton.getInstance();
        //TODO This needs to change i don't know how it should work.
        mboSingleton.getLocation(x, y);
    }
    
    //==================================================
   // Code to talk with other UIs
    
    //This is a hack to get the UIs to work.
    //TODO remove this
    double getPower(){
    	if(tainControllerSingleton.getnumPower() <= 0) {
    		return 0.0;
    	}
        return tainControllerSingleton.getnumPower();
    }
    
	boolean getServiceBrake() {
		//TODO add service brake
		return false;
	}
	

	boolean getEmergancyBrakeState() {
		return tainControllerSingleton.getemergencyBrake();
	}
	
	void setEmergancyBrake(boolean emergencyBrake) {
//		System.out.println("Set the emergancy brake");
		tainControllerSingleton.setemergencyBrake(emergencyBrake);
	}
	
	//==============================================
	//Getters and setters
    
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
    public String getCordinets() {
    	return "[" + x + ", " + y + "]";
    }

	public int getPassangers() {
		return passangers;
	}


	@Override
	public int boardPassangers(int numPassangers) {
		//TODO check for edge cases
		mass += AVGMASS*numPassangers;
		return passangers+=numPassangers;
	}
	
	@Override
	public int alightPassangers(int numPassangers) {
		//TODO check for edge cases
		mass -= AVGMASS*numPassangers;
		return passangers-=numPassangers;
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
		return interierLights;
	}

	@Override
	public boolean toggleInterierLight() {
		interierLights = !interierLights;
		return true;
	}

	@Override
	public boolean engineState() {
		return engineOperatioState;
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
	
	public int getTrainID() {
		return trainID;
	}
	
    public String toString(){
        return String.valueOf(getTrainID());
    }

}
