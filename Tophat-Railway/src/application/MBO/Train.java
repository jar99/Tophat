package application.MBO;

public class Train {
	private String ID;
	private String departTime;
	private String returnTime;
	private boolean operational;
	private boolean hasDriver;
	
	public Train(String ID, String departTime, String returnTime) {
		this.ID = ID;
		this.departTime = departTime;
		this.returnTime = returnTime;
		hasDriver = false;
	}
	
	public String getID() {
		return ID;
	}
	public String getDepartTime() {
		return departTime;
	}
	public String getReturnTime() {
		return returnTime;
	}
	public void setID(String ID) {
		this.ID = ID;
	}
	public void setDepartTime(String departTime) {
		this.departTime = departTime;
	}
	public void setReturnTime(String returnTime) {
		this.returnTime = returnTime;
	}

	public boolean isOperational(int currentTime) {
		if (currentTime >= Integer.valueOf(departTime) && currentTime <= Integer.valueOf(returnTime))
		{
			return true;
		}
		return false;
	}

	public void setOperational(boolean operational) {
		this.operational = operational;
	}
	
	public boolean hasDriver() {
		return hasDriver;
	}
	
	public void setHasDriver(boolean hasDriver) {
		this.hasDriver = hasDriver;
	}
	
	public void reset() {
		hasDriver = false;
	}
	
}
