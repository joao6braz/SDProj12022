package Server;

import java.io.*;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.text.*;
import Client.ClientInterface;
import Client.PublisherClient;



public class RMIImp extends UnicastRemoteObject implements RMIInterface{
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Topic> listOfTopics;
	private ArrayList<News> listOfNews;
	private ArrayList<PublisherClient> listOfPublishers; 
	private File topicsFile;
	private File newsFile;
	private File publishersRegistration; 
	private String clientName;
	
	protected RMIImp() throws RemoteException {
		super();
		listOfTopics = new ArrayList<Topic>();
		listOfNews = new ArrayList<News>();
		listOfPublishers = new ArrayList<PublisherClient>();
		
		newsFile = new File ("newsFile.dat");
		topicsFile = new File ("topicsFile.dat");
		publishersRegistration = new File ("publishersRegistration.dat");
		//
		
		
		if(!newsFile.exists()) {
			try {
				boolean result = newsFile.createNewFile();
				
				if (result) System.out.println("News file created!");
				
			} catch (IOException e) {
				System.out.println("Error creating the news File!");
				System.out.println(e.getMessage());
			}
		} else {
			if(newsFile.length() == 0) {
				System.out.println("News file exists and is empty!");
			} else {
				try {
					FileInputStream istream = new FileInputStream(newsFile);
					ObjectInputStream ois = new ObjectInputStream(istream);
					
					while(true) {
						try {
							@SuppressWarnings("unchecked")
							ArrayList<News> readObject = ((ArrayList<News>) ois.readObject());
							listOfNews = readObject;
							
						} catch (EOFException e) {
							System.out.println("End of News file reached!");
							break;
						} catch (IOException | ClassNotFoundException e) {
							System.out.println("Error reading news file!");
							e.printStackTrace();
							break;
						}
					}
					istream.close();
					ois.close();
					
				} catch(IOException e) {
					System.out.println("Error reading topic file!");
					e.printStackTrace();
				}
			}
		}
		
		if(!topicsFile.exists()) {
			try {
				boolean result2 = topicsFile.createNewFile();
				
				if (result2) System.out.println("Topic file created!");
				
			} catch(IOException e) {
				System.out.println("Error creating the topic File!");
				System.out.println(e.getMessage());
			}
		} else {
			if(topicsFile.length() == 0) {
				System.out.println("Topics file exists and is empty!");
			} else {
				try {
					FileInputStream istream = new FileInputStream(topicsFile);
					ObjectInputStream ois = new ObjectInputStream(istream);
					
					while(true) {
						try {
							@SuppressWarnings("unchecked")
							ArrayList<Topic> readObject = ((ArrayList<Topic>) ois.readObject());
							listOfTopics = readObject;
							
						} catch (EOFException e) {
							System.out.println("End of topic file reached!");
							break;
						} catch (IOException | ClassNotFoundException e) {
							System.out.println("Error reading topic file!");
							e.printStackTrace();
							break;
						}
					}
					istream.close();
					ois.close();
				} catch(IOException e) {
					System.out.println("Error reading topic file!");
					e.printStackTrace();
				}
			}
		}
		
		if(!publishersRegistration.exists()) {
			try {
				boolean result3 = publishersRegistration.createNewFile();
				
				if (result3) System.out.println("Publishers registration file created!");
				
			} catch(IOException e) {
				System.out.println("Error creating publishers registration file!");
				System.out.println(e.getMessage());
			}
		} else {
			if(publishersRegistration.length() == 0) {
				System.out.println("Publisher file exists and is empty!");
			} else {
				try {
					FileInputStream istream = new FileInputStream(publishersRegistration);
					ObjectInputStream ois = new ObjectInputStream(istream);
					
					while(true) {
						try {
							@SuppressWarnings("unchecked")
							ArrayList<PublisherClient> readObject = ((ArrayList<PublisherClient>) ois.readObject());
							listOfPublishers = readObject;
							
						} catch (EOFException e) {
							System.out.println("End of publishers registration file reached!");
							break;
						} catch (IOException | ClassNotFoundException e) {
							System.out.println("Error reading publishers registration file!");
							e.printStackTrace();
							break;
						}
					}
					istream.close();
					ois.close();
				} catch(IOException e) {
					System.out.println("Error reading publishers registration file!");
					e.printStackTrace();
				}
			}
		}
		
	}
	// Implementar método para instanciar client e para identificar qual o cliente
	
