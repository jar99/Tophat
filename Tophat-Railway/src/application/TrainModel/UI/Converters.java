package application.TrainModel.UI;

/**
 * This is a library of of some helpful converters for an interface
 * 
 * @author jar99
 * @version 1.0
 *
 */
import java.util.function.Function;

public class Converters<T> {

	String preText;
	String postText;
	private Function<T, T> converter;

	public Converters(String preText, String postText, Function<T, T> converter) {
		this.converter = converter;
		this.preText = preText == null ? "" : preText;
		;
		this.postText = postText == null ? "" : postText;
	}

	public Converters(String postText, Function<T, T> converter) {
		this(null, postText, null);
	}

	public Converters(String postText) {
		this(null, postText, null);
	}

	public static String OpenOrClosed(boolean itemState) {
		if (itemState)
			return "Open";
		return "Closed";
	}

	public static String OnOrOff(boolean itemState) {
		if (itemState)
			return "On";
		return "Off";
	}

	public static String YesOrNo(boolean itemState) {
		if (itemState)
			return "Yes";
		return "No";
	}

	public static String PassangerFormat(int passangers) {
		if (passangers == 0)
			return "None";
		if (passangers > 1)
			return passangers + " passanger";
		return passangers + " passangers";
	}

	public static String TemperatureConverter(double tempC) {
		tempC = ctof(tempC);
		return String.format("%.2f\u00B0F", tempC);
	}

	public static String SpeedConverter(double speedM) {
		speedM = kmhTomph(speedM);
		return String.format("%.2f mph", speedM);
	}

	public static String AccelerationConverter(double acelM) {
		acelM = msTofs(acelM);
		return String.format("%.2f f/s^2", acelM);
	}

	public static String Waight(double waightKG) {
		waightKG = kgTolbs(waightKG);
		return String.format("%.2f LBS", waightKG);
	}
	
	public static String LengthConverter(double lengthMeter) {
		lengthMeter = mToi(lengthMeter);
		int feet = (int) (lengthMeter / 12);
		double inches = (lengthMeter % 12);
		return String.format("%d\" %.1f'", feet, inches);
	}

	public static String KiloWatt(double kiloWatts) {
		return String.format("%.2f KW", kiloWatts);
	}

	public String concat(T item) {
		return preText + convert(item) + postText;
	}

	public T convert(T item) {
		if (converter == null)
			return item;
		return converter.apply(item);
	}
	
	public static double kmhToms(double kmh) {
		return 0.277778 * kmh;
	}

	public static double msTokmh(double ms) {
		return 3.60000288 * ms;
	}
	
	public static double ctof(double tempC) {
		return tempC * 9 / 5 + 32;
	}
	
	public static double ftoc(double tempF) {
		return (tempF - 32) * 5 / 9 ;
	}
	
	public static double kmhTomph(double speedM) {
		return speedM * 0.621371;
	}
	
	public static double msTofs(double acelM) {
		return acelM * 3.2808398950131;
	}
	
	public static double kgTolbs(double waightKG) {
		return waightKG * 2.20462;
	}
	
	public static double baseToKilo(double value) {
		return value / 1000.0;
	}
	
	public static double baseFromKilo(double value) {
		return value * 1000.0;
	}
	
	public static double mToi(double lengthMeter) {
		return lengthMeter*39.37;
	}

	public static double mTof(double meter) {
		return meter * 3.281;
	}
}
