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

public class PlayerScene implements Initializable {

    private Stage thisStage;
    private JSONObject playerClicked;
    private JSONObject teamClicked;
    private JSONArray array = new JSONArray();
    private ObservableList<String> playerInfo = observableArrayList();

    @FXML
    private Label name;
    @FXML
    private MenuItem exitMenu;
    @FXML
    private Button backButton;

    @FXML
    private ListView<String> listViewInfo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exitMenu.setOnAction(event -> exitClick());
        backButton.setOnAction(event -> backClick());
    }

    public PlayerScene(Stage stage, JSONObject playerClicked, JSONObject teamClicked, JSONArray games) {
        //getting player, team, games information from previous scene
        this.playerClicked = playerClicked;
        thisStage = stage;
        this.teamClicked = teamClicked;
        for (int i = 0; i < games.length(); i++) {
            JSONObject o = games.getJSONObject(i);
            array.put(o);
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("playerscene.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setResizable(false);

            //setting information about player
            name.setText(playerClicked.getString("firstName") + " " + playerClicked.getString("lastName"));
            setInfo(playerClicked);
            listViewInfo.setItems(playerInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void exitClick() {
        System.exit(0);
    }

    private void backClick() {
        TeamScene teamScene = new TeamScene(thisStage, teamClicked, array);
    }

    //setting information about player
    private void setInfo(JSONObject obj) {
        playerInfo.clear();
        playerInfo.add("Shirt number: " + obj.getInt("shirtNumber"));
        playerInfo.add("Nationality: " + obj.getString("nationality"));
        playerInfo.add("Position: " + obj.getString("position"));
        playerInfo.add("Birthday: " + obj.getString("birthday"));
    }
}
