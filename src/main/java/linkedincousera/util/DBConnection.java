package linkedincoursera.util;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Created by harsh on 5/10/15.
 */
public class DBConnection {
    public static MongoTemplate getConnection() {
        MongoClientURI uri = null;
        MongoClient mongoclient = null;
        MongoTemplate mongoConnection = null;

        try {
            uri = new MongoClientURI("mongodb://harshank:password@ds047581.mongolab.com:47581/cmpe273");
            MongoCredential mongoCredential = MongoCredential.createMongoCRCredential(uri.getUsername(), uri.getDatabase(), uri.getPassword());
            mongoclient = new MongoClient(new ServerAddress("ds047581.mongolab.com",47581), Arrays.asList(mongoCredential));
            mongoConnection = new MongoTemplate(mongoclient, uri.getDatabase());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mongoConnection;
    }

}
