package sample;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;


public class Connection {

    private Socket socket = new Socket("localhost", 1234);

    public Connection() throws IOException {

    }

    public JSONArray request(String request) throws IOException {
        //sending request to server
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bw.write(request);
        bw.newLine();
        bw.flush();

        //receiving data from server
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line = br.readLine();

        //getting JSONArray from JSONObject
        JSONObject js = new JSONObject(line);
        JSONArray array = js.getJSONArray("array");

        socket.close();

        return array;
    }




}
