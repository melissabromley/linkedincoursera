package linkedincoursera.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import linkedincoursera.model.linkedin.Educations;
import org.json.simple.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by harsh on 4/17/15.
 */
public class UserDetails {

    @JsonProperty("firstName")String firstName;
    @JsonProperty("headline")String headline;
    @JsonProperty("id")String id;
    @JsonProperty("lastName")String lastName;
    @JsonProperty("location")HashMap location;
    @JsonProperty("industry")String industry;
    @JsonProperty("summary")String summary;
    @JsonProperty("skills")JSONObject skills;
    @JsonProperty("specialties")String specialties;
//    @JsonProperty("positions")String positions;
    @JsonProperty("publicProfileUrl")String publicProfileUrl;
    @JsonProperty("educations")List<Educations> educations;
    @JsonProperty("dateOfBirth")Date dateOfBirth;
    @JsonProperty("following")String following;

}
