package Model;

import Controller.Utils;
import javafx.beans.property.SimpleStringProperty;

public class Todo {

    private Cours cours;
    private Type type;
    private SimpleStringProperty description;

    public Todo(Cours cours, Type type , String description) {
        this.cours = cours;

        this.type = type;
        this.description = new SimpleStringProperty(description);
    }

    public String export(){
        return cours.getCode() + Utils.s + cours.getName() + Utils.s + type.name() + Utils.s + description.getValue();
    }

    public Cours getCours() {
        return cours;
    }

    public void setCours(Cours cours) {
        this.cours = cours;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }
}
