package com.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Teams")
public class Teams implements Comparable<Teams>, Serializable {

    @Id
    private int Id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Country")
    private String country;

    @Column(name = "League_Id")
    private int league_id;

    @Column(name = "Points")
    private int points;

    @Column(name = "Games")
    private int games;

    @Column(name = "GamesWon")
    private int gamesWon;

    @Column(name = "GamesDraw")
    private int gamesDraw;

    @Column(name = "GamesLost")
    private int gamesLost;

    @Column(name = "GoalsScored")
    private int goalsScored;

    @Column(name = "GoalsLost")
    private int goalsLost;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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

    @Override
    public String toString() {
        return "Teams{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", league_id=" + league_id +
                ", points=" + points +
                ", games=" + games +
                ", gamesWon=" + gamesWon +
                ", gamesDraw=" + gamesDraw +
                ", gamesLost=" + gamesLost +
                ", goalsScored=" + goalsScored +
                ", goalsLost=" + goalsLost +
                '}';
    }

    public int compareTo(Teams o) {
        if(getPoints() < o.getPoints()) return 1;
        else if(getPoints() > o.getPoints()) return -1;
        else if(getGoalsScored()-getGoalsLost() < o.getGoalsScored()-o.getGoalsLost()) return 1;
        else if(getGoalsScored()-getGoalsLost() > o.getGoalsScored()-o.getGoalsLost()) return -1;
        else if(getGoalsScored() < o.getGoalsScored()) return 1;
        else if(getGoalsScored() > o.getGoalsScored()) return -1;
        else return 0;
    }
}
