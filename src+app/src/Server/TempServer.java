/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author TUNG
 */
//--------------------------------------MODEL--------------------------------------
import java.net.ServerSocket;
import java.net.Socket;


public class TempServer extends Thread {
    ServerSocket server;
    int port;
    TempServer(int port){
        this.port = port;
    }
    public void run(){
        try{
            this.server = new ServerSocket(this.port);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Failed to create server");
            return;
        }
        while(true){
            try{
                   Socket socket = server.accept();
                   System.out.println("A new client connected");
                   SC_Communication SC = new SC_Communication(socket);
                   SC.start();
            }catch(Exception e){
                System.out.println("Error!!");
            } 
        }
    }
}
