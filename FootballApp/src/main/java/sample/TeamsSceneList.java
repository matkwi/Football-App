package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class TeamsSceneList implements Initializable {

    private Stage thisStage;
    private JSONArray obj = new JSONArray();
    private ObservableList<String> teams = observableArrayList();
    private String teamClicked;

    @FXML
    private MenuItem laligaMenu;
    @FXML
    private MenuItem plMenu;
    @FXML
    private MenuItem ekstraMenu;
    @FXML
    private Label label;
    @FXML
    private Button backButton;
    @FXML
    private MenuItem exitMenu;
    @FXML
    private ListView<String> listView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        laligaMenu.setOnAction(event -> laligaClick());
        plMenu.setOnAction(event -> plClick());
        ekstraMenu.setOnAction(event -> ekstraClick());
        listView.setOnMouseClicked(event -> teamClicked());
        backButton.setOnAction(event -> backClick());
        exitMenu.setOnAction(event -> exitClick());
    }

    public TeamsSceneList(Stage stage) {
        thisStage = stage;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("teamsscenelist.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setResizable(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exitClick() {
        System.exit(0);
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

    private void clickType(String leagueType) {
        JSONObject league = new JSONObject();
        league.put("request", "scoreboard");
        league.put("type", leagueType);
        label.setText(leagueType);
        try {
            Connection con = new Connection();
            obj = con.request(league.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        setTeam(obj);
        listView.setItems(teams);
    }

    private void setTeam(JSONArray obj) {
        teams.clear();
        for (int i = 0; i < obj.length(); i++) {
            JSONObject o = new JSONObject((String)obj.get(i));
            teams.add(o.getString("name"));
        }
    }

    private void teamClicked() {
        teamClicked = listView.getSelectionModel().getSelectedItem();
        JSONObject o = new JSONObject();
        for (int i = 0; i < obj.length(); i++) {
            o = new JSONObject((String)obj.get(i));
            if(teamClicked.equals(o.getString("name"))) {
                JSONObject games = new JSONObject();
                games.put("request", "timetable");
                games.put("type", label.getText());
                games.put("round", 0);
                games.put("team_id", o.getInt("id"));
                try {
                    Connection con = new Connection();
                    obj = con.request(games.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        TeamScene team = new TeamScene(thisStage, o, obj);
    }

}
