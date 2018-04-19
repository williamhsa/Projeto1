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
    private static InetAddress IPAddress;
    private static int port;

    private static byte[] receiveData = new byte[1400];
    private static byte[] sendData = new byte[1400];

    private static final Map<BigInteger, String> map = new HashMap();
    private static final Queue<DatagramPacket> comandosRecebidos = new LinkedList<>();
    private static final Queue<DatagramPacket> disco = new LinkedList<>();
    private static final Queue<DatagramPacket> processamento = new LinkedList<>();

    private final PropertyManagement pm = new PropertyManagement();

    public static void main(String[] args) throws SocketException, InterruptedException {
        System.out.println("Servidor Iniciado...");
        socketServidor = new DatagramSocket();
        //Thread para receber datagran e colocar na fila
        Thread receiveThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        DatagramPacket pacoteRecebido = pacoteRecebido();
                        comandosRecebidos.offer(pacoteRecebido);

                        String texto = new String(receiveData, 0, pacoteRecebido.getLength());
                        sendData = texto.toUpperCase().getBytes();
                        System.out.println("Mensagem Recebida: " + texto);

                        enviarPacote(pacoteRecebido, texto);

                        // Thread.sleep(300);
                    } catch (IOException e) {
                        System.out.println("Excessão causada: " + e.getLocalizedMessage());
                        // } catch (InterruptedException ex) {
                        //  break;
                    }

                }
            }

        });

        /*//Thread que vai consumir do fila e responder para cliente;
        Thread consumeThread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {

                    processamentoComandos();
                    
                    //Thread.sleep(800);
                    //} catch (InterruptedException ex) {
                    //  break;
                }

            }
        });*/
        receiveThread.start();
        receiveThread.join();
        //consumeThread.start();
       // consumeThread.join();

    }

    // Metodo para receber Datagrams
    static DatagramPacket pacoteRecebido() throws IOException {
        // recebendo datagrama do cliente
        DatagramPacket pacoteRecebido = new DatagramPacket(receiveData, receiveData.length);
        socketServidor.receive(pacoteRecebido);
        return pacoteRecebido;
    }

    static void enviarPacote(DatagramPacket pacoteRecebido, String texto) throws IOException {
        // Pegando ip e porta do cliente que enviou o zdatagram
        IPAddress = pacoteRecebido.getAddress();
        port = pacoteRecebido.getPort();

        sendData = texto.getBytes();
        DatagramPacket envioPacote = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        socketServidor.send(envioPacote);

    }
    
    static void processamentoComandos(){
        
    }
}
