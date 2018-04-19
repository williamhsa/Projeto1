package com.sd.projeto1.main;

import com.sd.projeto1.dao.MapaDao;
import com.sd.projeto1.model.Mapa;
import com.sd.projeto1.util.PropertyManagement;
import java.net.*;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class Client implements Runnable {

    private InetAddress enderecoIP;
    private int port; //porta UDP
    private BufferedReader tecladoUsuario;
    private DatagramSocket socketCliente;

    private byte[] outData;
    private byte[] inData;

    PropertyManagement pm = new PropertyManagement();

    Client() throws SocketException, UnknownHostException {
        this.enderecoIP = InetAddress.getByName(pm.getAddress());
        this.port = pm.getPort();
        socketCliente = new DatagramSocket();
        tecladoUsuario = new BufferedReader(new InputStreamReader(System.in));
    }

    public static void main(String[] args) throws IOException, Exception {
        new Thread(new Client()).start();

    }

    @Override
    public void run() {

        System.out.println("Cliente Iniciado, esperando por mensagem:");

        while (true) {
            try {
                inData = new byte[1400];
                outData = new byte[1400];

                System.out.print("> ");
                String sentence = tecladoUsuario.readLine();
                outData = sentence.getBytes(); // Pegando tamanho da String
                
                //Enviando o pacote de datagrama para o servidor
                DatagramPacket outDatagram = new DatagramPacket(outData, outData.length, this.enderecoIP, this.port);
                socketCliente.send(outDatagram);

                //recebendo o pacote datagrama do servidor
                DatagramPacket inDatagram = new DatagramPacket(inData, inData.length);
                socketCliente.receive(inDatagram);

                //imprimindo mensagem recebida do servidor;
                String modifiedSentence = new String(inDatagram.getData());
                System.out.println("Servidor >" + modifiedSentence);
                
                // Close o socket do cliente
                shutdown();

            } catch (IOException e) {
                System.out.println("Excessão causada: " + e.getLocalizedMessage());
            }
        }
    }

    private void shutdown() {
        socketCliente.close();
    }

}
