package application.MBO;

public class Operator {
	private String name;
	private String startTime;
	private String endTime;
	private int workingDuration;
	private int breakDuration;
	private boolean onBreak;
	private boolean working;
	
	public Operator(String name, String startTime, String endTime) {
		this.name = name;
		this.startTime = startTime;
		this.endTime = endTime;
		workingDuration = 0;
		breakDuration = 0;
		onBreak = false;
		working = true;
	}
	
	public String getName() {
		return name;
	}
	public String getStartTime() {
		return startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setID(String name) {
		this.name = name;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public int getWorkingDuration() {
		return workingDuration;
	}

	public void setWorkingDuration(int workingDuration) {
		this.workingDuration = workingDuration;
	}

	public boolean isOnBreak() {
		return onBreak;
	}

	public void setOnBreak(boolean onBreak) {
		this.onBreak = onBreak;
	}

	public boolean isWorking() {
		return working;
	}

	public void setWorking(boolean working) {
		this.working = working;
	}
	
	public void workOneMin()
	{
		if (onBreak)
		{
			if (breakDuration == 30)
			{
				onBreak = false;
				working = true;
			}
			else
			{
				breakDuration++;
			}
		}
		if (working)
		{
			if (workingDuration == 240)
			{
				onBreak = true;
				working = false;
			}
			else if (workingDuration == 480)
			{
				onBreak = false;
				working = false;
			}
			else
			{
				workingDuration++;
			}
		}
	}
	
	public boolean availableToWork(int currentTime) {
		if (currentTime >= Integer.valueOf(startTime) && currentTime <= Integer.valueOf(endTime) && !onBreak)
		{
			return true;
		}
		return false;
	}
	
	public void reset() {
		workingDuration = 0;
		breakDuration = 0;
		onBreak = false;
		working = true;
	}
}
