package linkedincoursera.model.stackoverflow;

import java.util.List;

/**
 * Created by harsh on 4/25/15.
 */
public class QuesSof {
    private List<QuestionCountSOF> items;
    private boolean has_more;
    private Integer quota_max;
    private Integer quota_remaining;

    public List<QuestionCountSOF> getItems() {
        return items;
    }

    public void setItems(List<QuestionCountSOF> items) {
        this.items = items;
    }

    public boolean getHas_more() {
        return has_more;
    }

    public void setHas_more(boolean has_more) {
        this.has_more = has_more;
    }

    public Integer getQuota_max() {
        return quota_max;
    }

    public void setQuota_max(Integer quota_max) {
        this.quota_max = quota_max;
    }

    public Integer getQuota_remaining() {
        return quota_remaining;
    }

    public void setQuota_remaining(Integer quota_remaining) {
        this.quota_remaining = quota_remaining;
    }
}
