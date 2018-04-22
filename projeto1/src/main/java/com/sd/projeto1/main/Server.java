package com.sd.projeto1.main;

public class Server{
    
    public static void main(String[] args) {
        System.out.println("Servidor Iniciado...");
        new Thread(new ServerThreadOne()).start();
    }
   
}
