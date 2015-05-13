package linkedincoursera.services;

import linkedincoursera.model.coursera.*;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Created by harsh on 4/20/15.
 */
@Component
public class CourseraService {

    public RestTemplate restTemplate = new RestTemplate();

    public List<Course> fetchCourses() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> stringHttpEntity = new HttpEntity<String>(headers);
        try{
            // {elements:[arrOfCourseObjects]} .. need to keep the names same to map
            ResponseEntity<Elements> response = restTemplate.exchange(
                    "https://api.coursera.org/api/catalog.v1/courses?includes=categories&fields=language,photo," +
                            "largeIcon,instructor,previewLink,shortDescription",
                    HttpMethod.GET, stringHttpEntity, Elements.class);
            System.out.println(response.getStatusCode());
            Elements elements= response.getBody();
            return elements.getElements();
        }catch(HttpClientErrorException e){
            throw new Exception(e);
        }
    }

    public List<Course> fetchCourses(String queryValue) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> stringHttpEntity = new HttpEntity<String>(headers);
        try{
            // {elements:[arrOfCourseObjects]} .. need to keep the names same to map
            String url = MessageFormat.format("https://api.coursera.org/api/catalog.v1/courses?" +
                    "includes=categories,sessions&fields=language,photo,largeIcon,instructor,previewLink,shortDescription&" +
                    "q=search&query={0}", queryValue);
            ResponseEntity<Elements> response = restTemplate.exchange(
                    url, HttpMethod.GET, stringHttpEntity, Elements.class);
            Elements elements= response.getBody();
            List<Course> courses = elements.getElements();
            allocateSessionFields(courses);
            return elements.getElements();
        }catch(HttpClientErrorException e){
            throw new Exception(e);
        }
    }

    private void allocateSessionFields(List<Course> courses) {
        for(int i=0;i<courses.size();i++) {
            try {
                getHomeLinkFromSession(courses.get(i).getLinks().getSessions().get(courses.get(i).getLinks().getSessions().size()-1), courses.get(i));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    private void getHomeLinkFromSession(Integer sessionId, Course c) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> stringHttpEntity = new HttpEntity<String>(headers);
        try{
            // {elements:[arrOfCourseObjects]} .. need to keep the names same to map
            ResponseEntity<SessionElements> response = restTemplate.exchange(
                    "https://api.coursera.org/api/catalog.v1/sessions/"+sessionId+"?fields=startDay,startMonth,startYear,durationString",
                    HttpMethod.GET, stringHttpEntity, SessionElements.class);
            SessionElements sessions= response.getBody();
            c.setHomeLink(sessions.getElements().get(0).getHomeLink());
            c.setDurationString(sessions.getElements().get(0).getDurationString());
            c.setStartDay(sessions.getElements().get(0).getStartDay());
            c.setStartMonth(sessions.getElements().get(0).getStartMonth());
            c.setStartYear(sessions.getElements().get(0).getStartYear());
        }catch(HttpClientErrorException e){
        }
    }

    public List<Categories> getCategoriesList() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> stringHttpEntity = new HttpEntity<String>(headers);
        try{
            ResponseEntity<CategoryElement> response = restTemplate.exchange(
                    "https://api.coursera.org/api/catalog.v1/categories",
                    HttpMethod.GET, stringHttpEntity, CategoryElement.class);
            CategoryElement elements= response.getBody();
            return elements.getElements();
        }catch(HttpClientErrorException e){
            throw new Exception(e);
        }
    }

    public List<Session> getSessions() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> stringHttpEntity = new HttpEntity<String>(headers);
        try{
            ResponseEntity<SessionElements> response = restTemplate.exchange(
                    "https://api.coursera.org/api/catalog.v1/sessions?fields=startMonth,startYear",
                    HttpMethod.GET, stringHttpEntity, SessionElements.class);
            SessionElements elements = response.getBody();
            return elements.getElements();
        }catch(HttpClientErrorException e){
            throw new Exception(e);
        }
    }



    public void filterCourses(String searchParam) {

    }
}
