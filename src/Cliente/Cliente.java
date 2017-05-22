package Cliente;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

import Bluetooth.MyDiscoveryListener;

public class Cliente {
	
	private static final int SERVICE_NAME_ATTRID = 0x0100;
	
	//Realiza la conexion saliente bluetooth
	
	private LocalDevice ld;
	
	private DiscoveryAgent da;
	
	
	private DiscoveryListener lstnr;
	
	private String url;
	private String servidor; //friendlyName del servidor
	
	private Object completedEvent;
	
	private StreamConnection con;
	private OutputStream os;
	public InputStream in;
	
	HebraRecibirCliente h;
	
	Scanner s;
	
	public boolean seguir;
	
	
	public Cliente(String friendlyName) throws BluetoothStateException{
		ld = LocalDevice.getLocalDevice();
		da = ld.getDiscoveryAgent();
		servidor = friendlyName;
		completedEvent = new Object();
		lstnr = new MyDiscoveryListener(completedEvent);
		s = new Scanner(System.in);
		seguir=true;
	}
	
	public void buscarDispositivos() throws BluetoothStateException, InterruptedException{

		//alt: retrieveDevice
		da.startInquiry(DiscoveryAgent.GIAC, lstnr);
		
		synchronized (completedEvent) {
			completedEvent.wait();
		}
		for(int i=0 ; i<MyDiscoveryListener.devicesDiscovered.size() ; i++) {
			try {
				System.out.println( MyDiscoveryListener.devicesDiscovered.get(i).getFriendlyName(false) + " " + MyDiscoveryListener.devicesDiscovered.get(i).getBluetoothAddress());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void buscarServicios() throws IOException, InterruptedException{
		
		for(int i=0 ; i<MyDiscoveryListener.devicesDiscovered.size() ; i++) {
            RemoteDevice btDevice = MyDiscoveryListener.devicesDiscovered.get(i); //referencia a cada dispositivo de la lista

            synchronized(completedEvent) {
            	if(btDevice.getFriendlyName(false).equals(servidor)) {
            		
            		//Solo buscamos en el dispositivo que nos interesa
            		
            		System.out.println("search services on " + btDevice.getBluetoothAddress() + " " + btDevice.getFriendlyName(false));
                    /* CONFIGURACION DE LOS PARAMETROS DE BUSQUEDA DE SERVICIOS*/
                    UUID uuids[]=new UUID[1];
                    uuids[0] = new UUID (0x1101);
                    int attridset[] = new int[1];
                    attridset[0] = SERVICE_NAME_ATTRID;
                    /* INICIAR BUSQUEDA DE SERVICIOS EN UN DISPOSITIVO EN CONCRETO*/
                    da.searchServices(attridset, uuids, btDevice, lstnr);
                   
                   
                   
                   /*ESPERAR A QUE DICHA BUSQUEDA FINALICE*/
                    synchronized(completedEvent){
                    	completedEvent.wait();
                    }
                    
                    url = ((MyDiscoveryListener) lstnr).getUrl();
                    
            	}
                
            }
        }
	}
	
	public void conectar() throws IOException {
		
			con = (StreamConnection) Connector.open(url);
			os = con.openOutputStream();
			in = con.openInputStream();
			h = new HebraRecibirCliente(this);
			h.start();
		
		
	}
	
	public void enviarMensaje() throws IOException{
		System.out.println("Iniciando chat escribe CLOSE para terminar");
		
		String m;
		
		while(seguir) {
			
			m = null;
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
	
	public void close() throws IOException {
		os.close();
		in.close();
		con.close();
		s.close();
		System.out.println("Stream closed");
	}
	

}
