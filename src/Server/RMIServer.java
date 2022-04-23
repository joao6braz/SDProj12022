package Server;

import java.net.*;
import java.rmi.*;

public class RMIServer {
		
	@SuppressWarnings({ "deprecation", "removal" })
	public static void main(String[] args) {
		System.setSecurityManager(new SecurityManager());
		
		try {
			java.rmi.registry.LocateRegistry.createRegistry(1099);
			System.out.println("Registry ready!");
		} catch (RemoteException e) {
			System.out.println("Exception starting the RMI registry!");
		}
		
		try {
			RMIInterface remoteObject = new RMIImp();
			
			Naming.rebind("RMIImp", remoteObject);
			
			System.out.println("Remote object ready!");
			
		} catch (MalformedURLException | RemoteException e) {
			System.out.println(e.getMessage());
		}
	}
}
