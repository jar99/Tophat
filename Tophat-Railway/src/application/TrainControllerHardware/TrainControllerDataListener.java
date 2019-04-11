package application.TrainControllerHardware;

import java.nio.ByteBuffer;

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
		
		System.out.println(data[0]);
		TrainControllerHWSingleton mySin = TrainControllerHWSingleton.getInstance();
		if(data.length == 2) { // boolean value passed
			switch(data[0]) {
			case 0x04:
				mySin.toggleServiceBrake();
				break;
			case 0x05:
				mySin.toggleLights();
				break;
			case 0x06:
				mySin.toggleLeftDoor();
				break;
			case 0x07:
				mySin.toggleRightDoor();
				break;
			case 0x08:
				mySin.resetEmergencyBrake();
				break;
			default:
			}
		}
		else if(data.length == 3) {
			byte[] val = {data[1], data[2]};
			ByteBuffer wrapped = ByteBuffer.wrap(val);
			short num = wrapped.getShort();
			switch(data[0]) {
			case 0x00:
				// speed
				mySin.setSpeed((double) num);
				break;
			case 0x01:
				// ki
				mySin.setKi( num);
				break;
			case 0x02:
				// kp
				mySin.setKp((double) num);
				break;
			case 0x03:
				// temp
				mySin.setTemperature((double) num);
				break;
			case 0x09:
				// id
				mySin.setTrainId(num);
				break;
			default:
			
			}
		}

		
		// data includes multiple bytes, the first containing the value changed,
		// and the rest containing the changed value
	}
}
