package application.TrackController;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;


public class TrackControllerCtrlG4 implements Initializable {

	// Links to your Singleton (NO TOUCHY!!)
	private TrackControllerSingleton mySin = TrackControllerSingleton.getInstance();

	private AnimationTimer updateAnimation;
	private int CBIDG4 = mySin.getCurrentBlockIDG4();


	// NOTE: This is where you link to elements in your FXML file
	// Example:(fx:id="counter")
	// WARNING: Your fx:id and variable name Must Match!
	
	@FXML
    private Label currentBlockG4;

	@FXML
    private ChoiceBox<?> choiceBoxBlockG4;

	@FXML
    private Label blockSpeedG4;

	@FXML
    private Label blockAuthorityG4;

	@FXML
    private Label speedUnits;

	@FXML
    private Label authorityUnits;

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
		currentBlockG4.setText(mySin.getCBNameG4());
		mySin.setSwitchG2();
		mySin.setSwitchG3();
		CBIDG4 = mySin.getCurrentBlockIDG4();
		
		blockSpeedG4.setText(mySin.getSpeed(4, CBIDG4));
		blockAuthorityG4.setText(mySin.getAuthority(4, CBIDG4));
		
		if (mySin.isCBOccupied())
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
	}
}