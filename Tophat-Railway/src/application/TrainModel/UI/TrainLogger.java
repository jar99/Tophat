package application.TrainModel.UI;
/**
 * This is a logger method that I created to remove cluster from the console.
 * 
 * 
 * @author jar99
 * @version 1.0
 *
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TrainLogger {
	private static String LOGLOCATION = "debug.log";
	private static String TIMEFORMAT = "HH:mm:ss";
	private static MODE modeS = MODE.INFO;
	
	public static enum MODE {DEBUG, INFO, ERROR, CRITICAL, NONE };
	
	private static TrainLogger logger;
	
	
	private static void setLogger(TrainLogger log) {
		if(logger != null) logger.closeFile();
		logger = log;
	}
	
	private BufferedWriter writer;
	
	private boolean debug = false;
	private boolean console = false;
	private String location = LOGLOCATION;
	private MODE mode = modeS;
	
	private Date time;
	private DateFormat timeFormat;
	
	
	public TrainLogger(boolean debug, String path, String format) {
		this.debug = debug;
		printDebug(debug);
		this.time = new java.util.Date();
		this.timeFormat = new SimpleDateFormat(format);
		info("-----{Running}-----");
	}
	
	TrainLogger(boolean debug) {
		this(debug, LOGLOCATION, TIMEFORMAT);
	}

	public static TrainLogger getInstance() {
		if(logger == null) {
			setLogger(new TrainLogger(true));
		}
		return logger;	
	}
	
	
	public static void printToConsoleS(boolean value) {
		getInstance().printToConsole(value);
	}
	
	public void printToConsole(boolean value) {
		console = value;
	}
	
	public static void disableS() {
		getInstance().disable();
	}
	
	public void disable() {
		printDebug(false);
	}
	
	public static void setModeS(MODE mode) {
		getInstance().setMode(mode);
	}
	
	public void setMode(MODE mode) {
		this.mode = mode;
	}
	
	public static void enableS() {
		getInstance().disable();	
	}
	
	public void enable() {
		printDebug(true);
	}

	public static void printDebugS(boolean value) {
		getInstance().printDebug(value);
	}
	
	public void printDebug(boolean value) {
		debug = value;
		if(debug) {
			setLogFile(location);
		}
	}
	
	private static void setTimeFormatS(String format) {
		TIMEFORMAT = format;
		logger.setTimeFormat(format);
	}
	private void setTimeFormat(String format) {
		this.timeFormat = new SimpleDateFormat(format);
		
	}
	
	public static void setLogFileS(String path) {
		LOGLOCATION = path;
		getInstance().setLogFile(path);
	}
	
	public void setLogFile(String path) {
		openFile(path);
	}
	
	private void closeFile() {
		if(writer != null) {
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void openFile(String fileLocation) {
		closeFile();
		try {
			writer = new BufferedWriter(new FileWriter(fileLocation, true));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public static void debugS(String tag, String text) {
		printS(MODE.DEBUG, tag, text);
	}
	
	public static void infoS(String tag, String text) {
		printS(MODE.INFO, tag, text);
	}
	
	public static void errorS(String tag, String text) {
		printS(MODE.ERROR, tag, text);
	}
	
	public static void criticalS(String tag, String text) {
		printS(MODE.CRITICAL, tag, text);
	}
	
	public static void debugS(String text) {
		printS(MODE.DEBUG, text);
	}
	
	public static void infoS(String text) {
		printS(MODE.INFO, text);
	}
	
	public static void errorS(String text) {
		printS(MODE.ERROR, text);
	}
	
	public static void criticalS(String text) {
		printS(MODE.CRITICAL, text);
	}
	
	public void debug(String text) {
		print(MODE.DEBUG, text);
	}
	
	public void info(String text) {
		print(MODE.INFO, text);
	}
	
	public void error(String text) {
		print(MODE.ERROR, text);
	}
	
	public void critical(String text) {
		print(MODE.CRITICAL, text);
	}
	
	public void debug(String tag, String text) {
		print(MODE.DEBUG, tag, text);
	}
	
	public void info(String tag, String text) {
		print(MODE.INFO, tag, text);
	}
	
	public void error(String tag, String text) {
		print(MODE.ERROR, tag, text);
	}
	
	public void critical(String tag, String text) {
		print(MODE.CRITICAL, tag, text);
	}
	
	public static void printS(String text) {
		printS(modeS, "log", text);
	}
		
	public static void printS(MODE mode, String text) {
		printS(mode, "log", text);
	}
	
	public static void printS(MODE mode, String tag, String text) {
		getInstance().print(mode, tag, text);
	}
	
	public void print(MODE mode, String text) {
		print(mode, "log", text);
	}
	
	public void print(String text) {
		print(mode, "log", text);
	}
	
	public void print(MODE mode, String tag, String text) {
		if(!debug) return;
		if(mode.ordinal() < this.mode.ordinal()) return;
		
		String line = String.format("%s [%-8s] %8s: %s\n", timeFormat.format(time), mode.toString(), tag, text);
		if(console) System.out.print(line);
		if(writer != null) {
			try {
				writer.write(line);
				writer.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}

	public static MODE getModeS() {
		return getInstance().getMode();
	}
	public MODE getMode() {
		return mode;
	}

}
