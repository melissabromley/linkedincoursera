package linkedincoursera.model.linkedin;

import java.util.Date;

/**
 * Created by harsh on 4/18/15.
 */
public class Educations {
    private  String activities;

    private String degree;

    private String fieldOfStudy;

    private String id;

    private String notes;

    private String schoolName;

    private Date startDate;

    private Date endDate;

    public Educations(){}
    public Educations(String activities, String degree, String fieldOfStudy, String id, String notes,
                     String schoolName, Date startDate, Date endDate) {
        this.activities = activities;
        this.degree = degree;
        this.fieldOfStudy = fieldOfStudy;
        this.id = id;
        this.notes = notes;
        this.schoolName = schoolName;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public Educations(String activities, String degree, String schoolName, String fieldOfStudy) {
        this.activities = activities;
        this.degree = degree;
        this.schoolName = schoolName;
        this.fieldOfStudy = fieldOfStudy;
    }


    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
