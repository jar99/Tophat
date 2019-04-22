package application.TrainModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class TrainFileLoader {

	static double parseDouble(String key, Hashtable<String, String> values) {
		if (!values.containsKey(key))
			return Double.NaN;
		double value = Double.parseDouble(values.get(key));
		return value;
	}

	static int parseInt(String key, Hashtable<String, String> values) {
		if (!values.containsKey(key))
			return Integer.MIN_VALUE;
		int value = Integer.parseInt(values.get(key));
		return value;
	}
	
	public static void loadFile(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		Hashtable<String, String> values = new Hashtable<>();
		while (reader.ready()) {
			String line = reader.readLine();
			if (line.isEmpty() || line.startsWith("#"))
				continue;
			String[] tokin = line.split(":\\s+");
			if (tokin.length != 2)
				throw new IOException("File format exception.");
			values.put(tokin[0].toLowerCase(), tokin[1]);
		}
		reader.close();
		setData(values);
		
	}

	public static void loadFile(String path) throws IOException {
		loadFile(new File(path));
	}

	private static void setData(Hashtable<String, String> values) throws IOException {
		try {
			if (values.containsKey("maxvelocity"))
				TrainModel.setMaxVelocity(parseDouble("maxvelocity", values));

			if (values.containsKey("maxacceleration"))
				TrainModel.setMaxAcceleration(parseDouble("maxacceleration", values));

			if (values.containsKey("maxpower"))
				TrainModel.setMaxPower(parseDouble("maxpower", values));

			if (values.containsKey("stdfriction"))
				TrainModel.setSTDFriction(parseDouble("stdfriction", values));

			if (values.containsKey("trainwaight"))
				TrainModel.setTrainWaight(parseDouble("trainwaight", values));

			if (values.containsKey("servicebrakeforce"))
				TrainModel.setServiceBrakeForce(parseDouble("servicebrakeforce", values));

			if (values.containsKey("emergencybrakeforce"))
				TrainModel.setEmergencyBrakeForce(parseDouble("emergencybrakeforce", values));

			if (values.containsKey("length"))
				TrainModel.setLength(parseDouble("length", values));

			if (values.containsKey("width"))
				TrainModel.setWidth(parseDouble("width", values));

			if (values.containsKey("height"))
				TrainModel.setHeight(parseDouble("height", values));

			if (values.containsKey("carcount"))
				TrainModel.setCarCount(parseInt("carcount", values));

			if (values.containsKey("passengercap"))
				TrainModel.setPassengerCap(parseInt("passengercap", values));

		} catch (NumberFormatException e) {
			throw new IOException("File format is wrong.");
		}

	}

}
