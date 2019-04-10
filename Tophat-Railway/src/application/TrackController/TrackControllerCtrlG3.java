package application.TrackController;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;


public class TrackControllerCtrlG3 implements Initializable {

	// Links to your Singleton (NO TOUCHY!!)
	private TrackControllerSingleton mySin = TrackControllerSingleton.getInstance();

	private AnimationTimer updateAnimation;
	private int CBIDG3 = mySin.getCurrentBlockIDG3();

	// NOTE: This is where you link to elements in your FXML file
	// Example:(fx:id="counter")
	// WARNING: Your fx:id and variable name Must Match!
	
	@FXML
    private Label currentBlockG3;

	@FXML
    private ChoiceBox<?> choiceBoxBlockG3;

	@FXML
    private Label blockSpeedG3;

	@FXML
    private Label blockAuthorityG3;

	@FXML
    private Label speedUnits;

	@FXML
    private Label authorityUnits;

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
	

	// NOTE: This is where you build UI functionality
	// functions can be linked through FX Builder or manually
	// Control Functions
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
		currentBlockG3.setText(mySin.getCBNameG3());
		
		//blockSpeedG1.setText(mySin.getSpeed(1, CBIDG1));
		//blockAuthority.setText(mySin.getAuthority());
		
		if (mySin.isCBOccupied())
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
		
		if ((CBIDG3 != 57) && (CBIDG3 != 62))
			nextBlockG3.setText("--");
	}
}
