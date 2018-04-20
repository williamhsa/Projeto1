/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd.projeto1.main;

import java.net.DatagramPacket;

/**
 *
 * @author willi
 */
public class ClientThreadReceive extends Thread{
     private DatagramPacket receivePacket;
        
        public ClientThreadReceive(DatagramPacket packet){
            this.receivePacket = packet;
            start();
        }
        
        public void run(){
            
        }
}
