package Controller;

import Model.Cours;
import Model.Todo;
import Model.Type;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;

public class Utils {

    public static String s = " , ";
    public static final String TODO_PATH = "src/Ressources/todoList.exp";
    public static final String DONE_PATH = "src/Ressources/doneList.exp";

    public static void saveToFile(ObservableList<Todo> list, String path) throws IOException {
        File exportFile = new File(path);
        BufferedWriter bw = new BufferedWriter(new FileWriter(exportFile));

        list.forEach(x->{
            try {
                System.out.println("writting : " + x.export() );
                bw.write(x.export() + "\n");

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        bw.close();
    }

    public static ObservableList<Todo> readFromFile(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
        ObservableList<Todo> list = FXCollections.observableArrayList();

        boolean lineRemaining = true;
        while(lineRemaining){
            String line = reader.readLine();
            if(line == null){
                lineRemaining = false;
            }else{
                String[] content = line.split(s);

                Cours cours = Cours.valueOf(content[0]);
                Type type = Type.valueOf(content[2]);

                Todo element = new Todo(cours,type,content[3]);
                list.add(element);
                System.out.println(element.export());
            }
        }
        return list;
    }
}
