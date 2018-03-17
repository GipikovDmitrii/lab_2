package client.gui;

import client.ClientMain;
import client.handler.Handler;
import client.task.Task;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class NotificationController {

    private Handler handler;
    private ClientMain clientMain;
    private Task task;
    private Stage stage;

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
    }

    public void setGui(ClientMain clientMain) {
        this.clientMain = clientMain;
    }

    public void getStage(Stage stage) {
        this.stage = stage;
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
        clientMain.showNotificationWindow();
    }
}