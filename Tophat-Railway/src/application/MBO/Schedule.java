package application.MBO;

import java.util.ArrayList;

public class Schedule {
	private String line;
	private int throughPut;
	public ArrayList<ScheduleLink> scheduleLinks;
	
	public Schedule(String line, int throughPut, ArrayList<ScheduleLink> scheduleLinks) {
		this.setLine(line);
		this.setThroughPut(throughPut);
		this.scheduleLinks = scheduleLinks;
	}

	public void convertToFile()
	{
		//scheduleLinks.add(scheduleLink);
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}
	
	public int getThroughPut() {
		return throughPut;
	}

	public void setThroughPut(int throughPut) {
		this.throughPut = throughPut;
	}

	
}
