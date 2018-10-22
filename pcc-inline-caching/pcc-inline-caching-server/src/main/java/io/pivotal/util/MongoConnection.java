package io.pivotal.util;

import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class MongoConnection {
	
	private static MongoConnection mongoSingleInstance = null;
	public DBCollection itemCollection;
	
	private static final String MONGO_ADDRESS = "127.0.0.1";
	private static final Integer MONGO_PORT = 27017;
	private static final String DB_NAME = "test";
	private static final String COLLECTION_NAME = "test";
	
	private MongoConnection() {
		itemCollection = new MongoClient(MONGO_ADDRESS, MONGO_PORT).getDB(DB_NAME).getCollection(COLLECTION_NAME);
	}
	
	public static MongoConnection getInstance() {
		if (mongoSingleInstance == null) {
			mongoSingleInstance = new MongoConnection();
		}
		
		return mongoSingleInstance;
	}

}
