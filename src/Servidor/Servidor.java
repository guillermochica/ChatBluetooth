package Servidor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import javax.bluetooth.LocalDevice;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;

public class Servidor {
	//acepta la conexion entrante
	private static final String SERVICE_CLASS = "1101";
	
	private String url;
	
	private LocalDevice ld;
	
	private StreamConnectionNotifier service;
	private StreamConnection con;
	private OutputStream os;
	private InputStream in;
	
	private HebraRecibirServidor h;

	private Scanner s;
	
	
	public Servidor() throws IOException{
		ld = LocalDevice.getLocalDevice();
		url = "btspp://" + ld.getBluetoothAddress() + ":" + SERVICE_CLASS;
		service = (StreamConnectionNotifier) Connector.open(url);
		s = new Scanner(System.in);
	}
	
	public void escucharPeticiones() throws IOException{
		
		con = (StreamConnection) service.acceptAndOpen();
		os = con.openOutputStream();
		in = con.openInputStream();
		
		h = new HebraRecibirServidor(in);
		h.start();
		
	}
	
	public void enviarMensaje() throws IOException{
		os.write("holaaa".getBytes());
		String m;
		while(true) {
			m = s.nextLine();
			os.write(m.getBytes());
		}
	}

}
