package application.MBO;

public class ScheduleLink {
	private Train train;
	private Operator operator;
	private int duration;
	private boolean linkActive;
	
	public ScheduleLink(Train train, Operator operator) {
		this.train = train;
		this.operator = operator;
		duration = 0;
		linkActive = true;
	}
	
	public Train getTrain() {
		return train;
	}
	public void setTrain(Train train) {
		this.train = train;
	}
	public Operator getOperator() {
		return operator;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	
	public void increaseDuration()
	{
		duration++;
	}
	
	public int getDuration()
	{
		return duration;
	}
	
	public boolean linkActive()
	{
		return linkActive;
	}
	
	public void setLinkActive(boolean linkActive)
	{
		this.linkActive = linkActive;
	}
	
	
}
