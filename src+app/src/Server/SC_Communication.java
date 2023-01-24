/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author TUNG
 */
import java.net.Socket;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;


public class SC_Communication extends Thread{
        public static ArrayList<String> ClientList = new ArrayList<String>();
        public static ArrayList<Integer> ClientPortList = new ArrayList<Integer>();
        public static ArrayList<String> OnlineUser = new ArrayList<String>();
        public static ArrayList<String> userIPs = new ArrayList<String>();
        public static ArrayList<SC_Communication> Online = new ArrayList<SC_Communication>();
        DataInputStream receive;
        public DataOutputStream send;
        Socket socket;
        int index;
        public SC_Communication(Socket socket){
            this.socket = socket;
            try{
                this.receive = new DataInputStream(this.socket.getInputStream());
                this.send = new DataOutputStream(this.socket.getOutputStream());
            }catch(Exception e){
                System.out.println("Failed to create server");
            }
        }
        
        public void run(){        
            while(socket.isConnected()){
                try{

                    // Nhận gói tin báo là đăng nhập từ người dùng
                    String message = receive.readUTF();
                    System.out.println(message);
                    if(message.contains("login")){
                        String username = message.split(":")[1];
                        if((this.index = SC_Communication.ClientList.indexOf(username)) ==-1){
                            this.send.writeUTF("Not exist");
                            this.send.flush();
                            this.close();
                            return;
                        }
                        SC_Communication.OnlineUser.add(SC_Communication.ClientList.get(index));
                        SC_Communication.Online.add(this);
                        this.send.writeUTF("");

                        // Cập nhật trạng thái của người dùng thành online
                        Iterator<ArrayList<String>> iter = ServerHome.dummyDB.iterator();
                        while (iter.hasNext()) {
                            ArrayList<String> tList = iter.next();
                            if (SC_Communication.ClientList.get(index) == tList.get(0)) {
                                tList.set(3, "online");
                                break;
                            }
                        }                        

                        continue;
                    }

                    // Nhận gói tin báo là đăng ký từ người dùng
                    if(message.contains("Reg")){
                        String[] split = message.split(":");
                        String username = split[1];
                        Integer clientport = Integer.valueOf(split[2]);
                        String ip = split[3]; 
                        if(SC_Communication.ClientList.indexOf(username)!=-1){
                            this.send.writeUTF("Duplicate name");
                            this.send.flush();
                            this.close();
                            return;
                        }
                        SC_Communication.ClientList.add(username);
                        SC_Communication.ClientPortList.add(clientport);
                        SC_Communication.userIPs.add(ip);
                        this.send.writeUTF("");
                        this.close();
                        // Cập nhật bảng dummyDB sau khi có người dùng đăng ký
                        ArrayList<String> newDBEntry = new ArrayList<String>();
                        newDBEntry.add(username);
                        newDBEntry.add(ip);
                        newDBEntry.add(String.valueOf(clientport));
                        newDBEntry.add("offline");
                        ServerHome.dummyDB.add(newDBEntry);
                        return;
                    }

                    // Nhận gói tin yêu cầu IP và port của người dùng khác
                    if(message.contains("GetIPandPort")){
                        boolean add = false;
                        if(message.contains("Add")){
                            add= true;
                        }
                        String name = message.split(":")[1];
                        int i;
                        if(SC_Communication.OnlineUser.indexOf(name) == -1 ){
                            this.send.writeUTF("Response4getIPandPort:Not found");
                            this.send.flush();
                            continue;
                        }
                        i = SC_Communication.ClientList.indexOf(name);
                        if(add){
                            String mes = new String("Response4getIPandPort:"+SC_Communication.userIPs.get(i)+":"+ SC_Communication.ClientPortList.get(i)+":"+SC_Communication.ClientList.get(i)+":Add:");
                            this.send.writeUTF(mes);
                            this.send.flush();
                            continue;
                        }
                        this.send.writeUTF("Response4getIPandPort:"+SC_Communication.userIPs.get(i)+":"+ SC_Communication.ClientPortList.get(i)+":"+SC_Communication.OnlineUser.get(i));
                        System.out.println(SC_Communication.userIPs.get(i)+":"+ SC_Communication.ClientPortList.get(i));
                        this.send.flush();
                        continue;
                    }

                    // Nhận gói tin báo client đã logout
                    if(message.equals("close")){
                        System.out.println("A client has logged out");
                        SC_Communication.OnlineUser.remove(SC_Communication.ClientList.get(this.index));

                        // Cập nhật trạng thái của người dùng thành offline
                        Iterator<ArrayList<String>> iter = ServerHome.dummyDB.iterator();
                        while (iter.hasNext()) {
                            ArrayList<String> tList = iter.next();
                            if (SC_Communication.ClientList.get(index) == tList.get(0)) {
                                tList.set(3, "offline");
                                break;
                            }
                        }  

                        this.close();
                        return;
                    }
                }catch(Exception e){
                    System.out.println("Failed to connect");
                }
            }
            
        }
        
        public void close(){
            try{
                this.socket.close();
                this.send.close();
                this.receive.close();
            }catch(Exception e){}
            
        }
}
