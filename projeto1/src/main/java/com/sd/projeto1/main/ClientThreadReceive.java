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
public class ClientThreadReceive extends Thread {

    private DatagramSocket receiveSocketClient;
    private DatagramPacket receivePacket;
    private InetAddress ip;
    private int port;
    private byte[] sendData;
    private byte[] receiveData;
    private PropertyManagement pm = new PropertyManagement();

    ClientThreadReceive() {
        start();
    }

    @Override
    public void run() {
        MultiQueue fila = new MultiQueue();
        this.sendData = new byte[1400];
        this.receiveData = new byte[1400];

        try {
            receiveSocketClient = new DatagramSocket();
        } catch (SocketException ex) {
            ex.printStackTrace();
        }

        while (true) {
            this.port = pm.getPortTwo();
            receivePacket = new DatagramPacket(receiveData, receiveData.length);
            try {
                receiveSocketClient.receive(receivePacket);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            String textReceive = new String(receivePacket.getData());

            //sendData = textReceive.toUpperCase().getBytes();
            System.out.println("Mensagem Recebida pelo cliente: " + textReceive);
        }

    }
}
