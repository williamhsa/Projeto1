package com.sd.projeto1.main;

import com.sd.projeto1.dao.MapaDao;
import com.sd.projeto1.model.Mapa;
import com.sd.projeto1.model.MapaDTO;
import com.sd.projeto1.util.PropertyManagement;
import com.sd.projeto1.util.Utilidades;
import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.SerializationUtils;

public class ServerThreadOne implements Runnable {

  
   // private static Map<BigInteger, String> mapa = new HashMap();
    private static DatagramSocket socketServidor;
    private static PropertyManagement pm;
    private static byte[] in;
    //private MapaDao mapaDAO = new MapaDao();
    private ExecutorService executor;

    @Override
    public void run() {
        try {
            executor = Executors.newCachedThreadPool();
            pm = new PropertyManagement();
            socketServidor = new DatagramSocket(pm.getPort());

            while (true) {
                in = new byte[1400];
                DatagramPacket receivedPacket = receive();
                
               MultiQueue.setComandoFila(receivedPacket); // Colocando na F1
                
               ServerThreadTwo serverSend = new ServerThreadTwo(socketServidor);

                if (serverSend != null) {
                    executor.execute(serverSend);
     
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static DatagramPacket receive() throws IOException {

        DatagramPacket receivePacket = new DatagramPacket(in, in.length);
        socketServidor.receive(receivePacket);

        return receivePacket;
    }

}
