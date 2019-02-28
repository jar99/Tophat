package application.TrackModel;

public class TrackTrain {
	private int ID;
	//private String FXID;
	private double X;
	private double Y;
	
	private boolean add = true;
	private boolean delete = false;
	
	public TrackTrain(int ID, double X, double Y) {
		this.ID = ID;
		//this.FXID = "train" + Integer.toString(ID);
		this.X = X;
		this.Y = Y;
	}
	
	void changeCoord(double X, double Y){
		this.X = X;
		this.Y = Y;
	}
	
	
	public int getID() {
		return ID;
	}
	
	public double getX() {
		return X;
	}
	
	public double getY() {
		return Y;
	}
	
	void added() {
		add = false;
	}
	
	void delete() {
		delete = true;
	}
	
	boolean mustDelete() {
		return delete;
	}
	
	boolean mustAdd() {
		return add;
	}
}
