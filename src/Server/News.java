package Server;

import java.rmi.*;
import java.sql.Timestamp;

public interface News extends Remote{
	public String getPublisher() throws RemoteException;
	public char[] getNews() throws RemoteException;
	public Timestamp getTimestamp() throws RemoteException;
}
