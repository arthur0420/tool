package arthur.pi4j;

import java.io.IOException;
import java.util.Date;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.serial.Baud;
import com.pi4j.io.serial.DataBits;
import com.pi4j.io.serial.FlowControl;
import com.pi4j.io.serial.Parity;
import com.pi4j.io.serial.Serial;
import com.pi4j.io.serial.SerialConfig;
import com.pi4j.io.serial.SerialDataEvent;
import com.pi4j.io.serial.SerialDataEventListener;
import com.pi4j.io.serial.SerialFactory;
import com.pi4j.io.serial.SerialPort;
import com.pi4j.io.serial.StopBits;
import com.pi4j.util.CommandArgumentParser;

public class TestPi4j {
	public static void main(String[] args) throws InterruptedException {
     /*  final GpioController gpio = GpioFactory.getInstance();
        
       final GpioPinDigitalOutput pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "LED", PinState.HIGH);
       
	while(true){
		pin.high();
	       Thread.sleep(1000);
       	pin.low();
	       Thread.sleep(1000);
	}*/
		
		  final Serial serial = SerialFactory.createInstance();

	        // create and register the serial data listener
		  SerialDataEventListener serialDataEventListener = new SerialDataEventListener() {
			  @Override
	          public void dataReceived(SerialDataEvent event) {
	                try {
	                    System.out.println("[ASCII DATA] " + event.getAsciiString());
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        };
	        SerialDataEventListener[] a = new SerialDataEventListener[1];
	        a[0]= serialDataEventListener;
	        serial.addListener(a);
	
	
	 try {
         // create serial config object
         SerialConfig config = new SerialConfig();

         // set default serial settings (device, baud rate, flow control, etc)
         //
         // by default, use the DEFAULT com port on the Raspberry Pi (exposed on GPIO header)
         // NOTE: this utility method will determine the default serial port for the
         //       detected platform and board/model.  For all Raspberry Pi models
         //       except the 3B, it will return "/dev/ttyAMA0".  For Raspberry Pi
         //       model 3B may return "/dev/ttyS0" or "/dev/ttyAMA0" depending on
         //       environment configuration.
         config.device(SerialPort.getDefaultPort())
               .baud(Baud._115200)
               .dataBits(DataBits._8)
               .parity(Parity.NONE)
               .stopBits(StopBits._1)
               .flowControl(FlowControl.NONE);

         // parse optional command argument options to override the default serial settings.
         if(args.length > 0){
             config = CommandArgumentParser.getSerialConfig(config, args);
         }
         
         // open the default serial device/port with the configuration settings
         serial.open(config);

         // continuous loop to keep the program running until the user terminates the program
         while(true) {
             try {
            	 byte[] data = new byte[1];
            	 data[0] = (byte)1;
            	 
                 serial.write(data);
                 
                 Thread.sleep(1000);
                 
                 data[0] = (byte)2;
                 serial.write(data);
             }
             catch(IllegalStateException ex){
                 ex.printStackTrace();
             }
         }
     }
     catch(IOException ex) {
    	 System.out.println("error");
     }
    }
}
