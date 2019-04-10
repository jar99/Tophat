package application.TrackController;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;


public class TrackControllerCtrlG2 implements Initializable {

	// Links to your Singleton (NO TOUCHY!!)
	private TrackControllerSingleton mySin = TrackControllerSingleton.getInstance();

	private AnimationTimer updateAnimation;
	private int CBIDG2 = mySin.getCurrentBlockIDG2();

	// NOTE: This is where you link to elements in your FXML file
	// Example:(fx:id="counter")
	// WARNING: Your fx:id and variable name Must Match!
	
	@FXML
    private Label currentBlockG2;

	@FXML
    private ChoiceBox<?> choiceBoxBlockG2;

	@FXML
    private Label blockSpeedG2;

	@FXML
    private Label blockAuthorityG2;

	@FXML
    private Label speedUnits;

	@FXML
    private Label authorityUnits;

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
	

	// NOTE: This is where you build UI functionality
	// functions can be linked through FX Builder or manually
	// Control Functions
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
		currentBlockG2.setText(mySin.getCBNameG2());
		mySin.setSwitchG4();
		
		//blockSpeedG2.setText(mySin.getSpeed(1, CBIDG2));
		//blockAuthority.setText(mySin.getAuthority());
		
		if (mySin.isCBOccupied())
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
		
		if ((CBIDG2 != 28) && (CBIDG2 != 150))
				nextBlockG2.setText("--");
	}
}
