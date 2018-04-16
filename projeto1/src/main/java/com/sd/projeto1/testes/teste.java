
package com.sd.projeto1.testes;

import com.sd.projeto1.dao.MapaDao;
import com.sd.projeto1.model.Mapa;

public class teste {
  
    
    public static void main(String[] args) throws Exception {
        MapaDao mapa = new MapaDao();
        
        System.out.println("Buscar Todos:");
        for(Mapa m: mapa.buscarTodos()){
            System.out.print(m.getChave() + " ");
            System.out.print(m.getTexto() + " ");
            System.out.print(m.getData() + " ");
            System.out.print(m.getTipoOperacaoId() + "\n\n");
        }
        System.out.println("\nBuscar por Id: \n");
        Mapa mapa2 = mapa.buscarPorId(1);
        
        System.out.print(mapa2.getChave() + " ");
        System.out.print(mapa2.getTexto() + " ");
        System.out.print(mapa2.getData() + " ");
        System.out.print(mapa2.getTipoOperacaoId() + "\n\n");
        
        
//        Mapa mapa3 = new Mapa();
//        mapa3.setTexto("Novo Registro:");
//        mapa3.setTipoOperacaoId(1);
//     
//        mapa.salvar(mapa3);
//        
//        
//        Mapa mapa4 = new Mapa();
//        mapa4.setChave(9);
//        mapa4.setTexto("Registro Alterado");
//        
//        mapa.editar(mapa4);
//        
//        mapa.excluir(1);
//        
        
        
    }
}
