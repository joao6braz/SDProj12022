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
		
		
		if(!newsFile.exists()) { // Verifica se arquivo j� existe
			try {
				boolean result = newsFile.createNewFile(); // Cria um novo arquivo e retorna true se foi bem sucedido
				if (result) System.out.println("News file created!");
			
			} catch (IOException e) { // Captura exce��o na cria��o do arquivo
				System.out.println("Error creating the news File!");
				System.out.println(e.getMessage());
			}
		} else { // Arquivo j� existe
			if(newsFile.length() == 0) { // Verifica se arquivo est� vazio
				System.out.println("News file exists and is empty!");
			} else { // Arquivo n�o vazio
				try { // Leitura de objetos de um ficheiro com ObjectInputStream
					FileInputStream istream = new FileInputStream(newsFile);
					ObjectInputStream ois = new ObjectInputStream(istream);
					
					while(true) { // Enquanto n�o chegar no fim do arquivo
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
		
		if(!topicsFile.exists()) { // Verifica se arquivo j� existe
			try {
				boolean result2 = topicsFile.createNewFile(); // Cria um novo arquivo e retorna true se foi bem sucedido
				
				if (result2) System.out.println("Topic file created!");
				
			} catch(IOException e) { // Captura exce��o na cria��o do arquivo
				System.out.println("Error creating the topic File!");
				System.out.println(e.getMessage());
			}
		} else { // Arquivo j� existe
			if(topicsFile.length() == 0) {
				System.out.println("Topics file exists and is empty!");
			} else { // Arquivo n�o vazio
				try { // Leitura do arquivo com ObjectInputStream
					FileInputStream istream = new FileInputStream(topicsFile);
					ObjectInputStream ois = new ObjectInputStream(istream);
					
					while(true) { // Enquanto n�o chegar no fim do arquivo
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
		
		if(!publishersRegistration.exists()) { // Verifica se arquivo j� existe
			try {
				boolean result3 = publishersRegistration.createNewFile();
				
				if (result3) System.out.println("Publishers registration file created!");
				
			} catch(IOException e) { // Captura exce��o na cria��o do arquivo
				System.out.println("Error creating publishers registration file!");
				System.out.println(e.getMessage());
			}
		} else { // Arquivo j� existe
			if(publishersRegistration.length() == 0) {
				System.out.println("Publisher file exists and is empty!");
			} else { // Arquivo n�o vazio
				try { // Leitura do arquivo com ObjectInputStream
					FileInputStream istream = new FileInputStream(publishersRegistration);
					ObjectInputStream ois = new ObjectInputStream(istream);
					
					while(true) { // Enquanto n�o chegar no fim do arquivo
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
		
		if(!subscriberRegistration.exists()) { // Verifica se arquivo j� existe
			try {
				boolean result3 = subscriberRegistration.createNewFile();
				
				if (result3) System.out.println("Subscribers registration file created!");
				
			} catch(IOException e) { // Captura exce��o na cria��o do arquivo
				System.out.println("Error creating subscribers registration file!");
				System.out.println(e.getMessage());
			}
		} else { // Arquivo j� existe
			if(subscriberRegistration.length() == 0) {
				System.out.println("Subscribers file exists and is empty!");
			} else { // Arquivo n�o vazio
				try { // Leitura do arquivo com ObjectInputStream
					FileInputStream istream = new FileInputStream(subscriberRegistration);
					ObjectInputStream ois = new ObjectInputStream(istream);
					
					while(true) { // Enquanto n�o chegar no fim do arquivo
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
		// Cria um objeto da classe T�pico
		Topic newTopic = new Topic(topicName);
		// Adiciona � lista de t�picos
		listOfTopics.add(newTopic);
		// Escreve no arquivo de t�picos
		writeInTopicFile(topicsFile, listOfTopics);
	}
	
	@Override
	public String consultTopics() throws RemoteException {
		// Inicializa vari�vel de resposta
		String response = "";
		// Percorre toda a lista de t�picos
		for (int i = 0; i < listOfTopics.size(); i++) {
			// Armazena o nome do t�pico e seu n�mero de not�cias na vari�vel de resposta
			response += listOfTopics.get(i).getTopicName() + " n� de not�cias: " + listOfTopics.get(i).getNewsCount() + "\n";
		}
		// Retorna a vari�vel re resposta
		return response;
	}
	
	@Override
	public synchronized String addPieceOfNews(String topicName, char[] pieceOfNews, String publisherName) throws RemoteException {
		String response = ""; // Vari�vel de retorno da fun��o
		News newPieceOfNews = new News (topicName, publisherName, pieceOfNews); // Instanciar objeto da classe News
		boolean topicFound = false; // Vari�vel para verificar se encontrou o t�pico
		// Percorrer a lista de t�picos
		for (int i = 0; i < listOfTopics.size(); i++) {
			if(listOfTopics.get(i).getTopicName().equals(topicName)) { // Condicional para verificar se encontrou o t�pico
				topicFound = true; // Indica que t�pico foi encontrado
				
				// Callback
				ArrayList<SubscriberClient> subscribers = listOfTopics.get(i).getSubscribers();
				System.out.println(subscribers.size());
				for (int j = 0; j < subscribers.size(); j++) {
					System.out.println(subscribers.get(j).getName());
					String msg = "Foi adicionada ao t�pico " + listOfTopics.get(i).getTopicName() 
							+ " uma nova not�cia: " 
							+ String.valueOf(pieceOfNews);
					subscribers.get(j).printOnClient(msg);
				}
				
				if(reachedLimitOfNews(i)) { // Verifica se alcan�ou limite de not�cias para o t�pico
					// Limite alcan�ado
					cleanTopic(i, topicName); // Realiza a remo��o de 50% das not�cias do t�pico
				}
				listOfTopics.get(i).setLatestPieceOfNews(newPieceOfNews); // Armazena a nova not�cia como a  �ltima not�cia do t�pico
				listOfTopics.get(i).incrementNewsCount(); // Incrementa o contador de not�cias do t�pico
				writeInTopicFile(topicsFile, listOfTopics); // Subescreve no arquivo de t�picos
				listOfNews.add(newPieceOfNews); // Adiciona a not�cia na lista de not�cias
				writeInNewsFile(newsFile, listOfNews); // Subscreve no arquivo de not�cias
				break; // encerra o ciclo
			}
		}
		// Verifica se t�pico foi encontrado
		if (topicFound) {
			response += "News added!";
		} else {
			response += "Topic not found!";
		}
		return response; // Retorna resposta ao cliente
	}
	
	private synchronized boolean reachedLimitOfNews(int i) {
		// Arquivo contendo o n�mero limite de not�cias por t�pico
		File f = new File ("config.dat");
		
		try { // Leitura do arquivo
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			
			String linha = br.readLine();
			// Convers�o em um inteiro
			int limitNumber = Integer.parseInt(linha);
			// Fechar o stream de leitura
			br.close();
			// Condicional para verifica��o se o limite foi alcan�ado
			if(listOfTopics.get(i).getNewsCount() >= limitNumber) {
				// Limite alcan�ado, retorna true
				return true;
			}
			
		} catch(IOException e) {
			System.out.println("Error reading the config.dat!");
			e.printStackTrace();
		}
		// Limite n�o alcan�ado, retorna false
		return false;
	}
	
	private synchronized void cleanTopic(int i, String topicName) {
		// N�mero de not�cias a serem removidas para o servidor de backup
		int count = listOfTopics.get(i).getNewsCount() / 2; 
		// Vari�vel de controle que define quando parar de remover not�cias de um t�pico
		int iterator = 0;
		// Lista de not�cias que ser�o enviadas ao servidor de backup
		ArrayList<News> toBackup = new ArrayList<News>();
		// Percorre a lista de not�cias at� a condi��o de paragem ser satisfeita
		for (int j = 0; j < listOfNews.size(); j++) {
			if(listOfNews.get(j).getTopicName().equals(topicName) && (iterator <= count)) {
				// Incremeta a vari�vel de controle de paragem
				iterator++;
				// Remove a not�cia da lista de not�cia e adiciona a lista de not�cias para backup
				toBackup.add(listOfNews.remove(j));
				// Condicional que define o crit�rio de paragem
				if(iterator > count) break;
			}
		}
		// Chamda do m�todo que envia a lista de not�cias removidas para o servidor de backup
		copyToBackup(toBackup);
		// Atualiza o contador de not�cias do t�pico
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
		String response = ""; // Vari�vel de resposta
		DateFormat dateFormat = new SimpleDateFormat("\"dd/MM/yyyy\""); // Vari�vel de formata��o de data
		// Percorrer a lista de not�cias
		for (int i = 0; i < listOfNews.size(); i++) {
			String strDate = dateFormat.format(listOfNews.get(i).getTimestamp()); // Converte a data em String
			// Cria a String de resposta com todas as informa��es das not�cias
			response += "T�pico: " + listOfNews.get(i).getTopicName() + " | " 
					+  "Produtor: " + listOfNews.get(i).getPublisher() + " | "
					+ "Data da publica��o: " + strDate + " | "
					+ "Not�cia: " + String.valueOf(listOfNews.get(i).getPieceOfNews()) + "\n";
		}
		// Retorna a vari�vel de resposta
		return response;
	}
	
	@Override
	public synchronized void subscribeToTopic(String topicName) throws RemoteException {
		// Percorre toda a lista de t�picos
		for (int i = 0; i < listOfTopics.size(); i++) {
			// Verifica se encontrou o t�pico pelo nome
			if(listOfTopics.get(i).getTopicName().equals(topicName)) {
				// Adiciona o consumidor � lista de inscritos
				listOfTopics.get(i).setSubscribers(client);
			}
		}
		writeInTopicFile(topicsFile, listOfTopics);
	}

	@Override
	public String consultNewsInDateRange(String topicName, Date date1, Date date2) throws RemoteException {
		String response = ""; // Vari�vel de resposta
		// Percorre toda a lista de not�cias
		for (int i = 0; i < listOfNews.size(); i++) {
			if(listOfNews.get(i).getTopicName().equals(topicName)) { // Verifica se t�pico da not�cia � o t�pico alvo
				Date objDate = listOfNews.get(i).getTimestamp(); // Armazena a data da not�cia
				
				int gte = objDate.compareTo(date1); // Fun��o que compara a data do objeto com o par�metro data1
				int lte = objDate.compareTo(date2); // Fun��o que compara a data do objeto com o par�metro data2
				
				if(gte == 0 || gte > 0) { // Verifica se data do objeto � maior ou igual a data1
					// DataObj >= data1
					if(lte == 0 || lte < 0) { // Verifica se data do objeto � menor ou igual a data2
						// DataObj <= data2
						// Armazena a not�cia na vari�vel de resposta
						response += String.valueOf(listOfNews.get(i).getPieceOfNews()) + "\n";
					}
				}
			}
		}
		
		if(response.equals("")) { // Se nenhum not�cia foi encontrada, envia o IP e porto do servidor de backup para pesquisa
			response = "notfound";
		}
		// Retorno da fun��o
		return response;
	}

	@Override
	public String consultLastestNews(String topicName) throws RemoteException {
		String response = "Topic not found!"; // Vari�vel de resposta
		
		try {
			// Percorre toda a lista de t�pico
			for (int i = 0; i < listOfTopics.size(); i++) { 
				if(listOfTopics.get(i).getTopicName().equals(topicName)) { // Verifica se t�pico da not�cia � o t�pico alvo
					// Recupera �ltima not�cia vinculada ao t�pico
					News latestPieceOfNews = listOfTopics.get(i).getLatestPieceOfNews();
					// Recupera o vetor char com a not�cia em si
					char[] latestPieceOfNewsChar = latestPieceOfNews.getPieceOfNews();
					// Converte o vetor de char em String
					response = String.valueOf(latestPieceOfNewsChar);
				}
			}
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
			response = "Nenhuma not�cia vinculada ao t�pico: " + topicName + "\n";
		}
		// Retorno da fun��o
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
