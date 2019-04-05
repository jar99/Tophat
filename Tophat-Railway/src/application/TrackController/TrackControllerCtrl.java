package application.TrackController;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;


 class TrackControllerCtrl implements Initializable {

	// Links to your Singleton (NO TOUCHY!!)
	private TrackControllerSingleton mySin = TrackControllerSingleton.getInstance();

	private AnimationTimer updateAnimation;

	// NOTE: This is where you link to elements in your FXML file
	// Example:(fx:id="counter")
	// WARNING: Your fx:id and variable name Must Match!
	
	//Block Anchor
	@FXML
	private Label currentBlock;
	@FXML
	private ChoiceBox<String> choiceBoxBlock;
	@FXML
	private Label blockSpeed;
	@FXML
	private Label blockAuthority;
	@FXML
	private Label speedUnits;
	@FXML
	private Label authorityUnits;
	
	
	//Block Info Anchor
	@FXML
	private Circle iconOccupancy;
	@FXML
	private Circle iconOperational;
	
	//Track Controller Function Anchor
	@FXML
	private Circle iconLightsOn;
	@FXML
	private Circle iconLightsOff;
	@FXML
	private Circle iconCrossingOn;
	@FXML
	private Circle iconCrossingOff;
	@FXML
	private Label nextBlock;
	
	@FXML
	private Label selectButton;
	@FXML
	private Label runButton;
	@FXML
	private Label stopButton;
	

	// NOTE: This is where you build UI functionality
	// functions can be linked through FX Builder or manually
	// Control Functions
	@FXML
	void getLeftBlock() {
		mySin.shiftBlockLeft();
	}
	
	@FXML
	void getRightBlock() {
		mySin.shiftBlockRight();
	}
	
	@FXML
	void getBlocklist() {
		
	}
	
	@FXML
	void selectPLC() {
		
	}
	
	@FXML
	void runPLC() {
		
	}
	
	@FXML
	void stopPLC() {
		
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
		currentBlock.setText(mySin.getCBName());
		
		blockSpeed.setText(mySin.getSpeed());
		blockAuthority.setText(mySin.getAuthority());
		
		if (mySin.isCBOccupied())
			iconOccupancy.setFill(javafx.scene.paint.Color.GREEN);
		else
			iconOccupancy.setFill(javafx.scene.paint.Color.WHITE);
		
		

	}
}
