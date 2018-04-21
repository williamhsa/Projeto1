package com.sd.projeto1.model;

import java.io.Serializable;
import java.util.Date;


public class Mapa implements Serializable{
 
    private int chave;
    private String texto;
    private int tipoOperacaoId;
    private Date data;
    
    public int getChave() {
        return chave;
    }
    public void setChave(int chave) {
        this.chave = chave;
    }

    public String getTexto() {
        return texto;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getTipoOperacaoId() {
        return tipoOperacaoId;
    }
    public void setTipoOperacaoId(int tipoOperacaoId) {
        this.tipoOperacaoId = tipoOperacaoId;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
    
    
}
