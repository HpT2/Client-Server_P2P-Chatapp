/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author TUNG
 */

//-----------------------------------MODEL------------------------------
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.DefaultListModel;
public class Client {
           String username;
           DefaultListModel<String> Friends;
           String hostip;
           int serverport;
           Socket socket;
           clientserver server;
           ClientMain main = null;
           Client(String name,int clientport,Socket socket){
               try{
                    server = new clientserver(clientport,this);
                    this.socket = socket;
                    this.username = name;
                    this.serverport = clientport;
                    this.Friends = new DefaultListModel<>();
                    
               }catch(Exception e){
                   System.out.println("Cannot create client");
               }
               server.start();
           }
}