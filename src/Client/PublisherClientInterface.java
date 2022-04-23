package Client;

public interface PublisherClientInterface extends java.rmi.Remote {
	public void printOnClient(String s) throws java.rmi.RemoteException;
}
