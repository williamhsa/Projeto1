/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd.projeto1.main;

import java.net.DatagramPacket;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author willi
 */
public class MultiQueue {

    private static Queue<DatagramPacket> comandos = new LinkedList<>();
    private static Queue<DatagramPacket> discoFila = new LinkedList<>();
    private static Queue<DatagramPacket> processamentoFila = new LinkedList<>();

    public static void setComandoFila(DatagramPacket packet) {
        comandos.offer(packet);
    }

    public static DatagramPacket getComandoFila() {
        return comandos.poll();
    }

    public static void setDiscoFila(DatagramPacket packet) {
        discoFila.offer(packet);
    }

    public static DatagramPacket getDiscoFila() {
        return discoFila.poll();
    }

    public static void setProcessamentoFila(DatagramPacket packet) {
        processamentoFila.offer(packet);
    }

    public static DatagramPacket getProcessamentoFila() {
        return processamentoFila.poll();
    }

}
