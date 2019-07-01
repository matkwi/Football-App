package com.company;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.company.Main.FACTORY;

public class Database {

    private Socket socket;

    public Database(Socket socket) {
        this.socket = socket;
    }

    public void scoreboardRequest(JSONObject js) {
        String type = js.getString("type");
        int league = 0;
        EntityManager entityManager = FACTORY.createEntityManager();
        if (type.equals("La Liga")) league = 1;
        else if (type.equals("Premier League")) league = 2;
        else if (type.equals("Ekstraklasa")) league = 3;
        if (league != 0) {
            //getting teams list
            Query query = entityManager.createQuery("Select o from Teams o where o.league_id = :a");
            query.setParameter("a", league);
            List<Teams> list = query.getResultList();
            //sorting teams for scoreboard
            Collections.sort(list);
            JSONArray array = new JSONArray();
            ObjectMapper mapper = new ObjectMapper();
            //parsing to JSON
            try {
                for (Teams o : list) {
                    String json = mapper.writeValueAsString(o);
                    array.put(json);
                }
                //sending data back
                JSONObject j = new JSONObject();
                j.put("array", array);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bw.write(j.toString());
                bw.newLine();
                bw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void timetableRequest(JSONObject js) {
        String type = js.getString("type");
        int round = js.getInt("round");
        int team = js.getInt("team_id");
        EntityManager entityManager = FACTORY.createEntityManager();
        int league = 0;
        if (type.equals("La Liga")) league = 1;
        else if (type.equals("Premier League")) league = 2;
        else if (type.equals("Ekstraklasa")) league = 3;
        List<Games> listGames;
        if (league != 0) {
            if(round == 0) {
                //get every game of chosen team if round == 0
                Query query = entityManager.createQuery("Select o from Games o where o.teamHome = :a OR o.teamAway = :a");
                query.setParameter("a", team);
                listGames = query.getResultList();
            }
            else {
                //get every game from chosen round if round != 0
                Query query = entityManager.createQuery("Select o from Games o where o.round_num = :a");
                query.setParameter("a", round);
                listGames = query.getResultList();
            }
            //get every team from chosen league
            Query query2 = entityManager.createQuery("Select o from Teams o where o.league_id = :b");
            query2.setParameter("b", league);
            List<Teams> listTeams = query2.getResultList();

            JSONArray array = new JSONArray();
            for(int i = 0; i < listGames.size(); i++) {
                boolean check = false;
                JSONObject g = new JSONObject();
                for(int j = 0; j < listTeams.size(); j++) {
                    //if teamHome from games list equals to team from teams list, put team to JSON
                    if(listGames.get(i).getTeamHome() == listTeams.get(j).getId()) {
                        g.put("home", listTeams.get(j).getName());
                        check = true;
                    }
                    //if teamAway from games list equals to team from teams list, put team to JSON
                    else if (listGames.get(i).getTeamAway() == listTeams.get(j).getId()) {
                        g.put("away", listTeams.get(j).getName());
                        check = true;
                    }
                }
                //if teams added to JSON, add more information
                if(check == true) {
                    g.put("gameDate", listGames.get(i).getDate().toString());
                    g.put("score", listGames.get(i).getHomeScore() + " : " + listGames.get(i).getAwayScore());
                    g.put("home_id", listGames.get(i).getTeamHome());
                    g.put("away_id", listGames.get(i).getTeamAway());
                    g.put("game_id", listGames.get(i).getId());
                    array.put(g);
                }
            }
            //sending data back
            try {
                JSONObject j = new JSONObject();
                j.put("array", array);
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                bw.write(j.toString());
                bw.newLine();
                bw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void playersRequest(JSONObject js) {
        int team = js.getInt("team_id");
        EntityManager entityManager = FACTORY.createEntityManager();
        Query query = entityManager.createQuery("Select o from Players o where o.team_id = :a");
        query.setParameter("a", team);
        List<Players> playersList = query.getResultList();

        JSONArray array = new JSONArray();
        try {
            for (Players o : playersList) {
                JSONObject json = new JSONObject();
                json.put("id", o.getId());
                json.put("firstName", o.getFirstName());
                json.put("lastName", o.getLastName());
                json.put("shirtNumber", o.getShirtNumber());
                json.put("nationality", o.getNationality());
                json.put("team_id", o.getTeam_id());
                json.put("position", o.getPosition());
                json.put("birthday", o.getBirthday().toString());
                array.put(json);
            }
            JSONObject j = new JSONObject();
            j.put("array", array);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bw.write(j.toString());
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void statisticsRequest(JSONObject js) {
        JSONObject j = new JSONObject();
        String stats;
        stats = goalsScored();
        j.put("goalsScored", stats);
        stats = goalsLost();
        j.put("goalsLost", stats);
        stats = gamesWon();
        j.put("gamesWon", stats);
        stats = gamesDraw();
        j.put("gamesDraw", stats);
        stats = gamesLost();
        j.put("gamesLost", stats);
        stats = points();
        j.put("points", stats);
        stats = goalDifference();
        j.put("goalDifference", stats);
        JSONArray array = new JSONArray();
        JSONObject json = new JSONObject();
        array.put(j);
        json.put("array" ,array);
        try {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bw.write(json.toString());
        bw.newLine();
        bw.flush();
        } catch (IOException e) {
        e.printStackTrace();
        }
    }

    private String goalsScored() {
        EntityManager entityManager = FACTORY.createEntityManager();
        Query query = entityManager.createQuery("Select o from Teams o where o.goalsScored = (SELECT MAX(o.goalsScored) from Teams o)");
        List<Teams> list = query.getResultList();
        int x = 0;
        String stats = "";
        for(Teams o : list) {
            if(x == 0) stats = stats + o.getName();
            else stats = stats + " ," + o.getName();
            x = o.getGoalsScored();
        }
        stats = stats + " (" + x + ")";

        return stats;
    }

    private String goalsLost() {
        EntityManager entityManager = FACTORY.createEntityManager();
        Query query = entityManager.createQuery("Select o from Teams o where o.goalsLost = (SELECT MAX(o.goalsLost) from Teams o)");
        List<Teams> list = query.getResultList();
        int x = 0;
        String stats = "";
        for(Teams o : list) {
            if(x == 0) stats = stats + o.getName();
            else stats = stats + " ," + o.getName();
            x = o.getGoalsLost();
        }
        stats = stats + " (" + x + ")";

        return stats;
    }

    private String goalDifference() {
        EntityManager entityManager = FACTORY.createEntityManager();
        Query query = entityManager.createQuery("Select o from Teams o where (o.goalsScored - o.goalsLost) = (SELECT MAX(o.goalsScored - o.goalsLost) from Teams o)");
        List<Teams> list = query.getResultList();
        int x = 0;
        String stats = "";
        for(Teams o : list) {
            if(x == 0) stats = stats + o.getName();
            else stats = stats + " ," + o.getName();
            x = o.getGoalsScored() - o.getGoalsLost();
        }
        stats = stats + " (" + x + ")";

        return stats;
    }

    private String gamesWon() {
        EntityManager entityManager = FACTORY.createEntityManager();
        Query query = entityManager.createQuery("Select o from Teams o where o.gamesWon = (SELECT MAX(o.gamesWon) from Teams o)");
        List<Teams> list = query.getResultList();
        int x = 0;
        String stats = "";
        for(Teams o : list) {
            if(x == 0) stats = stats + o.getName();
            else stats = stats + " ," + o.getName();
            x = o.getGamesWon();
        }
        stats = stats + " (" + x + ")";

        return stats;
    }

    private String gamesDraw() {
        EntityManager entityManager = FACTORY.createEntityManager();
        Query query = entityManager.createQuery("Select o from Teams o where o.gamesDraw = (SELECT MAX(o.gamesDraw) from Teams o)");
        List<Teams> list = query.getResultList();
        int x = 0;
        String stats = "";
        for(Teams o : list) {
            if(x == 0) stats = stats + o.getName();
            else stats = stats + " ," + o.getName();
            x = o.getGamesDraw();
        }
        stats = stats + " (" + x + ")";

        return stats;
    }

    private String gamesLost() {
        EntityManager entityManager = FACTORY.createEntityManager();
        Query query = entityManager.createQuery("Select o from Teams o where o.gamesLost = (SELECT MAX(o.gamesLost) from Teams o)");
        List<Teams> list = query.getResultList();
        int x = 0;
        String stats = "";
        for(Teams o : list) {
            if(x == 0) stats = stats + o.getName();
            else stats = stats + " ," + o.getName();
            x = o.getGamesLost();
        }
        stats = stats + " (" + x + ")";

        return stats;
    }

    private String points() {
        EntityManager entityManager = FACTORY.createEntityManager();
        Query query = entityManager.createQuery("Select o from Teams o where o.points = (SELECT MAX(o.points) from Teams o)");
        List<Teams> list = query.getResultList();
        int x = 0;
        String stats = "";
        for(Teams o : list) {
            if(x == 0) stats = stats + o.getName();
            else stats = stats + " ," + o.getName();
            x = o.getPoints();
        }
        stats = stats + " (" + x + ")";

        return stats;
    }

    public void deleteGameRequest(JSONObject js) {
        EntityManager entitymanager = FACTORY.createEntityManager();
        EntityTransaction transaction = entitymanager.getTransaction();
        Games game = entitymanager.find(Games.class, js.getInt("game_id"));
        transaction.begin();
        entitymanager.remove(game);
        transaction.commit();
        entitymanager.close();

        JSONArray array = new JSONArray();
        JSONObject j = new JSONObject();
        j.put("array", array);

        try {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bw.write(j.toString());
        bw.newLine();
        bw.flush();
        } catch (IOException e) {
        e.printStackTrace();
        }
    }

    public void deletePlayerRequest(JSONObject js) {
        String[] name = new String[2];
        name = js.getString("name").split(" ");
        Players player = new Players();
        EntityManager entityManager = FACTORY.createEntityManager();
        Query query = entityManager.createQuery("Select o from Players o where o.firstName = :a AND o.lastName = :b");
        query.setParameter("a", name[0]);
        query.setParameter("b", name[1]);
        player = (Players) query.getSingleResult();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(player);
        transaction.commit();
        entityManager.close();

        JSONArray array = new JSONArray();
        JSONObject j = new JSONObject();
        j.put("array", array);

        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bw.write(j.toString());
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addGameRequest(JSONObject js) {
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        JSONObject j = new JSONObject();
        EntityManager entityManager = FACTORY.createEntityManager();
        Query query = entityManager.createQuery("Select o from Teams o where o.name = :a");
        query.setParameter("a", js.getString("homeTeam"));
        Query query2 = entityManager.createQuery("Select o from Teams o where o.name = :a");
        query2.setParameter("a", js.getString("awayTeam"));
        Teams homeTeam = new Teams();
        Teams awayTeam = new Teams();
        try {
        homeTeam = (Teams) query.getSingleResult();
        awayTeam = (Teams) query2.getSingleResult();
        } catch (NoResultException e) {
            json.put("ifAdded", "wrongTeams");
        }
        if(js.getString("homeTeam").equals(homeTeam.getName()) && js.getString("awayTeam").equals(awayTeam.getName())) {
            Query query3 = entityManager.createQuery("Select o from Games o where o.teamHome = :a AND o.teamAway = :b");
            query3.setParameter("a", homeTeam.getId());
            query3.setParameter("b", awayTeam.getId());
            try {
            Games game = (Games) query3.getSingleResult();
                if(homeTeam.getId() == game.getTeamHome() && awayTeam.getId() == game.getTeamAway()) {
                    json.put("ifAdded", "game already exists in round" + " " + game.getRound_num());
                }
            } catch (NoResultException e) {
                Games addGame = new Games();
                try {
                    Date date1 = new SimpleDateFormat("dd.MM.yyyy").parse(js.getString("gameDate"));
                    java.sql.Date sd = new java.sql.Date(date1.getTime());
                    addGame.setDate(sd);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                addGame.setTeamHome(homeTeam.getId());
                addGame.setTeamAway(awayTeam.getId());
                addGame.setHomeScore(js.getInt("homeScore"));
                addGame.setAwayScore(js.getInt("awayScore"));
                addGame.setRound(Integer.parseInt(js.getString("round_num")));
                Query query4 = entityManager.createQuery("Select o from Games o where o.Id = (SELECT MAX(o.Id) from Games o)");
                Games game2 = (Games) query4.getSingleResult();
                addGame.setId(game2.getId() + 1);
                EntityManager entityManager2 = FACTORY.createEntityManager();
                EntityTransaction transaction = entityManager2.getTransaction();
                transaction.begin();
                entityManager2.merge(addGame);
                transaction.commit();

                json.put("ifAdded", " ");
            }
        }
        array.put(json);
        j.put("array", array);
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bw.write(j.toString());
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addPlayerRequest(JSONObject js) {
        Players addPlayer = new Players();
        try {
            Date date1 = new SimpleDateFormat("dd.MM.yyyy").parse(js.getString("date_of_birth"));
            java.sql.Date sd = new java.sql.Date(date1.getTime());
            addPlayer.setBirthday(sd);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        addPlayer.setFirstName(js.getString("firstName"));
        addPlayer.setLastName(js.getString("lastName"));
        addPlayer.setShirtNumber(Integer.parseInt(js.getString("shirtNumber")));
        addPlayer.setNationality(js.getString("nationality"));
        addPlayer.setPosition(js.getString("position"));
        addPlayer.setTeam_id(js.getInt("team_id"));

        EntityManager entityManager = FACTORY.createEntityManager();
        Query query = entityManager.createQuery("Select o from Players o where o.Id = (SELECT MAX(o.Id) from Players o)");
        Players player = (Players) query.getSingleResult();
        addPlayer.setId(player.getId() + 1);

        EntityManager entityManager2 = FACTORY.createEntityManager();
        EntityTransaction transaction = entityManager2.getTransaction();
        transaction.begin();
        entityManager2.merge(addPlayer);
        transaction.commit();

        JSONObject json = new JSONObject();

        json.put("ifAdded", "yes");

        JSONArray array = new JSONArray();
        array.put(json);
        JSONObject j = new JSONObject();
        j.put("array", array);
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bw.write(j.toString());
            bw.newLine();
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


