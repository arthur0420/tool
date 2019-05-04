package rxtx;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;


import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

public class SerialPortHandler implements  SerialPortEventListener{
	private  String comName = null; // 配置文件
	
    // 检测系统中可用的通讯端口类
    private CommPortIdentifier portId =null;
    
    // 枚举类型
    private Enumeration<CommPortIdentifier> portList =null;
    
    // RS485串口
    private SerialPort serialPort = null;
    
    // 输入输出流
    private InputStream inputStream =null;
    private OutputStream outputStream =null;
   
    private SerialPortHandler() {}
    
    public static SerialPortHandler getInstance(String comportname) {
    	if(comportname == null || comportname.isEmpty())return null;
    	
    	SerialPortHandler object = new SerialPortHandler();
    	object.comName = comportname;
    	object.init();
    	return object;
    }
 // 初始化串口
    @SuppressWarnings("unchecked")
    public void init() {
        // 获取系统中所有的通讯端口
        portList = CommPortIdentifier.getPortIdentifiers();
        // 循环通讯端口
        while (portList.hasMoreElements()) {
            portId = portList.nextElement();
            // 判断是否是串口
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
            	
                // 比较串口名称是否是"COM3"
            	System.out.println("comport:"+portId.getName());
                if (comName.equals(portId.getName())) {
                    System.out.println("find com"+comName);
                    // 打开串口
                    try {
                        // open:（应用程序名【随意命名】，阻塞时等待的毫秒数）
                        serialPort = (SerialPort) portId.open(comName, 2000);
                        //实例化输入流
                        inputStream = serialPort.getInputStream();
                        // 设置串口监听
                        serialPort.addEventListener(this);
                        // 设置串口数据时间有效(可监听)
                        serialPort.notifyOnDataAvailable(true);
                        // 设置串口通讯参数
                        // 波特率，数据位，停止位和校验方式
                        serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, //
                                SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                        
                    } catch (PortInUseException e) {
                        e.printStackTrace();
                    } catch (TooManyListenersException e) {
                        e.printStackTrace();
                    } catch (UnsupportedCommOperationException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    // 实现接口SerialPortEventListener中的方法 读取从串口中接收的数据
    @Override
    public void serialEvent(SerialPortEvent event) {
        switch (event.getEventType()) {
        case SerialPortEvent.BI: // 通讯中断
        	System.out.println("bi");break;
        case SerialPortEvent.OE: // 溢位错误
        	System.out.println("oe");break;
        case SerialPortEvent.FE: // 帧错误
        	System.out.println("fe");break;
        case SerialPortEvent.PE: // 奇偶校验错误
        	System.out.println("pe");break;
        case SerialPortEvent.CD: // 载波检测
        	System.out.println("cd");break;
        case SerialPortEvent.CTS: // 清除发送
        	System.out.println("cts");break;
        case SerialPortEvent.DSR: // 数据设备准备好
        	System.out.println("dsr");break;
        case SerialPortEvent.RI: // 响铃侦测
        	System.out.println("ri");break;
        case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 输出缓冲区已清空
        	System.out.println("OUTPUT_BUFFER_EMPTY");break;
        case SerialPortEvent.DATA_AVAILABLE: // 有数据到达
        	System.out.println("DATA_AVAILABLE");
            readComm();
            break;
        default:
        	System.out.println("default event");
            break;
        }
    }

    // 读取串口返回信息
    public void readComm() {
    	try {
			Thread.sleep(100);
			// 等待数据完全到达。
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
        byte[] readBuffer = new byte[1024];
        try {
            inputStream = serialPort.getInputStream();
            // 从线路上读取数据流
            int len = 0;
            String test ="";
            if((len = inputStream.read(readBuffer)) != -1) {
            	for(int i =0 ;i <len;i++ ) {
            		Byte one = readBuffer[i];
            		String byteToHex = byteToHex(one);
            		test+= byteToHex;
            	}
            }
            byte[] receiveData = new byte[len];
            System.arraycopy(readBuffer,0,receiveData, 0,len);
            DeviceControl.notifyThis(receiveData);
            System.out.println("receive data:"+test);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    //向串口发送数据
    public void sendMsg(byte[] data){
        try {
            //实例化输出流
        	if(outputStream == null) 
        		outputStream = serialPort.getOutputStream();
        } catch (IOException e) {
            System.out.println("get outputstream error");
            e.printStackTrace();
        }
        try {
            outputStream.write(data);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
    public  String byteToHex(byte b){  
        String hex = Integer.toHexString(b & 0xFF);  
        if(hex.length() < 2){  
            hex = "0" + hex;  
        }  
        return hex;  
    }
}
