package Server;


import java.io.*;
import java.net.*;
import java.util.*;

public class BackupServer {
	private ServerSocket serverSocket;
	private Socket socket;
	private Connection c;
	
	
	private ArrayList<News> listOfNewsBackUp;
	private int numeroAcessos;
	private File backupFile; 
	
	
	public  BackupServer() { 
		listOfNewsBackUp = new ArrayList<News>();
		numeroAcessos = 0;
		backupFile = new File("backup.dat"); 
		
		
		
		//creates file if it does not exist
		if(!backupFile.exists()) {
			try {
			
				if (backupFile.createNewFile()) 
				    System.out.println("News file created!");
				
			} catch (IOException e) {
				System.out.println("Error creating the news File!");
				System.out.println(e.getMessage());
			}
			

		}
		
		try {
			serverSocket = new ServerSocket(5432);
			System.out.println("Servidor está a escutar!");
			
			while (true) {
				socket = serverSocket.accept();
				numeroAcessos++;
				System.out.println(numeroAcessos);
				c = new Connection(socket, listOfNewsBackUp, numeroAcessos, backupFile);
			}
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
			
	
	}
	
	public static void main(String[] args) {
		BackupServer s = new BackupServer();
	}
}
