package Cliente;
import java.io.IOException;

public class ClienteMain {

	public static void main(String[] args) throws InterruptedException, IOException {
		
		Cliente c = new Cliente("fd");
		c.buscarDispositivos();
		c.buscarServicios();
		c.conectar();
		c.enviarMensaje();
	}

}
