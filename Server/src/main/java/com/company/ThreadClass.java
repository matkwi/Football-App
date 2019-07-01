package com.company;


import org.json.JSONObject;

import java.io.*;
import java.net.Socket;

public class ThreadClass extends Thread {

    private Socket socket;

    private String line = null;

    private Database db;

    private JSONObject js;

    private String requestType;

    public ThreadClass(Socket socket) {
        this.socket = socket;
        db = new Database(socket);
    }

    @Override
    public void run() {
        readFromClient();

        //recognizing type of request
        if(requestType.equals("scoreboard")) db.scoreboardRequest(js);
        else if(requestType.equals("timetable")) db.timetableRequest(js);
        else if(requestType.equals("players")) db.playersRequest(js);
        else if(requestType.equals("statistics")) db.statisticsRequest(js);
        else if(requestType.equals("deleteGame")) db.deleteGameRequest(js);
        else if(requestType.equals("addGame")) db.addGameRequest(js);
        else if(requestType.equals("addPlayer")) db.addPlayerRequest(js);
        else if (requestType.equals("deletePlayer")) db.deletePlayerRequest(js);

        fromWho();
    }

    //reading request from client
   private void readFromClient() {
       try {
           BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
           line = br.readLine();
           js = new JSONObject(line);
           requestType = js.getString("request");
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

   private void fromWho() {
       System.out.println(line);
       System.out.println(socket.getInetAddress().getHostName());
   }
}
