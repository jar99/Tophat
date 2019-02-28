package application.TrainModel;

import java.net.URL;
import java.util.ResourceBundle;

import application.TrainModel.TrainModelCtrl.TableRow;
import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
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

    public void setTrain(TrainModel trainModel) {
        runSetup(trainModel);
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
 			power.update(String.valueOf(trainModel.getPower()));
 			velcity.update(String.valueOf(trainModel.getVelocity()));
 			cord.update(String.valueOf(trainModel.getCord()));
 		}
 	}
 	
 	public TableRow power, velcity, cord;
 	
 	private void runSetup(TrainModel trainModel) {
 		this.trainModel = trainModel;
 		power = new TableRow("Power", String.valueOf(trainModel.getPower()));
 		velcity = new TableRow("Velocity", String.valueOf(trainModel.getVelocity()));
 		cord = new TableRow("Cord", String.valueOf(trainModel.getCord()));
 		train_info.getItems().addAll(power, velcity, cord);
 	}
 	
 	
 	public class TableRow{
 		private String name;
 		private SimpleStringProperty value;
 		
 		protected TableRow(String name, String value) {
 			this.name = name;
 			this.value = new SimpleStringProperty(value);
 		}
 		
 		protected void update(String value) {
 			this.value.setValue(value);
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
