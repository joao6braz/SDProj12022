package Client;

import java.io.*;
import java.net.*;
import java.rmi.*;
import java.text.*;
import java.util.*;

import Server.RMIInterface;


public class SubscriberClient extends java.rmi.server.UnicastRemoteObject implements SubscriberClientInterface {

    private static final long serialVersionUID = 1L;
    private String name;
    private int id;
    private String password;
    private static SubscriberClient subscriber;

    public SubscriberClient(String name, int id, String password) throws RemoteException {
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

    public void printOnClient(String s) throws java.rmi.RemoteException {
    	System.out.println("Message from server: " + s);
    }

    private static void menuSubscriber(SubscriberClient susbcriber, RMIInterface serverObj, boolean verification)
	    throws RemoteException, ParseException {

	Scanner input = new Scanner(System.in);
	int option;
	String str = "";

	if (verification) { // If the client is registered

	    do {
		System.out.println("Select an option below: \n" + "1. Susbcribe a topic (you'll be notified)\n"
			+ "2. Consult all news given in a certain date range\n"
			+ "3. Consult latest news of a given topic\n" 
			+ "4. Log out\n");

		option = Integer.parseInt(input.nextLine()); // Tratar dos casos em que o input n�o seja um inteiro

		switch (option) {
		case 1:
			System.out.println("Enter the topic name:\n");
		    String topicToSubscribe = input.nextLine();
		    serverObj.subscribeToTopic(topicToSubscribe);
		    break;

		case 2:
		    System.out.println("Enter start date as dd/MM/yyyy :\n");
		    String sDate1 = input.nextLine();

		    System.out.println("Enter end date as dd/MM/yyyy :\n");
		    String sDate2 = input.nextLine();

		    System.out.println("Enter topic name: \n");
		    String topic = input.nextLine();

		    Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
		    Date date2 = new SimpleDateFormat("dd/MM/yyyy").parse(sDate2);

		    String resultOfSearch = serverObj.consultNewsInDateRange(topic, date1, date2);

		    if (!resultOfSearch.equals("notfound"))
			System.out.println(resultOfSearch);
		    else {

			System.out.println("No news found on this server\n"
				+ "News might be on backup server @ 127.0.0.1, port: 5432\n"
				+ "Do you wish to connect now? (y/n)\n");

			str = input.nextLine();

			if (str.equals("y") || str.equals("yes")) {

			    try {
				Socket s = new Socket("127.0.0.1", 5432);
				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				System.out.println("Welcome\n" + "You're currently on the backupserver\n");

				String optionstr = "";

				while (!optionstr.equals("0")) {

				    System.out.println(ois.readObject() + "\n"); // 1st read, prints message with menu
				    optionstr = input.nextLine(); // waits for user input with option
				    oos.writeObject(optionstr); // first write. send to server option number
								// if option one of backserver menu

				    if (optionstr.equals("1")) // 1st case of backupserver menu "get all news"
				    {
					System.out.println(ois.readObject() + "\n");
				    }

				    if (optionstr.equals("2")) {
					System.out.println(ois.readObject() + "\n");
				    }

				}

			    } catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			    }

			}

		    }

		    break;

		case 3:
		    System.out.println("Enter topic to search news in:\n");
		    String topicname = input.nextLine();
		    System.out.println("Existing news for topic"+ topicname +"\n");
		    System.out.println(serverObj.consultLastestNews(topicname));
		    break;
		    
		case 4:
		    System.out.println("Leaving...");
		    System.exit(0);
		    break;

		default:
		    System.out.println("Select an valid option");
		    break;
		}
	    } while (option != 4);
	} else
	    System.out.println("You need to be registered in the system");
    }
    
    
    

    public static void main(String args[]) {

		System.setSecurityManager(new SecurityManager());
	
		Scanner input = new Scanner(System.in);
		boolean verification;
		int option, id;
		String str1 = "", str2 = "";
	
		try {
		    RMIInterface serverObj = (RMIInterface) Naming.lookup("RMIImp");
	
		    do {
		    	System.out.println("Select an option below: \n" + "1. I am an unregistered subscriber \n"
		    			+ "2. I am a registered subscriber \n" + "3. I want to log as a guest\n" + "4. Log out");
			option = Integer.parseInt(input.nextLine()); // Tratar dos casos em que o input n�o seja um inteiro
	
			switch (option) {
			case 1:
			    // Creating user registration
			    System.out.print("Write your name: ");
			    str1 = input.nextLine();
			    System.out.print("Write your ID: ");
			    id = Integer.parseInt(input.nextLine());
			    System.out.print("Write your password: ");
			    str2 = input.nextLine();
			    subscriber = new SubscriberClient(str1, id, str2);
			    System.out.println(serverObj.subscriberRegistration(str1, id, str2));
			    menuSubscriber(subscriber, serverObj, true);
	
			case 2:
			    // Checking if user is registed
			    System.out.println("Write your name: ");
			    str1 = input.nextLine();
			    System.out.println("Write your ID: ");
			    id = Integer.parseInt(input.nextLine());
			    System.out.println("Write your password: ");
			    str2 = input.nextLine();
			    verification = serverObj.subscriberVerification(str1, id, str2);
			    menuSubscriber(subscriber, serverObj, verification);
	
			case 3: 
				SubscriberClient guest = new SubscriberClient("guest", 0 , "guest");
			    menuSubscriber(guest, serverObj, true);
			    
			case 4:
			    System.out.println("Leaving...");
			    System.exit(0);
	
			default:
			    System.out.println("Select a valid option");
			}
		    } while (option != 4);
		} catch (Exception e) {
		    System.out.println("Exception occured: " + e);
		    System.exit(0);
		}
    }
}