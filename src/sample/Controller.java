package sample;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;

public class Controller {


    @FXML
    private ListView listView;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label progressLabel;

    private Service<ObservableList<String>> service;


    public void initialize(){



        service = new EmployeeService();
        listView.itemsProperty().bind(service.valueProperty());
        progressBar.progressProperty().bind(service.progressProperty());
        progressLabel.textProperty().bind(service.messageProperty());

        service.setOnRunning(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                progressBar.setVisible(true);
                progressLabel.setVisible(true);
            }
        });

        progressBar.setVisible(false);
        progressLabel.setVisible(false);

        service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                progressBar.setVisible(false);
                progressLabel.setVisible(false);
            }
        });

    }

    @FXML
    public void buttonPressed(){
        if (service.getState() == Worker.State.SUCCEEDED){
            service.reset();
            service.start();
        } else if (service.getState() == Worker.State.READY){
            service.start();
        }

    }
}
