package Cliente;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import javax.microedition.io.StreamConnection;

public class HebraRecibirCliente extends Thread{
	
	
	private byte read_buffer[] = new byte[100];

	Cliente client;
	private String m; 
	
	public HebraRecibirCliente(Cliente c) {
		
		client = c;
	}
	
	public void run(){
		while(client.seguir) {
			
			try {
				read_buffer=new byte[100];
				client.in.read(read_buffer);
				m = new String(read_buffer, StandardCharsets.UTF_8);
				
				if(m.trim().equals("CLOSE")) {
					client.seguir=false;
					
				}
				else{
					System.out.println(m);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Stream closed");
				
				
			}

		}
		System.out.println("Finalizada conversación. Pulsa enter para salir");
		
	}

}
