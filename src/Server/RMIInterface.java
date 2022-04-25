package Server;

import java.rmi.*;
import java.util.*;

import Client.SubscriberClient;
import Client.SubscriberClientInterface;

public interface RMIInterface extends Remote{
	public void addNewTopic(String topicName) throws RemoteException;
	public String consultTopics() throws RemoteException;
	public String addPieceOfNews(String topicName, char[] pieceOfNews, String publisherName) throws RemoteException;
	public void subscribeToTopic(String topicName) throws RemoteException;
	public String consultNewsInDateRange (String topicName, Date date1, Date date2) throws RemoteException;
	public String consultLastestNews (String topicName) throws RemoteException;
	public String consultAllNews () throws RemoteException;
	public void publisherRegistration(String name, int id, String password) throws RemoteException;;
	public boolean publisherVerification(int id, String password) throws RemoteException;
	public String subscriberRegistration(String name, int id, String password) throws RemoteException;;
	public boolean subscriberVerification(String name, int id, String password) throws RemoteException;
}
