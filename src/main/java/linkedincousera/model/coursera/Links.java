package linkedincoursera.model.coursera;

import java.util.List;

/**
 * Created by harsh on 5/9/15.
 */
public class Links {
    private List<Integer> categories;
    private List<Integer> sessions;

    public List<Integer> getCategories() {
        return categories;
    }

    public void setCategories(List<Integer> categories) {
        this.categories = categories;
    }

    public List<Integer> getSessions() {
        return sessions;
    }

    public void setSessions(List<Integer> sessions) {
        this.sessions = sessions;
    }
}
