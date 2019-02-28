package application.TrainController;

import com.fazecast.jSerialComm.*;

public class TrainControllerHardware {
	// have all variables from singleton
	
	public static void main(String[] args) throws SerialPortIOException {
		String portName = "COM14";
		SerialPort msp = SerialPort.getCommPort(portName);
		if(!msp.openPort()) {
			throw new SerialPortIOException("Unable to open port, try opening another port.");
		}
		TrainCtrlDataListener listener = new TrainCtrlDataListener(msp);
		msp.addDataListener(listener);
	}
}
