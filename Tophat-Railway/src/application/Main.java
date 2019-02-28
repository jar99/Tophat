package application;

import application.MBO.MBOSingleton;
import application.TrackController.TrackControllerSingleton;
import application.TrackModel.TrackModelSingleton;
import application.TrainController.TrainControllerSingleton;
import application.TrainModel.TrainModelSingleton;
import application.CTC.CTCSingleton;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// WARNING: You MUST run this program from Main.java, 
// 		otherwise the Singleton's won't talk to each other
public class Main {

	private static boolean DEBUG = true;	//Set True to Debug Update Methods
	
	private static boolean printedUpdateDebugs = false;	// ensures 1 print for update methods
	
	public static void main(String[] args) {
		// Singleton References
		CTCSingleton 				ctcSin = 		CTCSingleton.getInstance();
		TrackControllerSingleton 	tckCtrlSin = 	TrackControllerSingleton.getInstance();
		TrackModelSingleton 		tckModSin = 	TrackModelSingleton.getInstance();
		TrainModelSingleton 		trnModSin = 	TrainModelSingleton.getInstance();
		TrainControllerSingleton 	trnCtrlSin = 	TrainControllerSingleton.getInstance();
		MBOSingleton 				mboSin = 		MBOSingleton.getInstance();

		// Calling Thread which starts the FX UI
		new Thread() {
			@Override
			public void run() {
				javafx.application.Application.launch(UIApp.class);
			}
		}.start();
		UIApp.waitForUITest();

		// Scheduler which tells the singleton's to update
		final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				update(ctcSin, tckCtrlSin, tckModSin, trnModSin, trnCtrlSin, mboSin);
			}
		}, 0, 1, TimeUnit.SECONDS); // Determines update frequency (1 sec atm)

	}

	// calls each singleton's update() method
	private static void update(CTCSingleton ctcSin, TrackControllerSingleton tckCtrlSin, TrackModelSingleton tckModSin,
			TrainModelSingleton trnModSin, TrainControllerSingleton trnCtrlSin, MBOSingleton mboSin) {
		// call singleton update methods
		if(DEBUG && !printedUpdateDebugs) System.out.println("DEBUG: 0 - Scheduler worked");
		ctcSin.update();
		if(DEBUG && !printedUpdateDebugs) System.out.println("DEBUG: 1 - CTC update worked");
		tckCtrlSin.update();
		if(DEBUG && !printedUpdateDebugs) System.out.println("DEBUG: 2 - Train Controller update worked");
		tckModSin.update();
		if(DEBUG && !printedUpdateDebugs) System.out.println("DEBUG: 3 - Track Model update worked");
		trnModSin.update();
		if(DEBUG && !printedUpdateDebugs) System.out.println("DEBUG: 4 - Train Model update worked");
		trnCtrlSin.update();
		if(DEBUG && !printedUpdateDebugs) System.out.println("DEBUG: 5 - Train Controller update worked");
		mboSin.update();
		if(DEBUG && !printedUpdateDebugs) System.out.println("DEBUG: 6 - MBO update worked");

		printedUpdateDebugs = true;
	}
}
