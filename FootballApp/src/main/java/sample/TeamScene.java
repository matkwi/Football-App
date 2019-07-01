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

public class TeamScene implements Initializable {

    private Stage thisStage;
    private JSONObject teamClicked;
    private JSONArray obj = new JSONArray();
    private ObservableList<String> infoAboutTeam = observableArrayList();
    private ObservableList<Games> games = observableArrayList();
    protected ObservableList<String> players = observableArrayList();
    private String playerClicked;
    private JSONArray array = new JSONArray();

    @FXML
    private ListView<String> listViewInfo;

    @FXML
    protected ListView<String> listView;

    @FXML
    private Label name1;
    @FXML
    private Label name2;
    @FXML
    private Label name3;
    @FXML
    private MenuItem exitMenu;
    @FXML
    private Button backButton1;
    @FXML
    private Button backButton2;
    @FXML
    private Button backButton3;

    @FXML
    private TableView<Games> tableTeam;
    @FXML
    private TableColumn<Games, String> columnDate;
    @FXML
    private TableColumn<Games, String> columnHome;
    @FXML
    private TableColumn<Games, String> columnAway;
    @FXML
    private TableColumn<Games, String> columnScore;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listView.setOnMouseClicked(event -> playerClicked());
        exitMenu.setOnAction(event -> exitClick());
        backButton1.setOnAction(event -> backClick());
        backButton2.setOnAction(event -> backClick());
        backButton3.setOnAction(event -> backClick());
    }

    public TeamScene() {

    }

    public TeamScene(Stage stage, JSONObject teamClicked, JSONArray games) {
        this.teamClicked = teamClicked;
        thisStage = stage;

        for (int i = 0; i < games.length(); i++) {
            JSONObject o = games.getJSONObject(i);
            array.put(o);
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("teamscene.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setResizable(false);
            //setting name of team
            name1.setText(teamClicked.getString("name"));
            name2.setText(teamClicked.getString("name"));
            name3.setText(teamClicked.getString("name"));

            //setting info about team
            setInfo(teamClicked);
            listViewInfo.setItems(infoAboutTeam);
            //setting games of particular team
            setTable(games);
            //setting players of particular team
            setPlayersList(teamClicked.getInt("id"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exitClick() {
        System.exit(0);
    }

    private void backClick() {
        TeamsSceneList teamsSceneList = new TeamsSceneList(thisStage);
    }

    private void setInfo(JSONObject obj) {
        infoAboutTeam.clear();
        infoAboutTeam.add("Country: " + obj.getString("country"));
        infoAboutTeam.add("Points: " + String.valueOf(obj.getInt("points")));
        infoAboutTeam.add("Games: " + String.valueOf(obj.getInt("games")));
        infoAboutTeam.add("Games won: " + String.valueOf(obj.getInt("gamesWon")));
        infoAboutTeam.add("Games drawn: " + String.valueOf(obj.getInt("gamesDraw")));
        infoAboutTeam.add("Games lost: " + String.valueOf(obj.getInt("gamesLost")));
        infoAboutTeam.add("Goals scored: " + String.valueOf(obj.getInt("goalsScored")));
        infoAboutTeam.add("Goals lost: " + String.valueOf(obj.getInt("goalsLost")));
    }

    private void setTable(JSONArray obj) {
        games.clear();
        for (int i = 0; i < obj.length(); i++) {
            JSONObject o = new JSONObject(obj.get(i).toString());
            Games game = new Games(o.getString("gameDate"), o.getString("home"), o.getString("away"), o.getString("score"), o.getInt("game_id"), o.getInt("home_id"), o.getInt("away_id"));
            games.add(game);
        }
        columnDate.setCellValueFactory(new PropertyValueFactory<>("gameDate"));
        columnHome.setCellValueFactory(new PropertyValueFactory<>("home"));
        columnAway.setCellValueFactory(new PropertyValueFactory<>("away"));
        columnScore.setCellValueFactory(new PropertyValueFactory<>("score"));
        tableTeam.setItems(games);
    }

    protected void setPlayersList(int team_id) {
        JSONObject request = new JSONObject();
        request.put("request", "players");
        request.put("team_id", team_id);
        try {
            Connection con = new Connection();
            obj = con.request(request.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        setPlayers(obj);
        listView.setItems(players);
    }

    protected void setPlayers(JSONArray obj) {
        players.clear();
        for (int i = 0; i < obj.length(); i++) {
            JSONObject o = obj.getJSONObject(i);
            players.add(o.getString("firstName") + " " + o.getString("lastName"));
        }

    }

    //getting information about clicked player and sending it to next scene
    private void playerClicked() {
        playerClicked = listView.getSelectionModel().getSelectedItem();
        JSONObject o = new JSONObject();
        for (int i = 0; i < obj.length(); i++) {
            o = obj.getJSONObject(i);
            if (playerClicked.equals(o.getString("firstName") + " " + o.getString("lastName"))) break;
        }
        PlayerScene player = new PlayerScene(thisStage, o, teamClicked, array);
    }

}
