package rxtx;

import java.util.ArrayList;
import java.util.List;

// 先写 127.0.0.0的。  再实现，对ip的通讯

public class DeviceControl {
	
	private static SerialPortHandler serialPorts = null; // 串口对象
	private static Object lock = new Object();
	private byte[] receiveData = null;
	public  static  void notifyThis(byte[] receiveData) {
		synchronized (lock) {
			receiveData = null;
			lock.notifyAll();
		}
	}
	public  DeviceControl(String comName) {
		serialPorts = SerialPortHandler.getInstance(comName);
	}
	
	public void sendBatchTask() {
		List<byte[]> datalist = new ArrayList();
			byte rs = (byte) (1);
			byte[] data = new byte[] {(byte)0xFF,(byte)rs ,(byte)0x01 ,(byte)0x17 ,
					(byte)0x00,(byte)0xfe ,
					(byte)0x00,(byte)0xfe ,
					(byte)0x00,(byte)0xfe ,
					(byte)0x00,(byte)0xfe ,
					(byte)0x00,(byte)0xfe ,
					(byte)0x00,(byte)0xfe ,
					(byte)0x00,(byte)0xfe ,
					(byte)0x00,(byte)0xfe ,
					(byte)0x00,(byte)0xfe ,
					(byte)0x00,(byte)0xfe ,
					(byte)0xFF};
			datalist.add(data);
		for(int i = 0 ; i<datalist.size();i++) {
			serialPorts.sendMsg(datalist.get(i));
			System.out.println("send,i:"+i);
			synchronized (lock) {
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static void main(String[] args) {
		String comName = "ttyS0";
		if(args!=null && args.length!=0) {
			comName =args[0];
		}
		DeviceControl dc = new DeviceControl(comName);
		while(true) {
			dc.sendBatchTask();
			try {
				Thread.sleep(1000 * 0xff);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
