package com.sd.projeto1.main;

import com.sd.projeto1.util.PropertyManagement;
import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private static DatagramSocket socketServidor;

    private static byte[] receiveData;
    private static byte[] sendData;
    
    private static final Map<BigInteger,String> map = new HashMap();
    private static final Queue<DatagramPacket> mensagens = new LinkedList<>();

    
    private final PropertyManagement pm = new PropertyManagement();

    public static void main(String[] args) throws SocketException {
        System.out.println("Servidor Iniciado...");

        //Thread para receber datagran e colocar na fila
        Thread receiveThread = new Thread(new Runnable() {
         
            @Override
            public void run() {
                while (true) {
                    try {
                        receiveData = new byte[1400];
                        sendData = new byte[1400];

                        // recebendo datagrama do cliente
                        DatagramPacket receivedPacket = new DatagramPacket(receiveData, receiveData.length);
                        socketServidor.receive(receivedPacket);

                        String text = new String(receivedPacket.getData());
                        sendData = text.toUpperCase().getBytes();
                        System.out.println("Mensagem Recebida: " + text);

                        // Pegando ip e porta do cliente que enviou o zdatagram
                        InetAddress IPAddress = receivedPacket.getAddress();
                        int port = receivedPacket.getPort();

                        DatagramPacket sendPacket = new DatagramPacket(receiveData, receiveData.length, IPAddress, port);
                        socketServidor.send(sendPacket);
                        
                        Thread.sleep(300);
                    } catch (IOException e) {
                        System.out.println("Excessão causada: " + e.getLocalizedMessage());
                    } catch (InterruptedException ex) {
                        break;
                    }

                }
            }

        });
        
        //Thread que vai consumir do fila e responder para cliente;
        Thread consumeThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {

                    try {

                        Thread.sleep(800);
                    } catch (InterruptedException ex) {
                        break;
                    }
                }

            }
        });
        receiveThread.start();
        consumeThread.start();

    }

}


