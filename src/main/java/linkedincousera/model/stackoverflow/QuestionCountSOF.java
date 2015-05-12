package linkedincoursera.model.stackoverflow;

import org.springframework.data.annotation.Id;

import java.util.concurrent.atomic.AtomicInteger;

public class QuestionCountSOF {
//    static AtomicInteger seq = new AtomicInteger(1);
    private int id;
    private int count;
    private String name;
    private boolean has_synonyms;
    private boolean is_moderator_only;
    private boolean is_required;
//    QuestionCountSOF() {
//        super();
//        this.id = seq.incrementAndGet();
//    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean is_required() {
        return is_required;
    }

    public void setIs_required(boolean is_required) {
        this.is_required = is_required;
    }

    public boolean is_moderator_only() {
        return is_moderator_only;
    }

    public void setIs_moderator_only(boolean is_moderator_only) {
        this.is_moderator_only = is_moderator_only;
    }

    public boolean isHas_synonyms() {
        return has_synonyms;
    }

    public void setHas_synonyms(boolean has_synonyms) {
        this.has_synonyms = has_synonyms;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
