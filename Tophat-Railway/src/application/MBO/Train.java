package application.MBO;

public class Train {
	private int ID;
	private int departTime;
	private int returnTime;
	
	public Train(int ID, int departTime, int returnTime) {
		this.ID = ID;
		this.departTime = departTime;
		this.returnTime = returnTime;
	}
	
	public int getID() {
		return ID;
	}
	public int getDepartTime() {
		return departTime;
	}
	public int getReturnTime() {
		return returnTime;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	public void setDepartTime(int departTime) {
		this.departTime = departTime;
	}
	public void setReturnTime(int returnTime) {
		this.returnTime = returnTime;
	}
}
