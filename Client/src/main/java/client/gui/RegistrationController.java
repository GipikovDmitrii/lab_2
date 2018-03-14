package client.gui;

import client.ClientMain;
import client.handler.Handler;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationController {
    private ClientMain clientMain;
    private Handler handler;
    private Pattern pattern = Pattern.compile("/+@+\\.+/");

    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField usernameField;
    @FXML
    private Button cancel;
    @FXML
    private Button signUp;
    @FXML
    private Label errorMessage;

    public RegistrationController() {
        handler = Handler.getHandler();
    }

    public void setGui(ClientMain clientMain) {
        this.clientMain = clientMain;
    }

    public void register() {
        if (!(usernameField.getLength() > 3)) {
            errorMessage.setText("Username is too short");  //(minimum is 3 characters)
        } else if ((checkValidEmail(emailField.getText()))) {
            errorMessage.setText("Email is incorrect");
        } else if (!(passwordField.getLength() > 5)) {
            errorMessage.setText("Password is too short");  //(minimum is 6 characters)
        } else {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            handler.registration(username, email, password);
            clientMain.showLoginWindow();
        }
    }

    private boolean checkValidEmail(String email) {
        Matcher m = pattern.matcher(email);
        return m.matches();
    }

    @FXML
    private void cancel() {
        clientMain.showLoginWindow();
    }
}