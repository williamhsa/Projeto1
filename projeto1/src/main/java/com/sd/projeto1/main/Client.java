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

    private static PropertyManagement pm = new PropertyManagement();

    private static InetAddress enderecoIP;
    private static int port; //porta UDP
    private static BufferedReader tecladoUsuario;
    private static DatagramSocket socketCliente;   //criação de sockets udp

    private static byte[] sendData = new byte[1400];
    private static byte[] receiveData = new byte[1400];

    public static void main(String[] args) throws IOException, Exception {

        enderecoIP = InetAddress.getByName(pm.getAddress());
        port = pm.getPort();
        socketCliente = new DatagramSocket();
        tecladoUsuario = new BufferedReader(new InputStreamReader(System.in));

//Thread para receber os comandos
        Thread commandThread = new Thread(new Runnable() {

            @Override
            public void run() {

                System.out.println("Cliente Iniciado, esperando por mensagem:");

                while (true) {

                    try {
                        System.out.print("> ");
                        String sentence = tecladoUsuario.readLine();
                        sendData = sentence.getBytes(); // Pegando tamanho da String

                        //Enviando o pacote de datagrama para o servidor
                        DatagramPacket envioPacote = new DatagramPacket(sendData, sendData.length, enderecoIP, port);
                        socketCliente.send(envioPacote);

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
                        DatagramPacket pacoteRecebido = new DatagramPacket(receiveData, receiveData.length);
                        socketCliente.receive(pacoteRecebido);

                        //imprimindo mensagem recebida do servidor;
                        String textoModificado = new String(pacoteRecebido.getData());
                        System.out.println("Servidor >" + textoModificado);

                        //Thread.sleep(800);
                        // } catch (InterruptedException ex) {
                        // break;
                    } catch (IOException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        });

        commandThread.start();
        commandThread.join();
        resultThread.start();
        resultThread.join();
        // Close o socket do cliente

    }

    static void shutdown() {
        socketCliente.close();
    }

}
