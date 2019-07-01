package com.company;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "Games")
public class Games {

    @Id
    private int Id;

    @Column(name = "Team_Id")
    private int teamHome;

    @Column(name = "Team_Id2")
    private int teamAway;

    @Column(name = "Round_num")
    private int round_num;

    @Column(name = "GameDate")
    private Date gameDate;

    @Column(name = "HomeScore")
    private int homeScore;

    @Column(name = "AwayScore")
    private int awayScore;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getTeamHome() {
        return teamHome;
    }

    public void setTeamHome(int teamHome) {
        this.teamHome = teamHome;
    }

    public int getTeamAway() {
        return teamAway;
    }

    public void setTeamAway(int teamAway) {
        this.teamAway = teamAway;
    }

    public int getRound_num() {
        return round_num;
    }

    public void setRound(int round_num) {
        this.round_num = round_num;
    }

    public Date getDate() {
        return gameDate;
    }

    public void setDate(Date gameDate) {
        this.gameDate = gameDate;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }
}
