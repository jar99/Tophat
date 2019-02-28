package application.TrainModel;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class TrainModelCtrl implements Initializable {

	// Links to your Singleton (NO TOUCHY!!)
	private TrainModelSingleton mySin = TrainModelSingleton.getInstance();

    TrainModel trainModel;
    private AnimationTimer updateAnimation;

    @FXML
    TableView train_info;
    
    @FXML
    TableColumn<TableRow, String> information_item;
    
    @FXML
    TableColumn information_value;

    public void setTrain(TrainModel trainModel) {
        this.trainModel = trainModel;
    }

    // Starts the automatic update (NO TOUCHY!!)
 	@Override
 	public void initialize(URL arg0, ResourceBundle arg1) {
 		information_item.setCellValueFactory(new PropertyValueFactory<TableRow,String>("name"));
 		power = new TableRow("Power", String.valueOf(trainModel.getPower()));
 		train_info.getItems().add(power);
 		
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
 		power.update(power.value+"+");
 	}
 	
 	TableRow power;
 	
 	private class TableRow{
 		private String name;
 		private SimpleStringProperty value;
 		
 		protected TableRow(String name, String value) {
 			this.name = name;
 			this.value = new SimpleStringProperty(value);
 		}
 		
 		protected void update(String value) {
 			this.value.set(value);
 		}
 		
 		public String getName() {
 			return name;
 		}
 		
 		public String getValue() {
 			return value.get();
 		}
 		
 		
 	}
}
