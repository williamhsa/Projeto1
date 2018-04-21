package com.sd.projeto1.main;

import com.sd.projeto1.dao.MapaDao;
import static com.sd.projeto1.main.Client.send;
import com.sd.projeto1.model.Mapa;
import com.sd.projeto1.util.PropertyManagement;
import com.sd.projeto1.util.Utilidades;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.SerializationUtils;

public class Server implements Runnable{
    
    private static Queue<DatagramPacket> comandos = new LinkedList<>();
   
    private static Map<BigInteger, String> mapa = new HashMap();
    
    private static MapaDao mapaDAO;
    
    private DatagramSocket socketServidor;

    private PropertyManagement pm;
    
    private byte[] in;
    private byte[] out;
   

    public Server() throws SocketException{
        this.pm = new PropertyManagement();
        this.socketServidor = new DatagramSocket(pm.getPort());
        this.mapaDAO = new MapaDao();
        this.in = new byte[1400];
        this.out = new byte[1400];
        
    }
    
    public static void main(String[] args) throws SocketException{
        System.out.println("Servidor Iniciado...");
        new Thread(new Server()).start();
    }	
    
    public DatagramPacket receive() throws IOException{
       
        DatagramPacket receivePacket = new DatagramPacket(in, in.length);
        socketServidor.receive(receivePacket);
        
        return receivePacket;
    }
    
    public void send(DatagramPacket receivePacket, String msg) throws IOException{
        
        InetAddress IPAddress = receivePacket.getAddress();
        int port = receivePacket.getPort();
        byte mensagem[] = new byte[1400];   
                
        mensagem = msg.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(mensagem, mensagem.length, IPAddress, port);
        
        socketServidor.send(sendPacket);
    }
    
    public void run() {
        while(true){
            try {
               
                DatagramPacket receivedPacket = receive();

                Mapa maparetorno = (Mapa) SerializationUtils.deserialize(receivedPacket.getData());

                tipoOperacao(maparetorno, receivedPacket);
               
        } catch (IOException e) {
            System.out.println("Excessão causada: " + e.getLocalizedMessage());
        }   catch (Exception ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }

    }
    }
    
    public static void salvar(Mapa mapa1){
        BigInteger chave = new BigInteger(String.valueOf(mapa1.getChave()));
        
        mapa.put(chave, mapa1.getTexto());
    }
    
    public static void imprimeCRUD(Mapa mapa1){
        System.out.println("\n===============================");
        System.out.println("Chave: " + mapa1.getChave());
        System.out.println("Texto: " + mapa1.getTexto());
        System.out.println("Tipo de Operaçao: " + Utilidades.retornaTipoOperacao(mapa1.getTipoOperacaoId()));
        System.out.println("Data: " + mapa1.getData());
        System.out.println("Tamanho da fila: " + mapa.size());
        System.out.println("===============================");
    }
 
    public void tipoOperacao(Mapa mapaEntity, DatagramPacket receivedPacket) throws Exception{
        
        switch(mapaEntity.getTipoOperacaoId()){
            case 1:
                Mapa mi = mapaDAO.salvar(mapaEntity);
                if(mi != null){
                    send(receivedPacket, "Inserido com Sucesso!");
                    salvar(mi);
                    imprimeCRUD(mi);
                }else{
                    send(receivedPacket, "Erro ao inserir!");
                }
                break;
            case 2:
                Mapa ma = mapaDAO.editar(mapaEntity);
                if(ma != null){
                    send(receivedPacket, "Atualizado com Sucesso!");
                    salvar(ma);
                    imprimeCRUD(ma);
                }else{
                    send(receivedPacket, "Erro ao atualizar!");
                }
                break;
            case 3:
//                if(mapaDAO.excluir(mapaEntity.getChave()) > 0){
//                    send(receivedPacket, "Excluido com Sucesso!");
//                }else{
//                    send(receivedPacket, "Erro ao excluir!");
//                }
//                break;
            case 4:
//               Mapa m = mapaDAO.buscarPorId(mapaEntity.getChave());
//               
//               String retornoMsg = "Chave: " + m.getChave() + "\n" +
//                       "Texto: " + m.getTexto()+ "\n" +
//                       "Tipo da operação: " + Utilidades.retornaTipoOperacao(m.getTipoOperacaoId()) + "\n" +
//                       "Chave: " + m.getChave() + "\n";
//                   
//                send(receivedPacket, retornoMsg);
                
                break;
        }
    } 
}
