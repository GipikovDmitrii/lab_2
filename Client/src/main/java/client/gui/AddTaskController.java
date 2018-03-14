package client.gui;

import client.ClientMain;
import client.handler.Handler;
import client.task.Task;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class AddTaskController {

    private ClientMain clientMain;
    private Handler handler;
    private Stage dialogStage;

    @FXML
    private Button saveTask;
    @FXML
    private Button cancel;
    @FXML
    private TextField titleTask;
    @FXML
    private TextArea descriptionTask;
    @FXML
    private JFXDatePicker date;
    @FXML
    private JFXTimePicker time;
    @FXML
    private Label errorMessage;

    public AddTaskController() {
        handler = Handler.getHandler();
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setGui(ClientMain clientMain) {
        this.clientMain = clientMain;
    }

    @FXML
    private void saveTask() {
        if (titleTask.getText().equals("")) {
            errorMessage.setText("error");
        } else if (descriptionTask.getText().equals("")) {
            errorMessage.setText("error");
        } else if (time.getValue() == null) {
            errorMessage.setText("error");
        } else if (date.getValue() == null) {
            errorMessage.setText("error");
        } else {
            handler.newTask(new Task(
                    titleTask.getText(),
                    descriptionTask.getText(),
                    time.getValue().toString().concat(" ").concat(date.getValue().toString())));
            dialogStage.close();
        }
    }

    @FXML
    private void closeWindow() {
        dialogStage.close();
    }
}
