package client.gui;

import client.handler.Handler;
import client.task.Task;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class NotificationController {

    private Handler handler;
    private Task task;
    private Stage stage;

    @FXML
    private AnchorPane pane;
    @FXML
    private Button completeTask;
    @FXML
    private MenuItem fiveMin;
    @FXML
    private MenuItem oneHour;
    @FXML
    private MenuItem oneDay;
    @FXML
    private Label taskTitle;
    @FXML
    private Label taskDescription;

    public NotificationController () {
    }

    public NotificationController(Task task) {
        this.handler = Handler.getHandler();
        this.task = task;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/notification.fxml"));
        loader.setController(this);
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void complete() {
        handler.deleteTask(task.getTaskId());
        stage.close();
    }

    @FXML
    private void rescheduleFiveMin() {
        handler.rescheduleTask(task.getTaskId(), 1);
        stage.close();
    }

    @FXML
    private void rescheduleOneHour() {
        handler.rescheduleTask(task.getTaskId(), 2);
        stage.close();
    }

    @FXML
    private void rescheduleOneDay() {
        handler.rescheduleTask(task.getTaskId(), 3);
        stage.close();
    }

    public void showNotification() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                taskTitle.setText(task.getName());
                taskDescription.setText(task.getDescription());
                Scene scene = new Scene(pane);
                stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Notification");
                stage.setResizable(false);
                stage.getIcons().add(new Image("images/icon.png"));
                stage.show();
            }
        });
    }
}