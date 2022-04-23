package Server;

import java.io.*;
import java.net.*;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.text.*;

public class RMIImp extends UnicastRemoteObject implements RMIInterface{
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Topic> listOfTopics;
	private ArrayList<News> listOfNews;
	private File topicsFile;
	private File newsFile;
	
	protected RMIImp() throws RemoteException {
		super();
		listOfTopics = new ArrayList<Topic>();
		listOfNews = new ArrayList<News>();
		
		newsFile = new File ("newsFile.dat");
		topicsFile = new File ("topicsFile.dat");
		
		
		if(!newsFile.exists()) {
			try {
				boolean result = newsFile.createNewFile(); 
				
				if (result) System.out.println("News file created!");
				
			} catch (IOException e) {
				System.out.println("Error creating the news File!");
				System.out.println(e.getMessage());
			}
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
		
		if(!topicsFile.exists()) {
			try {
				boolean result2 = topicsFile.createNewFile();
				
				if (result2) System.out.println("Topic file created!");
				
			} catch(IOException e) {
				System.out.println("Error creating the topic File!");
				System.out.println(e.getMessage());
			}
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
	
	@Override
	public void addNewTopic(String topicName) throws RemoteException {
		Topic newTopic = new Topic(topicName);
		listOfTopics.add(newTopic);
		
		writeInTopicFile(topicsFile, listOfTopics);
		
		/*try {
			FileOutputStream ostream = new FileOutputStream(topicsFile);
			ObjectOutputStream oos = new ObjectOutputStream(ostream);
			oos.writeObject(listOfTopics);
			oos.flush();
			oos.close();
			
		} catch (IOException e) {
			System.out.println("Error writing in topics file!");
		}*/
	}
	
	@Override
	public String consultTopics() throws RemoteException {
		String response = "";
		for (int i = 0; i < listOfTopics.size(); i++) {
			response += listOfTopics.get(i).getTopicName() + "\n";
		}
		return response;
	}
	
	@Override
	public void subscribeTopic(String targetTopicName, String newName) throws RemoteException {
		for (int i = 0; i < listOfTopics.size(); i++) { 
			if(listOfTopics.get(i).getTopicName().equals(targetTopicName)) {
				listOfTopics.get(i).setTopicName(newName);
				break;
			}
		}
		
		try {
			boolean deleted = topicsFile.delete();
			
			if(deleted) {
				System.out.println("Topic file deleted successfully!");
				topicsFile = new File ("topicsFile.dat");
				
				writeInTopicFile(topicsFile, listOfTopics);
				/*FileOutputStream ostream = new FileOutputStream(topicsFile);
				ObjectOutputStream oos = new ObjectOutputStream(ostream);
				oos.writeObject(listOfTopics);
				oos.flush();
				oos.close();*/
				
			} else System.out.println("Topic file not deleted!");
			
		} catch (SecurityException e) {
			System.out.println("Error deleting the topic file!");
		}
	}
	
	@Override
	public void addPieceOfNews(String topicName, char[] pieceOfNews, String publisherName) throws RemoteException {

		News newPieceOfNews = new News (topicName, publisherName, pieceOfNews);

		boolean topicFound = false;
		
		for (int i = 0; i < listOfTopics.size(); i++) {
			if(listOfTopics.get(i).getTopicName().equals(topicName)) {
				topicFound = true;
				
				if(reachedLimitOfNews(i)) {
					cleanTopic(i, topicName);
				}
				
				listOfTopics.get(i).setLatestPieceOfNews(newPieceOfNews);
				listOfTopics.get(i).incrementNewsCount();
				
				writeInTopicFile(topicsFile, listOfTopics);
				
				break;
			}
		}
		
		if(topicFound) {
			listOfNews.add(newPieceOfNews);
			
			writeInNewsFile(newsFile, listOfNews);
			
		} else { 
			//Escrever ao cliente que t�pico n�o foi encontrado (callback?)
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
		int iterator = 1;
		ArrayList<News> toBackup = new ArrayList<News>();
		
		for (int j = 0; j < listOfNews.size(); j++) {
			if(listOfNews.get(j).getTopicName().equals(topicName) && (iterator <= count)) {
				toBackup.add(listOfNews.get(j));
				listOfNews.remove(j);
				iterator++;
				
				if(iterator > count) break;
			}
		}
		copyToBackup(toBackup);
		listOfTopics.get(i).setNewsCount(count);
	}
	
	
	
	
	
	//RMI é o "cliente" 
	private void copyToBackup (ArrayList<News> backupNews) {
		Socket s;
		
		try {
			s = new Socket("127.0.0.1", 5432); // Inserir o IP e porto
			
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream()); //para testar, remover depois
			
			System.out.println(ois.readObject()+"\n");
			oos.writeObject("thIsActsLiKeaPsWD777122");
			oos.writeObject(backupNews); 
			System.out.println(ois.readObject()+"\n");
			oos.flush();
			oos.close();
			
		} catch(IOException | ClassNotFoundException e) { //remover exceção de teste
			System.out.println("Error starting socket connection with backup server!");
			e.printStackTrace();
		}
	}


	/*
	@Override
	public String consultAllNews() throws RemoteException {
		String response = "";
		DateFormat dateFormat = new SimpleDateFormat("\"dd/MM/yyyy\"");
		
		for (int i = 0; i < listOfNews.size(); i++) {
			String strDate = dateFormat.format(listOfNews.get(i).getTimestamp());
			
			response += "T�pico: " + listOfNews.get(i).getTopicName() + " | " 
					+  "Produtor: " + listOfNews.get(i).getPublisher() + " | "
					+ "Data da publica��o: " + strDate + " | "
					+ "Not�cia: " + String.valueOf(listOfNews.get(i).getPieceOfNews()) + "\n";
		}
		
		return response;
	}
*/
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
				if (lte == 0 || lte < 0) {
					response += String.valueOf(listOfNews.get(i).getPieceOfNews()) + "\n";
				}
				
				if(gte == 0 || gte > 0) {
					response += String.valueOf(listOfNews.get(i).getPieceOfNews()) + "\n";
				}
			}
		}
		
		if(response.equals("")) {
			response = "Not�cias entre as datas especificadas n�o encontradas! Servidor de arquivo @ IP: 127.0.0.1, Porta: 5432";
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
			response = "Nenhuma noticia vinculada ao topico: " + topicName + "\n";
		}
		
		return response;
	}

}
