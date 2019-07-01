package sample;

import java.sql.Date;

public class Games {

    public Games(String gameDate, String home, String away, String score, int game_id, int home_id, int away_id) {
        this.gameDate = gameDate;
        this.home = home;
        this.away = away;
        this.score = score;
        this.game_id = game_id;
        this.home_id = home_id;
        this.away_id = away_id;
    }

    private String gameDate;
    private int game_id;
    private String home;
    private int home_id;
    private int away_id;
    private String away;
    private String score;

    public int getGame_id() {
        return game_id;
    }

    public void setGame_id(int game_id) {
        this.game_id = game_id;
    }

    public int getHome_id() {
        return home_id;
    }

    public void setHome_id(int home_id) {
        this.home_id = home_id;
    }

    public int getAway_id() {
        return away_id;
    }

    public void setAway_id(int away_id) {
        this.away_id = away_id;
    }

    public String getGameDate() {
        return gameDate;
    }

    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
