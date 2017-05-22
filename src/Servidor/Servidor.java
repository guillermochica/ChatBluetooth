package Servidor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;


import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class Servidor {
	//acepta la conexion entrante
	private static final String SERVICE_CLASS = "0x1101";
	private static final String SERVICE_NAME = "chat";
	
	private String url;
	
	private LocalDevice ld;
	
	private StreamConnectionNotifier service;
	private StreamConnection con;
	private OutputStream os;
	public InputStream in;
	
	private HebraRecibirServidor h;

	private Scanner s;
	
	public boolean seguir;

	
	public Servidor() throws IOException{
		ld = LocalDevice.getLocalDevice();
		url = "btspp://" + "localhost" + ":" + new UUID(0x1101).toString()+";name="+SERVICE_NAME;
		service = (StreamConnectionNotifier) Connector.open(url);
		System.out.println("Servidor iniciado");
		s = new Scanner(System.in);
		seguir=true;
	}
	
	public void escucharPeticiones() throws IOException{
		
		con = (StreamConnection) service.acceptAndOpen();
		os = con.openOutputStream();
		in = con.openInputStream();
		
		h = new HebraRecibirServidor(this);
		h.start();
		
	}
	
	public void enviarMensaje() throws IOException{
		System.out.println("Iniciando chat, escribe CLOSE para terminar");
		
		String m;
		
		while(seguir) {
			
			m=null;
			m = s.nextLine();
			if(m.equals("CLOSE")) {
				seguir=false;
				os.write(m.getBytes());
				os.flush();
			}
			
			else{
				if(seguir) {
					os.write(m.getBytes());
					os.flush();
				}
				
			}

		}
	
		
	}
	
	public void close() throws IOException{
		os.close();
		in.close();
		con.close();
		s.close();
		System.out.println("Stream closed");
		
	}

}
