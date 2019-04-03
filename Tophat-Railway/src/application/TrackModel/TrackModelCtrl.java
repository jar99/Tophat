package application.TrackModel;

import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
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
	private ChoiceBox<String> choiceBoxFail;

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

	// : Link to anchor box for the map
	@FXML
	private AnchorPane trackMap;

	private Map<Integer, Circle> trainIcons = new Hashtable<Integer, Circle>();

	// TODO: implement conversion ration, xdisplacement, ydisplacement

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
	
	//TODO: Create a nextLine() method/button

	@FXML
	void importLine() {
		// TODO: Import Line Block by Block, Switch by Switch, Section by Section

		/*
		 * //: Add 3 lines based on the start/ length of the TrackBlock Objects
		 * ArrayList<TrackBlock> blockList = mySin.getBlockList(); for (TrackBlock
		 * trackBlock : blockList) { Line line = new Line();
		 * line.setStartX(trackBlock.getStartX());
		 * line.setStartY(trackBlock.getStartY()); line.setEndX(trackBlock.getEndX());
		 * line.setEndY(trackBlock.getEndY()); trackMap.getChildren().add(line); }
		 */

	}

	@FXML
	void toggleFailure() {
		// : Activate Toggle Failure Method
		String selFail = choiceBoxFail.getValue();
		if (selFail.equals("Broken Rail"))
			mySin.toggleCBFailRail();
		else if (selFail.equals("Track Circuit"))
			mySin.toggleCBFailCircuit();
		else if (selFail.equals("Power"))
			mySin.toggleCBFailPower();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// TODO: Implement CB Selection Block (See old code)
		/*
		 * ObservableList<String> list = FXCollections.observableArrayList();
		 * ArrayList<String> blockNameList = mySin.getBlockNameList(); for (String
		 * blockName : blockNameList) { list.add(blockName); }
		 * choiceBoxBlock.setItems(list);
		 * 
		 * choiceBoxBlock.getSelectionModel().selectedItemProperty().addListener(new
		 * ChangeListener<String>() {
		 * 
		 * @Override public void changed(ObservableValue<? extends String> selected,
		 * String oldCB, String newCB) { if (newCB != null) { newCB =
		 * newCB.replaceAll("\\D+",""); mySin.setCB(Integer.parseInt(newCB)); } } });
		 */

		// Fill in choiceBoxFail
		ObservableList<String> list2 = FXCollections.observableArrayList();
		list2.addAll("Broken Rail", "Track Circuit", "Power");
		choiceBoxFail.setItems(list2);

		// Starts the automatic update (NO TOUCHY!!)
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
		if (mySin.hasALine()) {
			TrackBlock CB = mySin.getCurrentBlock();

			currentBlock.setText(CB.getName());

			propLength.setText(CB.getLength() * 0.000621371 + " miles");
			propGrade.setText(CB.getGrade() + " %");
			propSpeedLimit.setText(CB.getSpdLmt() * 2.23694 + " mph");
			propElevation.setText(CB.getElev() * 3.28084 + " ft");
			propTotalElevation.setText(CB.getTotElev() * 3.28084 + " ft");

			if (CB.isOccupied())
				iconPropOccupied.setFill(javafx.scene.paint.Color.GREEN);
			else
				iconPropOccupied.setFill(javafx.scene.paint.Color.WHITE);

			if (CB.isUnderground())
				iconPropUnderground.setFill(javafx.scene.paint.Color.GREEN);
			else
				iconPropUnderground.setFill(javafx.scene.paint.Color.WHITE);

			if (CB.isStation())
				iconPropStation.setFill(javafx.scene.paint.Color.GREEN);
			else
				iconPropStation.setFill(javafx.scene.paint.Color.WHITE);

			if (CB.isCrossing())
				iconPropCrossing.setFill(javafx.scene.paint.Color.GREEN);
			else
				iconPropCrossing.setFill(javafx.scene.paint.Color.WHITE);

			if (CB.hasBeacon())
				iconPropBeacon.setFill(javafx.scene.paint.Color.GREEN);
			else
				iconPropBeacon.setFill(javafx.scene.paint.Color.WHITE);

			if (CB.isHeated())
				iconPropHeated.setFill(javafx.scene.paint.Color.GREEN);
			else
				iconPropHeated.setFill(javafx.scene.paint.Color.WHITE);

			if (CB.getJunctionA().isSwitch() || CB.getJunctionB().isSwitch())
				iconPropSwitch.setFill(javafx.scene.paint.Color.GREEN);
			else
				iconPropSwitch.setFill(javafx.scene.paint.Color.WHITE);

			// TODO: Get switch connection

			// : Get Failure Status and display just like above
			if (CB.isFailRail())
				iconFailRail.setFill(javafx.scene.paint.Color.RED);
			else
				iconFailRail.setFill(javafx.scene.paint.Color.WHITE);

			if (CB.isFailCircuit())
				iconFailCircuit.setFill(javafx.scene.paint.Color.RED);
			else
				iconFailCircuit.setFill(javafx.scene.paint.Color.WHITE);

			if (CB.isFailPower())
				iconFailPower.setFill(javafx.scene.paint.Color.RED);
			else
				iconFailPower.setFill(javafx.scene.paint.Color.WHITE);
		}

		// TODO: Get list of trains and coords, add dots to map. Make sure train has
		// fx:id
		Map<Integer, TrainLocation> existingTrains = mySin.getTrainMap(); // getX, Y, ID
		ObservableList<Node> visibleTrains = trackMap.getChildren();
		for (TrainLocation eTrain : existingTrains.values()) {
			if (eTrain.mustAdd()) {

				// Create new icon
				Circle newTrain = new Circle();
				newTrain.setCenterX(eTrain.getCoordX());
				newTrain.setCenterY(eTrain.getCoordY());
				newTrain.setRadius(5);
				newTrain.setFill(javafx.scene.paint.Color.BLUE);

				// add icon to icon map
				trainIcons.put(eTrain.getTrainID(), newTrain);

				// add icon to visible list
				visibleTrains.add(newTrain);

				// signify that the train has been added
				eTrain.added();

			} else if (eTrain.mustDelete()) {

				// Get the icon from the icon map and remove it
				Circle oldTrain = trainIcons.get(eTrain.getTrainID());
				trainIcons.remove(eTrain.getTrainID());

				// Remove train from visible list
				existingTrains.remove(eTrain.getTrainID());

				// Remove train from existing list
				visibleTrains.remove(oldTrain);

			} else {
				// Get the icon from the icon map and update it
				Circle trainIcon = trainIcons.get(eTrain.getTrainID());
				visibleTrains.remove(trainIcon);
				trainIcon.setCenterX(eTrain.getCoordX());
				trainIcon.setCenterY(eTrain.getCoordY());

				visibleTrains.add(trainIcon);

			}

		}

		// TODO: Keep Track of current block on map by drawing a different colored line
		// over it
	}

}
