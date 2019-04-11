package application.TrackModel;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.Hashtable;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.fxml.Initializable;

public class TrackModelCtrl implements Initializable {

	// implement conversion ratio, xdisplacement, ydisplacement
	double leastX = -3130.07;
	double mostX = 372.67;
	double leastY = 0;
	double mostY = 3554.92;
	double windowX = 481;
	double windowY = 502;
	
	double dispX = 0 - leastX;
	double dispY = 0 - leastY;
	double ratioX = windowX / (mostX + dispX + 200);
	double ratioY = windowY / (mostY + dispY + 200);


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

	// Set Temperature
	@FXML
	private TextField textTemperature;

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
	@FXML
	private Label propDirection;

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

	// Station Properties
	@FXML
	private Label propSchdBoarders;
	@FXML
	private Label propSchdAlighters;
	@FXML
	private Label propBoarding;
	@FXML
	private Label propAlighting;

	// : Link to anchor box for the map
	@FXML
	private AnchorPane trackMap;
	@FXML
	private ChoiceBox<?> choiceBoxLine;

	private Map<Integer, Circle> trainIcons = new Hashtable<Integer, Circle>();
	
	private Arc currentArc = new Arc();
	private Line currentLine = new Line();

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

	// TODO: Create a Line Selection Box Listener

	@FXML
	void importLine() {
		mySin.importLine("green.xlsx");

		for (TrackSection section : mySin.getLineSection("green")) {
			
			//System.out.println("RatioX: " + ratioX + "RatioY: " + ratioY);
			
			if (section instanceof TrackSectionStraight) {
				drawLine(section.getStartX(), section.getStartY(), section.getEndX(), section.getEndY());
			} else if (section instanceof TrackSectionCurve) {
				TrackSectionCurve sectionCurve = (TrackSectionCurve) section;
				drawArc(sectionCurve.getCenterX(), sectionCurve.getCenterY(), sectionCurve.getRadius(), sectionCurve.getStartAngle(), sectionCurve.getLengthAngle());
				//System.out.println("Section: " + sectionCurve.getSectionID() + " Length: " + sectionCurve.getLength() + " CX: " + sectionCurve.getCenterX() + " CY: " + sectionCurve.getCenterY() + " R: " + sectionCurve.getRadius() + " SAng: " + Math.toDegrees(sectionCurve.getStartAngle()) + " LAng: " + Math.toDegrees(sectionCurve.getLengthAngle()));
			}
			
			
		}

	}

	private void drawLine(double startX, double startY, double endX, double endY) {
		startX = (startX + dispX) * ratioX + 10;
		startY = (startY + dispY) * ratioY + 10;
		endX = (endX + dispX) * ratioX + 10;
		endY = (endY + dispY) * ratioY + 10;		//System.out.println(startX + " " + startY + " " + endX + " " + endY);

		Line line = new Line();
		line.setStartX(startX);
		line.setStartY(startY);
		line.setEndX(endX);
		line.setEndY(endY);
		line.setStroke(Color.GREY);
		line.setStrokeWidth(3);

		trackMap.getChildren().add(line);
	}
	
	private void drawArc(double centerX, double centerY, double radius, double startAngle, double lengthAngle) {
		centerX = (centerX + dispX) * ratioX + 10;
		centerY = (centerY + dispY) * ratioY + 10;
		double radiusX = radius * ratioX;
		double radiusY = radius * ratioY;
		
		Arc arc = new Arc();
		arc.setCenterX(centerX);
		arc.setCenterY(centerY);
		arc.setRadiusX(radiusX);
		arc.setRadiusY(radiusY);
		arc.setStartAngle(Math.toDegrees(startAngle));
		arc.setLength(Math.toDegrees(lengthAngle));
		arc.setType(ArcType.OPEN);
		arc.setFill(Color.TRANSPARENT); 
		arc.setStroke(Color.GREY);
		arc.setStrokeWidth(3);
		
		trackMap.getChildren().add(arc);
	}

