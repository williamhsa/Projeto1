package com.sd.projeto1.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class LeitorArquivo {
    
    @SuppressWarnings("ConvertToTryWithResources")
    public static Entry<String, Integer> lerArquivo(String arquivo) throws IOException {

        HashMap<String, Integer> conexao = new LinkedHashMap<>();

        // FileReader ler = new FileReader(arquivo);
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), "ISO-8859-1"));
        String input;
        
        input = reader.readLine();
        
        String[] linha = input.split(" ");
        
        conexao.put(linha[0], Integer.parseInt(linha[1]));
     
        reader.close();

        return conexao.entrySet().iterator().next();
    }
}
