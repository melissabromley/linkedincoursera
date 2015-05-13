package linkedincoursera.model.linkedin;

import org.springframework.social.linkedin.api.Education;
import org.springframework.social.linkedin.api.Position;

import java.io.Serializable;
import java.util.List;

/**
 * Created by harsh on 5/12/15.
 */
public class LinkedinUser implements Serializable {
    private String userName;
    private String profilePhotoUrl;
    private List<Educations> education;
    private String headline;
    private List<String> skillSet;
    private String summary;
    private String email;
    private List<Positions> positions;
    public LinkedinUser() {}
    public LinkedinUser(String userName, String profilePhotoUrl, List<Educations> education, String headline, List<String> skillSet, String summary, String email, List<Positions> positions) {
        this.userName = userName;
        this.profilePhotoUrl = profilePhotoUrl;
        this.education = education;
        this.headline = headline;
        this.skillSet = skillSet;
        this.summary = summary;
        this.email = email;
        this.positions = positions;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public List<Educations> getEducation() {
        return education;
    }

    public void setEducation(List<Educations> education) {
        this.education = education;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public List<String> getSkillSet() {
        return skillSet;
    }

    public void setSkillSet(List<String> skillSet) {
        this.skillSet = skillSet;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Positions> getPositions() {
        return positions;
    }

    public void setPositions(List<Positions> positions) {
        this.positions = positions;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