	@FXML
	void setTemperature(ActionEvent event) {
		// : Evaluate temperature to change heaters. Check for bad input.
		try {
			double newTemperature = Double.parseDouble(textTemperature.getText());
			mySin.setTemperature(newTemperature);
		} catch (NumberFormatException e) {
			double curTemperature = mySin.getTemperature();
			textTemperature.setText(Double.toString(curTemperature));
		}
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

		trackMap.getChildren().add(currentLine);
		trackMap.getChildren().add(currentArc);
		
		// TODO: Implement CB Selection Box (See old code)
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

		DecimalFormat df1 = new DecimalFormat("#.##");

		if (mySin.hasALine()) {
			
			TrackBlock cBlock = mySin.getCurrentBlock();
			int cBlockID = cBlock.getBlockID();
			
			TrackSection cSection = mySin.getCurrentSection();
			
			if (cSection instanceof TrackSectionStraight) {
				TrackSectionStraight cSectionStraight = (TrackSectionStraight) cSection;
				moveCurrentLine(cSectionStraight.getBlockStartX(cBlockID), cSectionStraight.getBlockStartY(cBlockID), cSectionStraight.getBlockEndX(cBlockID), cSectionStraight.getBlockEndY(cBlockID));
				hideCurrentArc();
			} else if (cSection instanceof TrackSectionCurve) {
				TrackSectionCurve cSectionCurve = (TrackSectionCurve) cSection;
				moveCurrentArc(cSectionCurve.getCenterX(), cSectionCurve.getCenterY(), cSectionCurve.getRadius(), cSectionCurve.getBlockStartAngle(cBlockID), cSectionCurve.getBlockLengthAngle(cBlockID));
				hideCurrentLine();
				//System.out.println("Section: " + cSectionCurve.getSectionID() + " Length: " + cSectionCurve.getLength() + " CX: " + cSectionCurve.getCenterX() + " CY: " + cSectionCurve.getCenterY() + " R: " + cSectionCurve.getRadius() + " SAng: " + Math.toDegrees(cSectionCurve.getStartAngle()) + " LAng: " + Math.toDegrees(cSectionCurve.getLengthAngle()));
			}

			currentBlock.setText(cBlock.getName());

			propLength.setText(df1.format(cBlock.getLength() * 0.000621371) + " miles");
			propGrade.setText(df1.format(cBlock.getGrade()) + " %");
			propSpeedLimit.setText(df1.format(cBlock.getSpdLmt() * 2.23694) + " mph");
			propElevation.setText(df1.format(cBlock.getElev() * 3.28084) + " ft");
			propTotalElevation.setText(df1.format(cBlock.getTotElev() * 3.28084) + " ft");
			propDirection.setText(cBlock.getCardinalDirection());

			if (cBlock.isOccupied())
				iconPropOccupied.setFill(javafx.scene.paint.Color.GREEN);
			else
				iconPropOccupied.setFill(javafx.scene.paint.Color.WHITE);

			if (cBlock.isUnderground())
				iconPropUnderground.setFill(javafx.scene.paint.Color.GREEN);
			else
				iconPropUnderground.setFill(javafx.scene.paint.Color.WHITE);

			if (cBlock.isStation())
				iconPropStation.setFill(javafx.scene.paint.Color.GREEN);
			else
				iconPropStation.setFill(javafx.scene.paint.Color.WHITE);

			if (cBlock.isCrossing())
				iconPropCrossing.setFill(javafx.scene.paint.Color.GREEN);
			else
				iconPropCrossing.setFill(javafx.scene.paint.Color.WHITE);

			//TODO: Get Crossing On state
			
			if (cBlock.hasBeacon())
				iconPropBeacon.setFill(javafx.scene.paint.Color.GREEN);
			else
				iconPropBeacon.setFill(javafx.scene.paint.Color.WHITE);

			if (cBlock.isHeated())
				iconPropHeated.setFill(javafx.scene.paint.Color.GREEN);
			else
				iconPropHeated.setFill(javafx.scene.paint.Color.WHITE);

			if (cBlock.getJunctionA().isSwitch() || cBlock.getJunctionB().isSwitch())
				iconPropSwitch.setFill(javafx.scene.paint.Color.GREEN);
			else
				iconPropSwitch.setFill(javafx.scene.paint.Color.WHITE);

			// : Get switch connection
			if (cBlock.getJunctionA().isSwitch()) {
				TrackJunction junctionA = cBlock.getJunctionA();
				String switchConnection = mySin.getSwitchConnection(junctionA);
				connectedSwitch.setText(switchConnection);
			} else if (cBlock.getJunctionB().isSwitch()) {
				TrackJunction junctionB = cBlock.getJunctionB();
				String switchConnection = mySin.getSwitchConnection(junctionB);
				connectedSwitch.setText(switchConnection);
			} else {
				connectedSwitch.setText("--");
			}

			// : Get Failure Status and display just like above
			if (cBlock.isFailRail())
				iconFailRail.setFill(javafx.scene.paint.Color.RED);
			else
				iconFailRail.setFill(javafx.scene.paint.Color.WHITE);

			if (cBlock.isFailCircuit())
				iconFailCircuit.setFill(javafx.scene.paint.Color.RED);
			else
				iconFailCircuit.setFill(javafx.scene.paint.Color.WHITE);

			if (cBlock.isFailPower())
				iconFailPower.setFill(javafx.scene.paint.Color.RED);
			else
				iconFailPower.setFill(javafx.scene.paint.Color.WHITE);

			// : Get Station Properties if a Station, otherwise erase
			if (cBlock.isStation()) {
				TrackStation cStation = mySin.getCurrentStation();
				propSchdBoarders.setText(Integer.toString(cStation.getScheduledBoarders()));
				propSchdAlighters.setText(Integer.toString(cStation.getScheduledAlighters()));
				propBoarding.setText(Integer.toString(cStation.getBoarding()));
				propAlighting.setText(Integer.toString(cStation.getAlighting()));
			} else {
				propSchdBoarders.setText("--");
				propSchdAlighters.setText("--");
				propBoarding.setText("--");
				propAlighting.setText("--");
			}
		}

		// TODO: Get list of trains and coords, add dots to map. Make sure train has
		// fx:id
		Map<Integer, TrainLocation> existingTrains = mySin.getTrainMap(); // getX, Y, ID
		ObservableList<Node> visibleTrains = trackMap.getChildren();
		for (TrainLocation eTrain : existingTrains.values()) {
			if (eTrain.mustAdd()) {

				// Create new icon
				Circle newTrain = new Circle();
				
				double centerX = (eTrain.getCoordX() + dispX) * ratioX + 10;
				double centerY = (eTrain.getCoordY() + dispY) * ratioY + 10;
				
				newTrain.setCenterX(centerX);
				newTrain.setCenterY(centerY);
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
				
				double centerX = (eTrain.getCoordX() + dispX) * ratioX + 10;
				double centerY = (eTrain.getCoordY() + dispY) * ratioY + 10;
				
				trainIcon.setCenterX(centerX);
				trainIcon.setCenterY(centerY);

				visibleTrains.add(trainIcon);

			}

		}

		// TODO: Keep Track of current block on map by drawing a different colored line
		// over it
	}

