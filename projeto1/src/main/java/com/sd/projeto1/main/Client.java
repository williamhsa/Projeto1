package com.sd.projeto1.main;


import com.sd.projeto1.util.ChatMessage;
import com.sd.projeto1.util.LeitorArquivo;
import com.sd.projeto1.util.Utilidades;
import java.net.*;
import java.io.*;
import java.util.*;


//The Client that can be run as a console
public class Client  {
	
	// notificação
	private String notif = " *** ";

	private ObjectInputStream sInput;	 // ler do socket
	private ObjectOutputStream sOutput;      // escrever no socket
	private Socket socket;
	
	private String server, username;	// servidor e usuário
	private int port;		        //porta

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

        
	Client(String server, int port, String username) {
		this.server = server;
		this.port = port;
		this.username = username;
	}
	
	/*
	 * To start the chat
	 */
	public boolean start() {
		// try to connect to the server
		try {
			socket = new Socket(server, port);
		} 
		// exception handler if it failed
		catch(Exception ec) {
			display("Erro ao conectar no servidor:" + ec);
			return false;
		}
		
		String msg = "Conexão aceita " + socket.getInetAddress() + ":" + socket.getPort();
		display(msg);
	
		/* Creating both Data Stream */
		try
		{
			sInput  = new ObjectInputStream(socket.getInputStream());
			sOutput = new ObjectOutputStream(socket.getOutputStream());
		}
		catch (IOException eIO) {
			display("Excessão ao criar novos Input/output Streams: " + eIO);
			return false;
		}

		// creates the Thread to listen from the server 
		new ListenFromServer().start();
		// Send our username to the server this is the only message that we
		// will send as a String. All other messages will be ChatMessage objects
		try
		{
			sOutput.writeObject(username);
		}
		catch (IOException eIO) {
			display("Excessão fazendo login : " + eIO);
			disconnect();
			return false;
		}
		// success we inform the caller that it worked
		return true;
	}

	/*
	 * To send a message to the console
	 */
	private void display(String msg) {

		System.out.println(msg);
		
	}
	
	/*
	 * To send a message to the server
	 */
	void sendMessage(ChatMessage msg) {
		try {
			sOutput.writeObject(msg);
		}
		catch(IOException e) {
			display("Excessão escrevendo para o servidor: " + e);
		}
	}

	/*
	 * When something goes wrong
	 * Close the Input/Output streams and disconnect
	 */
	private void disconnect() {
		try { 
			if(sInput != null) sInput.close();
		}
		catch(Exception e) {}
		try {
			if(sOutput != null) sOutput.close();
		}
		catch(Exception e) {}
        try{
			if(socket != null) socket.close();
		}
		catch(Exception e) {}
			
	}
	/*
	 * To start the Client in console mode use one of the following command
	 * > java Client
	 * > java Client username
	 * > java Client username portNumber
	 * > java Client username portNumber serverAddress
	 * at the console prompt
	 * If the portNumber is not specified 1500 is used
	 * If the serverAddress is not specified "localHost" is used
	 * If the username is not specified "Anonymous" is used
	 */
	public static void main(String[] args) throws IOException {
		// default values if not entered
		
                Map.Entry<String, Integer> conexao = LeitorArquivo.lerArquivo(Utilidades.CAMINHO_CONEXAO);
                
		String userName = "Anonymous";
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Digite o username: ");
		userName = scan.nextLine();

		
		// create the Client object
		Client client = new Client(conexao.getKey(), conexao.getValue(), userName);
		// try to connect to the server and return if not connected
		if(!client.start())
			return;
		
		System.out.println("Instruções:");
		System.out.println("1. Enviar mensagem para todos os clientes!");
		System.out.println("2. Digite '@username<space>yourmessage' sem aspas para enviar mensagem ao designado cliente!");
		System.out.println("3. Digite 'WHOISIN' sem aspas para listar todos os clientes ativos!");
		System.out.println("4. Digite 'LOGOUT' sem aspas para sair do servidor!");
		
		// infinite loop to get the input from the user
		while(true) {
			System.out.print("> ");
			// read message from user
			String msg = scan.nextLine();
			// logout if message is LOGOUT
			if(msg.equalsIgnoreCase("LOGOUT")) {
				client.sendMessage(new ChatMessage(ChatMessage.LOGOUT, ""));
				break;
			}
			// message to check who are present in chatroom
			else if(msg.equalsIgnoreCase("WHOISIN")) {
				client.sendMessage(new ChatMessage(ChatMessage.WHOISIN, ""));				
			}
			// regular text message
			else {
				client.sendMessage(new ChatMessage(ChatMessage.MESSAGE, msg));
			}
		}
		// close resource
		scan.close();
		// client completed its job. disconnect client.
		client.disconnect();	
	}

	/*
	 * a class that waits for the message from the server
	 */
	class ListenFromServer extends Thread {

		public void run() {
			while(true) {
				try {
					// read the message form the input datastream
					String msg = (String) sInput.readObject();
					// print the message
					System.out.println(msg);
					System.out.print("> ");
				}
				catch(IOException e) {
					display(notif + "Server fechado para a conexão: " + e + notif);
					break;
				}
				catch(ClassNotFoundException e2) {
				}
			}
		}
	}
}

