package linkedincoursera.repository;

import linkedincoursera.model.stackoverflow.QuestionCountSOF;
import linkedincoursera.util.DBConnection;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

public class StackOverflowRepo {
    static MongoTemplate mongoTemplate = DBConnection.getConnection();

    public static void addQuestionsCount(List<QuestionCountSOF> qtnCountSof) {
        for(QuestionCountSOF q : qtnCountSof) {
            mongoTemplate.save(q,"sofQuestions");
        }
    }
}
