package application.TrainModel.UI;

/**
 * This is is a class that adds table rows to a javafx table
 * 
 * @author jar99
 * @version 1.0
 *
 */
import java.util.function.Function;

import javafx.beans.property.SimpleStringProperty;

public class TableRow<T> {
	private String name;
	private Function<T, String> formater;
	private SimpleStringProperty value;

	public TableRow(String name, T value, Function<T, String> formater) {
		this.name = name;
		this.formater = formater;
		this.value = new SimpleStringProperty(formater.apply(value));
	}

	public TableRow(String name, T value) {
		this.name = name;
		this.value = new SimpleStringProperty(value.toString());
	}

	public void update(T value) {
		String result = formater != null ? formater.apply(value) : value.toString();
		if (this.value.getValue().equals(value))
			return;
		this.value.setValue(result);
	}

	public String getName() {
		return name;
	}

	public SimpleStringProperty getValue() {
		return value;
	}

	public String getValueS() {
		return value.getValue();
	}
}