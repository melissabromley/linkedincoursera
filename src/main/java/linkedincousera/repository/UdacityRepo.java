package linkedincoursera.repository;

import linkedincoursera.model.udacity.UdacityCourse;
import linkedincoursera.util.DBConnection;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UdacityRepo {
    static MongoTemplate mongoTemplate = DBConnection.getConnection();

    public void addCourses(List<UdacityCourse> courses) {
        for(UdacityCourse course : courses) {
            mongoTemplate.save(course, "udacityCourses");
        }
    }
}
