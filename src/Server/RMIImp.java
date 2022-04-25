package Server;

import java.io.*;
import java.net.*;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.text.*;
import Client.PublisherClient;
import Client.SubscriberClient;
import Client.SubscriberClientInterface;



public class RMIImp extends UnicastRemoteObject implements RMIInterface{
	
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Topic> listOfTopics;
	private ArrayList<News> listOfNews;
	private ArrayList<PublisherClient> listOfPublishers;
	private ArrayList<SubscriberClient> listOfSubscriber;
	private File topicsFile;
	private File newsFile;
	private File publishersRegistration;
	private File subscriberRegistration;
	private static SubscriberClient client;
	
	protected RMIImp() throws RemoteException {
		super();
		listOfTopics = new ArrayList<Topic>();
		listOfNews = new ArrayList<News>();
		listOfPublishers = new ArrayList<PublisherClient>();
		listOfSubscriber = new ArrayList<SubscriberClient>();
		
		newsFile = new File ("newsFile.dat");
		topicsFile = new File ("topicsFile.dat");
		publishersRegistration = new File ("publishersRegistration.dat");
		subscriberRegistration = new File ("subscriberRegistration.dat");
		
		
		if(!newsFile.exists()) { // Verifica se arquivo já existe
			try {
				boolean result = newsFile.createNewFile(); // Cria um novo arquivo e retorna true se foi bem sucedido
				if (result) System.out.println("News file created!");
			
			} catch (IOException e) { // Captura exceção na criação do arquivo
				System.out.println("Error creating the news File!");
				System.out.println(e.getMessage());
			}
		} else { // Arquivo já existe
			if(newsFile.length() == 0) { // Verifica se arquivo está vazio
				System.out.println("News file exists and is empty!");
			} else { // Arquivo não vazio
				try { // Leitura de objetos de um ficheiro com ObjectInputStream
					FileInputStream istream = new FileInputStream(newsFile);
					ObjectInputStream ois = new ObjectInputStream(istream);
					
					while(true) { // Enquanto não chegar no fim do arquivo
						try {
							@SuppressWarnings("unchecked") // Leitura do objeto do arquivo
							ArrayList<News> readObject = ((ArrayList<News>) ois.readObject());
							listOfNews = readObject; // Armazena no ArrayList
							
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
		
		if(!topicsFile.exists()) { // Verifica se arquivo já existe
			try {
				boolean result2 = topicsFile.createNewFile(); // Cria um novo arquivo e retorna true se foi bem sucedido
				
				if (result2) System.out.println("Topic file created!");
				
			} catch(IOException e) { // Captura exceção na criação do arquivo
				System.out.println("Error creating the topic File!");
				System.out.println(e.getMessage());
			}
		} else { // Arquivo já existe
			if(topicsFile.length() == 0) {
				System.out.println("Topics file exists and is empty!");
			} else { // Arquivo não vazio
				try { // Leitura do arquivo com ObjectInputStream
					FileInputStream istream = new FileInputStream(topicsFile);
					ObjectInputStream ois = new ObjectInputStream(istream);
					
					while(true) { // Enquanto não chegar no fim do arquivo
						try {
							@SuppressWarnings("unchecked") // Leitura do objeto do arquivo
							ArrayList<Topic> readObject = ((ArrayList<Topic>) ois.readObject());
							listOfTopics = readObject; // Armazena no ArrayList
							
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
		
		if(!publishersRegistration.exists()) { // Verifica se arquivo já existe
			try {
				boolean result3 = publishersRegistration.createNewFile();
				
				if (result3) System.out.println("Publishers registration file created!");
				
			} catch(IOException e) { // Captura exceção na criação do arquivo
				System.out.println("Error creating publishers registration file!");
				System.out.println(e.getMessage());
			}
		} else { // Arquivo já existe
			if(publishersRegistration.length() == 0) {
				System.out.println("Publisher file exists and is empty!");
			} else { // Arquivo não vazio
				try { // Leitura do arquivo com ObjectInputStream
					FileInputStream istream = new FileInputStream(publishersRegistration);
					ObjectInputStream ois = new ObjectInputStream(istream);
					
					while(true) { // Enquanto não chegar no fim do arquivo
						try {
							@SuppressWarnings("unchecked") // Leitura do objeto do arquivo
							ArrayList<PublisherClient> readObject = ((ArrayList<PublisherClient>) ois.readObject());
							listOfPublishers = readObject; // Armazena no ArrayList
							
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
		
		if(!subscriberRegistration.exists()) { // Verifica se arquivo já existe
			try {
				boolean result3 = subscriberRegistration.createNewFile();
				
				if (result3) System.out.println("Subscribers registration file created!");
				
			} catch(IOException e) { // Captura exceção na criação do arquivo
				System.out.println("Error creating subscribers registration file!");
				System.out.println(e.getMessage());
			}
		} else { // Arquivo já existe
			if(subscriberRegistration.length() == 0) {
				System.out.println("Subscribers file exists and is empty!");
			} else { // Arquivo não vazio
				try { // Leitura do arquivo com ObjectInputStream
					FileInputStream istream = new FileInputStream(subscriberRegistration);
					ObjectInputStream ois = new ObjectInputStream(istream);
					
					while(true) { // Enquanto não chegar no fim do arquivo
						try {
							@SuppressWarnings("unchecked") // Leitura do objeto do arquivo
							ArrayList<SubscriberClient> readObject = ((ArrayList<SubscriberClient>) ois.readObject());
							listOfSubscriber = readObject; // Armazena no ArrayList
							
						} catch (EOFException e) {
							System.out.println("End of subscribers registration file reached!");
							break;
						} catch (IOException | ClassNotFoundException e) {
							System.out.println("Error reading subscribers registration file!");
							e.printStackTrace();
							break;
						}
					}
					istream.close();
					ois.close();
				} catch(IOException e) {
					System.out.println("Error reading subscribers registration file!");
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private synchronized void writeInTopicFile(File file, ArrayList<Topic> array) {
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
	
	private synchronized void writeInNewsFile(File file, ArrayList<News> array) {
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
	
	private synchronized void writeInPublisherRegistrationFile(File file, ArrayList<PublisherClient> array) {
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
	
	private synchronized void writeInSubscriberRegistrationFile(File file, ArrayList<SubscriberClient> array) {
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
	public synchronized void addNewTopic(String topicName) throws RemoteException {
		// Cria um objeto da classe Tópico
		Topic newTopic = new Topic(topicName);
		// Adiciona à lista de tópicos
		listOfTopics.add(newTopic);
		// Escreve no arquivo de tópicos
		writeInTopicFile(topicsFile, listOfTopics);
	}
	
	@Override
	public String consultTopics() throws RemoteException {
		// Inicializa variável de resposta
		String response = "";
		// Percorre toda a lista de tópicos
		for (int i = 0; i < listOfTopics.size(); i++) {
			// Armazena o nome do tópico e seu número de notícias na variável de resposta
			response += listOfTopics.get(i).getTopicName() + " nº de notícias: " + listOfTopics.get(i).getNewsCount() + "\n";
		}
		// Retorna a variável re resposta
		return response;
	}
	
	@Override
	public synchronized String addPieceOfNews(String topicName, char[] pieceOfNews, String publisherName) throws RemoteException {
		String response = ""; // Variável de retorno da função
		News newPieceOfNews = new News (topicName, publisherName, pieceOfNews); // Instanciar objeto da classe News
		boolean topicFound = false; // Variável para verificar se encontrou o tópico
		// Percorrer a lista de tópicos
		for (int i = 0; i < listOfTopics.size(); i++) {
			if(listOfTopics.get(i).getTopicName().equals(topicName)) { // Condicional para verificar se encontrou o tópico
				topicFound = true; // Indica que tópico foi encontrado
				
				// Callback
				ArrayList<SubscriberClient> subscribers = listOfTopics.get(i).getSubscribers();
				System.out.println(subscribers.size());
				for (int j = 0; j < subscribers.size(); j++) {
					System.out.println(subscribers.get(j).getName());
					String msg = "Foi adicionada ao tópico " + listOfTopics.get(i).getTopicName() 
							+ " uma nova notícia: " 
							+ String.valueOf(pieceOfNews);
					subscribers.get(j).printOnClient(msg);
				}
				
				if(reachedLimitOfNews(i)) { // Verifica se alcançou limite de notícias para o tópico
					// Limite alcançado
					cleanTopic(i, topicName); // Realiza a remoção de 50% das notícias do tópico
				}
				listOfTopics.get(i).setLatestPieceOfNews(newPieceOfNews); // Armazena a nova notícia como a  última notícia do tópico
				listOfTopics.get(i).incrementNewsCount(); // Incrementa o contador de notícias do tópico
				writeInTopicFile(topicsFile, listOfTopics); // Subescreve no arquivo de tópicos
				listOfNews.add(newPieceOfNews); // Adiciona a notícia na lista de notícias
				writeInNewsFile(newsFile, listOfNews); // Subscreve no arquivo de notícias
				break; // encerra o ciclo
			}
		}
		// Verifica se tópico foi encontrado
		if (topicFound) {
			response += "News added!";
		} else {
			response += "Topic not found!";
		}
		return response; // Retorna resposta ao cliente
	}
	
	private synchronized boolean reachedLimitOfNews(int i) {
		// Arquivo contendo o número limite de notícias por tópico
		File f = new File ("config.dat");
		
		try { // Leitura do arquivo
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			
			String linha = br.readLine();
			// Conversão em um inteiro
			int limitNumber = Integer.parseInt(linha);
			// Fechar o stream de leitura
			br.close();
			// Condicional para verificação se o limite foi alcançado
			if(listOfTopics.get(i).getNewsCount() >= limitNumber) {
				// Limite alcançado, retorna true
				return true;
			}
			
		} catch(IOException e) {
			System.out.println("Error reading the config.dat!");
			e.printStackTrace();
		}
		// Limite não alcançado, retorna false
		return false;
	}
	
	private synchronized void cleanTopic(int i, String topicName) {
		// Número de notícias a serem removidas para o servidor de backup
		int count = listOfTopics.get(i).getNewsCount() / 2; 
		// Variável de controle que define quando parar de remover notícias de um tópico
		int iterator = 0;
		// Lista de notícias que serão enviadas ao servidor de backup
		ArrayList<News> toBackup = new ArrayList<News>();
		// Percorre a lista de notícias até a condição de paragem ser satisfeita
		for (int j = 0; j < listOfNews.size(); j++) {
			if(listOfNews.get(j).getTopicName().equals(topicName) && (iterator <= count)) {
				// Incremeta a variável de controle de paragem
				iterator++;
				// Remove a notícia da lista de notícia e adiciona a lista de notícias para backup
				toBackup.add(listOfNews.remove(j));
				// Condicional que define o critério de paragem
				if(iterator > count) break;
			}
		}
		// Chamda do método que envia a lista de notícias removidas para o servidor de backup
		copyToBackup(toBackup);
		// Atualiza o contador de notícias do tópico
		int newNewsCount = listOfTopics.get(i).getNewsCount() - iterator;
		listOfTopics.get(i).setNewsCount(newNewsCount);
	}
	
	private synchronized void copyToBackup (ArrayList<News> backupNews) {
		Socket s;
		 
		try {
			s = new Socket("127.0.0.1", 5432); // Inserir o IP e porto
			
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			//ObjectInputStream ois = new ObjectInputStream(s.getInputStream()); // Usada para teste
			
			//System.out.println(ois.readObject()+"\n");
			oos.writeObject("-1");
			oos.writeObject(backupNews); 
			//System.out.println(ois.readObject()+"\n");
			oos.flush();
			oos.close();
			
		} catch(IOException e) {
			System.out.println("Error starting socket connection with backup server!");
			e.printStackTrace();
		}
	}
	
	@Override
	public String consultAllNews() throws RemoteException {
		String response = ""; // Variável de resposta
		DateFormat dateFormat = new SimpleDateFormat("\"dd/MM/yyyy\""); // Variável de formatação de data
		// Percorrer a lista de notícias
		for (int i = 0; i < listOfNews.size(); i++) {
			String strDate = dateFormat.format(listOfNews.get(i).getTimestamp()); // Converte a data em String
			// Cria a String de resposta com todas as informações das notícias
			response += "Tópico: " + listOfNews.get(i).getTopicName() + " | " 
					+  "Produtor: " + listOfNews.get(i).getPublisher() + " | "
					+ "Data da publicação: " + strDate + " | "
					+ "Notícia: " + String.valueOf(listOfNews.get(i).getPieceOfNews()) + "\n";
		}
		// Retorna a variável de resposta
		return response;
	}
	
	@Override
	public synchronized void subscribeToTopic(String topicName) throws RemoteException {
		// Percorre toda a lista de tópicos
		for (int i = 0; i < listOfTopics.size(); i++) {
			// Verifica se encontrou o tópico pelo nome
			if(listOfTopics.get(i).getTopicName().equals(topicName)) {
				// Adiciona o consumidor à lista de inscritos
				listOfTopics.get(i).setSubscribers(client);
			}
		}
		writeInTopicFile(topicsFile, listOfTopics);
	}

	@Override
	public String consultNewsInDateRange(String topicName, Date date1, Date date2) throws RemoteException {
		String response = ""; // Variável de resposta
		// Percorre toda a lista de notícias
		for (int i = 0; i < listOfNews.size(); i++) {
			if(listOfNews.get(i).getTopicName().equals(topicName)) { // Verifica se tópico da notícia é o tópico alvo
				Date objDate = listOfNews.get(i).getTimestamp(); // Armazena a data da notícia
				
				int gte = objDate.compareTo(date1); // Função que compara a data do objeto com o parâmetro data1
				int lte = objDate.compareTo(date2); // Função que compara a data do objeto com o parâmetro data2
				
				if(gte == 0 || gte > 0) { // Verifica se data do objeto é maior ou igual a data1
					// DataObj >= data1
					if(lte == 0 || lte < 0) { // Verifica se data do objeto é menor ou igual a data2
						// DataObj <= data2
						// Armazena a notícia na variável de resposta
						response += String.valueOf(listOfNews.get(i).getPieceOfNews()) + "\n";
					}
				}
			}
		}
		
		if(response.equals("")) { // Se nenhum notícia foi encontrada, envia o IP e porto do servidor de backup para pesquisa
			response = "notfound";
		}
		// Retorno da função
		return response;
	}

	@Override
	public String consultLastestNews(String topicName) throws RemoteException {
		String response = "Topic not found!"; // Variável de resposta
		
		try {
			// Percorre toda a lista de tópico
			for (int i = 0; i < listOfTopics.size(); i++) { 
				if(listOfTopics.get(i).getTopicName().equals(topicName)) { // Verifica se tópico da notícia é o tópico alvo
					// Recupera última notícia vinculada ao tópico
					News latestPieceOfNews = listOfTopics.get(i).getLatestPieceOfNews();
					// Recupera o vetor char com a notícia em si
					char[] latestPieceOfNewsChar = latestPieceOfNews.getPieceOfNews();
					// Converte o vetor de char em String
					response = String.valueOf(latestPieceOfNewsChar);
				}
			}
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			response = "Nenhuma notícia vinculada ao tópico: " + topicName + "\n";
		}
		// Retorno da função
		return response;
	}

	@Override
	public synchronized void publisherRegistration(String name, int id, String password) throws RemoteException {
		PublisherClient newPublisher = new PublisherClient(name, id, password);
		
		if(!publisherVerification(id, password)) {
			listOfPublishers.add(newPublisher);
			writeInPublisherRegistrationFile(publishersRegistration, listOfPublishers);
			System.out.println("Successful registration");
		}
		else
			System.out.println("You are already registered");
	}
	
	@Override
	public boolean publisherVerification(int id, String password) throws RemoteException{
        for (int i = 0; i < listOfPublishers.size(); i++) {
            if(listOfPublishers.get(i).getId() == id && listOfPublishers.get(i).getPassword().equals(password))
                return true;
        }
        return false;
    }
	
	@Override
	public synchronized String subscriberRegistration(String name, int id, String password) throws RemoteException {
		String response = "";
		client = new SubscriberClient(name, id, password);
		
		if(!subscriberVerification(name, id, password)) { //if user doesnt exist create him and add him to our listofsubscribers
			listOfSubscriber.add(client);
			writeInSubscriberRegistrationFile(subscriberRegistration, listOfSubscriber);
			response = "Successful registration";
		}
		else
			response = "You are already registered";
		
		return response;
	}
	
	@Override
	public boolean subscriberVerification(String name, int id, String password) throws RemoteException{
		client = new SubscriberClient(name, id, password);
		
		for (int i = 0; i < listOfSubscriber.size(); i++) {
			if(listOfSubscriber.get(i).getId() == id && listOfSubscriber.get(i).getPassword().equals(password))
				return true;
		}
		return false;
	}
}
