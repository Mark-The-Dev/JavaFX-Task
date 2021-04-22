package sample;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;

public class Controller {

    private Task<ObservableList<String>> task;

    @FXML
    private ListView listView;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label progressLabel;


    public void initialize(){
        task = new Task<ObservableList<String>>() {
            @Override
            protected ObservableList<String> call() throws Exception {
                //Thread.sleep(1000);

                String[] names = {"Mister Peanutbutter",
                        "Bojack Horseman",
                        "Diane N.",
                        "Todd Chavez",
                        "Jurj Clooners",
                        "Someone Else"};

                ObservableList<String> employees = FXCollections.observableArrayList();

                for(int i=0; i<6; i++){
                    employees.add(names[i]);
                    updateMessage("Added " + names[i] + " to the list");
                    updateProgress(i + 1, 6);
                    Thread.sleep(200);
                }



                return employees;
            }
        };

        // one way to do it without bind.
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        listView.setItems(employees);
//                    }
//                });
//                return employees;
//            }
//        };

        listView.itemsProperty().bind(task.valueProperty());
        progressBar.progressProperty().bind(task.progressProperty());
        progressLabel.textProperty().bind(task.messageProperty());
    }

    @FXML
    public void buttonPressed(){
        new Thread(task).start();
    }
}
