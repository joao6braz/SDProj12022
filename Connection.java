package Server;

import java.io.*;
import java.net.*;
import java.util.*;


public class Connection extends Thread {
	private Socket socket;
	private ArrayList<News> listOfNewsBackUp;
	private int numeroAcessos;
	private File file;
	
	public Connection(Socket s, ArrayList<News> listOfNewsBackUp, int numeroAcessos, File file) {
		super();
		socket = s;
		this.listOfNewsBackUp = listOfNewsBackUp;
		this.numeroAcessos = numeroAcessos;
		this.file = file;
		start();
	}
/*	
	public static String UmaString() {
		String str = "";
		
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			str = br.readLine();
			
		} catch (IOException e) {
			System.out.println("Erro na leitura do fluxo de entrada!");
		}
		return str;
	}
	
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
	
	public int getNmbrAcess( ) {
		return numeroAcessos;
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
	
	@SuppressWarnings("unchecked")
	public void run() {
		try {
			
		
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			
			String msgDefault = ("<Servidor> Escolha uma das opções abaixo:"
					+ "\n1 - Consultar uma noticia"
					+ "\n2 - Consultar o número de acessos"
					+ "\n0 - Para encerrar");
			
			oos.writeObject(msgDefault);
			
			String str = (String) ois.readObject(); 
			 
			while (!str.equals("0")) {
			    
			    	if(str.equals("thIsActsLiKeaPsWD777122"))
			    	{
			    	    //ArrayList <News> bckp = new ArrayList<News>();
			    	    ArrayList<News> holder = (ArrayList<News>)ois.readObject();
			    	    listOfNewsBackUp.addAll(holder);			    	    
			    	    
			    	    oos.writeObject(getAllNews());
			    	
			    	}
	
			    	if(str.equals("1"))
			    	   oos.writeObject(getAllNews());
			    	    
			    	
			    	
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
