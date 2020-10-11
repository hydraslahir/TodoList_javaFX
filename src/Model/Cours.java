package Model;

import javafx.beans.property.SimpleStringProperty;

public enum Cours {
    INF4150("INF4150","Interfaces Personnes-Machines"),
    INF4170("INF4170", "Architecture des ordinateurs"),
    INF5071("INF5071", "Infographie"),
    INF5130("INF5130", "Algorithmique"),
    INF6120("INF6120", "Haskell");

    private SimpleStringProperty code;
    private SimpleStringProperty name;
    Cours(String code, String name){
        this.code = new SimpleStringProperty(code);
        this.name = new SimpleStringProperty(name);
    }

    @Override
    public String toString() {
        return code.getValue();
    }

    public String getCode() {
        return code.get();
    }

    public SimpleStringProperty codeProperty() {
        return code;
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }
}
