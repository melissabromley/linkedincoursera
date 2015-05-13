package linkedincoursera.repository;


import com.mongodb.*;
import linkedincoursera.model.coursera.Categories;
import linkedincoursera.model.coursera.Course;
import linkedincoursera.util.MongoConnection;
import org.bson.BSONDecoder;
import org.bson.BSONObject;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import linkedincoursera.util.DBConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


@Component
public class CourseraRepo {
    public CourseraRepo()
    {
        try
        {

            String mongoUri = "mongodb://harshank:password@ds047581.mongolab.com:47581/cmpe273"; //To connect using driver via URI
            MongoClientURI mongoLabUrl = new MongoClientURI(mongoUri);
            //To authenticate the user.
            MongoCredential mongoCredential = MongoCredential.createMongoCRCredential(
                    mongoLabUrl.getUsername(), mongoLabUrl.getDatabase(), mongoLabUrl.getPassword());
            //To connect to the mongo server.
            MongoClient mongoClient = new MongoClient(new ServerAddress(
                    "ds047581.mongolab.com",47581), Arrays.asList(mongoCredential));
            mongoTemplate = new MongoTemplate(mongoClient,mongoLabUrl.getDatabase());
        }
        catch(Exception e)
        {
        }
    }

    static MongoTemplate mongoTemplate = DBConnection.getConnection();
    @Autowired
    MongoClient client;

    public void addCourses(List<Course> courses) {
        for(Course c:courses) {
            mongoTemplate.save(c);
        }
    }

    public void addCategories(List <Categories> categoryList) {
        for(Categories cat:categoryList) {
            mongoTemplate.save(cat);
        }
    }

    public List<Categories> findAllCategories() {
        return mongoTemplate.findAll(Categories.class);
    }

    public List<Categories> findCategories(String attr, String regex) {
        return mongoTemplate.find(new Query(Criteria.where(attr).regex(regex)), Categories.class);
    }

    public List<Course> findAllCourses() {
        return mongoTemplate.findAll(Course.class);
    }

    public List<Course> findCourses(Query query) {
        return mongoTemplate.find(query, Course.class);
    }

    public void saveCourse(Course course, String email) throws IOException {
        BasicDBObject document = new BasicDBObject();
            DB db = MongoConnection.getConnection();
            DBCollection collection = db.getCollection("userCourses");
            document.put("id",course.getId());
            document.put("name",course.getName());
            document.put("homeLink",course.getHomeLink());
            document.put("instructor",course.getInstructor());
            document.put("userEmail",email);
            document.put("startDay",course.getStartDay());
            document.put("startMonth",course.getStartMonth());
            document.put("startYear",course.getStartYear());
            collection.insert(document);


    }

    public void deleteCourse(Integer id, String userEmail) {
        Query query = new Query(Criteria.where("id").is(id).and("userEmail").is(userEmail));
        mongoTemplate.remove(query, "userCourses");

    }

    public ArrayList<Integer> fetchCoursesOfUser(String email) {
        DB db = null;
        ArrayList<Integer> al = new ArrayList<Integer>();
        try {
            db = MongoConnection.getConnection();
            DBCollection collection = db.getCollection("userCourses");
            BasicDBObject query = new BasicDBObject();
            query.put("userEmail", email);
            DBCursor dbCursor = collection.find(query);
            System.out.println(dbCursor.count());
            while(dbCursor.hasNext()) {
                al.add((Integer) dbCursor.next().get("id"));
            }
            System.out.println(al);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return al;
    }
}