	@Override
	public void subscribe(String name, ClientInterface client) throws RemoteException {
		System.out.println("Subscribing " + name);
		this.clientName = name;
	}
	
	private void writeInTopicFile(File file, ArrayList<Topic> array) {
		try {
			FileOutputStream ostream = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(ostream);
			oos.writeObject(array);
			oos.flush();
			oos.close();
			
		} catch (IOException e) {
			System.out.println("Error writing in topics file!");
		}
	}
	
	private void writeInNewsFile(File file, ArrayList<News> array) {
		try {
			FileOutputStream ostream = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(ostream);
			oos.writeObject(array);
			oos.flush();
			oos.close();
			
		} catch (IOException e) {
			System.out.println("Error writing in news file!");
		}
	}
	
	private void writeInRegistrationFile(File file, ArrayList<PublisherClient> array) {
		try {
			FileOutputStream ostream = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(ostream);
			oos.writeObject(array);
			oos.flush();
			oos.close();
			
		} catch (IOException e) {
			System.out.println("Error writing in news file!");
		}
	}
	
	@Override
	public void addNewTopic(String topicName) throws RemoteException {
		Topic newTopic = new Topic(topicName);
		listOfTopics.add(newTopic);
		
		writeInTopicFile(topicsFile, listOfTopics);
	}
	
	@Override
	public String consultTopics() throws RemoteException {
		String response = "";
		for (int i = 0; i < listOfTopics.size(); i++) {
			response += listOfTopics.get(i).getTopicName() + " nº de notícias: " + listOfTopics.get(i).getNewsCount() + "\n";
		}
		return response;
	}
	
	@Override
	public void subscribeToTopic(String topicName, String subscriber) throws RemoteException {
		for (int i = 0; i < listOfTopics.size(); i++) {
			if(listOfTopics.get(i).getTopicName().equals(topicName)) {
				listOfTopics.get(i).setSubscribers(subscriber);
			}
		}
	}
	
	@Override
	public void addPieceOfNews(String topicName, char[] pieceOfNews, String publisherName) throws RemoteException {

		News newPieceOfNews = new News (topicName, publisherName, pieceOfNews);

		boolean topicFound = false;
		
		for (int i = 0; i < listOfTopics.size(); i++) {
			if(listOfTopics.get(i).getTopicName().equals(topicName)) {
				topicFound = true;
				
				// Communicate with client
				boolean clientSubscribed = listOfTopics.get(i).checkSubscription(clientName);
				if(clientSubscribed) {
					String msg = "Foi adicionada ao tópico " + listOfTopics.get(i).getTopicName() + " uma nova notícia: " + String.valueOf(pieceOfNews);
					//client.printOnClient(msg);
				}
				
				if(reachedLimitOfNews(i)) {
					cleanTopic(i, topicName);
				}
				
				listOfTopics.get(i).setLatestPieceOfNews(newPieceOfNews);
				listOfTopics.get(i).incrementNewsCount();
				writeInTopicFile(topicsFile, listOfTopics);
				
				listOfNews.add(newPieceOfNews);
				writeInNewsFile(newsFile, listOfNews);
				
				
				break;
			}
		}
	}
	
	private boolean reachedLimitOfNews(int i) {
		File f = new File ("config.dat");
		
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			
			String linha = br.readLine();
			int limitNumber = Integer.parseInt(linha);
			
			br.close();
			
			if(listOfTopics.get(i).getNewsCount() >= limitNumber) {
				return true;
			}
			
		} catch(IOException e) {
			System.out.println("Error reading the config.dat!");
			e.printStackTrace();
		}
		
