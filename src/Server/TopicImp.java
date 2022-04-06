package Server;

import Client.Publisher;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class TopicImp extends UnicastRemoteObject implements Topic{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String topicName;
	private ArrayList<News> listOfNews;
	
	protected TopicImp() throws RemoteException {
		super();
		listOfNews = new ArrayList<News>();
	}
	
	@Override
	public ArrayList<News> getNews() throws RemoteException {
		return this.listOfNews;
	}

	@Override
	public String getTopicName() throws RemoteException {
		return this.topicName;
	}

	@Override
	public int getCountOfNews() throws RemoteException {
		return this.listOfNews.size();
	}
	
	@Override
	public News getLastElement() throws RemoteException {
		return this.listOfNews.get(this.listOfNews.size() - 1);
	}

	@Override
	public News getNewsInDateRange(Date date1, Date date2) throws RemoteException {
		return null;
	}

	@Override
	public void setTopicName(String topicName) throws RemoteException {
		this.topicName = topicName;
		
	}

	@Override
	public void setNews(char[] pieceOfNews, Publisher publisher) throws RemoteException {
		News newPieceOfNews = new NewsImp(pieceOfNews, publisher);
		this.listOfNews.add(newPieceOfNews);
		
	}

}
