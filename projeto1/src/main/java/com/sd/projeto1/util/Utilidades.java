package com.sd.projeto1.util;

import com.sd.projeto1.model.TipoOperacao;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class Utilidades {
    
    public static String CAMINHO_CONEXAO = "src/main/java/com/sd/projeto1/util/conexao.txt";
    
    public static boolean validaInstrucao(String instrucao){
        
        String[] input = instrucao.split(";");
        
        if(input.length != 2)
            return false;
        
        switch(input[1].toUpperCase().trim()){
            case "INSERIR":
            case "ATUALIZAR":
            case "EXCLUIR":
            case "LISTAR":
            case "SAIR":
               return true; 
                
            default:
                return false;
        }
    }
    
    
    public static Entry<Integer, String> retornaInstrucao(String instrucao){
        
        String[] input = instrucao.split(";");
        Map<Integer, String> retorno = new HashMap<>();
        
        switch(input[1].toUpperCase().trim()){
            case "INSERIR":
                retorno.put(TipoOperacao.CREATE, input[0]);
                break;
            case "ATUALIZAR":
                retorno.put(TipoOperacao.UPDATE, input[0]);
                break;
            case "EXCLUIR":
                retorno.put(TipoOperacao.DELETE, input[0]);
                break;
            case "LISTAR":
                retorno.put(TipoOperacao.SEARCH, input[0]);
                break;
            case "SAIR":
                retorno.put(TipoOperacao.LOGOUT, input[0]);
                break;
        }
         return retorno.entrySet().iterator().next();
    }
}
