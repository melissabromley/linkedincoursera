package linkedincoursera.model.coursera;

import linkedincoursera.model.coursera.Categories;

import java.util.List;


public class CategoryElement {
    private List<Categories> elements;
    private Object linked;

    public List<Categories> getElements() {
        return elements;
    }

    public void setElements(List<Categories> elements) {
        this.elements = elements;
    }

    public Object getLinked() {
        return linked;
    }

    public void setLinked(Object linked) {
        this.linked = linked;
    }
}
