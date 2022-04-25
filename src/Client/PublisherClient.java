package Client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

import Server.RMIInterface;

public class PublisherClient extends java.rmi.server.UnicastRemoteObject implements PublisherClientInterface {

	private static final long serialVersionUID = 1L;
	private String name;
	private int id;
	private String password;

	public PublisherClient(String name, int id, String password) throws RemoteException {
		this.name = name;
		this.id = id;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getPassword() {
		return password;
	}

	public void printOnClient(String s) throws java.rmi.RemoteException { // Tratar situações em que o consumidor não
																			// está ligado
		System.out.println("Message from server: " + s);
	}

	private static void menuPublisher(PublisherClient publisher, RMIInterface serverObj, boolean verification)
			throws RemoteException {
		Scanner input = new Scanner(System.in);
		int option;
		String str1 = "", str2 = "";
		char[] news = new char[180];

		if (verification) { // If the publisher is registered

			do {
				System.out.println("Select an option below: \n" + "1. Add a topic\n" + "2. Consult topics\n"
						+ "3. Insert a news item from a topic\n" + "4. Consult all published news\n"
						+ "5. Log out\n");
				option = Integer.parseInt(input.nextLine()); // Tratar dos casos em que o input não seja um inteiro

				switch (option) {
				case 1:
					System.out.println("Write the topic name: ");
					str1 = input.nextLine();
					serverObj.addNewTopic(str1);
					break;

				case 2:
					System.out.println("Existing Topics: \n");
					System.out.println(serverObj.consultTopics());
					break;

				case 3:
					System.out.println("Write the topic name: ");
					str1 = input.nextLine();
					System.out.println("Write a piece of news (180 characters max): ");
					str2 = input.nextLine();
					
					while(str2.length() > 180) {
						System.out.println("The piece of news has over 180 characters! Please, write a piece of news with 180 characters at most.");
						str2 = input.nextLine(); 
					}
					
					for (int i = 0; i < str2.length(); i++) {
						news[i] = str2.charAt(i);
					}
					
					System.out.println(serverObj.addPieceOfNews(str1, news, publisher.getName()));
					break;

				case 4:
					System.out.println("All published news: \n");
					System.out.println(serverObj.consultAllNews());
					break;

				case 5:
					System.out.println("Leaving...");
					System.exit(0);
					break;

				default:
					System.out.println("Select an valid option");
					break;
				}
			} while (option != 5);
		} else
			System.out.println("You need to be registered in the system");
	}

	public static void main(String args[]) {
		System.setSecurityManager(new SecurityManager());
		
		Scanner input = new Scanner(System.in);
		PublisherClient publisher;
		boolean verification;
		int option, id;
		String str1 = "", str2 = "";

		try {
			RMIInterface serverObj = (RMIInterface) Naming.lookup("RMIImp");

			do {
				System.out.println("Select an option below: \n" + "1. I am an unregistered publisher \n"
						+ "2. I am a registered publisher \n" + "3. Log out");
				option = Integer.parseInt(input.nextLine()); // Tratar dos casos em que o input não seja um inteiro

				switch (option) {
				case 1:
					// Checking user registration
					System.out.print("Write your name: ");
					str1 = input.nextLine();
					System.out.print("Write your ID: ");
					id = Integer.parseInt(input.nextLine());
					System.out.print("Write your password: ");
					str2 = input.nextLine();
					publisher = new PublisherClient(str1, id, str2);
					serverObj.publisherRegistration(str1, id, str2);
					menuPublisher(publisher, serverObj, true);
					

				case 2:
					// Checking user registration
					System.out.println("Write your name: ");
					str1 = input.nextLine();
					System.out.println("Write your ID: ");
					id = Integer.parseInt(input.nextLine());
					System.out.println("Write your password: ");
					str2 = input.nextLine();
					publisher = new PublisherClient(str1, id, str2);
					verification = serverObj.publisherVerification(id, str2);
					menuPublisher(publisher, serverObj, verification);

				case 3:
					System.out.println("Leaving...");
					System.exit(0);

				default:
					System.out.println("Select an valid option");
				}
			} while (option != 3);
		} catch (Exception e) {
			System.out.println("Exception occured: " + e);
			System.exit(0);
		}
	}
}
