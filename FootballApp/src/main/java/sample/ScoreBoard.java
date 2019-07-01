package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class ScoreBoard implements Initializable {

    private Stage thisStage;
    private JSONArray obj = new JSONArray();
    private ObservableList<Teams> teams = observableArrayList();

    @FXML
    private MenuItem laligaMenu;

    @FXML
    private MenuItem plMenu;

    @FXML
    private MenuItem ekstraMenu;

    @FXML
    private Label label;

    @FXML
    private MenuItem exitMenu;

    @FXML
    private Button backButton;

    @FXML
    private TableView<Teams> table;

    @FXML
    private TableColumn<Teams, String> column1;

    @FXML
    private TableColumn<Teams, String> columnTeam;

    @FXML
    private TableColumn<Teams, String> columnM;

    @FXML
    private TableColumn<Teams, String> columnW;

    @FXML
    private TableColumn<Teams, String> columnD;

    @FXML
    private TableColumn<Teams, String> columnL;

    @FXML
    private TableColumn<Teams, String> columnGoals;

    @FXML
    private TableColumn<Teams, String> columnPoints;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        laligaMenu.setOnAction(event -> laligaClick());
        plMenu.setOnAction(event -> plClick());
        ekstraMenu.setOnAction(event -> ekstraClick());
        exitMenu.setOnAction(event -> exitClick());
        backButton.setOnAction(event -> backClick());
    }

    public ScoreBoard(Stage stage) {
        thisStage = stage;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scoreboard.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setResizable(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void plClick() {
        clickType(plMenu.getText());
    }

    private void laligaClick() {
        clickType(laligaMenu.getText());
    }

    private void ekstraClick()  {
        clickType(ekstraMenu.getText());
    }

    private void backClick() {
        Controller controller = new Controller(thisStage);
    }

    private void exitClick() {
        System.exit(0);
    }

    private void clickType(String leagueType) {
        //create scoreboard request
        JSONObject league = new JSONObject();
        league.put("request", "scoreboard");
        league.put("type", leagueType);
        label.setText(leagueType);
        //sending request
        try {
            Connection con = new Connection();
            obj = con.request(league.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        setTeam(obj);
        setTable();
    }

    //setting scoreboard
    private void setTable() {
        column1.setCellValueFactory(new PropertyValueFactory<>("position"));
        columnTeam.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnM.setCellValueFactory(new PropertyValueFactory<>("games"));
        columnW.setCellValueFactory(new PropertyValueFactory<>("gamesWon"));
        columnD.setCellValueFactory(new PropertyValueFactory<>("gamesDraw"));
        columnL.setCellValueFactory(new PropertyValueFactory<>("gamesLost"));
        columnGoals.setCellValueFactory(new PropertyValueFactory<>("goalsScored"));
        columnPoints.setCellValueFactory(new PropertyValueFactory<>("points"));
        table.setItems(teams);
    }

    //setting teams array with objects of class "Games"
    private void setTeam(JSONArray obj) {
        teams.clear();
        for (int i = 0; i < obj.length(); i++) {
            JSONObject o = new JSONObject((String)obj.get(i));
            Teams team = new Teams(i+1, o.getString("name"), o.getString("country"), o.getInt("league_id"), o.getInt("points"), o.getInt("games"), o.getInt("gamesWon"), o.getInt("gamesDraw"), o.getInt("gamesLost"), o.getInt("goalsScored"), o.getInt("goalsLost"));
            teams.add(team);
        }
    }

}
