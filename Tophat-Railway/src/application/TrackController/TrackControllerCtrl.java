package application.TrackController;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;


public class TrackControllerCtrl implements Initializable {

	// Links to your Singleton (NO TOUCHY!!)
	private TrackControllerSingleton mySin = TrackControllerSingleton.getInstance();

	private AnimationTimer updateAnimation;
	private int CBIDG1 = mySin.getCurrentBlockIDG1();
	private int CBIDG2 = mySin.getCurrentBlockIDG2();
	private int CBIDG3 = mySin.getCurrentBlockIDG3();
	private int CBIDG4 = mySin.getCurrentBlockIDG4();
	private int CBIDG5 = mySin.getCurrentBlockIDG5();

	// NOTE: This is where you link to elements in your FXML file
	// Example:(fx:id="counter")
	// WARNING: Your fx:id and variable name Must Match!
	
	@FXML
    private Label currentBlockG1;

	@FXML
    private ChoiceBox<?> choiceBoxBlockG1;

	@FXML
    private Label blockSpeedG1;

	@FXML
    private Label blockAuthorityG1;

	@FXML
    private Label speedUnits;

	@FXML
    private Label authorityUnits;

	@FXML
    private Circle iconOccupancyG1;

	@FXML
    private Circle iconOperationalG1;

	@FXML
    private Circle iconLightsOnG1;

	@FXML
    private Circle iconLightsOffG1;

	@FXML
    private Circle iconCrossingOnG1;

	@FXML
    private Circle iconCrossingOffG1;

	@FXML
    private Label nextBlockG1;
	

	// NOTE: This is where you build UI functionality
	// functions can be linked through FX Builder or manually
	// Control Functions
	@FXML
	void getLeftBlockG1() {
		mySin.shiftBlockLeftG1();
	}
	
	@FXML
	void getRightBlockG1() {
		mySin.shiftBlockRightG1();
	}
	
	@FXML
	void selectPLC() {
		//TODO
	}
	
	@FXML
	void runPLC() {
		//TODO
	}
	
	@FXML
	void stopPLC() {
		//TODO
	}
	
	@FXML
	void setLightsOnG1() {
		mySin.setLightsOnG1();
	}
	
	@FXML
	void setLightsOffG1() {
		mySin.setLightsOffG1();
	}
	
	@FXML
	void setCrossingOnG1() {
		mySin.setCrossingOnG1();
	}
	
	@FXML
	void setCrossingOffG1() {
		mySin.setCrossingOffG1();
	}
	
	@FXML
	void setSwitchStraightG1() {
		mySin.setSwitchStraightG1();
	}
	
	@FXML
	void setSwitchDivergeG1() {
		mySin.setSwitchDivergeG1();
	}
	
	@FXML
    private Label currentBlockG2;

	@FXML
    private ChoiceBox<?> choiceBoxBlockG2;

	@FXML
    private Label blockSpeedG2;

	@FXML
    private Label blockAuthorityG2;

	@FXML
    private Label speedUnits1;

	@FXML
    private Label authorityUnits1;

	@FXML
    private Circle iconOccupancyG2;

	@FXML
    private Circle iconOperationalG2;

	@FXML
    private Circle iconLightsOnG2;

	@FXML
    private Circle iconLightsOffG2;

	@FXML
    private Circle iconCrossingOnG2;

	@FXML
    private Circle iconCrossingOffG2;

	@FXML
    private Label nextBlockG2;
	

	//TRACK CONTROLLER G3
	@FXML
	void getLeftBlockG2() {
		mySin.shiftBlockLeftG2();
	}
	
	@FXML
	void getRightBlockG2() {
		mySin.shiftBlockRightG2();
	}
	
	@FXML
	void selectPLCG2() {
		//TODO
	}
	
	@FXML
	void runPLCG2() {
		//TODO
	}
	
	@FXML
	void stopPLCG2() {
		//TODO
	}
	
	@FXML
	void setLightsOnG2() {
		mySin.setLightsOnG2();
	}
	
	@FXML
	void setLightsOffG2() {
		mySin.setLightsOffG2();
	}
	
	@FXML
	void setCrossingOnG2() {
		mySin.setCrossingOnG2();
	}
	
	@FXML
	void setCrossingOffG2() {
		mySin.setCrossingOffG2();
	}
	
	@FXML
	void setSwitchStraightG2() {
		mySin.setSwitchStraightG2();
	}
	
	@FXML
	void setSwitchDivergeG2() {
		mySin.setSwitchDivergeG2();
	}
	
