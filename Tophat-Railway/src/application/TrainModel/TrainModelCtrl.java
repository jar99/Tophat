package application.TrainModel;
/**
 * This is the TrainModelCtrl this class is used to run the UI of the train model.
 * 
 * @author jar254
 * @version 1.0
 *
 */

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;

import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class TrainModelCtrl implements Initializable {

	// Links to your Singleton (NO TOUCHY!!)
	private TrainModelSingleton mySin = TrainModelSingleton.getInstance();

    TrainModel trainModel;
    private AnimationTimer updateAnimation;

    @FXML
    TableView<TableRow> train_info;
    
    @FXML
    TableColumn<TableRow, String> information_item;
    
    @FXML
    TableColumn<TableRow, String> information_value;
    
    @FXML
    ToggleButton emergencyButton;
    
    //TODO fix the emergency brake button
    @FXML
    public void clickEmergencyButton(ActionEvent event) {
    	if(trainModel != null && !trainModel.getEmergancyBrakeState()) {
    		toggleEmergencyBrake();
    		System.out.println("Emergency button pressed");
    		
    	}
    }
    
    private void toggleEmergencyBrake() {
    	if(trainModel.getEmergancyBrakeState()) {
    		emergencyButton.setStyle("-fx-background-color: #688bed; ");
    		//trainModel.setEmergancyBrake(true);
    	}else {
    		emergencyButton.setStyle("-fx-background-color: #ed412a; ");
    	}
    }

    // Starts the automatic update (NO TOUCHY!!)
 	@Override
 	public void initialize(URL arg0, ResourceBundle arg1) {
 		information_item.setCellValueFactory(new PropertyValueFactory<TableRow,String>("name"));
 		information_value.setCellValueFactory(new Callback<CellDataFeatures<TableRow, String> ,ObservableValue<String>>(){
 			public ObservableValue<String> call(CellDataFeatures<TableRow, String> c) {
 		        return c.getValue().getValue();
 		    }
 		});
 		setupTable();
 		
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
 		if(trainModel != null) {
 			toggleEmergencyBrake(); // NOTE this could be better
 			//Update table
 			trackAuthority.update(trainModel.getTrackAuthority());
 			trackSpeed.update(trainModel.getTrackSpeed());
 			
 			mboAuthority.update(trainModel.getMBOAuthority());
 			mboSpeed.update(trainModel.getMBOSpeed());
 			
 			emergancyBrake.update(trainModel.getEmergancyBrakeState());
 			serviceBrake.update(trainModel.getServiceBrake());
 			power.update(trainModel.getPower());
 			speed.update(trainModel.getSpeed());
 			waight.update(trainModel.getWeight());
 			cord.update(trainModel.getCordinets());
 			
 			leftDoor.update(trainModel.getLeftDoorState());
 			rightDoor.update(trainModel.getRightDoorState());
 			
 			passangers.update(trainModel.getPassangers());
 			temprature.update(trainModel.getTemperature());
 		}
 	}
 	
 	public TableRow<Double> power, trackSpeed, mboSpeed, speed, temprature, waight;
 	public TableRow<String> trainid, cord;
 	
 	public TableRow<Boolean> serviceBrake, emergancyBrake, leftDoor, rightDoor;
 	public TableRow<Integer> trackAuthority, mboAuthority, passangers;
 	
 	
 	void setTrain(TrainModel trainModel) {
 		this.trainModel = trainModel;		
 	}
 	
 	private void setupTable() {
 		//TODO add more unit formatting
 		trainid = new TableRow<String>("Train ID", "N/A"); 
 		
 		trackAuthority = new TableRow<Integer>("Track Authority", 0);
 		trackSpeed = new TableRow<Double>("Track Suggested Speed", 0.0,  (a) -> a + " MPH");
 		
 		mboAuthority = new TableRow<Integer>("MBO Authority", 0);
 		mboSpeed = new TableRow<Double>("MBO Suggested Speed", 0.0,  (a) -> a + " MPH");
 		
 		power = new TableRow<Double>("Power", 0.0,  (a) -> a + " KW");
 		speed = new TableRow<Double>("Speed", 0.0, (a) -> a + " MPH");
 		temprature = new TableRow<Double>("Temprature", 68.0, (a)-> tempratureConverter(a));
 		waight = new TableRow<Double>("Waight", 0.0, (a) -> a + " LBS");
 		cord = new TableRow<String>("Cord", "N/A");
 		passangers = new TableRow<Integer>("Passangers", 0, (a) -> passangerFormat(a));
 		
 		leftDoor = new TableRow<Boolean>("Left Door", true, (a)-> doorState(a));
 		rightDoor = new TableRow<Boolean>("Right Door", true, (a)-> doorState(a));
 		
 		serviceBrake = new TableRow<Boolean>("Service Brake", true, (a)-> onOrOff(a));
 		emergancyBrake = new TableRow<Boolean>("Emergency Brake", true, (a)-> onOrOff(a));
 		
 		train_info.getItems().addAll(trainid, trackAuthority, trackSpeed, mboAuthority, mboSpeed, power, speed, serviceBrake, emergancyBrake, waight, cord, leftDoor, rightDoor, passangers, temprature);
 	}
 	
 	private String doorState(boolean doorState) {
 		if(doorState) return "Open";
 		return "Closed";
 	}
 	
 	private String onOrOff(boolean itemState) {
 		if(itemState) return "On";
 		return "Off";
 	}
 	
 	private String passangerFormat(int passangers) {
 		if(passangers == 0) return "None";
 		if(passangers > 1) return passangers + " passanger";
 		return passangers + " passangers";
 	}
 	
 	private String tempratureConverter(double tempC) {
 		return tempC + "°F";
 	}
 	
 	public class TableRow<T>{
 		private String name;
 		private Function<T, String> formater;
 		private SimpleStringProperty value;
 		
 		
 		protected TableRow(String name, T value, Function<T, String> formater) {
 			this.name = name;
 			this.formater = formater;
 			this.value = new SimpleStringProperty(formater.apply(value));
 		}
 		
 		protected TableRow(String name, T value) {
 			this.name = name;
 			this.value = new SimpleStringProperty(value.toString());
 		}
 		
 		protected void update(T value) {
 			String result = formater != null ? formater.apply(value) : value.toString();
 			if(this.value.getValue().equals(value)) return;
 			this.value.setValue(result); 
 		}
 		
 		public String getName() {
 			return name;
 		}
 		
 		public SimpleStringProperty getValue() {
 			return value;
 		}
 		
 		public String getValueS() {
 			return value.getValue();
 		}	
 	}
}
