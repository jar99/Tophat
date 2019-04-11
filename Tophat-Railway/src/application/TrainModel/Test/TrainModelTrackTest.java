package application.TrainModel.Test;


import application.TrackModel.TrackCircuitFailureException;
import application.TrackModel.TrackModelInterface;
import application.TrackModel.TrainCrashedException;

public class TrainModelTrackTest implements TrackModelInterface {

	private int authority;
	private double grade;
	private double suggestedSpeed;

	public TrainModelTrackTest(double grade, int authority, double suggestedSpeed) {
		// TODO Auto-generated constructor stub
		this.authority = authority;
		this.suggestedSpeed = suggestedSpeed;
		this.grade = grade;
		
	}

	@Override
	public int getScheduledBoarding(String lineName, String stationName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getScheduledAlighting(String lineName, String stationName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void createTrain(String lineName, int trainID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSwitch(String lineName, int switchID, boolean straight) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSuggestedSpeed(String lineName, int blockID, double suggestedSpeed)
			throws TrackCircuitFailureException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAuthority(String lineName, int blockID, int authority) throws TrackCircuitFailureException {
		// TODO Auto-generated method stub
		this.authority = authority;
		
	}

	@Override
	public void setControlAuthority(String lineName, int blockID, boolean ctrlAuthority)
			throws TrackCircuitFailureException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLightStatus(String lineName, int blockID, boolean green) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getOccupancy(String lineName, int blockID) throws TrackCircuitFailureException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setHeating(String lineName, int blockID, boolean heated) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTrainDisplacement(int trainID, double displacement) throws TrainCrashedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getTrainXCoordinate(int trainID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getTrainYCoordinate(int trainID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int stationPassengerExchange(int trainID, int currentPassengers, int capacity) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getTrainBlockLength(int trainID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getTrainBlockGrade(int trainID) {
		// TODO Auto-generated method stub
		return grade;
	}

	@Override
	public double getTrainBlockSpeedLimit(int trainID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getTrainBlockElevation(int trainID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getTrainBlockTotalElevation(int trainID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean trainBlockHasBeacon(int trainID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getTrainBlockBeaconData(int trainID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean trainBlockIsStation(int trainID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean trainBlockIsUnderground(int trainID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean trainBlockHasLight(int trainID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean trainBlockLightIsGreen(int trainID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getTrainSuggestedSpeed(int trainID) throws TrackCircuitFailureException {
		// TODO Auto-generated method stub
		return suggestedSpeed;
	}

	@Override
	public int getTrainBlockAuthority(int trainID) throws TrackCircuitFailureException {
		// TODO Auto-generated method stub
		return authority;
	}

	@Override
	public boolean trainHasPower(int trainID) {
		return true;
	}

	@Override
	public boolean getSwitchState(String lineName, int switchID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCrossing(String lineName, int blockID, boolean crossingOn) {
		// TODO Auto-generated method stub
		
	}

}