	@FXML
    private Label currentBlockG3;

	@FXML
    private ChoiceBox<?> choiceBoxBlockG3;

	@FXML
    private Label blockSpeedG3;

	@FXML
    private Label blockAuthorityG3;

	@FXML
    private Label speedUnits2;

	@FXML
    private Label authorityUnits2;

	@FXML
    private Circle iconOccupancyG3;

	@FXML
    private Circle iconOperationalG3;

	@FXML
    private Circle iconLightsOnG3;

	@FXML
    private Circle iconLightsOffG3;

	@FXML
    private Circle iconCrossingOnG3;

	@FXML
    private Circle iconCrossingOffG3;

	@FXML
    private Label nextBlockG3;
	

	//TRACK CONTROLLER G3
	@FXML
	void getLeftBlockG3() {
		mySin.shiftBlockLeftG3();
	}
	
	@FXML
	void getRightBlockG3() {
		mySin.shiftBlockRightG3();
	}
	
	@FXML
	void selectPLCG3() {
		//TODO
	}
	
	@FXML
	void runPLCG3() {
		//TODO
	}
	
	@FXML
	void stopPLCG3() {
		//TODO
	}
	
	@FXML
	void setLightsOnG3() {
		mySin.setLightsOnG3();
	}
	
	@FXML
	void setLightsOffG3() {
		mySin.setLightsOffG3();
	}
	
	@FXML
	void setCrossingOnG3() {
		mySin.setCrossingOnG3();
	}
	
	@FXML
	void setCrossingOffG3() {
		mySin.setCrossingOffG3();
	}
	
	@FXML
	void setSwitchStraightG3() {
		mySin.setSwitchStraightG3();
	}
	
	@FXML
	void setSwitchDivergeG3() {
		mySin.setSwitchDivergeG3();
	}
	
	@FXML
	void createTrain() {
		mySin.createTrain("green", 1);
		mySin.manuallyCreateTrainAuthority();
		mySin.manuallyCreateTrainSpeed();
	}
	
	
	//TRACK CONTROLLER G4
	@FXML
    private Label currentBlockG4;

	@FXML
    private ChoiceBox<?> choiceBoxBlockG4;

	@FXML
    private Label blockSpeedG4;

	@FXML
    private Label blockAuthorityG4;

	@FXML
    private Label speedUnits3;

	@FXML
    private Label authorityUnits3;

	@FXML
    private Circle iconOccupancyG4;

	@FXML
    private Circle iconOperationalG4;

	@FXML
    private Circle iconLightsOnG4;

	@FXML
    private Circle iconLightsOffG4;

	@FXML
    private Circle iconCrossingOnG4;

	@FXML
    private Circle iconCrossingOffG4;

	@FXML
    private Label nextBlockG4;
	

	// NOTE: This is where you build UI functionality
	// functions can be linked through FX Builder or manually
	// Control Functions
	@FXML
	void getLeftBlockG4() {
		mySin.shiftBlockLeftG4();
	}
	
	@FXML
	void getRightBlockG4() {
		mySin.shiftBlockRightG4();
	}
	
	@FXML
	void selectPLCG4() {
		//TODO
	}
	
	@FXML
	void runPLCG4() {
		//TODO
	}
	
	@FXML
	void stopPLCG4() {
		//TODO
	}
	
	@FXML
	void setLightsOnG4() {
		mySin.setLightsOnG4();
	}
	
	@FXML
	void setLightsOffG4() {
		mySin.setLightsOffG4();
	}
	
	@FXML
	void setCrossingOnG4() {
		mySin.setCrossingOnG4();
	}
	
	@FXML
	void setCrossingOffG4() {
		mySin.setCrossingOffG4();
	}
	
	@FXML
	void setSwitchStraightG4() {
		mySin.setSwitchStraightG4();
	}
	
	@FXML
	void setSwitchDivergeG4() {
		mySin.setSwitchDivergeG4();
	}
	
	
	//TRACK CONTROLLER G5
	@FXML
    private Label currentBlockG5;

	@FXML
    private ChoiceBox<?> choiceBoxBlockG5;

	@FXML
    private Label blockSpeedG5;

	@FXML
    private Label blockAuthorityG5;

	@FXML
    private Label speedUnits4;

	@FXML
    private Label authorityUnits4;

	@FXML
    private Circle iconOccupancyG5;

	@FXML
    private Circle iconOperationalG5;

	@FXML
    private Circle iconLightsOnG5;

