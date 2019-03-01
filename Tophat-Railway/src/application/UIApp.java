package application;

import java.util.concurrent.CountDownLatch;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//WARNING: You MUST run this program from Main.java, 
//	otherwise the Singleton's won't talk to each other
public class UIApp extends Application {

	//NOTE: Use this to disable update methods for other modules
		// Each number corresponds to a module below.
	private static boolean ENABLE_1 = true;
	private static boolean ENABLE_2 = true;
	private static boolean ENABLE_3 = true;
	private static boolean ENABLE_4 = true;
	private static boolean ENABLE_5 = true;
	private static boolean ENABLE_6 = true;
	
	
	public static final CountDownLatch latch = new CountDownLatch(1);
	public static UIApp uiApp = null;

	@Override
	public void start(Stage ctcStage) {
		try {

			// Root for CTC
			Parent ctcRoot = FXMLLoader.load(getClass().getResource("./CTC/CTC.fxml"));
			ctcStage.setTitle("CTC");
			Scene ctcScene = new Scene(ctcRoot, 1200, 900); // NOTE: Change last two ints to make window bigger
			ctcStage.setScene(ctcScene);
			if(ENABLE_1) ctcStage.show();
			

			// Root for Track Controller
			Stage tckCtrlStage = new Stage();
			Parent tckCtrlRoot = FXMLLoader.load(getClass().getResource("./TrackController/TrackController.fxml"));
			tckCtrlStage.setTitle("Track Controller");
			Scene tckCtrlScene = new Scene(tckCtrlRoot, 810, 600); // NOTE: Change last two ints to make window bigger
			tckCtrlStage.setScene(tckCtrlScene);
			if(ENABLE_2) tckCtrlStage.show();
			

			// Root for Track Model
			Stage tckModStage = new Stage();
			Parent tckModRoot = FXMLLoader.load(getClass().getResource("./TrackModel/TrackModel.fxml"));
			tckModStage.setTitle("Track Model");
			Scene tckModScene = new Scene(tckModRoot, 1000, 600); // NOTE: Change last two ints to make window bigger
			tckModStage.setScene(tckModScene);
			if(ENABLE_3) tckModStage.show();
			

			// Root for Train Model
			Stage trnModStage = new Stage();
			Parent trnModRoot = FXMLLoader.load(getClass().getResource("./TrainModel/TrainModel.fxml"));
			trnModStage.setTitle("Train Model");
			Scene trnModScene = new Scene(trnModRoot, 400, 400); // NOTE: Change last two ints to make window bigger
			trnModStage.setScene(trnModScene);
			if(ENABLE_4) trnModStage.show();
			
			
			// Root for Train Controller
			Stage trnCtrlStage = new Stage();
			Parent trnCtrlRoot = FXMLLoader.load(getClass().getResource("./TrainController/TrainController.fxml"));
			trnCtrlStage.setTitle("Train Controller");
			Scene trnCtrlScene = new Scene(trnCtrlRoot, 650, 500); // NOTE: Change last two ints to make window bigger
			trnCtrlStage.setScene(trnCtrlScene);
			if(ENABLE_5) trnCtrlStage.show();

			
			// Root for MBO
			Stage mboStage = new Stage();
			Parent mboRoot = FXMLLoader.load(getClass().getResource("./MBO/MBO.fxml"));
			mboStage.setTitle("MBO");
			Scene mboScene = new Scene(mboRoot, 1000, 600); // NOTE: Change last two ints to make window bigger
			mboStage.setScene(mboScene);
			if(ENABLE_6) mboStage.show();

			// If we have time, we'll start adding styles using the line below
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static UIApp waitForUITest() {
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return uiApp;
	}

	public static void setUITest(UIApp uiTest0) {
		uiApp = uiTest0;
		latch.countDown();
	}

	public UIApp() {
		setUITest(this);
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
