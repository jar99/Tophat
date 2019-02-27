package application.TrainModel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TrainModelMain extends Application {

    private static TrainModelSingleton trainSingleton;

    public static void main(String[] args){
        trainSingleton = TrainModelSingleton.getInstance();
        trainSingleton.dispatchTrain("train_1");
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("train_model_main_FX.fxml"));
        stage.setScene(new Scene(root, 300, 275));
        stage.show();
    }
}
