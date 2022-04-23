package Server;

import java.io.*;
import java.util.*;

public class Topic implements Serializable{

	private static final long serialVersionUID = 1L;
	private String topicName;
	private News latestPieceOfNews;
	private int newsCount;
	private ArrayList<String> subscribers;
	
	public Topic(String topicName) {
		this.topicName = topicName;
		this.newsCount = 0;
		this.subscribers = new ArrayList<String>();
	}

	public boolean checkSubscription(String consumerName) {
		boolean result = subscribers.contains(consumerName);
		return result;
	}

	public void setSubscribers(String subscriber) {
		this.subscribers.add(subscriber);
	}

	public void setTopicName(String newTopicName) {
		this.topicName = newTopicName;
	}
	
	public void setLatestPieceOfNews(News latestPieceOfNews) {
		this.latestPieceOfNews = latestPieceOfNews;
	}

	public void setNewsCount(int newsCount) {
		this.newsCount = newsCount;
	}

	public String getTopicName() {
		return topicName;
	}
	
	public News getLatestPieceOfNews() {
		return latestPieceOfNews;
	}

	public int getNewsCount() {
		return newsCount;
	}
	
	public void incrementNewsCount() {
		this.newsCount++;
	}
}