	private void hideCurrentLine() {
		currentLine.setVisible(false);
	}

	private void moveCurrentArc(double centerX, double centerY, double radius, double blockStartAngle,
			double blockLengthAngle) {
		centerX = (centerX + dispX) * ratioX + 10;
		centerY = (centerY + dispY) * ratioY + 10;
		double radiusX = radius * ratioX;
		double radiusY = radius * ratioY;
		
		currentArc.setCenterX(centerX);
		currentArc.setCenterY(centerY);
		currentArc.setRadiusX(radiusX);
		currentArc.setRadiusY(radiusY);
		currentArc.setStartAngle(Math.toDegrees(blockStartAngle));
		currentArc.setLength(Math.toDegrees(blockLengthAngle));
		
		currentArc.setType(ArcType.OPEN);
		currentArc.setFill(Color.TRANSPARENT);
		currentArc.setStroke(Color.YELLOW);
		currentArc.setStrokeWidth(4);
		currentArc.toFront();

		currentArc.setVisible(true);	
		//TODO move to front?
		
	}

	private void hideCurrentArc() {
		currentArc.setVisible(false);
	}

	private void moveCurrentLine(double blockStartX, double blockStartY, double blockEndX, double blockEndY) {
		blockStartX = (blockStartX + dispX) * ratioX + 10;
		blockStartY = (blockStartY + dispY) * ratioY + 10;
		blockEndX = (blockEndX + dispX) * ratioX + 10;
		blockEndY = (blockEndY + dispY) * ratioY + 10;		//System.out.println(startX + " " + startY + " " + endX + " " + endY);

		currentLine.setStartX(blockStartX);
		currentLine.setStartY(blockStartY);
		currentLine.setEndX(blockEndX);
		currentLine.setEndY(blockEndY);
		
		currentLine.setStroke(Color.YELLOW);
		currentLine.setStrokeWidth(4);
		currentLine.toFront();
		
		currentLine.setVisible(true);		
		//TODO move to front?
	}

}
