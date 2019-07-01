package sample;

public class Teams {

    public Teams(int position, String name, String country, int league_id, int points, int games, int gamesWon, int gamesDraw, int gamesLost, int goalsScored, int goalsLost) {
        this.position = position;
        this.name = name;
        this.country = country;
        this.league_id = league_id;
        this.points = points;
        this.games = games;
        this.gamesWon = gamesWon;
        this.gamesDraw = gamesDraw;
        this.gamesLost = gamesLost;
        this.goalsScored = goalsScored;
        this.goalsLost = goalsLost;
    }

    private int position;
    private String name;
    private String country;
    private int league_id;
    private int points;
    private int games;
    private int gamesWon;
    private int gamesDraw;
    private int gamesLost;
    private int goalsScored;
    private int goalsLost;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getLeague_id() {
        return league_id;
    }

    public void setLeague_id(int league_id) {
        this.league_id = league_id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public int getGamesDraw() {
        return gamesDraw;
    }

    public void setGamesDraw(int gamesDraw) {
        this.gamesDraw = gamesDraw;
    }

    public int getGamesLost() {
        return gamesLost;
    }

    public void setGamesLost(int gamesLost) {
        this.gamesLost = gamesLost;
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    public int getGoalsLost() {
        return goalsLost;
    }

    public void setGoalsLost(int goalsLost) {
        this.goalsLost = goalsLost;
    }
}
