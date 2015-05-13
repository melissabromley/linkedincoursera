package linkedincoursera.model.linkedin;

/**
 * Created by harsh on 5/12/15.
 */
public class Positions {
    private String companyName;
    private String title;
    public Positions(){}
    public Positions(String companyName, String title) {
        this.companyName = companyName;
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
