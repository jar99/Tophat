package application.MBO;

public class ScheduleLink {
	private String trainID;
	private String operatorName;
	private String trainDepartTime;
	
	public ScheduleLink(String trainID, String operatorName, String trainDepartTime) {
		this.trainID = trainID;
		this.operatorName = operatorName;
		this.trainDepartTime = trainDepartTime;
	}
	
	public String getTrainID() {
		return trainID;
	}
	public void setTrainID(String trainID) {
		this.trainID = trainID;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getTrainDepartTime() {
		return trainDepartTime;
	}
	public void setTrainDepartTime(String trainDepartTime) {
		this.trainDepartTime = trainDepartTime;
	}
	
}
