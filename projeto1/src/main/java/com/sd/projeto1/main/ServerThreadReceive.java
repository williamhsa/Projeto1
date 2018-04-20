/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd.projeto1.main;

import com.sd.projeto1.util.PropertyManagement;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author willi
 */
public class ServerThreadReceive extends Thread {

    private DatagramSocket receiveSocketServer;
    private DatagramPacket receivePacket;
    private InetAddress ip;
    private int port;
    private byte[] sendData;
    private byte[] receiveData;

    private PropertyManagement pm = new PropertyManagement();

    ServerThreadReceive() {
        start();
    }

    @Override
    public void run() {
        MultiQueue fila = new MultiQueue();
        this.sendData = new byte[1400];
        this.receiveData = new byte[1400];
        try {
            receiveSocketServer = new DatagramSocket(pm.getPort());
        } catch (SocketException se) {
            se.printStackTrace();
        }
     
        System.out.println("Servidor Iniciado...");
        while (true) {
            try {

              
                receivePacket = new DatagramPacket(receiveData, receiveData.length);
                receiveSocketServer.receive(receivePacket);
                String textReceive = new String(receivePacket.getData());

                //sendData = textReceive.toUpperCase().getBytes();
                System.out.println("Mensagem Recebida: " + textReceive);
                
               
                fila.push(receivePacket);
              

            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }

}
