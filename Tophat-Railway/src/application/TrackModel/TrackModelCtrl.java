package application.TrackModel;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.fxml.Initializable;

public class TrackModelCtrl implements Initializable {

	// Links to your Singleton (NO TOUCHY!!)
	private TrackModelSingleton mySin = TrackModelSingleton.getInstance();

	private AnimationTimer updateAnimation;

	// NOTE: This is where you link to elements in your FXML file
	// Example:(fx:id="counter")
	// WARNING: Your fx:id and variable name Must Match!
	// Links to FXML elements

	// Select Block
	@FXML
    private Label currentBlock;
    @FXML
    private ChoiceBox<?> choiceBoxBlock;
	
	// Select Failure
    @FXML
    private Circle iconFailRail;
    @FXML
    private Circle iconFailCircuit;
    @FXML
    private Circle iconFailPower;
    @FXML
    private ChoiceBox<?> choiceBoxFail;
	
	// Value Properties
    @FXML
    private Label propLength;
    @FXML
    private Label propGrade;
    @FXML
    private Label propSpeedLimit;
    @FXML
    private Label propElevation;
    @FXML
    private Label propTotalElevation;
	
	// Boolean Properties
    @FXML
    private Circle iconPropOccupied;
    @FXML
    private Circle iconPropUnderground;
    @FXML
    private Circle iconPropStation;
    @FXML
    private Circle iconPropCrossing;
    @FXML
    private Circle iconPropBeacon;
    @FXML
    private Circle iconPropHeated;
    @FXML
    private Circle iconPropSwitch;
    @FXML
    private Label connectedSwitch;
    

	// NOTE: This is where you build UI functionality
	// functions can be linked through FX Builder or manually
	// Control Functions

    @FXML
    void getLeftBlock() {

    }

    @FXML
    void getRightBlock() {

    }

    @FXML
    void importTrack() {

    }

    @FXML
    void toggleFailure() {

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
		int count = mySin.getCount();
		//counter.setText(Integer.toString(count));

	}
}
