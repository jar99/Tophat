package application.TrackModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
	private ChoiceBox<String> choiceBoxBlock;

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
		mySin.shiftBlockLeft();
	}

	@FXML
	void getRightBlock() {
		mySin.shiftBlockRight();
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

		// TODO: Implement Selection Block
		ObservableList<String> list = FXCollections.observableArrayList();
		ArrayList<String> blockList = mySin.getBlockList();
		for (String blockName : blockList) {
			list.add(blockName);
		}
		choiceBoxBlock.setItems(list);

		choiceBoxBlock.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> selected, String oldCB, String newCB) {
				if (newCB != null) {
					newCB = newCB.replaceAll("\\D+","");
					mySin.setCB(Integer.parseInt(newCB));
				}
			}
		});

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

		propLength.setText(mySin.getCBLength() + " miles");
		propGrade.setText(mySin.getCBGrade() + " %");
		propSpeedLimit.setText(mySin.getCBSpdLmt() + " mph");
		propElevation.setText(mySin.getCBElev() + " ft");
		propTotalElevation.setText(mySin.getCBTotElev() + " ft");

		if (mySin.isCBOccupied())
			iconPropOccupied.setFill(javafx.scene.paint.Color.GREEN);
		else
			iconPropOccupied.setFill(javafx.scene.paint.Color.WHITE);

		if (mySin.isCBUnderground())
			iconPropUnderground.setFill(javafx.scene.paint.Color.GREEN);
		else
			iconPropUnderground.setFill(javafx.scene.paint.Color.WHITE);

		if (mySin.isCBStation())
			iconPropStation.setFill(javafx.scene.paint.Color.GREEN);
		else
			iconPropStation.setFill(javafx.scene.paint.Color.WHITE);

		if (mySin.isCBCrossing())
			iconPropCrossing.setFill(javafx.scene.paint.Color.GREEN);
		else
			iconPropCrossing.setFill(javafx.scene.paint.Color.WHITE);

		if (mySin.isCBBeacon())
			iconPropBeacon.setFill(javafx.scene.paint.Color.GREEN);
		else
			iconPropBeacon.setFill(javafx.scene.paint.Color.WHITE);

		if (mySin.isCBHeated())
			iconPropHeated.setFill(javafx.scene.paint.Color.GREEN);
		else
			iconPropHeated.setFill(javafx.scene.paint.Color.WHITE);

		if (mySin.isCBSwitch())
			iconPropSwitch.setFill(javafx.scene.paint.Color.GREEN);
		else
			iconPropSwitch.setFill(javafx.scene.paint.Color.WHITE);

	}
}
