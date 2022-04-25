package Server;

import java.net.*;
import java.rmi.*;

public class RMIServer {
		
	@SuppressWarnings({ "deprecation", "removal" })
	public static void main(String[] args) {
		// Instalar gestor de segurança Security Manager
		System.setSecurityManager(new SecurityManager());
		
		try { // Iniciar a execução do registry no porto desejado
			java.rmi.registry.LocateRegistry.createRegistry(1099);
			System.out.println("Registry ready!");
		} catch (RemoteException e) {
			System.out.println("Exception starting the RMI registry!");
		}
		
		try {
			// instanciar objeto remoto
			RMIInterface remoteObject = new RMIImp();
			//registar o objeto remoto no Registry
			Naming.rebind("RMIImp", remoteObject);
			System.out.println("Remote object ready!");
			
		} catch (MalformedURLException | RemoteException e) {
			System.out.println(e.getMessage());
		}
	}
}
