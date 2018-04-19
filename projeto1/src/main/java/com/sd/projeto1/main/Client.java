package com.sd.projeto1.main;

import com.sd.projeto1.dao.MapaDao;
import com.sd.projeto1.model.Mapa;
import com.sd.projeto1.util.PropertyManagement;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {

    private static InetAddress enderecoIP;
    private static int port; //porta UDP
    private static BufferedReader tecladoUsuario;
    private static DatagramSocket socketCliente;

    private static byte[] sendData;
    private static byte[] receiveData;

    private final PropertyManagement pm = new PropertyManagement();

    Client() throws SocketException, UnknownHostException {
        enderecoIP = InetAddress.getByName(pm.getAddress());
        port = pm.getPort();
        socketCliente = new DatagramSocket();
        tecladoUsuario = new BufferedReader(new InputStreamReader(System.in));
    }

    public static void main(String[] args) throws IOException, Exception {
        //Thread para receber os comandos
        Thread commandThread = new Thread(new Runnable() {

            @Override
            public void run() {

                System.out.println("Cliente Iniciado, esperando por mensagem:");

                while (true) {
                    try {
                        receiveData = new byte[1400];
                        sendData = new byte[1400];

                        System.out.print("> ");
                        String sentence = tecladoUsuario.readLine();
                        sendData = sentence.getBytes(); // Pegando tamanho da String

                        //Enviando o pacote de datagrama para o servidor
                        DatagramPacket outDatagram = new DatagramPacket(sendData, sendData.length, enderecoIP, port);
                        socketCliente.send(outDatagram);

                    } catch (IOException e) {
                        System.out.println("Excessão causada: " + e.getLocalizedMessage());
                    }
                }
            }

        });

        //Thread que exibir resultados;
        Thread resultThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {

                    try {
                        //recebendo o pacote datagrama do servidor
                        DatagramPacket inDatagram = new DatagramPacket(receiveData, receiveData.length);
                        socketCliente.receive(inDatagram);

                        //imprimindo mensagem recebida do servidor;
                        String modifiedSentence = new String(inDatagram.getData());
                        System.out.println("Servidor >" + modifiedSentence);

                        Thread.sleep(800);
                    } catch (InterruptedException ex) {
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        });
        
        
        commandThread.start();
        resultThread.start();
        // Close o socket do cliente
        shutdown();

    }

    static void shutdown() {
        socketCliente.close();
    }

}
