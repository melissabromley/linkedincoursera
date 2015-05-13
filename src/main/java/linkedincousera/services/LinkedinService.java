package linkedincoursera.services;

import org.springframework.http.*;
import org.springframework.social.linkedin.api.*;
import org.springframework.social.linkedin.api.impl.LinkedInTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * Created by harsh on 4/20/15.
 */
@Component
public class LinkedinService {
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
        headers.set("Authorization", " Bearer "+accessToken);
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
}
