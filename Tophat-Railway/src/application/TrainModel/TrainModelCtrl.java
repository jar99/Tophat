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
import application.TrainModel.UI.Converters;
import application.TrainModel.UI.TableRow;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class TrainModelCtrl implements Initializable {
	
    TrainModel trainModel;

    @FXML
    TableView<TableRow> train_info;
    
    @FXML
    TableColumn<TableRow, String> information_item;
    
    @FXML
    TableColumn<TableRow, String> information_value;
    
    @FXML
    ToggleButton emergencyButton;
    
    @FXML
    ListView<String> train_log;

	private boolean shouldRun = false;
    
    //TODO fix the emergency brake button
    @FXML
    public void clickEmergencyButton(ActionEvent event) {
    	if(trainModel != null && !trainModel.getEmergencyBrake()) {
    		trainModel.triggerEmergencyBrake();
    		trainModel.addTrainInformation("Test Emergency Button.");
    		
    	}
    }
    
    private void updateEmergencyBrake() {
    	if(trainModel.getEmergencyBrake()) {
    		emergencyButton.setStyle("-fx-background-color: #688bed; ");
    		//trainModel.setEmergancyBrake(true);
    	}else {
    		emergencyButton.setStyle("-fx-background-color: #ed412a; ");
    	}
    }
    
    /**
     * This method is called when the tab should be removed.
     */
    void shutdown() {
        System.out.println("Stoped " + trainModel + " model window.");
        pause();
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
 		run();
 	}
    
    // NOTE: This is where you get new information from your singleton
 	// You can read/change fx elements linked above
 	// WARNING: This assumes your singleton is updating its information
 	void update() {
 		if(!shouldRun || trainModel == null) return;
 		if(train_log.isVisible()) {
 			while(!trainModel.trainLogEmpty()) {
 				String log = trainModel.poptrainInformation();
 				train_log.getItems().add(log);
 			}
 		}
 		
 		if(train_info.isVisible()) {
// 			System.out.println("Updating: " + trainModel);
 			updateEmergencyBrake(); // NOTE this could be better
 					
 			//Update table
 			trackAuthority.update(trainModel.getTrackAuthority());
 			trackSpeed.update(trainModel.getTrackSpeed());
 			
 			mboAuthority.update(trainModel.getMBOAuthority());
 			mboSpeed.update(trainModel.getMBOSpeed());
 			
 			emergancyBrake.update(trainModel.getEmergencyBrake());
 			serviceBrake.update(trainModel.getServiceBrake());
 			power.update(trainModel.getPower());
 			speed.update(trainModel.getSpeed());
 			weight.update(trainModel.getWeight());
 			cord.update(trainModel.getCoordinates());
 			
 			leftDoor.update(trainModel.getLeftDoorState());
 			rightDoor.update(trainModel.getRightDoorState());
 			
 			passangers.update(trainModel.getPassengers());
 			temprature.update(trainModel.getTemperature());
 		}
 	}
 	
 	void setTrain(TrainModel trainModel) {
 		this.trainModel = trainModel;
 		trainid.update(trainModel.toString());
 	}
 	
 	private TableRow<Double> power, trackSpeed, mboSpeed, speed, temprature, weight;
 	private TableRow<String> trainid, cord;
 	
 	private TableRow<Boolean> serviceBrake, emergancyBrake, leftDoor, rightDoor;
 	private TableRow<Integer> trackAuthority, mboAuthority, passangers;
 	
 	private void setupTable() {
 		//TODO add more unit formatting
 		trainid = new TableRow<String>("Train ID", "N/A"); 
 		
 		trackAuthority = new TableRow<Integer>("Track Authority", 0);
 		trackSpeed = new TableRow<Double>("Track Suggested Speed", 0.0,  (a) -> Converters.SpeedConverter(a));
 		
 		mboAuthority = new TableRow<Integer>("MBO Authority", 0);
 		mboSpeed = new TableRow<Double>("MBO Suggested Speed", 0.0,  (a) ->  Converters.SpeedConverter(a));
 		
 		Converters<Double> energy = new Converters<>(" KW");
 		power = new TableRow<Double>("Power", 0.0,  (a) -> energy.concat(a));
 		speed = new TableRow<Double>("Speed", 0.0, (a) -> Converters.SpeedConverter(a));
 		temprature = new TableRow<Double>("Temprature", 68.0, (a)-> Converters.TempratureConverter(a));
 		weight = new TableRow<Double>("Weight", 0.0, (a) -> Converters.Waight(a));
 		cord = new TableRow<String>("Cord", "N/A");
 		passangers = new TableRow<Integer>("Passangers", 0, (a) -> Converters.PassangerFormat(a));
 		
 		leftDoor = new TableRow<Boolean>("Left Door", true, (a)-> Converters.OpenOrClosed(a));
 		rightDoor = new TableRow<Boolean>("Right Door", true, (a)-> Converters.OpenOrClosed(a));
 		
 		serviceBrake = new TableRow<Boolean>("Service Brake", true, (a)-> Converters.OnOrOff(a));
 		emergancyBrake = new TableRow<Boolean>("Emergency Brake", true, (a)-> Converters.OnOrOff(a));
 		
 		train_info.getItems().addAll(trainid, trackAuthority, trackSpeed, mboAuthority, mboSpeed, power, speed, serviceBrake, emergancyBrake, weight, cord, leftDoor, rightDoor, passangers, temprature);
 	}

 	void run() {
		shouldRun = true;
	}
 	
	void pause() {
		shouldRun = false;
	}
 	
}
