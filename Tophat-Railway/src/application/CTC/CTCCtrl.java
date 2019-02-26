package application.CTC;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.*;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
public class CTCCtrl implements Initializable {

	// Links to your Singleton (NO TOUCHY!!)
	private CTCSingleton mySin = CTCSingleton.getInstance();

	private AnimationTimer updateAnimation;

	// NOTE: This is where you link to elements in your FXML file
	// Example:(fx:id="counter")
	// WARNING: Your fx:id and variable name Must Match!
	// Links to FXML elements
	@FXML
	private TextField TrainIDTextField;
	@FXML
	private TextField SpeedTextField;
	@FXML
    private TextField DepartureTimeText;
	@FXML
	private ChoiceBox<String> DepartureStationChoiceBox;
	@FXML
	private ChoiceBox<String> LineChoiceBox;
	@FXML
	private ChoiceBox<String> DestinationChoiceBox;
    @FXML
	private ListView<String> ScheduleListView;
	@FXML
	private ListView<String> ManagementListView;
	@FXML
    private TextField IDModify;
    @FXML
    private TextField AuthorityModify;
    @FXML
    private TextField SpeedModify;
	// NOTE: This is where you build UI functionality
	// functions can be linked through FX Builder or manually
	// Control Functions
	public void ButtonCreateTrainClicked() {
		if (mySin.addTrain(TrainIDTextField.getCharacters().toString(),SpeedTextField.getCharacters().toString())){
			System.out.print("Successful");//TODO Change this into UI
		}
		else return;
		String departure=DepartureStationChoiceBox.getSelectionModel().getSelectedItem();
		String line=LineChoiceBox.getSelectionModel().getSelectedItem();
		String destination=DestinationChoiceBox.getSelectionModel().getSelectedItem();
		String departuretime=DepartureTimeText.getCharacters().toString();
		String[] routine={"STATIONA","STATIONB"};
		mySin.addSchedule(Integer.parseInt(TrainIDTextField.getCharacters().toString()),line,routine);
		ObservableList<String> ScheduleString = FXCollections.observableArrayList(mySin.tolist());
		ScheduleListView.setItems(ScheduleString);
		ObservableList<String> TrainString = FXCollections.observableArrayList(mySin.tolistTrains());
		ManagementListView.setItems(TrainString);

	}
	public void ModifyClicked() {
		String ID=IDModify.getCharacters().toString();
		mySin.ModifyTrain(Integer.valueOf(ID),Integer.parseInt(AuthorityModify.getCharacters().toString()),Integer.parseInt(SpeedModify.getCharacters().toString()));
		ObservableList<String> TrainString = FXCollections.observableArrayList(mySin.tolistTrains());
		ManagementListView.setItems(TrainString);
    }
	// Starts the automatic update (NO TOUCHY!!)
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		DepartureStationChoiceBox.setItems(FXCollections.observableArrayList("STATIONA", "STATIONB"));
		//TODO load info from trackmodel
		LineChoiceBox.setItems(FXCollections.observableArrayList("Green", "Red"));
		DestinationChoiceBox.setItems(FXCollections.observableArrayList("STATIONC", "STATIOND"));
		//TODO load info from trackmodel
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
		//DepartureStationChoiceBox.setItems(FXCollections.observableArrayList("STATIONA", "STATIONB"));
		ObservableList<String> TrainString = FXCollections.observableArrayList(mySin.tolistTrains());
		ManagementListView.setItems(TrainString);


	}
}
