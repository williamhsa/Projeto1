package com.sd.projeto1.main;

import com.sd.projeto1.dao.MapaDao;
import com.sd.projeto1.model.Mapa;
import com.sd.projeto1.util.PropertyManagement;
import com.sd.projeto1.util.Utilidades;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class Client {

    private String server;	// servidor UDP
    private int port; //porta UDP

    

    Client(String server, int port) {
        this.server = server;
        this.port = port;
    }

    public static void main(String[] args) throws IOException, Exception {

        PropertyManagement pm = new PropertyManagement();

        Scanner in = new Scanner(System.in);
        
        
        Client client1 = new Client(pm.getAddress(), pm.getPort());
        //System.out.println(pm.getAddress());
        //System.out.println(pm.getPort());
        
        try{
            // criação de sockets UDP - Datagramas
            DatagramSocket clientSocket = new DatagramSocket();
            
        }catch()
    
        
        
    }
    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
