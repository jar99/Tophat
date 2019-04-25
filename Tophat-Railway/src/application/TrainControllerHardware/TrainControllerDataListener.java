package application.TrainControllerHardware;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortDataListener;

public class TrainControllerDataListener implements SerialPortDataListener{
	
	
	
	public TrainControllerDataListener(){}
	
	/**
	 * Only acceptable port events are data reception
	 */
	@Override
	public int getListeningEvents(){
		return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
	}
	/**
	 * Function to process data transmissions
	 */
	@Override
	public void serialEvent(SerialPortEvent ev){
		if(ev.getEventType() != SerialPort.LISTENING_EVENT_DATA_RECEIVED) return;
		
		byte[] data = ev.getReceivedData();
		if(data.length != 5) return;
		
		byte[] value = new byte[4];
		
		for(int i = 0; i < 4; i++) {
			value[i] = data[i + 1];
		}
		
		float convertedValue = ByteBuffer.wrap(value).order(ByteOrder.LITTLE_ENDIAN).getFloat();
		TrainControllerHWSingleton mySin = TrainControllerHWSingleton.getInstance();
		switch(data[0]) {
			case 0:
				mySin.setSpeed(convertedValue);
				break;
			case 1:
				mySin.setKi(convertedValue);
				break;
			case 2:
				mySin.setKp(convertedValue);
				break;
			case 3:
				mySin.setTemperature(convertedValue);
				break;
			case 4:
				if(convertedValue == 1) mySin.toggleServiceBrake();
				break;
			case 5:
				if(convertedValue == 1) mySin.resetEmergencyBrake();
				else if(convertedValue == 10) mySin.triggerEmergencyBrake();
				break;
			case 6:
				if(convertedValue == 1) mySin.toggleIntLights();
				break;
			case 7:
				if(convertedValue == 1) mySin.toggleExtLights();
				break;
			case 8:
				if(convertedValue == 1) mySin.toggleLeftDoor();
				break;
			case 9:
				if(convertedValue == 1) mySin.toggleRightDoor();
				break;
			case 10:
				if(convertedValue == 1) mySin.toggleDrivingMode();
				break;
			case 11:
				mySin.setTrainId((int) convertedValue);
				break;
			default:
			
		}

		
		
		// data includes multiple bytes, the first containing the value changed,
		// and the rest containing the changed value
	}	
}
