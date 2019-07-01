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

public class Controller implements Initializable {

    private final Stage thisStage;

    @FXML
    private MenuItem exitMenu;

    @FXML
    private MenuItem adminMenu;

    @FXML
    private Button scoreButton;

    @FXML
    private Button timeButton;

    @FXML
    private Button teamsButton;

    @FXML
    private Button statsButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exitMenu.setOnAction(event -> exitClick());
        adminMenu.setOnAction(event -> adminClick());
        scoreButton.setOnAction(event -> scoreClick());
        timeButton.setOnAction(event -> timeClick());
        teamsButton.setOnAction(event -> teamsClick());
        statsButton.setOnAction(event -> statsClick());

    }

    public Controller(Stage stage) {
        thisStage = stage;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("controller.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setTitle("Football App");
            thisStage.setResizable(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Controller() {
        thisStage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("controller.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setTitle("Football App");
            thisStage.setResizable(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showStage() {
        thisStage.show();
    }

    private void exitClick() {
        System.exit(0);
    }

    private void adminClick() {
        AdminLogInScene adminScene = new AdminLogInScene(thisStage);
    }

    private void scoreClick() {
        ScoreBoard scoreBoard = new ScoreBoard(thisStage);
    }

    private void timeClick() {
        Timetable timeTable = new Timetable(thisStage);
    }

    private void teamsClick() {
        TeamsSceneList teams = new TeamsSceneList(thisStage);
    }

    private void statsClick() {
        StatsScene statsScene = new StatsScene(thisStage);
    }
}
