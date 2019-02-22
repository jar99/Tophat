import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class MainWindow implements Initializable {

    public Label label;
    private MainWindowSingleton singleton = MainWindowSingleton.getInstance();


    public void upCount(ActionEvent actionEvent) {
        System.out.println("Add count.");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final BlockingQueue<String> messageQueue = new ArrayBlockingQueue<>(1);
        singleton.addCallback(() -> {
            try {
                messageQueue.put(String.valueOf(singleton.getCount()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> label.setText(messageQueue.poll()));
//            Platform.runLater(() -> label.setText(String.valueOf(singleton.getCount())));
            System.out.println("Calling callback.");
        });
    }
}
