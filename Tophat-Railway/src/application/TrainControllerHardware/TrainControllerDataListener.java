package application.TrainControllerHardware;

import com.fazecast.jSerialComm.*;

import application.TrainController.TrainControllerSingleton;

public class TrainControllerDataListener implements SerialPortDataListener{
	private SerialPort port;
	
	public TrainControllerDataListener(SerialPort port) {
		this.port = port;
	}
	
	@Override
	public int getListeningEvents(){
		return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
	}
	
	@Override
	public void serialEvent(SerialPortEvent ev){
		if(ev.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE) return;
		
		// send Singleton data over serial b/c this is simple for iteration 2
		//TrainControllerSingleton ctrlSin = TrainControllerSingleton.getInstance();
		//String trainSpeed = ctrlSin.getSpeed();
		//byte[] speedBuff = trainSpeed.getBytes();
		//String trainPower = ctrlSin.getPower();
		//byte[] powerBuff = trainPower.getBytes();
		//String trainTemp = ctrlSin.getTemperature();
		//byte[] tempBuff = trainTemp.getBytes();
		
		//port.writeBytes(speedBuff, speedBuff.length);
		//port.writeBytes(powerBuff, powerBuff.length);
		//port.writeBytes(tempBuff, tempBuff.length);
	}
}
