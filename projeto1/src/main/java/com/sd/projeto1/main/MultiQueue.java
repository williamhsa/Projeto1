/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd.projeto1.main;

import java.net.DatagramPacket;
import java.util.LinkedList;
import java.util.Queue;

public class MultiQueue {

    static Queue<DatagramPacket> requisicoes = new LinkedList<>();
    static Queue<DatagramPacket> disco = new LinkedList<>();
    static Queue<DatagramPacket> processamento = new LinkedList<>();
    
    static int teste = 2;
    
    MultiQueue() {
    }
    
    public static void push(DatagramPacket requisicao){
        requisicoes.offer(requisicao);
    }
    
    public static DatagramPacket pullPacket(){
        return requisicoes.poll();
    }
    
   
}