	@FXML
    private Circle iconLightsOffG5;

	@FXML
    private Circle iconCrossingOnG5;

	@FXML
    private Circle iconCrossingOffG5;

	@FXML
    private Label nextBlockG5;
	

	@FXML
	void getLeftBlockG5() {
		mySin.shiftBlockLeftG5();
	}
	
	@FXML
	void getRightBlockG5() {
		mySin.shiftBlockRightG5();
	}
	
	@FXML
	void selectPLCG5() {
		//TODO
	}
	
	@FXML
	void runPLCG5() {
		//TODO
	}
	
	@FXML
	void stopPLCG5() {
		//TODO
	}
	
	@FXML
	void setLightsOnG5() {
	}
	
	@FXML
	void setLightsOffG5() {
	}
	
	@FXML
	void setCrossingOnG5() {
	}
	
	@FXML
	void setCrossingOffG5() {
	}
	
	@FXML
	void setSwitchStraightG5() {
	}
	
	@FXML
	void setSwitchDivergeG5() {
	}


	// Starts the automatic update (NO TOUCHY!!)
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		updateAnimation = new AnimationTimer() {

			@Override
			public void handle(long now) {
				update();
			}
		};
		updateAnimation.start();
	}

	// NOTE: This is where you get new information from your singleton
	// You can read/change fx elements linked above
	// WARNING: This assumes your singleton is updating its information
	private void update() {
		currentBlockG1.setText(mySin.getCBNameG1());
		mySin.setSwitchG5();
		CBIDG1 = mySin.getCurrentBlockIDG1();
		
		blockSpeedG1.setText(mySin.getSpeed(1, CBIDG1));
		blockAuthorityG1.setText(mySin.getAuthority(1, CBIDG1));
		
		mySin.dualTrackVitalityG1();
		mySin.dualTrackVitalityG4();
		
		if (mySin.isCBOccupied(1))
			iconOccupancyG1.setFill(javafx.scene.paint.Color.GREEN);
		else
			iconOccupancyG1.setFill(javafx.scene.paint.Color.WHITE);
		
		if (mySin.isTrackEmpty()) {
			iconLightsOnG1.setFill(javafx.scene.paint.Color.WHITE);
			iconLightsOffG1.setFill(javafx.scene.paint.Color.WHITE);
			iconCrossingOnG1.setFill(javafx.scene.paint.Color.WHITE);
			iconCrossingOffG1.setFill(javafx.scene.paint.Color.WHITE);
			iconOperationalG1.setFill(javafx.scene.paint.Color.WHITE);
		}
		
		if (mySin.isLightGreen1() && CBIDG1 == 1) {
			iconLightsOnG1.setFill(javafx.scene.paint.Color.GREEN);
			iconLightsOffG1.setFill(javafx.scene.paint.Color.WHITE);
		}
		
		if (!mySin.isLightGreen1() && CBIDG1 == 1) {
			iconLightsOnG1.setFill(javafx.scene.paint.Color.WHITE);
			iconLightsOffG1.setFill(javafx.scene.paint.Color.RED);
		}
		
		if (mySin.isLightGreen13() && CBIDG1 == 13) {
			iconLightsOnG1.setFill(javafx.scene.paint.Color.GREEN);
			iconLightsOffG1.setFill(javafx.scene.paint.Color.WHITE);
		}
		
		if (!mySin.isLightGreen13() && CBIDG1 == 13) {
			iconLightsOnG1.setFill(javafx.scene.paint.Color.WHITE);
			iconLightsOffG1.setFill(javafx.scene.paint.Color.RED);
		}
		
		if (mySin.isCrossingOnG1() && CBIDG1 == 19)
		{
			iconCrossingOnG1.setFill(javafx.scene.paint.Color.RED);
			iconCrossingOffG1.setFill(javafx.scene.paint.Color.RED);
		}
		
		if (!mySin.isCrossingOnG1())
		{
			iconCrossingOnG1.setFill(javafx.scene.paint.Color.WHITE);
			iconCrossingOffG1.setFill(javafx.scene.paint.Color.WHITE);
		}
		
		if (CBIDG1 == 1) {
			if (mySin.isSwitchG5Straight() == true) {
				nextBlockG1.setText("--");
			}
			else if (mySin.isSwitchG5Straight() == false) {
				nextBlockG1.setText("13");
			}
				
		}
		
		if (CBIDG1 == 13) {
			if (mySin.isSwitchG5Straight()) {
				nextBlockG1.setText("12");
			}
			else if (!mySin.isSwitchG5Straight()) {
				nextBlockG1.setText("--");
			}
		}
		
		if ((CBIDG1 != 1) && (CBIDG1 != 13)) {
			nextBlockG1.setText("--");
			iconLightsOnG1.setFill(javafx.scene.paint.Color.WHITE);
			iconLightsOffG1.setFill(javafx.scene.paint.Color.WHITE);
		}
		
		currentBlockG2.setText(mySin.getCBNameG2());
		mySin.setSwitchG4();
		CBIDG2 = mySin.getCurrentBlockIDG2();
		
		blockSpeedG2.setText(mySin.getSpeed(2, CBIDG2));
		blockAuthorityG2.setText(mySin.getAuthority(2, CBIDG2));
		
		if (mySin.isCBOccupied(2))
			iconOccupancyG2.setFill(javafx.scene.paint.Color.GREEN);
		else
			iconOccupancyG2.setFill(javafx.scene.paint.Color.WHITE);
		
		if (mySin.isTrackEmpty()) {
			iconLightsOnG2.setFill(javafx.scene.paint.Color.WHITE);
			iconLightsOffG2.setFill(javafx.scene.paint.Color.WHITE);
			iconCrossingOnG2.setFill(javafx.scene.paint.Color.WHITE);
			iconCrossingOffG2.setFill(javafx.scene.paint.Color.WHITE);
			iconOperationalG2.setFill(javafx.scene.paint.Color.WHITE);
		}
		
		if (mySin.isLightGreen28() && CBIDG2 == 28) {
			iconLightsOnG2.setFill(javafx.scene.paint.Color.GREEN);
			iconLightsOffG2.setFill(javafx.scene.paint.Color.WHITE);
		}
		
		if (!mySin.isLightGreen28() && CBIDG2 == 28) {
			iconLightsOnG2.setFill(javafx.scene.paint.Color.WHITE);
			iconLightsOffG2.setFill(javafx.scene.paint.Color.RED);
		}
		
		if (mySin.isLightGreen150() && CBIDG2 == 150) {
			iconLightsOnG2.setFill(javafx.scene.paint.Color.GREEN);
			iconLightsOffG2.setFill(javafx.scene.paint.Color.WHITE);
		}
		
		if (!mySin.isLightGreen150() && CBIDG2 == 150) {
			iconLightsOnG2.setFill(javafx.scene.paint.Color.WHITE);
			iconLightsOffG2.setFill(javafx.scene.paint.Color.RED);
		}
		
		if (mySin.isCrossingOnG2())
		{
			iconCrossingOnG2.setFill(javafx.scene.paint.Color.RED);
			iconCrossingOffG2.setFill(javafx.scene.paint.Color.RED);
		}
		
		if (!mySin.isCrossingOnG2())
		{
			iconCrossingOnG2.setFill(javafx.scene.paint.Color.WHITE);
			iconCrossingOffG2.setFill(javafx.scene.paint.Color.WHITE);
		}
		
		if (CBIDG2 == 28) {
			if (mySin.isSwitchG4Straight()) {
				nextBlockG2.setText("--");
			}
			else if (!mySin.isSwitchG4Straight()) {
				nextBlockG2.setText("13");
			}
				
		}
		
		if (CBIDG2 == 150) {
			if (mySin.isSwitchG4Straight()) {
				nextBlockG2.setText("12");
			}
			else if (!mySin.isSwitchG4Straight()) {
				nextBlockG2.setText("--");
			}
		}
		
		if ((CBIDG2 != 28) && (CBIDG2 != 150)) {
				nextBlockG2.setText("--");
				iconLightsOnG2.setFill(javafx.scene.paint.Color.WHITE);
				iconLightsOffG2.setFill(javafx.scene.paint.Color.WHITE);
		}
		
currentBlockG3.setText(mySin.getCBNameG3());
		
		blockSpeedG3.setText(mySin.getSpeed(3, CBIDG3));
		blockAuthorityG3.setText(mySin.getAuthority(3, CBIDG3));
		CBIDG3 = mySin.getCurrentBlockIDG3();
		
		if (mySin.isCBOccupied(3))
			iconOccupancyG3.setFill(javafx.scene.paint.Color.GREEN);
		else
			iconOccupancyG3.setFill(javafx.scene.paint.Color.WHITE);
		
		if (mySin.isTrackEmpty()) {
			iconLightsOnG3.setFill(javafx.scene.paint.Color.WHITE);
			iconLightsOffG3.setFill(javafx.scene.paint.Color.WHITE);
			iconCrossingOnG3.setFill(javafx.scene.paint.Color.WHITE);
			iconCrossingOffG3.setFill(javafx.scene.paint.Color.WHITE);
			iconOperationalG3.setFill(javafx.scene.paint.Color.WHITE);
		}
		
		if (mySin.isLightGreen57() && CBIDG3 == 57) {
			iconLightsOnG3.setFill(javafx.scene.paint.Color.GREEN);
			iconLightsOffG3.setFill(javafx.scene.paint.Color.WHITE);
		}
		
		if (!mySin.isLightGreen57() && CBIDG3 == 57) {
			iconLightsOnG3.setFill(javafx.scene.paint.Color.WHITE);
			iconLightsOffG3.setFill(javafx.scene.paint.Color.RED);
		}
		
		if (mySin.isLightGreen62() && CBIDG3 == 62) {
			iconLightsOnG3.setFill(javafx.scene.paint.Color.GREEN);
			iconLightsOffG3.setFill(javafx.scene.paint.Color.WHITE);
		}
		
		if (!mySin.isLightGreen62() && CBIDG3 == 62) {
			iconLightsOnG3.setFill(javafx.scene.paint.Color.WHITE);
			iconLightsOffG3.setFill(javafx.scene.paint.Color.RED);
		}
		
		if (mySin.isCrossingOnG3() && CBIDG3 == 62)
		{
			iconCrossingOnG3.setFill(javafx.scene.paint.Color.RED);
			iconCrossingOffG3.setFill(javafx.scene.paint.Color.RED);
		}
		
		if (!mySin.isCrossingOnG3())
		{
			iconCrossingOnG3.setFill(javafx.scene.paint.Color.WHITE);
			iconCrossingOffG3.setFill(javafx.scene.paint.Color.WHITE);
		}
		
		if (CBIDG3 == 57) {
			if (mySin.isSwitchG6Straight()) {
				nextBlockG3.setText("Yard");
			}
			else if (!mySin.isSwitchG6Straight()) {
				nextBlockG3.setText("58");
			}
				
		}
		
		if (CBIDG3 == 62) {
			if (mySin.isSwitchG1Straight()) {
				nextBlockG3.setText("--");
			}
			else if (!mySin.isSwitchG1Straight()) {
				nextBlockG3.setText("63");
			}
		}
		
		if ((CBIDG3 != 57) && (CBIDG3 != 62)) {
			nextBlockG3.setText("--");
			iconLightsOnG3.setFill(javafx.scene.paint.Color.WHITE);
			iconLightsOffG3.setFill(javafx.scene.paint.Color.WHITE);
		}
		
		currentBlockG4.setText(mySin.getCBNameG4());
		mySin.setSwitchG2();
		mySin.setSwitchG3();
		CBIDG4 = mySin.getCurrentBlockIDG4();
		
		blockSpeedG4.setText(mySin.getSpeed(4, CBIDG4));
		blockAuthorityG4.setText(mySin.getAuthority(4, CBIDG4));
		
		if (mySin.isCBOccupied(4))
			iconOccupancyG4.setFill(javafx.scene.paint.Color.GREEN);
		else
			iconOccupancyG4.setFill(javafx.scene.paint.Color.WHITE);
		
		if (mySin.isTrackEmpty()) {
			iconLightsOnG4.setFill(javafx.scene.paint.Color.WHITE);
			iconLightsOffG4.setFill(javafx.scene.paint.Color.WHITE);
			iconCrossingOnG4.setFill(javafx.scene.paint.Color.WHITE);
			iconCrossingOffG4.setFill(javafx.scene.paint.Color.WHITE);
			iconOperationalG4.setFill(javafx.scene.paint.Color.WHITE);
		}
		
		if (mySin.isLightGreen76() && CBIDG4 == 76) {
			iconLightsOnG4.setFill(javafx.scene.paint.Color.GREEN);
			iconLightsOffG4.setFill(javafx.scene.paint.Color.WHITE);
		}
		
		if (!mySin.isLightGreen76() && CBIDG4 == 76) {
			iconLightsOnG4.setFill(javafx.scene.paint.Color.WHITE);
			iconLightsOffG4.setFill(javafx.scene.paint.Color.RED);
		}
		
		if (mySin.isLightGreen77() && CBIDG4 == 77) {
			iconLightsOnG4.setFill(javafx.scene.paint.Color.GREEN);
			iconLightsOffG4.setFill(javafx.scene.paint.Color.WHITE);
		}
		
		if (!mySin.isLightGreen77() && CBIDG4 == 77) {
			iconLightsOnG4.setFill(javafx.scene.paint.Color.WHITE);
			iconLightsOffG4.setFill(javafx.scene.paint.Color.RED);
		}
		
		if (mySin.isLightGreen85() && CBIDG4 == 85) {
			iconLightsOnG4.setFill(javafx.scene.paint.Color.GREEN);
			iconLightsOffG4.setFill(javafx.scene.paint.Color.WHITE);
		}
		
		if (!mySin.isLightGreen85() && CBIDG4 == 85) {
			iconLightsOnG4.setFill(javafx.scene.paint.Color.WHITE);
			iconLightsOffG4.setFill(javafx.scene.paint.Color.RED);
		} 
		
		if (mySin.isLightGreen100() && CBIDG4 == 100) {
				iconLightsOnG4.setFill(javafx.scene.paint.Color.GREEN);
				iconLightsOffG4.setFill(javafx.scene.paint.Color.WHITE);
			}
			
		if (!mySin.isLightGreen100() && CBIDG4 == 100) {
				iconLightsOnG4.setFill(javafx.scene.paint.Color.WHITE);
				iconLightsOffG4.setFill(javafx.scene.paint.Color.RED);
		} 		
		
		if (mySin.isCrossingOnG4())
		{
			iconCrossingOnG4.setFill(javafx.scene.paint.Color.RED);
			iconCrossingOffG4.setFill(javafx.scene.paint.Color.RED);
		}
		
		if (!mySin.isCrossingOnG4())
		{
			iconCrossingOnG4.setFill(javafx.scene.paint.Color.WHITE);
			iconCrossingOffG4.setFill(javafx.scene.paint.Color.WHITE);
		}
		
		if (CBIDG4 == 76) {
			if (mySin.isSwitchG2Straight()) {
				nextBlockG4.setText("77");
			}
			else if (!mySin.isSwitchG2Straight()) {
				nextBlockG4.setText("--");
			}
				
		}
		
		if (CBIDG4 == 77) {
			if (mySin.isSwitchG2Straight()) {
				nextBlockG4.setText("--");
			}
			else if (!mySin.isSwitchG4Straight()) {
				nextBlockG4.setText("101");
			}
		}
		
		if (CBIDG4 == 85) {
			if (mySin.isSwitchG3Straight()) {
				nextBlockG4.setText("86");
			}
			else if (!mySin.isSwitchG3Straight()) {
				nextBlockG4.setText("--");
			}
				
		}
		
		if (CBIDG4 == 100) {
			if (mySin.isSwitchG3Straight()) {
				nextBlockG4.setText("--");
			}
			else if (!mySin.isSwitchG3Straight()) {
				nextBlockG4.setText("85");
			}
		}
		
		if ((CBIDG4 != 76) && (CBIDG4 != 77) && (CBIDG4 != 85) && (CBIDG4 != 100)) {
			nextBlockG4.setText("--");
			iconLightsOnG4.setFill(javafx.scene.paint.Color.WHITE);
			iconLightsOffG4.setFill(javafx.scene.paint.Color.WHITE); 
		}
		
		currentBlockG5.setText(mySin.getCBNameG5());
		CBIDG5 = mySin.getCurrentBlockIDG5();

		
		blockSpeedG5.setText(mySin.getSpeed(5, CBIDG5));
		blockAuthorityG5.setText(mySin.getAuthority(5, CBIDG5));
		
		if (mySin.isCBOccupied(5))
			iconOccupancyG5.setFill(javafx.scene.paint.Color.GREEN);
		else
			iconOccupancyG5.setFill(javafx.scene.paint.Color.WHITE);
		
		if (mySin.isTrackEmpty()) {
			iconLightsOnG5.setFill(javafx.scene.paint.Color.WHITE);
			iconLightsOffG5.setFill(javafx.scene.paint.Color.WHITE);
			iconCrossingOnG5.setFill(javafx.scene.paint.Color.WHITE);
			iconCrossingOffG5.setFill(javafx.scene.paint.Color.WHITE);
			iconOperationalG5.setFill(javafx.scene.paint.Color.WHITE);
		}
		
		nextBlockG5.setText("--");
		iconLightsOnG5.setFill(javafx.scene.paint.Color.WHITE);
		iconLightsOffG5.setFill(javafx.scene.paint.Color.WHITE);
	}
	
}

