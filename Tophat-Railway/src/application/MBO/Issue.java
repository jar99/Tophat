package application.MBO;

public class Issue {
	private int startTime;
	private int conflictDuration;
	private int amtOfExcessOperators;
	
	public Issue(int amtOfExcessOperators, int startTime) {
		this.amtOfExcessOperators = amtOfExcessOperators;
		conflictDuration = 0;
		this.setStartTime(startTime);
	}
	
	public Issue() {
		startTime = 0;
		conflictDuration = 0;
		amtOfExcessOperators = 0;
	}
	
	public int getAmtOfExcessOperators() {
		return amtOfExcessOperators;
	}
	public void setAmtOfExcessOperators(int amtOfExcessOperators) {
		this.amtOfExcessOperators = amtOfExcessOperators;
	}
	public int getConflictDuration() {
		return conflictDuration;
	}
	public void setConflictDuration(int conflictDuration) {
		this.conflictDuration = conflictDuration;
	}
	public void increaseDuration() {
		conflictDuration++;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	
}
