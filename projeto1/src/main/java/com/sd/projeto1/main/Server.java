package com.sd.projeto1.main;

import com.sd.projeto1.util.PropertyManagement;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Server implements Runnable {

    private DatagramSocket socketServidor;

    private byte[] inData;
    private byte[] outData;

    PropertyManagement pm = new PropertyManagement();

    @Override
    public void run() {
        while (true) {
            try {
                inData = new byte[1400];
                outData = new byte[1400];

                // recebendo datagrama do cliente
                DatagramPacket receivedPacket = new DatagramPacket(inData, inData.length);
                socketServidor.receive(receivedPacket);

                String text = new String(receivedPacket.getData());
                outData = text.toUpperCase().getBytes();
                System.out.println("Mensagem Recebida: " + text);

                // Pegando ip e porta do cliente que enviou o datagram
                InetAddress IPAddress = receivedPacket.getAddress();
                int port = receivedPacket.getPort();

                DatagramPacket sendPacket = new DatagramPacket(inData, inData.length, IPAddress, port);
                socketServidor.send(sendPacket);
            } catch (IOException e) {
                System.out.println("Excessão causada: " + e.getLocalizedMessage());
            }

        }
    }
    public static void main(String[] args) throws SocketException{
            System.out.println("Servidor Iniciado...");
            new Thread(new Server()).start();
    }

}
