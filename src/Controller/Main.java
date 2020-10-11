package Controller;

import Model.Cours;
import Model.Todo;
import Model.Type;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Collection;

public class Main extends Application {

    public static Font ranchers_font;
    @Override
    public void init() throws Exception {
        super.init();
        ranchers_font = Font.loadFont("src\\Fonts\\Ranchers-Regular.ttf",30);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Stage
        primaryStage.setTitle("Progres Tracker");

        BorderPane root = new BorderPane();
        Scene scene = new TodoList(root,800,400,Color.WHITE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
