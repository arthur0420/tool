package arthur.test;

public class TestByte {
	public static void main(String[] args) {
		byte[] a = new byte[] { (byte)0xff,00,01,0x17,   00, 0x0a, 00, 00, 00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,00,(byte)0xff};
		System.out.println(a);
		for(int j = 0 ; j<10;j++) {
			int mv = (a[4+j*2]&0xff) << 8 | (a[5+j*2]&0xff) ;
			System.out.println(mv);
		}
	}
}
