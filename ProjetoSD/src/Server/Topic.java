package Server;

import java.rmi.*;
import java.util.*;

import Client.Publisher;

public interface Topic extends Remote{
	public String getTopicName() throws RemoteException;
	public int getCountOfNews() throws RemoteException;
	public ArrayList<News> getNews() throws RemoteException;
	public void setTopicName(String topicName) throws RemoteException;
	public void setNews(char[] pieceOfNews, Publisher publisher) throws RemoteException;
	public News getLastElement() throws RemoteException;
	public News getNewsInDateRange(Date date1, Date date2) throws RemoteException;
}
