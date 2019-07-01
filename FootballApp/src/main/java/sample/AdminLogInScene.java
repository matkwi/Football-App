package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminLogInScene implements Initializable {

    private Stage thisStage;

    @FXML
    private Button backButton;
    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField nickField;
    @FXML
    private Label wrongPassLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backButton.setOnAction(event -> backClick());
        loginButton.setOnAction(event -> loginClick());
    }

    public AdminLogInScene(Stage stage) {
        thisStage = stage;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminloginscene.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setResizable(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void backClick() {
        Controller controller = new Controller(thisStage);
    }

    //open Admin panel if login and password are ok
    private void loginClick() {
        if(nickField.getText().equals("admin") && passwordField.getText().equals("admin")) {
            AdminScene adminScene = new AdminScene(thisStage);
        }
        else wrongPassLabel.setText("wrong nickname or password");
    }

}
