package application.TrackController;
import java.util.HashMap;

import application.TrackModel.*;


public class TrainAuthority {
	
	final private HashMap<String, TrackLine> track = new HashMap<String, TrackLine>();


	//CONSTRUCTOR VARIABLES
	private int trainID;
	private int authority;
	private double suggestedSpeed;
	private int blockIDOccupied;
	
	//OTHER VARIABLES
	private int authorityDisplacement;

	
	public TrainAuthority (int trainID, int authority, double suggestedSpeed) {
		this.trainID = trainID;
		this.authority = authority;
		this.suggestedSpeed = suggestedSpeed;
	}
	
	//GET METHODS
	public int getTrainID() {
		return trainID;
	}
	
	public int getAuthority() {
		return authority;
	}
	
	public double getSuggestedSpeed() {
		return suggestedSpeed;
	}
	
	public int getBlockIDOccupied() {
		return blockIDOccupied;
	}
	
	public int getAuthorityDisplacement() {
		return authorityDisplacement;
	}
	
	
	public void setAuthority(int authority) {
		this.authority = authority;
	}
	
	public void setTrainID(int trainID) {
		this.trainID = trainID;
	}
	
	public void setSuggestedSpeed(double suggestedSpeed) {
		this.suggestedSpeed = suggestedSpeed;
	}
	
	public void setBlockIDOcupied() {
		for(int i = 0; i < 150; i++) {
			if(track.get("green").getBlock(i+1).isOccupied())
				blockIDOccupied = i+1;
		}
	}
	
	public void calculateBlockDisplacement(int authority, int blockIDOccupied) {
		if(authority > blockIDOccupied) 
			authorityDisplacement = authority - blockIDOccupied;
		else
			authorityDisplacement = (150-blockIDOccupied) + authority;
	}
	
	public void setSuggestedSpeedEachBlock(double suggestedSpeed, int blockIDOccupied, int authorityDisplacement) {
		TrackModelInterface trackModInt = TrackModelSingleton.getInstance();
		for(int i = 0; i <= authorityDisplacement; i++) {
			if(i+blockIDOccupied > 150) {
				for(int j = 1; j <= authority; j++) {
					try {
						trackModInt.setSuggestedSpeed("green", j, suggestedSpeed);
					} catch (TrackCircuitFailureException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						trackModInt.setControlAuthority("green", j, true);
					} catch (TrackCircuitFailureException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//track.get("green").getBlock(j).setSuggestedSpeed(suggestedSpeed);
					//track.get("green").getBlock(j).setControlAuthority(true);
				}
			}
			else {
			try {
				trackModInt.setSuggestedSpeed("green", i+blockIDOccupied, suggestedSpeed);
			} catch (TrackCircuitFailureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				trackModInt.setControlAuthority("green", i+blockIDOccupied, true);
			} catch (TrackCircuitFailureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//track.get("green").getBlock(i+blockIDOccupied).setSuggestedSpeed(suggestedSpeed);
			//track.get("green").getBlock(i+blockIDOccupied).setControlAuthority(true);
		}
		}
	}
	
	public void setAuthorityEachBlock(int authority, int blockIDOccupied, int authorityDisplacement) {
		TrackModelInterface trackModInt = TrackModelSingleton.getInstance();
		for(int i = 0; i < authorityDisplacement; i++) {
			if(i+blockIDOccupied > 150) {
				for(int j = 0; j < authority; j++) {
					try {
						trackModInt.setAuthority("green", j+1, authority-j);
					} catch (TrackCircuitFailureException e) {
						//TODO
						e.printStackTrace();
					}
					//track.get("green").getBlock(j).setAuthority(authorityDisplacement-i-j);
				}
			}
			else {
			try {
				trackModInt.setAuthority("green", i+blockIDOccupied, authorityDisplacement-i);
			} catch (TrackCircuitFailureException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//track.get("green").getBlock(i+blockIDOccupied).setAuthority(authorityDisplacement-i);
		}
		}
	}
	
	public double getSuggestedSpeedSpecificBlock(int blockID) {
		return track.get("green").getBlock(blockID).getSuggestedSpeed();
	}
	
	public int getAuthoritySpecificBlock(int blockID) {
		return track.get("green").getBlock(blockID).getAuthority();
	}
}
	
