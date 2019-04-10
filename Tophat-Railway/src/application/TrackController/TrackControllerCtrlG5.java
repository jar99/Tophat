package application.TrackController;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;


public class TrackControllerCtrlG5 implements Initializable {

	// Links to your Singleton (NO TOUCHY!!)
	private TrackControllerSingleton mySin = TrackControllerSingleton.getInstance();

	private AnimationTimer updateAnimation;
	private int CBIDG5 = mySin.getCurrentBlockIDG5();

	// NOTE: This is where you link to elements in your FXML file
	// Example:(fx:id="counter")
	// WARNING: Your fx:id and variable name Must Match!
	
	@FXML
    private Label currentBlockG5;

	@FXML
    private ChoiceBox<?> choiceBoxBlockG5;

	@FXML
    private Label blockSpeedG5;

	@FXML
    private Label blockAuthorityG5;

	@FXML
    private Label speedUnits;

	@FXML
    private Label authorityUnits;

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
	

	// NOTE: This is where you build UI functionality
	// functions can be linked through FX Builder or manually
	// Control Functions
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
		currentBlockG5.setText(mySin.getCBNameG5());
		//mySin.setSwitchG5();
		
		//blockSpeedG1.setText(mySin.getSpeed(1, CBIDG1));
		//blockAuthority.setText(mySin.getAuthority());
		
		if (mySin.isCBOccupied())
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
	}
}
