/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd.projeto1.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Willian
 */
public class MapaDTO implements Serializable{

    private List<Mapa> mapas;
    private Mapa mapa;
    private String mensagem;
    
    public MapaDTO() {
        mapa = new Mapa();
        mapas = new ArrayList<>();
    }

    public List<Mapa> getMapas() {
        return mapas;
    }
    public void setMapas(List<Mapa> mapas) {
        this.mapas = mapas;
    }
    public Mapa getMapa() {
        return mapa;
    }
    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
    }

    public String getMensagem() {
        return mensagem;
    }
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
 
}
