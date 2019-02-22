import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread t = new Thread(() -> {
			while(true){
				MainWindowSingleton.getInstance().updateCount();
//				System.out.println(MainWindowSingleton.getInstance().getCount());
			}
		});
		t.setDaemon(true);
		t.start();

		launch(args);
		System.out.println("Running code.");
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));
		Parent root2 = FXMLLoader.load(getClass().getResource("test.fxml"));

		Stage secondStage = new Stage();
		secondStage.setScene(new Scene(root2, 300, 275));
		secondStage.show();

		stage.setTitle("Hello World");
		stage.setScene(new Scene(root, 300, 275));
		stage.show();
	}
}
