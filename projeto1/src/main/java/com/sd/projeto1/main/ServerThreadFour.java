/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd.projeto1.main;

import com.sd.projeto1.dao.MapaDao;
import com.sd.projeto1.model.MapaDTO;
import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.SerializationUtils;

/**
 *
 * @author willi
 */
public class ServerThreadFour implements Runnable {

    private DatagramPacket receivedPacket;
    private DatagramSocket serverSocket;
    private MapaDTO mapaDTO;
    private static Map<BigInteger, String> mapa = new HashMap();  
   
    private MapaDao mapaDAO = new MapaDao();
    
    public ServerThreadFour(MapaDTO mapaDTO, DatagramPacket receivePacket, DatagramSocket serverSocket){
        this.mapaDTO = mapaDTO;
        this.receivedPacket = receivePacket;
        this.serverSocket = serverSocket;
    }
    
    @Override
    public void run() {
        try {
            send(receivedPacket, mapaDTO);
        } catch (Exception ex) {
            Logger.getLogger(ServerThreadTwo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void send(DatagramPacket receivePacket, MapaDTO mapaDTO) throws IOException{
        
        InetAddress IPAddress = receivePacket.getAddress();
        int port = receivePacket.getPort();
        byte mensagem[] = new byte[1400];   
                
        mensagem = SerializationUtils.serialize(mapaDTO);

        DatagramPacket sendPacket = new DatagramPacket(mensagem, mensagem.length, IPAddress, port);
        
        serverSocket.send(sendPacket);
    }

}
