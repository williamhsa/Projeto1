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
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author willi
 */
public class ServerThreadSend extends Thread {

    private DatagramSocket sendSocketServer;
    private DatagramPacket sendPacket;
    private InetAddress ip;
    private int port;
    private byte[] sendData;
    private byte[] receiveData;

    private PropertyManagement pm = new PropertyManagement();

    ServerThreadSend() {
        start();
    }

    @Override
    public void run() {
        this.sendData = new byte[1400];
        this.receiveData = new byte[1400];
        try {
            sendSocketServer = new DatagramSocket();
        } catch (SocketException se) {
            se.printStackTrace();
        }
        MultiQueue fila = new MultiQueue();
        while (true) {
            sendPacket = fila.pullPacket();

            try {
                ip = InetAddress.getByName(pm.getAddress());

                this.port = pm.getPortTwo();

                String modifiedSentence = new String(sendPacket.getData());
                sendData = modifiedSentence.toUpperCase().getBytes();

                sendPacket = new DatagramPacket(sendData, sendData.length, ip, this.port);

                sendSocketServer.send(sendPacket);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

    }

}
