package application.TrainControllerHardware;

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
		System.out.println(data);
	}
}
