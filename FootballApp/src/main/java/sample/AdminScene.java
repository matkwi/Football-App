package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminScene implements Initializable {

    private Stage thisStage;

    @FXML
    private Button backButton;
    @FXML
    private Button addPlayerButton;
    @FXML
    private Button addGameButton;
    @FXML
    private MenuItem exitMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backButton.setOnAction(event -> backClick());
        addGameButton.setOnAction(event -> addGameClick());
        addPlayerButton.setOnAction(event -> addPlayerClick());
        exitMenu.setOnAction(event -> exitClick());
    }

    public AdminScene(Stage stage) {
        thisStage = stage;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminscene.fxml"));
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

    private void exitClick() {
        System.exit(0);
    }

    private void addPlayerClick() {
        AddPlayerScene addPlayerScene = new AddPlayerScene(thisStage);
    }

    private void addGameClick() {
        AddGameScene addGameScene = new AddGameScene(thisStage);
    }

}
