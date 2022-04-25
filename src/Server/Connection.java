package Server;

import java.io.*;
import java.net.*;
import java.util.*;

import Client.PublisherClient;


public class Connection extends Thread {
	private Socket socket;
	private ArrayList<News> listOfNewsBackUp;
	private int connections_made;
	private File backupFile;
	
	public Connection(Socket s, ArrayList<News> listOfNewsBackUp, int connections_made, File file) {
		super();
		socket = s;
		this.listOfNewsBackUp = listOfNewsBackUp;
		this.connections_made = connections_made;
		this.backupFile = file;
		start();
	}
	
	
	
	public synchronized void saveOnFile(File file) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(listOfNewsBackUp);
			oos.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	public int getNmbrAcess( ) {
		return connections_made;
	}
	
	
	
	
	public String getAllNews() {
		String str = "";
		int i = 0;
		
		for (i = 0; i<listOfNewsBackUp.size(); i++) {
		    	
		    	
			String s = new String(listOfNewsBackUp.get(i).getPieceOfNews());
			str += i+"-: "+ s + "\n";
			i++;
		}
		 
		return str;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public void readFromFile(File file) {
		ArrayList<News> holder = new ArrayList<News>();
		
		if(file.length() == 0) 
			System.out.println("Publisher file exists and is empty!");
		else {
				
			try {

				 FileInputStream istream = new FileInputStream(file);
				 ObjectInputStream ois = new ObjectInputStream(istream);
				 
				 holder = (ArrayList<News>) ois.readObject();
				 listOfNewsBackUp = holder;
				 
			} catch (IOException | ClassNotFoundException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
			   
		
		}
	}
	
	
	
	@SuppressWarnings("unchecked")
	public void run() {
		try {
			
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			
			String msgDefault = ("<Server> Menu:"
					+ "\n1 - Get all news"
					+ "\n2 - Check how many connections have been established"
					+ "\n0 - Return");
			 
			
			String str = ""; 
			
			while (!str.equals("0"))
			{
			    oos.writeObject(msgDefault);//first write
			    
			    str = (String) ois.readObject();  //first read 
			    
			    
			    if(str.equals("-1")) //-1 means its our "client" RMIImpl that wants to communicate
			    {
			    
				ArrayList<News> holder = (ArrayList<News>)ois.readObject(); //second read
			        listOfNewsBackUp.addAll(holder);	//joins list of backup with new list "holder"
			   	    
			        if (backupFile.exists()) 
			            saveOnFile(backupFile);
			    	else
			    	    // we dont know for sure file was created on server class
			    	    System.out.println("Error creating file on server class!"); 
			    	   
			    	    //oos.writeObject(getAllNews());
			    	
			    	}
	
			    	if(str.equals("1")) 
			    	{
			    	   readFromFile(backupFile);
			    	   oos.writeObject(getAllNews());
			    	}   
			    	
			    	
			    	if(str.equals("2"))
			    	    oos.writeObject(getNmbrAcess()); 
			    	   	
			}
 
			oos.close();
			ois.close();
		} catch(IOException e) {
			e.getMessage();
		} catch (ClassNotFoundException e) {
			e.getMessage();
		}
	}
	
	
}
