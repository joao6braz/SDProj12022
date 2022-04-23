package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.*;

import Server.RMIInterface;

public class Publisher extends java.rmi.server.UnicastRemoteObject implements ClientInterface{

	protected Publisher() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public static String lerString (){
		 String s = "";
		 try{
			 BufferedReader in = new BufferedReader ( new InputStreamReader (System.in), 1);
			 System.out.println("Continuar? 0 para n�o!");
			 s= in.readLine();
		 } catch (IOException e){
			 System.out.println( e.getMessage());
		 }
		 return s;
	}

	@SuppressWarnings({ "deprecation", "removal" })
	public static void main(String[] args) {
		System.setSecurityManager(new SecurityManager());
		
		try {
			RMIInterface serverObj = (RMIInterface) Naming.lookup("RMIImp");
			String continuar = "1";
			while(!continuar.equals("0")) {
				Publisher publisher = new Publisher();
				serverObj.subscribe("Publisher 1", publisher);
				//serverObj.addNewTopic("Topico 1");
				serverObj.subscribeToTopic("Topico 1", "Publisher 1");
				
				char[] cs = new char[180];
				String s="Ol� esse � um teste de not�cia 10";    
				for(int i=0; i<s.length();i++){  
					cs[i] = s.charAt(i);  
				}
				
				serverObj.addPieceOfNews("Topico 1", cs, "Publisher 1");
				//System.out.println(serverObj.consultLastestNews("Topico 2"));
				
				String sDate1 = "19/04/2022";
				String sDate2 = "23/04/2022";
				
				Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
				Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate2);			
				
				//System.out.println(serverObj.consultNewsInDateRange("Topico 1", date1, date2));
				System.out.println(serverObj.consultTopics());
				System.out.println(serverObj.consultAllNews());
				continuar = lerString();
			}
			
		} catch (Exception e) {
			System.out.println("Exception occured: " + e);
			System.exit(0);
		}
	}

	@Override
	public void printOnClient(String s) throws RemoteException {
		System.out.println ("Message from server: " + s);
		
	}
}