package Server;

import java.io.*;
import java.util.*;

import Client.SubscriberClient;

public class Topic implements Serializable{

	private static final long serialVersionUID = 1L;
	private String topicName;
	private News latestPieceOfNews;
	private int newsCount;
	private ArrayList<SubscriberClient> subscribers;
	
	public Topic(String topicName) {
		this.topicName = topicName;
		this.newsCount = 0;
		this.subscribers = new ArrayList<SubscriberClient>();
	}

	public void setSubscribers(SubscriberClient targetSubcriber) {
		this.subscribers.add(targetSubcriber);
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
	
	public ArrayList<SubscriberClient> getSubscribers() {
		return subscribers;
	}

	public void incrementNewsCount() {
		this.newsCount++;
	}
}
