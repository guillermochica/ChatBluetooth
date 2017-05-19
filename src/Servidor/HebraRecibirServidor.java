package Servidor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import javax.microedition.io.StreamConnection;

import com.ibm.oti.connection.btgoep.Connection;

public class HebraRecibirServidor extends Thread{
	
	private InputStream is;
	private OutputStream os;
	private StreamConnection con;
	private byte read_buffer[] = new byte[100];
	boolean seguir;
	
	public HebraRecibirServidor(InputStream in, OutputStream on, StreamConnection con) {
		this.is= in;
		os = on;
		this.con=con;
		seguir=true;
	}
	
	public void run(){
		while(seguir) {
			
			try {
				read_buffer=new byte[100];
				is.read(read_buffer);
				System.out.println(new String(read_buffer, StandardCharsets.UTF_8));
				
				if(new String(read_buffer, StandardCharsets.UTF_8).equals("CLOSE")){
					seguir=false;
					is.close();
					os.close();
					con.close();				
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Stream closed");
				seguir=false;
				
			}
			
		
			
			
			
			
			
			
		}
		
	}

}
