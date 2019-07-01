package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class StatsScene implements Initializable {

    private Stage thisStage;
    private JSONArray obj = new JSONArray();
    private ObservableList<String> stats1 = observableArrayList();

    @FXML
    private MenuItem exitMenu;

    @FXML
    private Button backButton;

    @FXML
    private ListView<String> listViewStats1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        exitMenu.setOnAction(event -> exitClick());
        backButton.setOnAction(event -> backClick());
    }

    public StatsScene(Stage stage) {
        thisStage = stage;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("statsscene.fxml"));
            loader.setController(this);
            thisStage.setScene(new Scene(loader.load()));
            thisStage.setResizable(false);

            randomStatsRequest();
            setInfo(obj);
            listViewStats1.setItems(stats1);

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

    private void randomStatsRequest() {
        //creating statistics request
        JSONObject request = new JSONObject();
        request.put("request", "statistics");
        //sending request
        try {
            Connection con = new Connection();
            obj = con.request(request.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //setting random statistics
    private void setInfo(JSONArray obj) {
        JSONObject o = obj.getJSONObject(0);
        stats1.clear();
        stats1.add("Most points: " + o.getString("points"));
        stats1.add("Most scored goals: " + o.getString("goalsScored"));
        stats1.add("Most lost goals: " + o.getString("goalsLost"));
        stats1.add("Biggest goal difference: " + o.getString("goalDifference"));
        stats1.add("Most won games: " + o.getString("gamesWon"));
        stats1.add("Most drawn games: " + o.getString("gamesDraw"));
        stats1.add("Most lost games: " + o.getString("gamesLost"));
    }

}
