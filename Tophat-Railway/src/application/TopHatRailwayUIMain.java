package application;

import application.MBO.MBOSingleton;
import application.TrackController.TrackControllerSingleton;
import application.TrackModel.TrackModelSingleton;
import application.TrainController.TrainControllerSingleton;
import application.TrainControllerHardware.TrainControllerHWSingleton;
import application.TrainModel.TrainModelSingleton;
import application.CTC.CTCSingleton;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This is a secondary launcher that will run the program from a main window.
 * This is the main launch function of the program.
 * 
 * @author Jan Reihl
 * @version 1.0
 * @since 2019-04-24
 */
public class TopHatRailwayUIMain {

	// NOTE: Set True to Debug Update Methods
	private static boolean DEBUG = true;

	// NOTE: Use this to disable update methods for other modules
	// Each number corresponds to a module below.
	private static boolean ENABLE_1 = true;
	private static boolean ENABLE_2 = true;
	private static boolean ENABLE_3 = true;
	private static boolean ENABLE_4 = true;
	private static boolean ENABLE_5 = true;
	private static boolean ENABLE_6 = true;
	private static boolean ENABLE_7 = true;

	private static boolean printedUpdateDebugs = false; // ensures 1 print for update methods

	public static void main(String[] args) {
		// Singleton References
		CTCSingleton 				ctcSin = 		CTCSingleton.getInstance();
		TrackControllerSingleton 	tckCtrlSin = 	TrackControllerSingleton.getInstance();
		TrackModelSingleton 		tckModSin = 	TrackModelSingleton.getInstance();
		TrainModelSingleton 		trnModSin = 	TrainModelSingleton.getInstance();
		TrainControllerSingleton 	trnCtrlSin = 	TrainControllerSingleton.getInstance();
		TrainControllerHWSingleton 	trnHwSin = 		TrainControllerHWSingleton.getInstance();
		MBOSingleton 				mboSin = 		MBOSingleton.getInstance();

		ClockSingleton clock = ClockSingleton.getInstance();

		// Scheduler which tells the singleton's to update
		final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				update(ctcSin, tckCtrlSin, tckModSin, trnModSin, trnCtrlSin, trnHwSin, mboSin, clock);

			}
		}, 0, 1, TimeUnit.SECONDS); // Determines update frequency (1 sec atm)

		javafx.application.Application.launch(TopHatRailwayUI.class);
		executorService.shutdown();
	}

	/**
	 * Calls each singleton's update() method
	 * 
	 * @param ctcSin     - Reference to CTC Singleton
	 * @param tckCtrlSin - Reference to Track Controller Singleton
	 * @param tckModSin  - Reference to Track Model Singleton
	 * @param trnModSin  - Reference to Train Model Singleton
	 * @param trnCtrlSin - Reference to Train Controller Singleton
	 * @param mboSin     - Reference to MBO Singleton
	 * @param clock      - Reference to Clock Singleton
	 */
	private static void update(CTCSingleton ctcSin, TrackControllerSingleton tckCtrlSin, TrackModelSingleton tckModSin,
			TrainModelSingleton trnModSin, TrainControllerSingleton trnCtrlSin, TrainControllerHWSingleton trnHwSin,
			MBOSingleton mboSin, ClockSingleton clock) {

		if (DEBUG && !printedUpdateDebugs
				&& !(ENABLE_1 && ENABLE_2 && ENABLE_3 && ENABLE_4 && ENABLE_5 && ENABLE_6 && ENABLE_7)) {
			System.err.println("WARNING: Some updates Have been disabled");
		}

		try {

			if (DEBUG && !printedUpdateDebugs)
				System.out.println("DEBUG: 0 - Scheduler worked");

			if (ENABLE_1)
				ctcSin.update();
			if (DEBUG && !printedUpdateDebugs)
				System.out.println("DEBUG: 1 - CTC update worked");

			if (ENABLE_2)
				tckCtrlSin.update();
			if (DEBUG && !printedUpdateDebugs)
				System.out.println("DEBUG: 2 - Train Controller update worked");

			if (ENABLE_3)
				tckModSin.update();
			if (DEBUG && !printedUpdateDebugs)
				System.out.println("DEBUG: 3 - Track Model update worked");

			if (ENABLE_4)
				trnModSin.update();
			if (DEBUG && !printedUpdateDebugs)
				System.out.println("DEBUG: 4 - Train Model update worked");

			if (ENABLE_5)
				trnCtrlSin.update();
			if (DEBUG && !printedUpdateDebugs)
				System.out.println("DEBUG: 5 - Train Controller update worked");

			if (ENABLE_6)
				mboSin.update();
			if (DEBUG && !printedUpdateDebugs)
				System.out.println("DEBUG: 6 - MBO update worked");

			if (ENABLE_7)
				trnHwSin.update();
			if (DEBUG && !printedUpdateDebugs) {
				System.out.println("DEBUG: 7 - Train Controller Hardware update worked");
			}

			clock.update();

		} catch (Exception e) {
			System.err.println();
			StackTraceElement[] elements = e.getStackTrace();
			for (int iterator = 1; iterator <= elements.length; iterator++)
				System.err.println("Class Name:" + elements[iterator - 1].getClassName() + " Method Name:"
						+ elements[iterator - 1].getMethodName() + " Line Number:"
						+ elements[iterator - 1].getLineNumber());
		}

		printedUpdateDebugs = true;
	}
}
