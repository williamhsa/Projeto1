/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd.projeto1.main;

import com.sd.projeto1.util.PropertyManagement;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
public class ClientThreadSend extends Thread {

    private DatagramSocket sendSocketClient;
    private DatagramPacket sendPacket;
    private InetAddress ip;
    private int port;
    private byte[] sendData;
    private byte[] receiveData;
    private BufferedReader keyboardUser;

    private PropertyManagement pm = new PropertyManagement();

    ClientThreadSend() {
        start();
    }

    @Override
    public void run() {
    
        try {
            sendSocketClient = new DatagramSocket();
        } catch (SocketException se) {
            se.printStackTrace();
        }
        this.sendData = new byte[1400];
        this.receiveData = new byte[1400];

        System.out.println("Cliente Iniciado, esperando por comando:");
        while (true) {
            try {
                this.port = pm.getPort();
                ip = InetAddress.getByName(pm.getAddress());
                //System.out.println(this.port);
                // System.out.println(ip);
                keyboardUser = new BufferedReader(new InputStreamReader(System.in));

                System.out.print(">");
                String setence = keyboardUser.readLine();
                sendData = setence.getBytes(); //Pegando o tamanho da String

                //Enviando o pacote de Datagram para o servido
                sendPacket = new DatagramPacket(sendData, sendData.length, ip, this.port);
                sendSocketClient.send(sendPacket);

            } catch (SocketException se) {
                se.printStackTrace();
            } catch (UnknownHostException ue) {
                ue.printStackTrace();
            } catch (IOException io) {
                io.printStackTrace();
            }
        }
    }
}
