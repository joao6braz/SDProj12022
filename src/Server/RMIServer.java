package Server;

import java.net.MalformedURLException;
import java.rmi.*;
import java.util.*;

public class RMIServer {
	private ArrayList<Topic> listOfTopics;
	
	@SuppressWarnings({ "deprecation", "removal" })
	public static void main(String[] args) {
		System.setSecurityManager(new SecurityManager());
		
		try {
			java.rmi.registry.LocateRegistry.createRegistry(1097);
			System.out.println("Registry ready!");
		} catch (RemoteException e) {
			System.out.println("Exception starting the RMI registry!");
		}
		
		try {
			Topic topic = new TopicImp();
			
			Naming.rebind("TopicImp", topic);
			
			System.out.println("Remote object ready!");
			
		} catch (MalformedURLException | RemoteException e) {
			System.out.println(e.getMessage());
		}
		
		ServerSocket serverSocket = new Server
	}
}
