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
		//mySin.setSwitchG5();
		
		//blockSpeedG1.setText(mySin.getSpeed(1, CBIDG1));
		//blockAuthority.setText(mySin.getAuthority());
		
		if (mySin.isCBOccupied())
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
		
		if ((CBIDG1 != 1) && (CBIDG1 != 13))
			nextBlockG1.setText("--");
	}
}
