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

public class Timetable implements Initializable {

    private Stage thisStage;
    protected JSONArray obj = new JSONArray();
    protected ObservableList<Games> games = observableArrayList();

    @FXML
    protected TableView<Games> table;
    @FXML
    protected TableColumn<Games, String> columnDate;
    @FXML
    protected TableColumn<Games, String> columnHome;
    @FXML
    protected TableColumn<Games, String> columnAway;
    @FXML
    protected TableColumn<Games, String> columnScore;

    @FXML
    protected Label roundLabel;
    @FXML
    protected Label leagueLabel;

    @FXML
    protected MenuItem exitMenu;

    @FXML
    protected MenuItem laligaMenu;
    @FXML
    protected MenuItem plMenu;
    @FXML
    protected MenuItem ekstraMenu;
    @FXML
    protected MenuItem r1;
    @FXML
    protected MenuItem r2;
    @FXML
    protected MenuItem r3;
    @FXML
    protected MenuItem r4;
    @FXML
    protected MenuItem r5;
    @FXML
    protected MenuItem r6;
    @FXML
    protected MenuItem r7;
    @FXML
    protected MenuItem r8;
    @FXML
    protected MenuItem r9;
    @FXML
    protected MenuItem r10;
    @FXML
    protected MenuItem r11;
    @FXML
    protected MenuItem r12;
    @FXML
    protected MenuItem r13;
    @FXML
    protected MenuItem r14;
    @FXML
    protected MenuItem r15;
    @FXML
    protected MenuItem r16;
    @FXML
    protected MenuItem r17;
    @FXML
    protected MenuItem r18;

    @FXML
    private Button backButton;

    public Timetable() {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        laligaMenu.setOnAction(event -> laligaClick());
        plMenu.setOnAction(event -> plClick());
        ekstraMenu.setOnAction(event -> ekstraClick());
        r1.setOnAction(event -> r1Click());
        r2.setOnAction(event -> r2Click());
        r3.setOnAction(event -> r3Click());
        r4.setOnAction(event -> r4Click());
        r5.setOnAction(event -> r5Click());
        r6.setOnAction(event -> r6Click());
        r7.setOnAction(event -> r7Click());
        r8.setOnAction(event -> r8Click());
        r9.setOnAction(event -> r9Click());
        r10.setOnAction(event -> r10Click());
        r11.setOnAction(event -> r11Click());
        r12.setOnAction(event -> r12Click());
        r13.setOnAction(event -> r13Click());
        r14.setOnAction(event -> r14Click());
        r15.setOnAction(event -> r15Click());
        r16.setOnAction(event -> r16Click());
        r17.setOnAction(event -> r17Click());
        r18.setOnAction(event -> r18Click());
        backButton.setOnAction(event -> backClick());
        exitMenu.setOnAction(event -> exitClick());
    }

    public Timetable(Stage stage) {
        thisStage = stage;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("timetable.fxml"));
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

    private void backClick() {
        Controller controller = new Controller(thisStage);
    }

    private void clickType() {
        //creating timetable request
        JSONObject r = new JSONObject();
        r.put("request", "timetable");
        r.put("type", leagueLabel.getText());
        int round = Integer.parseInt(roundLabel.getText());
        r.put("round", round);
        r.put("team_id", 0);
        //sending the request
        try {
            Connection con = new Connection();
            obj = con.request(r.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        setGame(obj);
        setTable();
    }



    //setting array of games
    private void setGame(JSONArray obj) {
        games.clear();
        for (int i = 0; i < obj.length(); i++) {
            JSONObject o = new JSONObject(obj.get(i).toString());
            Games game = new Games(o.getString("gameDate"), o.getString("home"), o.getString("away"), o.getString("score"), o.getInt("game_id"), o.getInt("home_id"), o.getInt("away_id"));
            games.add(game);
        }
    }

    //setting timetable
    private void setTable() {
        columnDate.setCellValueFactory(new PropertyValueFactory<>("gameDate"));
        columnHome.setCellValueFactory(new PropertyValueFactory<>("home"));
        columnAway.setCellValueFactory(new PropertyValueFactory<>("away"));
        columnScore.setCellValueFactory(new PropertyValueFactory<>("score"));
        table.setItems(games);
    }

    protected void ekstraClick() {
        leagueLabel.setText(ekstraMenu.getText());
        clickType();
    }

    protected void plClick() {
        leagueLabel.setText(plMenu.getText());
        clickType();
    }

    protected void laligaClick() {
        leagueLabel.setText(laligaMenu.getText());
        clickType();
    }

    protected void r1Click() {
        roundLabel.setText(r1.getText());
        clickType();
    }

    protected void r2Click() {
        roundLabel.setText(r2.getText());
        clickType();
    }

    protected void r3Click() {
        roundLabel.setText(r3.getText());
        clickType();
    }

    protected void r4Click() {
        roundLabel.setText(r4.getText());
        clickType();
    }

    protected void r5Click() {
        roundLabel.setText(r5.getText());
        clickType();
    }

    protected void r6Click() {
        roundLabel.setText(r6.getText());
        clickType();
    }

    protected void r7Click() {
        roundLabel.setText(r7.getText());
        clickType();
    }

    protected void r8Click() {
        roundLabel.setText(r8.getText());
        clickType();
    }

    protected void r9Click() {
        roundLabel.setText(r9.getText());
        clickType();
    }

    protected void r10Click() {
        roundLabel.setText(r10.getText());
        clickType();
    }

    protected void r11Click() {
        roundLabel.setText(r11.getText());
        clickType();
    }

    protected void r12Click() {
        roundLabel.setText(r12.getText());
        clickType();
    }

    protected void r13Click() {
        roundLabel.setText(r13.getText());
        clickType();
    }

    protected void r14Click() {
        roundLabel.setText(r14.getText());
        clickType();
    }

    protected void r15Click() {
        roundLabel.setText(r15.getText());
        clickType();
    }

    protected void r16Click() {
        roundLabel.setText(r16.getText());
        clickType();
    }

    protected void r17Click() {
        roundLabel.setText(r17.getText());
        clickType();
    }

    protected void r18Click() {
        roundLabel.setText(r18.getText());
        clickType();
    }


}
