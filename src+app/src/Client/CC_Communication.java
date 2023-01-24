/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author TUNG
 */
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import javax.swing.JOptionPane;
public class CC_Communication extends Thread{
    DataInputStream receive;
    DataOutputStream send;
    private Socket socket;
    Client client;
    PopupMes Popup;
    public CC_Communication(Socket socket,Client client){
        this.socket = socket;
        this.client = client;
        try{
            this.receive = new DataInputStream(socket.getInputStream());
            this.send  = new DataOutputStream(socket.getOutputStream());
       }catch(Exception e){
           e.printStackTrace();
       }
    }
    
    public void run(){
        String message ;
        try{
            message = this.receive.readUTF();
            String name = message.split(":")[1];
            if(message.contains("Friend request")){
                   int ret = JOptionPane.showConfirmDialog(this.client.main,"Friend request from "+name,"Friend request",0);
                   if(ret == 0){
                       this.send.writeUTF("Accept");
                       this.send.flush();
                       this.close();
                       this.client.Friends.addElement(name);
                       this.client.main.updateFriendList();
                       return;
                   }
                   this.send.writeUTF("decline");
                   this.send.flush();
                   this.close();
                   return;
            }
            this.Popup = new PopupMes(this,name);
            this.Popup.setVisible(true);
            }catch(Exception e){
                return;
        }  
     while(true) {
      
        }
    }
    
    void close(){
        try{
            this.socket.close();
            this.send.close();
            this.receive.close();
        }catch(Exception e){
            
        }
    }
}
