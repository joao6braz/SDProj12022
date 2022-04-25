package Server;

import java.io.*;
import java.util.*;

public class News  implements Serializable{

	private static final long serialVersionUID = 1L;
	private String topicName;
	private String publisherName;
	private char[] pieceOfNews = new char[180];
	private Date timestamp;
	
	public News(String topicName, String publisherName, char[] pieceOfNews) {
		super();
		this.topicName = topicName;
		this.publisherName = publisherName;
		this.pieceOfNews = pieceOfNews;
		this.timestamp = new Date();
	}

	public String getPublisher() {
		return this.publisherName;
	}

	public char[] getPieceOfNews() {
		return pieceOfNews;
	}

	public String getTopicName() {
		return topicName;
	}

	public Date getTimestamp() {
		return timestamp;
	}
}
