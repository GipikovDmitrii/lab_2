package client.gui;

import client.ClientMain;
import client.handler.Handler;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {

    private Handler handler;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button signIn;
    @FXML
    private Hyperlink createAccount;
    @FXML
    private Label errorMessage;

    private ClientMain clientMain;

    public LoginController() {
        handler = Handler.getHandler();
    }

    public void setGui(ClientMain clientMain) {
        this.clientMain = clientMain;
    }

    private String getUsername() {
        return this.username.getText();
    }

    private String getPassword() {
        return this.password.getText();
    }

    @FXML
    private void login() {
        if (handler.login(getUsername(), getPassword())) {
            clientMain.showMainWindow();
        } else {
            errorMessage.setText("Incorrect username or password.");
        }
    }

    @FXML
    private void handleRegistrationWindow() {
        clientMain.showRegistrationWindow();
    }
}