		return false;
	}
	
	private void cleanTopic(int i, String topicName) {
		int count = listOfTopics.get(i).getNewsCount() / 2;
		
		int iterator = 0;
		ArrayList<News> toBackup = new ArrayList<News>();
		
		for (int j = 0; j < listOfNews.size(); j++) {
			if(listOfNews.get(j).getTopicName().equals(topicName) && (iterator <= count)) {
				iterator++;
				toBackup.add(listOfNews.remove(j));
				
				if(iterator > count) break;
			}
		}
		//copyToBackup(toBackup);
		int newNewsCount = listOfTopics.get(i).getNewsCount() - iterator;
		listOfTopics.get(i).setNewsCount(newNewsCount);
	}
	
	/*private void copyToBackup (ArrayList<News> backupNews) {
		Socket s;
		
		try {
			s = new Socket("", 0); // Inserir o IP e porto
			
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			
			oos.writeObject(backupNews);
			oos.flush();
			oos.close();
			
		} catch(IOException e) {
			System.out.println("Error starting socket connection with backup server!");
			e.printStackTrace();
		}
	}*/

	
	@Override
	public String consultAllNews() throws RemoteException {
		String response = "";
		DateFormat dateFormat = new SimpleDateFormat("\"dd/MM/yyyy\"");
		
		for (int i = 0; i < listOfNews.size(); i++) {
			String strDate = dateFormat.format(listOfNews.get(i).getTimestamp());
			
			response += "Tópico: " + listOfNews.get(i).getTopicName() + " | " 
					+  "Produtor: " + listOfNews.get(i).getPublisher() + " | "
					+ "Data da publicação: " + strDate + " | "
					+ "Notícia: " + String.valueOf(listOfNews.get(i).getPieceOfNews()) + "\n";
		}
		
		return response;
	}

	@Override
	public String consultNewsInDateRange(String topicName, Date date1, Date date2) throws RemoteException {
		String response = "";
		
		for (int i = 0; i < listOfNews.size(); i++) {
			if(listOfNews.get(i).getTopicName().equals(topicName)) {
				Date objDate = listOfNews.get(i).getTimestamp();
				
				int gte = objDate.compareTo(date1);
				int lte = objDate.compareTo(date2);
				System.out.println("Date of the news: " + objDate + " | date1: " + date1 + " | date2: " + date2);
				System.out.println("gte: " + gte + " | " + "lte: " + lte);
				
				if(gte == 0 || gte > 0) {
					if(lte == 0 || lte < 0) {
						response += String.valueOf(listOfNews.get(i).getPieceOfNews()) + "\n";
					}
				}
			}
		}
		
		if(response.equals("")) {
			response = "Notícias entre as datas especificadas não encontradas! Acesse o servidor backup para buscá-las pelo IP + porto";
		}
		
		return response;
	}

	@Override
	public String consultLastestNews(String topicName) throws RemoteException {
		String response = "Topic not found!";
		
		try {
			for (int i = 0; i < listOfTopics.size(); i++) { 
				if(listOfTopics.get(i).getTopicName().equals(topicName)) {
					News latestPieceOfNews = listOfTopics.get(i).getLatestPieceOfNews();
					char[] latestPieceOfNewsChar = latestPieceOfNews.getPieceOfNews();
						
					response = String.valueOf(latestPieceOfNewsChar);
				}
			}
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			response = "Nenhuma notícia vinculada ao tópico: " + topicName + "\n";
		}
		
		return response;
	}

	@Override
	public void publisherRegistration(String name, int id, String password) throws RemoteException {
		PublisherClient newPublisher = new PublisherClient(name, id, password);
		
		if(!publisherVerification(id, password)) {
			listOfPublishers.add(newPublisher);
			writeInRegistrationFile(publishersRegistration, listOfPublishers);
			System.out.println("Successful registration");
		}
		else
			System.out.println("You are already registered");
	}
	//
	
	@Override
	public boolean publisherVerification(int id, String password) throws RemoteException{
        for (int i = 0; i < listOfPublishers.size(); i++) {
            if(listOfPublishers.get(i).getId() == id && listOfPublishers.get(i).getPassword().equals(password))
                return true;
        }
        return false;
    }
}
