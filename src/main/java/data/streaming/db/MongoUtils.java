package data.streaming.db;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;

import data.streaming.db.vo.Project;
import data.streaming.dto.KeywordDTO;

public class MongoUtils {
	
	private static final String MONGO_DB = "mongodb://manuel:manuel@ds255455.mlab.com:55455/si1718-mha-projects";
	private static final String MONGO_CL = "projects";
	private static final String MONGO_TW = "tweets";
	
	public static MongoDatabase database= null; 
	
	public static void initialize() {
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		
		MongoClientURI uri = new MongoClientURI(MONGO_DB);
		@SuppressWarnings("resource")
		MongoClient mongoClient = new MongoClient(uri);
		database = mongoClient.getDatabase(uri.getDatabase()).withCodecRegistry(pojoCodecRegistry);
	}
	
	public static List<String> getKeywords() {
		List<String> keywords = new ArrayList<String>();
		MongoCollection<Project> collection = database.getCollection(MONGO_CL, Project.class);
		MongoCursor<Project> cursor = collection.find().iterator();
		
		try {
		    while (cursor.hasNext()) {
		    	for(String keyword: cursor.next().getKeywords()) {
		    		if(!keywords.contains(keyword) && !keyword.trim().isEmpty()) {
		    			keywords.add(keyword);
		    		}
		    	}
		    }
		} finally {
		    cursor.close();
		}
		
		return keywords;
	}

	public static KeywordDTO addKeyword(KeywordDTO x) {
		MongoCollection<KeywordDTO> collection = database.getCollection(MONGO_TW, KeywordDTO.class);
		BasicDBObject query = new BasicDBObject();
		query.append("date", x.getDate());
		query.append("key", x.getKey());
		
		try {
			collection.findOneAndUpdate(query, new Document("$set", x), (new FindOneAndUpdateOptions()).upsert(true));
		} catch (Exception e) {}
		
		return x;
	}

}
