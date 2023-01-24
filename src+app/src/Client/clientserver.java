/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author TUNG
 */
import java.net.ServerSocket;
import java.net.Socket;
public class clientserver extends Thread {
    ServerSocket server;
    Client client;
    clientserver(int port,Client client){
        try{
            this.client = client;
            this.server = new ServerSocket(port);
        }catch(Exception e){
            
        }
    }
    
    public void run(){
        while(true){
            try{
                   Socket socket = server.accept();
                   System.out.println("A Friend has connected to you");
                   CC_Communication Handler = new CC_Communication(socket,this.client);
                   Handler.start();
            }catch(Exception e){
                System.out.println("Error!!");
                return;
            } 
        }
    }
}
