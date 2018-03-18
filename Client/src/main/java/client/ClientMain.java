package client;

import client.gui.*;

import client.handler.Handler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class ClientMain extends Application {

    private Stage primaryStage;

    public ClientMain() {
    }

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.getIcons().add(new Image("images/icon.png"));
        showLoginWindow();
    }

    public void showLoginWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/login.fxml"));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            primaryStage.setTitle("Sign in");
            primaryStage.setScene(scene);
            LoginController controller = loader.getController();
            controller.setGui(this);
            primaryStage.centerOnScreen();
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showRegistrationWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/registration.fxml"));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            primaryStage.setTitle("Sign up");
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            RegistrationController controller = loader.getController();
            controller.setGui(this);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/main.fxml"));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            primaryStage.setTitle("Task manager");
            primaryStage.setResizable(true);
            primaryStage.setScene(scene);
            MainController controller = loader.getController();
            controller.setGui(this);
            primaryStage.centerOnScreen();
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showNewTaskWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/addTask.fxml"));
            AnchorPane pane = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(pane);
            stage.setTitle("New task");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(primaryStage);
            stage.setResizable(false);
            stage.setScene(scene);
            AddTaskController controller = loader.getController();
            controller.setDialogStage(stage);
            controller.setGui(this);
            stage.getIcons().add(new Image("images/icon.png"));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        Handler.getHandler().disconnect();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private static void launch() {
        Application.launch();
    }

    public static void main(String[] args) {
        launch();
    }
}