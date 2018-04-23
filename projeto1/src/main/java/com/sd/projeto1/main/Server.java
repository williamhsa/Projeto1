package com.sd.projeto1.main;

import com.sd.projeto1.dao.MapaDao;
import com.sd.projeto1.model.Mapa;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Server{
    
    private static MapaDao mapaDAO = new MapaDao();
    
    public static void main(String[] args) throws Exception {
        List<Mapa> logs = new ArrayList<Mapa>();
        
        logs = mapaDAO.buscarTodos();
        
        for(Mapa m: logs){
            BigInteger chave = new BigInteger(String.valueOf(m.getChave()));

            ServerThreadDisco.mapa.put(chave, m.getTexto());
        }
        
        System.out.println("Log do Disco Recuperado");
        System.out.println("Tamanho da Fila: " + ServerThreadDisco.mapa.size() + "\n");
        
        
        System.out.println("Servidor Iniciado...");
        new Thread(new ServerThreadReceive()).start();
    }
   
}
