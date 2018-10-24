package io.pivotal.event.writebehind;

import java.util.List;
import java.util.Properties;

import org.apache.geode.LogWriter;
import org.apache.geode.cache.CacheFactory;
import org.apache.geode.cache.Operation;
import org.apache.geode.cache.asyncqueue.AsyncEvent;
import org.apache.geode.cache.asyncqueue.AsyncEventListener;
import org.apache.geode.pdx.JSONFormatter;
import org.apache.geode.pdx.PdxInstance;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

@SuppressWarnings("deprecation")
public class ItemAsyncEventListener implements AsyncEventListener {

	private static LogWriter log;
	
	private DBCollection itemCollection;

	static {
		log = CacheFactory.getAnyInstance().getDistributedSystem().getLogWriter();
	}

	public void init(Properties props) {
		String mongoAddress = props.getProperty("mongoAddress");
		String mongoPort = props.getProperty("mongoPort");
		String dbName = props.getProperty("dbName");
		String collectionName = props.getProperty("collectionName");
		
		itemCollection = new MongoClient(mongoAddress, Integer.parseInt(mongoPort)).getDB(dbName).getCollection(collectionName);
	}

	public void close() {}

	@SuppressWarnings({ "rawtypes"})
	@Override
	public boolean processEvents(List<AsyncEvent> events) {

		for (AsyncEvent ge : events) {
			if (ge.getOperation().equals(Operation.CREATE)) {
				PdxInstance item = (PdxInstance) ge.getDeserializedValue();

				log.info("CREATE event caught... Inserting into MongoDB now...");
				
				insertItem(JSONFormatter.toJSON(item));
			}
			// SKIP "UPDATE" AND "DELETE" EVENT FOR NOW
		}
		
		return true;
	}
	
	private void insertItem(String jsonString) {
		
		BasicDBObject doc = new BasicDBObject().parse(jsonString);
		
		itemCollection.insert(doc); 
	}
	
}
