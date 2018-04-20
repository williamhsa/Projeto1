package com.sd.projeto1.main;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    public static void main(String[] args) {
        
        ServerThreadReceive threadReceive = new ServerThreadReceive();
        ServerThreadSend threadSend = new ServerThreadSend();
        
    }
}
