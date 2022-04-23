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
		
	/*	if(!backupFile.exists()) {
			try {
				boolean result = backupFile.createNewFile(); 
				
				if (result) System.out.println("News file created!");
				
			} catch (IOException e) {
				System.out.println("Error creating the news File!");
				System.out.println(e.getMessage());
			}
			
		} else {
			try {
				FileInputStream istream = new FileInputStream(backupFile);
				ObjectInputStream ois = new ObjectInputStream(istream);
				
				while(true) {
					try {
						@SuppressWarnings("unchecked")
						ArrayList<News> readObject = ((ArrayList<News>) ois.readObject());
						listOfNewsBackUp = readObject;
						
					} catch (EOFException e) {
						System.out.println("End of News file reached!");
						break;
					} catch (IOException | ClassNotFoundException e) {
						System.out.println("Error reading topic file!");
						e.printStackTrace();
						break;
					}
				}
				istream.close();
				ois.close(); 
				
			} catch(IOException e) {
				System.out.println("Error reading topic file!");
				e.printStackTrace();
			}
		}
		
		*/
		
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
