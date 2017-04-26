package Servidor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class HebraRecibirServidor extends Thread{
	
	private InputStream is;
	private byte read_buffer[] = new byte[100];
	
	public HebraRecibirServidor(InputStream in) {
		this.is= in;
	}
	
	public void run(){
		while(true) {
			
			try {
				is.read(read_buffer);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println(new String(read_buffer, StandardCharsets.UTF_8));
			
		}
	}

}
