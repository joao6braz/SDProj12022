package Server;

import java.rmi.*;
import java.util.*;

import Client.ClientInterface;
import Client.PublisherClient;

public interface RMIInterface extends Remote{
	public void addNewTopic(String topicName) throws RemoteException;
	public String consultTopics() throws RemoteException;
	public void addPieceOfNews(String topicName, char[] pieceOfNews, String publisherName) throws RemoteException;
	public void subscribeToTopic(String topicName, String subscriber) throws RemoteException;
	public String consultNewsInDateRange (String topicName, Date date1, Date date2) throws RemoteException;
	public String consultLastestNews (String topicName) throws RemoteException;
	public String consultAllNews () throws RemoteException;
	public void subscribe(String name, ClientInterface client) throws RemoteException; // Subscrever Consumidor
	public void publisherRegistration(String name, int id, String password) throws RemoteException;;
	public boolean publisherVerification(int id, String password) throws RemoteException;;
}
