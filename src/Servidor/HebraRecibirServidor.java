package Servidor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import javax.microedition.io.StreamConnection;

import com.ibm.oti.connection.btgoep.Connection;

public class HebraRecibirServidor extends Thread{
	
	
	private byte read_buffer[] = new byte[100];
	Servidor server;
	private String m;
	
	public HebraRecibirServidor(Servidor s) {
		
		server = s;
	}
	
	public void run(){
		while(server.seguir) {
			
			try {
				read_buffer=new byte[100];
				server.in.read(read_buffer);
				m = new String(read_buffer, StandardCharsets.UTF_8);
				if(m.trim().equals("CLOSE")){
					server.seguir=false;
					System.out.println("Closing...");
					
					
				}
				else{
					System.out.println( m);
				}
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Stream closed");
				
				
			}
			
		}
		System.out.println("Finalizada conversación. Pulsa enter para salir.");
		
	}

}
