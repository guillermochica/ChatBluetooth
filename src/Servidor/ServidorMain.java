package Servidor;
import java.io.IOException;

public class ServidorMain {

	public static void main(String[] args) throws IOException {
		Servidor s = new Servidor();
		
		s.escucharPeticiones();
		s.enviarMensaje();
		

	}

}
