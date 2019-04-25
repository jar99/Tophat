package application.TrackModel;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Collections;
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

/**
 * <h1>Track Model UI Controller</h1> Implements the controls and visual updates
 * for the Track Model UI.
 *
 * @author Cory Cizauskas
 * @version 1.0
 * @since 2019-04-13
 */
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
	private Label propStationName;
	@FXML
	private Circle iconPropBeacon;
	@FXML
	private Circle iconPropHeated;
	@FXML
	private Circle iconPropCrossing;
	@FXML
	private Circle iconPropLight;
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

	/**
	 * Sets the current block to the next ascending block
	 */
	@FXML
	void getLeftBlock() {
		mySin.shiftBlockLeft();
	}

	/**
	 * Sets the current block to the previous descending block
	 */
	@FXML
	void getRightBlock() {
		mySin.shiftBlockRight();
	}

	// TODO: Create a Line Selection Box Listener

	/**
	 * Imports line data from an excel file
	 */
	@FXML
	void importLine() {
		mySin.importLine("green.xlsx");

		for (TrackStation station : mySin.getLineStations("green")) {
			int blockID = station.getBlockID();
			char sectionID = station.getSectionID();
			TrackSection section = mySin.getSection(sectionID);

			if (section instanceof TrackSectionStraight) {
				TrackSectionStraight sectionStraight = (TrackSectionStraight) section;
				drawLine(sectionStraight.getBlockStartX(blockID), sectionStraight.getBlockStartY(blockID),
						sectionStraight.getBlockEndX(blockID), sectionStraight.getBlockEndY(blockID), Color.BLACK, 5);
			} else if (section instanceof TrackSectionCurve) {
				TrackSectionCurve sectionCurve = (TrackSectionCurve) section;
				drawArc(sectionCurve.getCenterX(), sectionCurve.getCenterY(), sectionCurve.getRadius(),
						sectionCurve.getBlockStartAngle(blockID), sectionCurve.getBlockLengthAngle(blockID),
						Color.BLACK, 5);
			}
		}

		for (TrackSection section : mySin.getLineSections("green")) {
			// System.out.println("RatioX: " + ratioX + "RatioY: " + ratioY);
			if (section instanceof TrackSectionStraight) {
				drawLine(section.getStartX(), section.getStartY(), section.getEndX(), section.getEndY(), Color.GREY, 3);
			} else if (section instanceof TrackSectionCurve) {
				TrackSectionCurve sectionCurve = (TrackSectionCurve) section;
				drawArc(sectionCurve.getCenterX(), sectionCurve.getCenterY(), sectionCurve.getRadius(),
						sectionCurve.getStartAngle(), sectionCurve.getLengthAngle(), Color.GREY, 3);
				// System.out.println("Section: " + sectionCurve.getSectionID() + " Length: " +
				// sectionCurve.getLength() + " CX: " + sectionCurve.getCenterX() + " CY: " +
				// sectionCurve.getCenterY() + " R: " + sectionCurve.getRadius() + " SAng: " +
				// Math.toDegrees(sectionCurve.getStartAngle()) + " LAng: " +
				// Math.toDegrees(sectionCurve.getLengthAngle()));
			}
		}

		// Implement CB Selection Box (See old code)
		ObservableList<String> list = FXCollections.observableArrayList();
		Collection<TrackSection> sections = mySin.getLineSections("green");

		for (TrackSection section : sections) {
			list.add(Character.toString(section.getSectionID()));
		}

		Collections.sort(list);
		choiceBoxBlock.setItems(list);
		choiceBoxBlock.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> selected, String oldCB, String newCB) {
				if (newCB != null) {
					// newCB = newCB.replaceAll("\\D+", "");
					mySin.setCurrentSection(newCB.charAt(0));
				}
			}
		});

	}

	/**
	 * Draws a line on the track map
	 * 
	 * @param startX - X coordinate for starting point (meters)
	 * @param startY - Y coordinate for starting point (meters)
	 * @param endX   - X coordinate for end point (meters)
	 * @param endY   - Y coordinate for end point (meters)
	 * @param color  - the color for line
	 * @param width  - the width of the line
	 */
	private void drawLine(double startX, double startY, double endX, double endY, Color color, double width) {
		startX = (startX + dispX) * ratioX + 10;
		startY = (startY + dispY) * ratioY + 10;
		endX = (endX + dispX) * ratioX + 10;
		endY = (endY + dispY) * ratioY + 10; // System.out.println(startX + " " + startY + " " + endX + " " + endY);

		Line line = new Line();
		line.setStartX(startX);
		line.setStartY(startY);
		line.setEndX(endX);
		line.setEndY(endY);
		line.setStroke(color);
		line.setStrokeWidth(width);

		trackMap.getChildren().add(line);
	}

	/**
	 * Draws an arc on the track map
	 * 
	 * @param centerX     - X coordinate for center point (meters)
	 * @param centerY     - Y coordinate for center point (meters)
	 * @param radius      - length of the radius (meters)
	 * @param startAngle  - angle where the arc begins (radians)
	 * @param lengthAngle - length of the arc angle (radians)
	 * @param color       - the color for arc
	 * @param width       - the width of the arc
	 */
	private void drawArc(double centerX, double centerY, double radius, double startAngle, double lengthAngle,
			Color color, double width) {
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
		arc.setStroke(color);
		arc.setStrokeWidth(width);

		trackMap.getChildren().add(arc);
	}

	/**
	 * Sets the temperature for the track environment
	 */
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

	/**
	 * Toggles the selected failure on the currently displayed block
	 */
	@FXML
	void toggleFailure() {
		// : Activate Toggle Failure Method
		String selFail = choiceBoxFail.getValue();
		if (selFail != null) {
			if (selFail.equals("Broken Rail"))
				mySin.toggleCBFailRail();
			else if (selFail.equals("Track Circuit"))
				mySin.toggleCBFailCircuit();
			else if (selFail.equals("Power"))
				mySin.toggleCBFailPower();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		// Add highlights to track map
		trackMap.getChildren().add(currentLine);
		trackMap.getChildren().add(currentArc);

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
	/**
	 * Updates the Track Model UI based on Track Model Singleton contents
	 */
	private void update() {

		if (mySin.hasALine()) {

			TrackBlock cBlock = mySin.getCurrentBlock();

			highlightCurrentBlock(cBlock);

			currentBlock.setText(cBlock.getName());

			showMeasurableCBAttributes(cBlock);

			showBooleanCBAttributes(cBlock);

			showFailureCBAttributes(cBlock);

			showStationCBAttributes(cBlock);
			
			animateTrains();
		}

	}

	/**
	 * Displays the current block station attributes to the Track Model UI
	 * 
	 * @param cBlock - the currently displayed block
	 */
	private void showStationCBAttributes(TrackBlock cBlock) {
		// : Get Station Properties if a Station, otherwise erase
		if (cBlock.isStation()) {
			TrackStation cStation = mySin.getCurrentStation();
			propSchdBoarders.setText(Integer.toString(cStation.getScheduledBoarders()));
			propSchdAlighters.setText(Integer.toString(cStation.getScheduledAlighters()));
			propBoarding.setText(Integer.toString(cStation.getBoarding()));
			propAlighting.setText(Integer.toString(cStation.getAlighting()));
			String stationName = cStation.getStationName();
			stationName = stationName.replace("NORTH", "N.");
			stationName = stationName.replace("EAST", "E.");
			stationName = stationName.replace("SOUTH", "S.");
			stationName = stationName.replace("WEST", "W.");
			propStationName.setText(stationName);
		} else {
			propSchdBoarders.setText("--");
			propSchdAlighters.setText("--");
			propBoarding.setText("--");
			propAlighting.setText("--");
			propStationName.setText("--");
		}
	}

	/**
	 * Displays the current block failure attributes to the Track Model UI
	 * 
	 * @param cBlock - the currently displayed block
	 */
	private void showFailureCBAttributes(TrackBlock cBlock) {
		// : Get Failure Status and display just like above
		if (cBlock.isFailRail())
			iconFailRail.setFill(Color.RED);
		else
			iconFailRail.setFill(Color.WHITE);

		if (cBlock.isFailCircuit())
			iconFailCircuit.setFill(Color.RED);
		else
			iconFailCircuit.setFill(Color.WHITE);

		if (cBlock.isFailPower())
			iconFailPower.setFill(Color.RED);
		else
			iconFailPower.setFill(Color.WHITE);
	}

	/**
	 * Displays the current block boolean attributes to the Track Model UI
	 * 
	 * @param cBlock - the currently displayed block
	 */
	private void showBooleanCBAttributes(TrackBlock cBlock) {
		Color nonLightColor = Color.BLACK;

		if (cBlock.isFailCircuit()) {
			iconPropOccupied.setFill(Color.TRANSPARENT);
			iconPropOccupied.setStroke(Color.RED);
		} else if (cBlock.isOccupied() || cBlock.isFailRail()) {
			iconPropOccupied.setFill(nonLightColor);
			iconPropOccupied.setStroke(Color.BLACK);
		} else {
			iconPropOccupied.setFill(Color.WHITE);
			iconPropOccupied.setStroke(Color.BLACK);
		}

		if (cBlock.isUnderground())
			iconPropUnderground.setFill(nonLightColor);
		else
			iconPropUnderground.setFill(Color.WHITE);

		if (cBlock.isStation())
			iconPropStation.setFill(nonLightColor);
		else
			iconPropStation.setFill(Color.WHITE);

		if (cBlock.hasBeacon())
			iconPropBeacon.setFill(nonLightColor);
		else
			iconPropBeacon.setFill(Color.WHITE);

		if (cBlock.isHeated() && !cBlock.isFailPower())
			iconPropHeated.setFill(nonLightColor);
		else
			iconPropHeated.setFill(Color.WHITE);

		if (cBlock.isCrossing())
			if (cBlock.isCrossingOn())
				iconPropCrossing.setFill(Color.RED);
			else
				iconPropCrossing.setFill(Color.GREEN);
		else
			iconPropCrossing.setFill(Color.WHITE);

		if (cBlock.hasLight())
			if (cBlock.isFailPower()) {
				iconPropLight.setFill(Color.TRANSPARENT);
				iconPropLight.setStroke(Color.RED);
			}
			else if (cBlock.isLightGreen()) {
				iconPropLight.setFill(Color.GREEN);
				iconPropLight.setStroke(Color.BLACK);
			}
				
			else {
				iconPropLight.setFill(Color.RED);
				iconPropLight.setStroke(Color.BLACK);
			}
				
		else {
			iconPropLight.setFill(Color.WHITE);
			iconPropLight.setStroke(Color.BLACK);
		}
			

		if (cBlock.getJunctionA().isSwitch() || cBlock.getJunctionB().isSwitch())
			iconPropSwitch.setFill(nonLightColor);
		else
			iconPropSwitch.setFill(Color.WHITE);

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
	}

	/**
	 * Displays the current block measurable attributes to the Track Model UI
	 * 
	 * @param cBlock - the currently displayed block
	 */
	private void showMeasurableCBAttributes(TrackBlock cBlock) {
		DecimalFormat df1 = new DecimalFormat("#.##");

		propLength.setText(df1.format(cBlock.getLength() * 0.000621371) + " miles");
		propGrade.setText(df1.format(cBlock.getGrade()) + " %");
		propSpeedLimit.setText(df1.format(cBlock.getSpdLmt() * 2.23694) + " mph");
		propElevation.setText(df1.format(cBlock.getElev() * 3.28084) + " ft");
		propTotalElevation.setText(df1.format(cBlock.getTotElev() * 3.28084) + " ft");
		propDirection.setText(cBlock.getCardinalDirection());
	}

	/**
	 * Highlights the current block on the map display
	 * 
	 * @param cBlock - the currently displayed block
	 */
	private void highlightCurrentBlock(TrackBlock cBlock) {
		// Keep Track of current block on map by drawing a different colored line over
		// it
		int cBlockID = cBlock.getBlockID();
		char cSectionID = cBlock.getSectionID();
		TrackSection cSection = mySin.getSection(cSectionID);

		if (cSection instanceof TrackSectionStraight) {
			TrackSectionStraight cSectionStraight = (TrackSectionStraight) cSection;
			moveCurrentLine(cSectionStraight.getBlockStartX(cBlockID), cSectionStraight.getBlockStartY(cBlockID),
					cSectionStraight.getBlockEndX(cBlockID), cSectionStraight.getBlockEndY(cBlockID));
			hideCurrentArc();
		} else if (cSection instanceof TrackSectionCurve) {
			TrackSectionCurve cSectionCurve = (TrackSectionCurve) cSection;
			moveCurrentArc(cSectionCurve.getCenterX(), cSectionCurve.getCenterY(), cSectionCurve.getRadius(),
					cSectionCurve.getBlockStartAngle(cBlockID), cSectionCurve.getBlockLengthAngle(cBlockID));
			hideCurrentLine();
			// System.out.println("Section: " + cSectionCurve.getSectionID() + " Length: " +
			// cSectionCurve.getLength() + " CX: " + cSectionCurve.getCenterX() + " CY: " +
			// cSectionCurve.getCenterY() + " R: " + cSectionCurve.getRadius() + " SAng: " +
			// Math.toDegrees(cSectionCurve.getStartAngle()) + " LAng: " +
			// Math.toDegrees(cSectionCurve.getLengthAngle()));
		}
	}

	/**
	 * Adds, moves, and removes trains on the map display
	 */
	private void animateTrains() {
		// : Get list of trains and coords, add dots to map. Make sure train has fx:id
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
				newTrain.setRadius(4);
				newTrain.setFill(Color.BLUE);

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
				
				if(eTrain.hasCrashed()) {
					trainIcon.setFill(Color.RED);
				}

				visibleTrains.add(trainIcon);

			}

		}

	}

	/**
	 * Moves the current block arc highlight to the new location and displays it
	 * 
	 * @param blockStartX - X coordinate for current block starting point
	 * @param blockStartY - Y coordinate for current block starting point
	 * @param blockEndX   - X coordinate for current block ending point
	 * @param blockEndY   - Y coordinate for current block ending point
	 */
	private void moveCurrentLine(double blockStartX, double blockStartY, double blockEndX, double blockEndY) {
		blockStartX = (blockStartX + dispX) * ratioX + 10;
		blockStartY = (blockStartY + dispY) * ratioY + 10;
		blockEndX = (blockEndX + dispX) * ratioX + 10;
		blockEndY = (blockEndY + dispY) * ratioY + 10; // System.out.println(startX + " " + startY + " " + endX + " " +
														// endY);

		currentLine.setStartX(blockStartX);
		currentLine.setStartY(blockStartY);
		currentLine.setEndX(blockEndX);
		currentLine.setEndY(blockEndY);

		currentLine.setStroke(Color.YELLOW);
		currentLine.setStrokeWidth(4);
		currentLine.toFront();

		currentLine.setVisible(true);
	}

	/**
	 * Hides the line which represents the current block when it is a line
	 */
	private void hideCurrentLine() {
		currentLine.setVisible(false);
	}

	/**
	 * Moves the current block arc highlight to the new location and displays it
	 * 
	 * @param centerX          - X coordinate for current block center point
	 * @param centerY          - Y coordinate for current block center point
	 * @param radius           - length of the current block radius in meters
	 * @param blockStartAngle  - angle where the current block starts in radians
	 * @param blockLengthAngle - angular length for the current block in radians
	 */
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
	}

	/**
	 * Hides the arc which represents the current block when it is an arc
	 */
	private void hideCurrentArc() {
		currentArc.setVisible(false);
	}

}
