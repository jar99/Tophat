package application.TrainModel.UI;

import java.util.function.Function;

public class Converters<T> {
	
	String preText;
	String postText;
	private Function<T, T> converter;
	
	public Converters(String preText, String postText, Function<T, T> converter) {
 		this.converter = converter;
 		this.preText = preText == null ? "": preText;;
 		this.postText = postText == null ? "": postText;
	}
	
	public Converters(String postText, Function<T, T> converter) {
 		this(null, postText, null);
	}
	
 	public Converters(String postText) {	
 		this(null, postText, null);
	}

	public static String OpenOrClosed(boolean itemState) {
 		if(itemState) return "Open";
 		return "Closed";
 	}
 	
 	public static String OnOrOff(boolean itemState) {
 		if(itemState) return "On";
 		return "Off";
 	}
 	
 	public static String YesOrNo(boolean itemState) {
 		if(itemState) return "Yes";
 		return "No";
 	}
 	
 	public static String PassangerFormat(int passangers) {
 		if(passangers == 0) return "None";
 		if(passangers > 1) return passangers + " passanger";
 		return passangers + " passangers";
 	}
 	
 	public static String TempratureConverter(double tempC) {
 		// TODO add c to f convention
 		return tempC + "°F";
 	}
 	
	public static String SpeedConverter(double speedM) {
 		// TODO add kmh to mph convention
 		return speedM + "mph";
 	}

	public static String Waight(double waightKG) {
		// TODO add kg to lbs
		return waightKG + " LBS";
	}

	public String concat(T item) {
		return preText + convert(item) + postText;
	}
	
	
	public T convert(T item) {
		if(converter == null) return item;
		return converter.apply(item);
	}
	
}
