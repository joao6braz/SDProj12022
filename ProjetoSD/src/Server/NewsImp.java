package Server;

import Client.Publisher;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;


public class NewsImp extends UnicastRemoteObject implements News{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Publisher publisher;
	private char[] pieceOfNews = new char[180];
	private Timestamp timestamp;
	
	public NewsImp(char[] pieceOfNews, Publisher publisher) throws RemoteException {
		super();
		this.pieceOfNews = pieceOfNews;
		this.publisher = publisher;
		timestamp = new Timestamp(System.currentTimeMillis());
	}
	
	@Override
	public String getPublisher() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char[] getNews() throws RemoteException {
		// TODO Auto-generated method stub
		return this.pieceOfNews;
	}

	@Override
	public Timestamp getTimestamp() throws RemoteException {
		// TODO Auto-generated method stub
		return this.timestamp;
	}
	
}
