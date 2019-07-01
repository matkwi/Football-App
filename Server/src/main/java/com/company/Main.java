package com.company;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("MyUnit");

    public static void main(String[] args) throws IOException {

        //setting up server
        ServerSocket server = new ServerSocket(1234);
        //waiting for client requests
        while(true) {
            Socket socket = server.accept();
            //starting new thread if request is received
            ThreadClass w = new ThreadClass(socket);
            w.start();
        }
    }
}
