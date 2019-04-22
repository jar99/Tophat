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
 		tempC = tempC*9/5 + 32;
 		return String.format("%.2f°F", tempC);
 	}
 	
	public static String SpeedConverter(double speedM) {
		speedM = speedM*0.621371;
 		return String.format("%.2f mph", speedM) ;
 	}
	
	public static String AccelerationConverter(double acelM) {
 		acelM = acelM*3.2808398950131;
 		return String.format("%.2f f/s^2", acelM);
 	}

	public static String Waight(double waightKG) {
		waightKG = waightKG*2.20462;
		return String.format("%.2f LBS", waightKG);
	}

	public static String KiloWatt(double watts) {
		watts = watts/1000;
		return String.format("%.2f KW", watts);
	}

	
	public String concat(T item) {
		return preText + convert(item) + postText;
	}
	
	
	public T convert(T item) {
		if(converter == null) return item;
		return converter.apply(item);
	}
	
}
