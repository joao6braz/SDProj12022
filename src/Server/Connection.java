package Server;

import java.io.*;
import java.net.*;
import java.util.*;


public class Connection extends Thread {
	private Socket socket;
	private ArrayList<News> listOfNewsBackUp;
	private int connections_made;
	private File backupFile;
	
	public Connection(Socket s, ArrayList<News> listOfNewsBackUp, int connections_mada, File file) {
		super();
		socket = s;
		this.listOfNewsBackUp = listOfNewsBackUp;
		this.connections_made = connections_made;
		this.backupFile = file;
		start();
	}
/*	

	
	public synchronized void gravarFicheiro(File file) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(this.alunosRegistados);
			oos.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void lerFicheiro(File file) {
		Aluno obj;
		
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			for (int i = 0; i < alunosRegistados.size(); i++) {
				obj = (Aluno) ois.readObject();
				System.out.println(obj.toString());
			}
			ois.close();
			
		} catch (IOException e) {
			e.getMessage();
		} catch (ClassNotFoundException e) {
			e.getMessage();
		}
		
	}
	
	*/
	
	
	
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
		
		for (News n : listOfNewsBackUp) {
		    	
		    	String string = new String(n.getPieceOfNews());
			str += i+"-: "+ string + "\n";
			i++;
		}
		 
		return str;
	}
	
	
	
	
	public void readFromFile(File file) {
		ArrayList<News> holder = new ArrayList<News>();
		News news;
		
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			for (int i = 0; i < listOfNewsBackUp.size(); i++) { //size of bckfile is always the same as listofNewsbckp
				news = (News) ois.readObject();
				holder.add(news);
			}
			listOfNewsBackUp = holder;
			ois.close();
			
		} catch (IOException e) {
			e.getMessage();
		} catch (ClassNotFoundException e) {
			e.getMessage();
		}
		
	}
	
	
	
	@SuppressWarnings("unchecked")
	public void run() {
		try {
			
		
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			
			String msgDefault = ("<Servidor> Escolha uma das opções abaixo:"
					+ "\n1 - Consultar uma noticia"
					+ "\n2 - Consultar o número de acessos"
					+ "\n0 - Para encerrar");
			 
			oos.writeObject("Esta a funcionar!");
			
			String str = (String) ois.readObject(); 
			 
			while (!str.equals("0")) {
			    
			    	if(str.equals("-1")) //-1 means its our "client" RMIImpl that wants to communicate
			    	{
			    
			    	    ArrayList<News> holder = (ArrayList<News>)ois.readObject();
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
