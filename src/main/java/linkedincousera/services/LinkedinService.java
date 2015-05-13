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
    public void insertUser(String name, String email, String photo, String headline, String summary) {
        LinkedinUser user = new LinkedinUser();

        user.setUserName(name);
        user.setProfilePhotoUrl(photo);
        user.setHeadline(headline);
        user.setSummary(summary);
        user.setEmail(email);
        List<Positions> positions = new ArrayList<Positions>();
        positions.add(0, new Positions("Bitcasa", "QA Automation Engineer"));
        user.setPositions(positions);
        List<Educations> edu = new ArrayList<Educations>();
        edu.add(0, new Educations("", "Masters", "San Jose State University","Software Engineering"));
        edu.add(1, new Educations("", "Bachelors", "University of California, Berkeley","Molecular and Cell Biology"));
        user.setEducation(edu);
        List<String> skills = new ArrayList<String>();
        skills.add(0,"Python");
        skills.add(1,"Java");
        skills.add(2,"C");
        skills.add(3,"C++");
        skills.add(4, "SQL");
        skills.add(5,"Linux");
        user.setSkillSet(skills);
//        user.setCity("Milpitas");
//        user.setState("CA");
        linkedinRepo.insertLinkedUser(user);
    }
    public LinkedinUser findUserByEmail(String email) {
        if(linkedinRepo.findUserByEmail(email)!=null)
            return linkedinRepo.findUserByEmail(email).get(0);
        else return null;
    }
}
