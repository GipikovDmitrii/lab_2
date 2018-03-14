package client.gui;

import client.ClientMain;
import client.handler.Handler;
import client.task.Task;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import java.util.*;

public class NotificationController extends TimerTask {

    private Handler handler;
    private ClientMain clientMain;
    private Task task;
    private Stage stage;

    @FXML
    private Label taskTitle;
    @FXML
    private Label taskDescription;
    @FXML
    private Button completeTask;
    @FXML
    private MenuItem fiveMin;
    @FXML
    private MenuItem oneHour;
    @FXML
    private MenuItem oneDay;

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
    private void rescheduleFiveMin() {
        handler.rescheduleTask(task.getTaskId(), 1);
    }

    @FXML
    private void rescheduleOneHour() {
        handler.rescheduleTask(task.getTaskId(), 2);
    }

    @FXML
    private void rescheduleOneDay() {
        handler.rescheduleTask(task.getTaskId(), 3);
    }

    private void createNotification() {
    }

    @Override
    public void run() {
        createNotification();
    }
}
