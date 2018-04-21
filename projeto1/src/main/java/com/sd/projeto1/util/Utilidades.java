
package com.sd.projeto1.util;


public class Utilidades {
   
    public static final int CREATE = 1;
    public static final int UPDATE = 2;
    public static final int DELETE = 3;
    public static final int SEARCH = 4;

    
    public static String retornaTipoOperacao(int tipo){
        switch(tipo){
            case CREATE:
                return "Inserção";
            case UPDATE:
                return "Atualização";
            case DELETE:
                return "Deleção";
            case SEARCH:
                return "Procura";
            default:
                return "Inválido";
        }
    }
}
