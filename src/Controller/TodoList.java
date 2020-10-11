package Controller;

import Model.Cours;
import Model.Todo;
import Model.Type;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class TodoList extends Scene {

    private GridPane center;
    private HBox addBox;
    private HBox searchBox;

    private ObservableList<Todo> todoList;
    private ObservableList<Todo> doneList;

    private TableView<Todo> todoTable;
    private TableView<Todo> doneTable;
    private VBox centerBox;

    public TodoList(BorderPane root, double v, double v1, Paint paint) throws IOException {
        super(root, v, v1, paint);

        //Core
        center = generateCenterPane();

        //Lists
        initLists();

        //Tables
            todoTable = generateTodoTableView();
            doneTable = generateTodoTableView();
            //Left
            Label left_Label = generateLabel("TODO",30);
            left_Label.prefWidthProperty().bind(todoTable.widthProperty());

            //Right
            Label right_Label = generateLabel("DONE",30);
            right_Label.prefWidthProperty().bind(todoTable.widthProperty());

        // CENTER_VBOX
        centerBox = generateCenterArrows();

        //Add
        addBox = generateAddBox();

        //Search
        searchBox = generateSearchBox();

        center.add(addBox,0,0);
        center.add(searchBox,2,0);
        center.add(left_Label,0,1);
        center.add(todoTable,0,2);
        center.add(centerBox,1,2);
        center.add(right_Label,2,1);
        center.add(doneTable,2,2);

        root.setTop(generateMenuBar());
        root.setCenter(center);

    }

    private HBox generateSearchBox() {
        HBox searchBox = new HBox(5);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        Label searchLabel = generateWeakLabel("Search: ", 15);
        searchLabel.prefWidthProperty().bind(searchBox.widthProperty().multiply(0.2));

        TextField searchField = new TextField();
        searchField.setAlignment(Pos.CENTER);
        searchField.prefWidthProperty().bind(searchBox.widthProperty().multiply(0.75));

        searchBox.getChildren().addAll(searchLabel,searchField);

        FilteredList<Todo> todoFilteredList = new FilteredList<Todo>(todoList, b->true);
        FilteredList<Todo> doneFilteredList = new FilteredList<Todo>(doneList, b->true);

        //When searchfield Varies from old to new
        searchField.textProperty().addListener((obs,old,newSearchValue)->{
            todoFilteredList.setPredicate(data ->{
                if(newSearchValue == null || newSearchValue.isEmpty()) return true;
                if(data.getCours().getName().toLowerCase().contains(newSearchValue.toLowerCase())) return true;
                if(data.getCours().getCode().toLowerCase().contains(newSearchValue.toLowerCase())) return true;
                if(data.getType().toString().toLowerCase().contains(newSearchValue.toLowerCase())) return true;
                if(data.getDescription().toLowerCase().contains(newSearchValue.toLowerCase())) return true;
                return false;
            });
        });
        searchField.textProperty().addListener((obs,old,newSearchValue)->{
            doneFilteredList.setPredicate(data ->{
                if(newSearchValue == null || newSearchValue.isEmpty()) return true;
                if(data.getCours().getName().toLowerCase().contains(newSearchValue.toLowerCase())) return true;
                if(data.getCours().getCode().toLowerCase().contains(newSearchValue.toLowerCase())) return true;
                if(data.getType().toString().toLowerCase().contains(newSearchValue.toLowerCase())) return true;
                if(data.getDescription().toLowerCase().contains(newSearchValue.toLowerCase())) return true;
                return false;
            });
        });
        SortedList<Todo> todoSortedList = new SortedList<>(todoFilteredList);
        SortedList<Todo> doneSortedList = new SortedList<>(doneFilteredList);

        doneSortedList.comparatorProperty().bind(doneTable.comparatorProperty());
        todoSortedList.comparatorProperty().bind(todoTable.comparatorProperty());


        todoTable.setItems(todoSortedList);
        doneTable.setItems(doneSortedList);
        return searchBox;
    }

    private HBox generateAddBox() {
        HBox addBoxLocal = new HBox(3);

        VBox coursBox = new VBox(2);
        coursBox.setAlignment(Pos.CENTER);
        Label coursL = generateWeakLabel("Cours",15);
        ComboBox<Cours> coursComboBox = new ComboBox<>(FXCollections.observableArrayList(Cours.values()));
        coursComboBox.prefWidthProperty().bind(coursBox.widthProperty());
        coursBox.getChildren().addAll(coursL,coursComboBox);

        VBox typeBox = new VBox(2);
        typeBox.setAlignment(Pos.CENTER);
        Label typeL = generateWeakLabel("Type",15);
        ComboBox<Type> typeComboBox = new ComboBox<>(FXCollections.observableArrayList(Type.values()));
        typeComboBox.prefWidthProperty().bind(typeBox.widthProperty());
        typeBox.getChildren().addAll(typeL,typeComboBox);

        VBox descriptionBox = new VBox(2);
        descriptionBox.setAlignment(Pos.CENTER);
        Label descriptionL = generateWeakLabel("Description",15);
        TextField descriptionText = new TextField();
        descriptionBox.getChildren().addAll(descriptionL,descriptionText);

        Button addButton = new Button("");
        addButton.setBackground(Background.EMPTY);
        addButton.setStyle("-fx-border-color: lightblue; -fx-border-radius: 15px;");
        Image img = new Image(getClass().getResourceAsStream("/Ressources/greenPlus.png"),35,35,false,false);
        addButton.setGraphic(new ImageView(img));
        addButton.setOnAction(event ->{
            Cours selectedCours = coursComboBox.getSelectionModel().getSelectedItem();
            Type selectedType = typeComboBox.getSelectionModel().getSelectedItem();
            if(selectedCours != null && selectedType != null){
                todoList.add(new Todo(selectedCours,selectedType,descriptionText.getText()));
            }
        });


        coursBox.prefWidthProperty().bind(todoTable.widthProperty().multiply(0.25));
        typeBox.prefWidthProperty().bind(todoTable.widthProperty().multiply(0.25));
        descriptionBox.prefWidthProperty().bind(todoTable.widthProperty().multiply(0.35));
        addButton.prefWidthProperty().bind(todoTable.widthProperty().multiply(0.15));
        addButton.setPrefHeight(50);

        addBoxLocal.setAlignment(Pos.CENTER_LEFT);
        addBoxLocal.setPadding(new Insets(10,0,5,3));
        addBoxLocal.getChildren().addAll(coursBox,typeBox,descriptionBox,addButton);

        return addBoxLocal;
    }

    private VBox generateCenterArrows() {
        VBox centerBox = new VBox(5);
        centerBox.setAlignment(Pos.CENTER);

        Button rButton = new Button(" > ");

        rButton.setFont(Font.font("Arial",15));
        rButton.setPrefHeight(75);
        rButton.setOnAction(actionEvent -> {
            if(todoTable.getSelectionModel().getSelectedItem()!= null){
                Todo todoItem = todoTable.getSelectionModel().getSelectedItem();
                todoTable.getSelectionModel().clearSelection();
                todoList.remove(todoItem);
                doneList.add(todoItem);
            }
        });
        Button lButton = new Button(" < ");
        lButton.setFont(Font.font("Arial",15));
        lButton.setPrefHeight(75);
        lButton.setOnAction(actionEvent -> {
            if(doneTable.getSelectionModel().getSelectedItem()!= null){
                Todo todoItem = doneTable.getSelectionModel().getSelectedItem();
                doneTable.getSelectionModel().clearSelection();
                doneList.remove(todoItem);
                todoList.add(todoItem);
            }
        });
        centerBox.getChildren().addAll(rButton,lButton );
        return centerBox;
    }

    private void initLists() {
        try {
            todoList = Utils.readFromFile(Utils.TODO_PATH);
            doneList = Utils.readFromFile(Utils.DONE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private MenuBar generateMenuBar(){
        MenuBar menuBar = new MenuBar();
            //Creating file menu
            Menu file = new Menu("File");
            //Creating file menu items
            MenuItem save = new MenuItem("Save all");
            save.setOnAction(event ->{
                try {
                    Utils.saveToFile(todoList, Utils.TODO_PATH);
                    Utils.saveToFile(doneList, Utils.DONE_PATH);
                }catch (Exception e){
                    System.out.println("Couldn't save");
                }
                });
            //Adding all the menu items to the file menu
            file.getItems().add(save);

            menuBar.getMenus().add(file);

            return menuBar;
    }

    private Label generateWeakLabel(String s, int i) {
        Label searchLabel = new Label(s);
        searchLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, i));
        searchLabel.setAlignment(Pos.CENTER);
        return searchLabel;
    }

    private GridPane generateCenterPane() {
        GridPane center = new GridPane();
        center.setVgap(3);
        center.setHgap(10);

        ColumnConstraints todoColumn = new ColumnConstraints(300,300,2000);
        todoColumn.setHgrow(Priority.ALWAYS);
        ColumnConstraints arrowColumn = new ColumnConstraints(50);
        ColumnConstraints doneColumn = new ColumnConstraints(300,300,2000);
        doneColumn.setHgrow(Priority.ALWAYS);
        center.getColumnConstraints().addAll(todoColumn,arrowColumn,doneColumn);
        return center;
    }

    private Label generateLabel(String text, int size) {
        Label left_Label = generateWeakLabel(text, size);
        left_Label.setPadding(new Insets(10));
        left_Label.setStyle(" -fx-background-color: radial-gradient(radius 180%, grey,white)");
        return left_Label;
    }

    private TableView<Todo> generateTodoTableView() {
        ObservableList<Cours> listCours = FXCollections.observableArrayList(Cours.values());
        TableView<Todo> todoTable = new TableView<>();
        todoTable.setEditable(true);
        TableColumn<Todo,Cours> todoc1 = new TableColumn<>("Cours");
        todoc1.prefWidthProperty().bind(todoTable.widthProperty().multiply(0.25));
        todoc1.setStyle("-fx-alignment: CENTER; -fx-font-size: 12pt;");

        todoc1.setCellValueFactory(new PropertyValueFactory<Todo,Cours>("cours"));
        todoc1.setCellFactory(ComboBoxTableCell.forTableColumn(listCours));


        TableColumn<Todo, Type> todoc2 = new TableColumn<>("Type");
        todoc2.prefWidthProperty().bind(todoTable.widthProperty().multiply(0.25));
        todoc2.setCellValueFactory(new PropertyValueFactory<Todo, Type>("type"));
        todoc2.setStyle("-fx-alignment: CENTER; -fx-font-size: 12pt;");

        TableColumn<Todo,String> todoc3 = new TableColumn<>("Description");
        todoc3.setCellValueFactory(new PropertyValueFactory<>("description"));
        todoc3.prefWidthProperty().bind(todoTable.widthProperty().multiply(0.49));
        todoc3.setCellFactory(TextFieldTableCell.forTableColumn());
        todoc3.setStyle("-fx-alignment: CENTER; -fx-font-size: 12pt;");

        todoTable.getColumns().addAll(todoc1,todoc2,todoc3);

        return todoTable;
    }
}
