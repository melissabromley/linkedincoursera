package linkedincoursera.services;

import linkedincoursera.model.linkedin.Educations;
import linkedincoursera.model.linkedin.LinkedinUser;
import linkedincoursera.model.linkedin.Positions;
import linkedincoursera.repository.LinkedinRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.social.linkedin.api.*;
import org.springframework.social.linkedin.api.impl.LinkedInTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by harsh on 4/20/15.
 */
@Component
public class LinkedinService {
    @Autowired
    LinkedinRepo linkedinRepo;
    private LinkedIn linkedIn;
    private LinkedInProfile linkedInProfile;
    private LinkedInProfileFull linkedInProfileFull;
    RestTemplate restTemplate = new RestTemplate();
    public LinkedIn getLinkedIn() {
        return linkedIn;
    }
    public LinkedInProfile getLinkedInProfile() {
        return linkedInProfile;
    }
    public LinkedInProfileFull getLinkedInProfileFull() {
        return linkedInProfileFull;
    }

    public void setApi(String access_token) {
        this.linkedIn = new LinkedInTemplate(access_token);
        this.linkedInProfile = linkedIn.profileOperations().getUserProfile();
        this.linkedInProfileFull = linkedIn.profileOperations().getUserProfileFull();
    }
    public List<String> getSkillSet() {
        return linkedInProfileFull.getSkills();
        // call to database here to fetch the skills
    }
    public List<Education> getEducations() {
        return linkedInProfileFull.getEducations();
    }
    public void getCompanyJobs(String accessToken) throws Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Authorization: Bearer ", accessToken);
        HttpEntity<String> stringHttpEntity = new HttpEntity<String>(headers);
        try{
            ResponseEntity<Boolean> response = restTemplate.exchange("https://api.linkedin.com/v1/companies/162479/is-company-share-enabled?format=json", HttpMethod.GET, stringHttpEntity, Boolean.class);
            Boolean t = response.getBody();
            System.out.println(t);
        }catch(HttpClientErrorException e){
            throw new Exception(e);
        }

    }
    public String getProfilePic(String accessToken) throws Exception{
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("Authorization", " Bearer " + accessToken);
        System.out.println(headers);
        HttpEntity<String> stringHttpEntity = new HttpEntity<String>(headers);
        try{
            ResponseEntity<String> response = restTemplate.exchange("http://api.linkedin.com/v1/people/~/picture-urls::(original)", HttpMethod.GET, stringHttpEntity, String.class);
            String t = response.getBody();
            System.out.println(t);
            return t;
        }catch(HttpClientErrorException e){
            throw new Exception(e);
        }
    }
    public void insertUser() {
        LinkedinUser user = new LinkedinUser();
        user.setUserName("Harshank Vengurlekar");
        user.setProfilePhotoUrl("https://media.licdn.com/mpr/mprx/0_3PuunxiPvk_Z-5mtTqpxnpPlvTl4K5YtiNZ1npCaEk5d73W-Svy84y9hUfA61CxYhKmtMUpHkkUh");
        user.setHeadline("Graduate Student at San Jose State Univeristy");
        user.setSummary("Experience in developing Web based, Client/Server, Distributed Architecture applications using Java, J2EE, Node.js.\n" +
                "\n" +
                "Specialties: Java, JavaScript, Node.js, REST API, SQL, NoSQL, SDLC, Scalable Architecture, Distributed Systems, Caching.");

        List<Positions> positions = new ArrayList<Positions>();
        positions.add(0, new Positions("Openradix Software Solutions", "Java Developer"));
        positions.add(1, new Positions("Mozilla Firefox", "Project Intern"));
        user.setPositions(positions);
        List<Educations> edu = new ArrayList<Educations>();
        edu.add(0, new Educations("", "Masters", "San Jose State University","Software Engineering"));
        edu.add(1, new Educations("", "Bachelors", "University of Mumbai","Computer Engineering"));
        user.setEducation(edu);
        List<String> skills = new ArrayList<String>();
        skills.add(0,"Java");
        skills.add(1,"Node.js");
        skills.add(2,"MySQL");
        skills.add(3,"C++");
        skills.add(4, "JavaScript");
        skills.add(5,"Linux");
        skills.add(6,"MongoDb");
        skills.add(7, "jQuery");
        user.setSkillSet(skills);
        linkedinRepo.insertLinkedUser(user);
    }
    public LinkedinUser findUser(String name) {
        return linkedinRepo.findUser(name).get(0);

    }
}

