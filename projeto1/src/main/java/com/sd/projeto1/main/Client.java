package com.sd.projeto1.main;

import com.sd.projeto1.dao.MapaDao;
import com.sd.projeto1.model.Mapa;
import com.sd.projeto1.util.PropertyManagement;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.SerializationUtils;

public class Client{
        private static Queue<DatagramPacket> comandos = new LinkedList<>();
	private static DatagramSocket socketCliente;
	private static InetAddress enderecoIP;
        
        
        static PropertyManagement pm = new PropertyManagement();
	
        public static void main(String[] args) throws SocketException, UnknownHostException{
            
            socketCliente = new DatagramSocket();
            enderecoIP = InetAddress.getByName(pm.getAddress());
            byte[] receiveData = new byte[1400];
            
            ExecutorService executor = Executors.newCachedThreadPool();
            
            Thread receive = new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        while(true){
                            DatagramPacket pacoteRecebido = new DatagramPacket(receiveData, receiveData.length);
                            socketCliente.receive(pacoteRecebido);
                            String msg = new String(pacoteRecebido.getData(), 0, pacoteRecebido.getLength());
                            System.out.println(msg);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            
            });
            
            Thread send = new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        while(true){
                          menu();
                          Thread.sleep(2000);
                        }
                       
                    } catch (IOException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            
            });
            
            executor.execute(receive);
            executor.execute(send);
            
            executor.shutdown();
        }
	
        public static DatagramPacket send(byte[] outData) throws IOException{
            
            DatagramPacket sendPacket = new DatagramPacket(outData, outData.length, enderecoIP, pm.getPort());
            socketCliente.send(sendPacket);
            
            return sendPacket; 
        }
       
        public static DatagramPacket receive(byte[] inData) throws IOException{
       
         DatagramPacket in = new DatagramPacket(inData, inData.length);
         socketCliente.receive(in);
         
         return in;
        }
    
	
	public static void menu() throws Exception{
          
            int opcao = 0, chave;
            String msg;
            BufferedReader mensagem;
            Mapa mapa;
            mensagem = new BufferedReader(new InputStreamReader(System.in));

            Scanner scanner = new Scanner(System.in);

            System.out.println("\n===============================");
            System.out.println("Digite a operação: ");
            System.out.println("1 - Inserir");
            System.out.println("2 - Atualizar");
            System.out.println("3 - Excluir");
            System.out.println("4 - Buscar");
            System.out.println("Opção:");

            opcao = scanner.nextInt();

            switch(opcao){
                case 1:

                System.out.println("Digite a Mensagem:");
                msg = mensagem.readLine();

                mapa = new Mapa();

                mapa.setTipoOperacaoId(1);
                mapa.setTexto(msg);

               
                byte[] object = SerializationUtils.serialize(mapa);

                if(object.length > 1400)
                    System.out.println("Pacote maior que o suportado!");
                else
                    send(object);
                
                break;
                case 2:
                    System.out.println("Digite a chave da mensagem que deseja atualizar:");
                    chave = scanner.nextInt();

                    System.out.println("Digite a Mensagem:");
                    msg = mensagem.readLine();

                    mapa = new Mapa();
                    mapa.setChave(chave);
                    mapa.setTipoOperacaoId(2);
                    mapa.setTexto(msg);

                    
                    byte[] objectUpdate = SerializationUtils.serialize(mapa);

                    if(objectUpdate.length > 1400)
                        System.out.println("Pacote maior que o suportado!");
                    else
                        send(objectUpdate);
                    
                    break;
    //            case 3:
    //                break;
    //            case 4:
    //                break;
            default:
                System.out.println("Opção Inválida");
                break;
            }       
        }  
}