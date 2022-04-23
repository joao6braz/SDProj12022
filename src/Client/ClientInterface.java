package Client;

public interface ClientInterface extends java.rmi.Remote{
	public void printOnClient (String s) throws java.rmi.RemoteException;
}
