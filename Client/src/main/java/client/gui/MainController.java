package client.gui;

import client.ClientMain;
import client.handler.Handler;
import client.task.Task;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainController {

    private ClientMain clientMain;
    private Handler handler;

    @FXML
    private TableView<Task> tableView;
    @FXML
    private TableColumn<Task, String> title;
    @FXML
    private TableColumn<Task, String> created;
    @FXML
    private Button newTask;
    @FXML
    private Button deleteTask;
    @FXML
    private Button deleteAllTask;
    @FXML
    private TextField taskTitle;
    @FXML
    private TextArea taskDescription;

    public MainController() {
        handler = Handler.getHandler();
    }

    public void setGui(ClientMain clientMain) {
        this.clientMain = clientMain;
    }

    @FXML
    private void initialize() {
        taskTitle.setEditable(false);
        taskDescription.setEditable(false);
        tableView.setItems(handler.tasks);
        title.setCellValueFactory(task -> new SimpleStringProperty(task.getValue().getName()));
        created.setCellValueFactory(task -> new SimpleStringProperty(task.getValue().getCreatedTime()));
        tableView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> MainController.this.showSelectedTask(newValue));
    }

    private void showSelectedTask(Task task) {
        if (task != null) {
            taskTitle.setText(task.getName());
            taskDescription.setText(task.getDescription());
        } else {
            taskTitle.setText("");
            taskDescription.setText("");
        }
    }

    @FXML
    private void deleteSelectedTask() {
        int selectedTask = tableView.getSelectionModel().getSelectedIndex();
        if (selectedTask >= 0) {
            handler.deleteTask(tableView.getSelectionModel().getSelectedItem().getTaskId());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(clientMain.getPrimaryStage());
            alert.setTitle("No selection");
            alert.setContentText("No task selected");
            alert.showAndWait();
        }
    }

    @FXML
    private void deleteAllTask() {
        handler.deleteAllTask();
    }

    @FXML
    private void showAddTaskWindow() {
        clientMain.showNewTaskWindow();
    }

    @FXML
    private void signOut() {
        clientMain.showLoginWindow();
        handler.disconnect();
    }

    @FXML
    private void exit() {
        handler.disconnect();
        System.exit(0);
    }

    @FXML
    private void about() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initOwner(clientMain.getPrimaryStage());
        alert.setHeaderText("About");
        alert.setTitle("Task manager");
        alert.setContentText("Author:\nhttps://github.com/GipikovDmitrii");
        alert.showAndWait();
    }
}
