package linkedincoursera;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import java.net.UnknownHostException;


@SpringBootApplication()
@PropertySource(value = {"classpath:/properties/application.properties"},ignoreResourceNotFound = false)

public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    MongoClient client() throws UnknownHostException {
        return new MongoClient(new MongoClientURI("mongodb://harshank:password@ds047581.mongolab.com:47581/cmpe273"));
    }

}

