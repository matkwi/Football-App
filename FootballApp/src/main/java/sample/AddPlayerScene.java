package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPlayerScene extends TeamScene {

    private Stage thisStage;
    private JSONArray obj = new JSONArray();
    private int playerClickedId;

    @FXML
    private MenuItem laligaMenu;
    @FXML
    private MenuItem plMenu;
    @FXML
    private MenuItem ekstraMenu;
    @FXML
    private ChoiceBox teamsChoice;
    @FXML
    private Label leagueLabel;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField textLastName;
    @FXML
    private TextField textFirstName;
    @FXML
    private TextField textShirtNumber;
    @FXML
    private TextField textNationality;
    @FXML
    private TextField textPosition;
    @FXML
    private DatePicker datePicker;
    @FXML
    private MenuItem exitMenu;
    @FXML
    private Button backButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        laligaMenu.setOnAction(event -> laligaClick());
        plMenu.setOnAction(event -> plClick());
        ekstraMenu.setOnAction(event -> ekstraClick());
        teamsChoice.setOnAction(event -> teamsChoiceClick());
        deleteButton.setOnAction(event -> deleteClick());
        addButton.setOnAction(event -> addClick());
        exitMenu.setOnAction(event -> exitClick());
        backButton.setOnAction(event -> backClick());
    }


    public AddPlayerScene(Stage stage) {
        thisStage = stage;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addplayerscene.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setResizable(false);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void laligaClick() {
        leagueLabel.setText(laligaMenu.getText());
        setChoiceBox();
    }

    private void ekstraClick() {
        leagueLabel.setText(ekstraMenu.getText());
        setChoiceBox();
    }

    private void plClick() {
        leagueLabel.setText(plMenu.getText());
        setChoiceBox();
    }

    //setting ChoiceBox with teams from chosen league
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
        teamsChoice.getItems().clear();
        for (int i = 0; i < obj.length(); i++) {
            JSONObject o = new JSONObject((String) obj.get(i));
            teamsChoice.getItems().add(o.getString("name"));

        }
    }

    private void teamsChoiceClick() {
        for (int i = 0; i < obj.length(); i++) {
            JSONObject o = new JSONObject((String) obj.get(i));
            if (teamsChoice.getValue().toString().equals(o.getString("name"))) {
                setPlayersList(o.getInt("id"));
                playerClickedId = o.getInt("id");
            }

        }
    }

    private void addClick() {
        //making add player request
        JSONObject request = new JSONObject();
        request.put("request", "addPlayer");
        request.put("firstName", textFirstName.getText());
        request.put("lastName", textLastName.getText());
        request.put("shirtNumber", textShirtNumber.getText());
        request.put("nationality", textNationality.getText());
        request.put("position", textPosition.getText());
        request.put("date_of_birth", datePicker.getEditor().getText());
        request.put("team_id", playerClickedId);
        //sending the request
            try {
                Connection con = new Connection();
                obj = con.request(request.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void deleteClick() {
        //making delete player request
        JSONObject request = new JSONObject();
        request.put("request", "deletePlayer");
        request.put("name", listView.getSelectionModel().getSelectedItem());
        listView.getItems().remove(listView.getSelectionModel().getSelectedItem());
        //sending the request
        try {
            Connection con = new Connection();
            obj = con.request(request.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exitClick() {
        System.exit(0);
    }

    protected void backClick() {
        AdminScene adminScene = new AdminScene(thisStage);
    }

}
