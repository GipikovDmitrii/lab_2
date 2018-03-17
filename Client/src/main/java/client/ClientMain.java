package client;

import client.gui.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class ClientMain extends Application{

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

/*    public void showNotificationWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/notification.fxml"));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            Stage stage = new Stage();
            NotificationController controller = loader.getController();
            controller.setGui(this);
            controller.getStage(stage);
            stage.initOwner(primaryStage);
            stage.setScene(scene);
            stage.setTitle("Notification");
            stage.setResizable(false);
            stage.getIcons().add(new Image("images/icon.png"));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

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


    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setFonts() {
        Font.loadFont(getClass().getResource("/fonts/Raleway-ExtraLight.ttf").toExternalForm(), 10);
    }

    private static void launch() {
        Application.launch();
    }

    public static void main(String[] args) {
        launch();
    }
}