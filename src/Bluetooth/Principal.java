package Bluetooth;
import javax.bluetooth.*;
import javax.bluetooth.UUID;

import java.io.IOException;


public class Principal {
	
	 
	
	private static final int SERVICE_NAME_ATTRID = 0x0100;

	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		LocalDevice ld = LocalDevice.getLocalDevice();
		//System.out.println(ld.getBluetoothAddress());
		
		DiscoveryAgent da = ld.getDiscoveryAgent();
		Object completedEvent = new Object();
		
		DiscoveryListener lstnr = new MyDiscoveryListener(completedEvent);
		
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
		

       

        for(int i=0 ; i<MyDiscoveryListener.devicesDiscovered.size() ; i++) {
            RemoteDevice btDevice = MyDiscoveryListener.devicesDiscovered.get(i);

            synchronized(completedEvent) {
                System.out.println("search services on " + btDevice.getBluetoothAddress() + " " + btDevice.getFriendlyName(false));
                /* CONFIGURACION DE LOS PARAMETROS DE BUSQUEDA DE SERVICIOS*/
                UUID uuids[]=new UUID[1];
                uuids[0] = new UUID (0x1002);
                int attridset[] = new int[1];
                attridset[0] = SERVICE_NAME_ATTRID;
                /* INICIAR BUSQUEDA DE SERVICIOS EN UN DISPOSITIVO EN CONCRETO*/
                da.searchServices(attridset, uuids, btDevice, lstnr);
               /*ESPERAR A QUE DICHA BUSQUEDA FINALICE*/
                completedEvent.wait();
            }
        }
		
	}

}

