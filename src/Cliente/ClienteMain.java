package Cliente;
import java.io.IOException;
import java.util.Scanner;

public class ClienteMain {

	public static void main(String[] args) throws InterruptedException, IOException {
		Scanner s;
		s = new Scanner(System.in);
		
		System.out.println("Escribe el friendly name del dispositivo bluetooth con el que te quieres conectar (Por ejemplo MASTERREDES11 o MASTERREDES15)");
		System.out.println("Asegúrate de que el dispositivo bluetooth con dicho friendly name está conectado:");
		String fName = s.next();
		Cliente c = new Cliente(fName);
		c.buscarDispositivos();
		c.buscarServicios();
		c.conectar();
		c.enviarMensaje();
		c.close();
	}

}
