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

public class Client implements Runnable{
    
	private BufferedReader tecladoUsuario;
	private DatagramSocket socketCliente;
	private InetAddress enderecoIP;
        
        PropertyManagement pm = new PropertyManagement();
	
	private byte[] outData;
        private byte[] inData;

	public Client() throws SocketException, UnknownHostException{
		socketCliente = new DatagramSocket();
		enderecoIP = InetAddress.getByName(pm.getAddress());
		tecladoUsuario = new BufferedReader(new InputStreamReader(System.in));
	}
	
	private void shutdown(){
		socketCliente.close();
	}
	
	public void run() {
	    
            System.out.println("Cliente Iniciado, esperando por mensagem:");
		
            while(true){
            try {
                    inData = new byte[1400];
                    outData = new byte[1400];
                   
                    System.out.print("> ");
                    String sentence = tecladoUsuario.readLine();
                    outData = sentence.getBytes();

                    DatagramPacket out = new DatagramPacket(outData, outData.length, enderecoIP, pm.getPort());
                    socketCliente.send(out);

                    DatagramPacket in = new DatagramPacket(inData, inData.length);
                    socketCliente.receive(in);

                    String modifiedSentence = new String(in.getData());
                    System.out.println("Servidor >" + modifiedSentence);

            } catch (IOException e) {
                System.out.println("Excessão causada: " + e.getLocalizedMessage());
            }
        }
    }
       
    public static void main(String[] args) throws SocketException, UnknownHostException{

            new Thread(new Client()).start();
    }
	
}