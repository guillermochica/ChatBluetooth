package Bluetooth;
import java.util.ArrayList;

import javax.bluetooth.DataElement;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;

public class MyDiscoveryListener implements javax.bluetooth.DiscoveryListener {
	Object completedEvent;
	public static final ArrayList<RemoteDevice>/*<RemoteDevice>*/ devicesDiscovered = new ArrayList<RemoteDevice>();
	private String url;
	
	public String getUrl() {
		return url;
	}
	
	public MyDiscoveryListener(Object event) {
		completedEvent = event;

	}

	public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
		//Guardamos los dispositivos descubiertos en este arrayList
		devicesDiscovered.add(btDevice);
	}

	public void inquiryCompleted(int discType) {
		/* NOTIFICAR LA FINALIZACIÓN DE LA BÚSQUEDA */
		System.out.println("Device Inquiry completed!");
		synchronized (completedEvent) {
			completedEvent.notifyAll();
		}
	}

	public void serviceSearchCompleted(int transID, int respCode) {
		/* NOTIFICAR LA FINALIZACIÓN DE LA BÚSQUEDA */
		System.out.println("Service search completed!");
		synchronized (completedEvent) {
			completedEvent.notifyAll();
		}
	}

	public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
		for (int i=0; i<servRecord.length; i++) {
			
			DataElement d = servRecord[i].getAttributeValue(0x0100);
			
			if(d!=null){
				System.out.println("Servicio: "+(String) d.getValue());
				
			}
			else{
				System.out.println("Unnamed service");
			}
			
			System.out.println("URL: "+servRecord[i].getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false));
			url = servRecord[i].getConnectionURL(ServiceRecord.NOAUTHENTICATE_NOENCRYPT, false);
			
		}
	}
}