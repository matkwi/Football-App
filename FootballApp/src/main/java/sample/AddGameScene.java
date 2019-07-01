package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddGameScene extends Timetable {

    private Stage thisStage;
    private JSONArray obj = new JSONArray();

    @FXML
    private ChoiceBox homeTeam;
    @FXML
    private ChoiceBox awayTeam;
    @FXML
    private Button deleteButton;
    @FXML
    private Button addButton;
    @FXML
    private Button backButton;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField textScoreHome;
    @FXML
    private TextField textScoreAway;
    @FXML
    private Label errorLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        deleteButton.setOnAction(event -> deleteClick());
        addButton.setOnAction(event -> addClick());
        backButton.setOnAction(event -> backClick());
    }

    public AddGameScene(Stage stage) {
        thisStage = stage;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addgamescene.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setResizable(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void backClick() {
        AdminScene adminScene = new AdminScene(thisStage);
    }

    private void addClick() {
        //making add game request
        JSONObject request = new JSONObject();
        request.put("request", "addGame");
        request.put("gameDate", datePicker.getEditor().getText());
        request.put("homeTeam", homeTeam.getValue().toString());
        request.put("awayTeam", awayTeam.getValue().toString());
        request.put("homeScore", textScoreHome.getText());
        request.put("awayScore", textScoreAway.getText());
        request.put("round_num", roundLabel.getText());
        //sending the request
        try {
            Connection con = new Connection();
            obj = con.request(request.toString());
            //set error label if game exists
            errorLabel.setText(obj.getJSONObject(0).getString("ifAdded"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        table.refresh();
    }

    private void deleteClick() {
        //making delete game request
        Games selectedItem = table.getSelectionModel().getSelectedItem();
        JSONObject request = new JSONObject();
        request.put("request", "deleteGame");
        request.put("game_id", selectedItem.getGame_id());
        table.getItems().remove(selectedItem);
        //sending the request
        try {
            Connection con = new Connection();
            obj = con.request(request.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void laligaClick() {
        super.laligaClick();
        setChoiceBox();
    }

    protected void ekstraClick() {
        super.ekstraClick();
        setChoiceBox();
    }

    protected void plClick() {
        super.plClick();
        setChoiceBox();
    }

    //setting ChoiceBox with teams
    private void setChoiceBox() {
        JSONObject league = new JSONObject();
        league.put("request", "scoreboard");
        league.put("type", leagueLabel.getText());
        try {
            Connection con = new Connection();
            obj = con.request(league.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        homeTeam.getItems().clear();
        awayTeam.getItems().clear();
        for (int i = 0; i < obj.length(); i++) {
            JSONObject o = new JSONObject((String)obj.get(i));
            homeTeam.getItems().add(o.getString("name"));
            awayTeam.getItems().add(o.getString("name"));
        }
    }

}
