package com.sd.projeto1.main;

import com.sd.projeto1.dao.MapaDao;
import com.sd.projeto1.model.Mapa;
import com.sd.projeto1.model.TipoOperacao;
import com.sd.projeto1.util.PropertyManagement;
import java.io.IOException;
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

public class Server implements Runnable{
    
    private Queue<DatagramPacket> comandos = new LinkedList<>();
    private List<DatagramPacket> logarDisco = new LinkedList<>();
    private List<DatagramPacket> processamento = new LinkedList<>();
    private Map<BigInteger, String> map = new HashMap();
    
    private static MapaDao mapaDao;
    
    private DatagramSocket socketServidor;

    private PropertyManagement pm;
    
    private byte[] in;
    private byte[] out;
   

    public Server() throws SocketException{
        this.pm = new PropertyManagement();
        socketServidor = new DatagramSocket(pm.getPort());
        mapaDao = new MapaDao();
    }
    
    public DatagramPacket receive() throws IOException{
        in = new byte[1400];
        out = new byte[1400];
        
        DatagramPacket receivePacket = new DatagramPacket(in, in.length);
        socketServidor.receive(receivePacket);
        
        return receivePacket;
    }
    
    public void send(DatagramPacket receivePacket) throws IOException{
        
        InetAddress IPAddress = receivePacket.getAddress();
        int port = receivePacket.getPort();
        DatagramPacket sendPacket = new DatagramPacket(in, in.length, IPAddress, port);
        
        socketServidor.send(sendPacket);
    }
    
    public void run() {
        while(true){
            try {
               
                DatagramPacket receivedPacket = receive();

                String text = new String(receivedPacket.getData());
                out = text.toUpperCase().getBytes();
                System.out.println("Mensagem Recebida: " + text);

                send(receivedPacket);
                
            } catch (IOException e) {
                System.out.println("Excessão causada: " + e.getLocalizedMessage());
            }

        }
    }
   
     public static void menu() throws Exception{
        int opcao = 0;
        String mensagem;
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("===============================");
        System.out.println("Digite a operação: ");
        System.out.println("1 - Inserir");
        System.out.println("2 - Atualizar");
        System.out.println("3 - Excluir");
        System.out.println("4 - Buscar");
        System.out.println("Opção:");
        
        opcao = scanner.nextInt();
        
        switch(opcao){
            case 1:
                System.out.println("------------------------");
                System.out.println("Opção escolhida: Inserir");
                
                System.out.println("Digite a Mensagem:");
                mensagem = scanner.nextLine();
                scanner.nextLine();
                
                Mapa mapa = new Mapa();
                
                mapa.setTipoOperacaoId(TipoOperacao.CREATE);
                comandos.offer(TipoOperacao.CREATE);
                mapa.setTexto(mensagem);
                
                mapaDao.salvar(mapa);
               
                break;
            case 2:   
                int chave;
                
                System.out.println("------------------------");
                System.out.println("Opção escolhida: Atualizar");
                
                System.out.println("Digite a chave da mensagem que você deseja atualizar:");
                System.out.print(">");
                chave = scanner.nextInt();
                
                Mapa mapa2 = new Mapa();
                
                mapa2 = mapaDao.buscarPorId(chave);
                
                if(mapa2 == null){
                    System.out.println("Mensagem não encontrada");
                    break;
                }
                
                System.out.println("Digite a mensagem: ");
                System.out.print(">");
                mensagem = scanner.nextLine();
                scanner.nextLine();
                
                mapa2.setTipoOperacaoId(TipoOperacao.UPDATE);
                mapa2.setTexto(mensagem);
                
                mapaDao.editar(mapa2);
                
                break;
            case 3:
                break;
            case 4:
                break;
            default:
                System.out.println("Opção Inválida");
                break;
        }
        
    }
    
    public static void main(String[] args) throws SocketException{
            System.out.println("Servidor Iniciado...");
            new Thread(new Server()).start();
    }	
}
