/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author TUNG
 */
import java.net.Socket;
import java.awt.*;
import javax.swing.*;
import java.io.*;

public class MessageGUI extends javax.swing.JFrame {

    /**
     * Creates new form MessageGUI
     */
    Wait4Message wait;
    DataInputStream receive;
    DataOutputStream send;
    DefaultListModel<String> ChatHistory;
    JList ChatTable;
    String user;
    Socket socket;
    public MessageGUI(String host,int port,String user,String thisuser,ClientMain main) {
        initComponents();
        try{
            this.socket = new Socket(host,port);
            this.receive = new DataInputStream(socket.getInputStream());
            this.send = new DataOutputStream(socket.getOutputStream());
            this.send.writeUTF(":" +thisuser);
            this.send.flush();
        }catch(Exception e){
            this.setVisible(false);
            this.dispose();
            JOptionPane.showMessageDialog(this, "Cannot connect to this user");
            return;
        }
        this.wait = new Wait4Message();
        wait.start();
        this.user = user;
        this.ChatHistory = new DefaultListModel();
        this.ChatTable = new JList();
        this.jScrollPane1.setViewportView(ChatTable);
        this.jLabel1.setText("Chatting with "+user);
        setLocationRelativeTo(main);
    }   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        SendFileButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setText("Send");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Left chat");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        SendFileButton.setText("Send File");
        SendFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SendFileButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton2)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(SendFileButton))))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(8, 8, 8)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(SendFileButton))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String message = this.jTextField1.getText();
        if(message.equals("")){
            JOptionPane.showMessageDialog(this,"Type something");
            return;
        }
        try{
            this.send.writeUTF(message);
        }catch(Exception e){
            JOptionPane.showMessageDialog(this,"Cannot send");
        }
        this.ChatHistory.addElement("You: " + message);
        this.updateChatTable();
        this.jTextField1.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        try{
            this.send.writeUTF("close");
            this.close();
            this.wait.stop();
            this.send.flush();
            this.dispose();
            this.setVisible(false);
        }catch(Exception e){
            
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void SendFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SendFileButtonActionPerformed
        // TODO add your handling code here:
        JFileChooser FileChooser = new JFileChooser();
        FileChooser.showOpenDialog(this);
        File selected = FileChooser.getSelectedFile();
        String name = selected.getName();
        try{
            Long FileSize = selected.length();
            byte[] fileContentBytes = new byte[(int)selected.length()];
            this.send.writeUTF(" SEND_FILE: "+name+":"+FileSize);
            this.send.flush();
            FileInputStream readFile = new FileInputStream(selected);
            readFile.read(fileContentBytes);
            readFile.close();
            this.send.write(fileContentBytes);
            this.send.flush();
            JOptionPane.showMessageDialog(this, "Send success");
        }catch(Exception e){
            
        }
    }//GEN-LAST:event_SendFileButtonActionPerformed
    
    void updateChatTable(){
        this.ChatTable.setModel(ChatHistory);
        this.jScrollPane1.setViewportView(this.ChatTable);
        JScrollBar vertical = this.jScrollPane1.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }
    /**
     * @param args the command line arguments
     */
    class Wait4Message extends Thread{
        public void run(){
            while(true){
                try{
                    String response = MessageGUI.this.receive.readUTF();
                    if(response.contains("SEND_FILE:")){
                        String[] split = response.split(":");
                        String fileName = split[1];
                        String fileSize = split[2];
                        byte[] FileContent = new byte[Integer.parseInt(fileSize)];
                        MessageGUI.this.receive.readFully(FileContent);
                        new File("D:\\ClientApp\\").mkdir();
                        new File("D:\\ClientApp\\"+MessageGUI.this.user).mkdir();
                        String pathSave = new String("D:\\ClientApp\\"+MessageGUI.this.user);
                        File tempFile = new File(pathSave+"\\"+fileName);
                        boolean exists = tempFile.exists();
                        if(exists){
                            int ret = JOptionPane.showConfirmDialog(MessageGUI.this,MessageGUI.this.user+" sent you a file.\n But this file existed.\n Do you want to over write it?","Confirm",0);
                            if(ret !=0) continue;
                        }
                        FileWriter writer = new FileWriter(pathSave+"\\"+fileName,false);
                        writer.close();
                        FileOutputStream getContent = new FileOutputStream(pathSave+"\\"+fileName);
                        getContent.write(FileContent);
                        getContent.close();
                        JOptionPane.showMessageDialog(MessageGUI.this,MessageGUI.this.user+" sent you a file. Save in: "+pathSave);
                        MessageGUI.this.ChatHistory.addElement(MessageGUI.this.user+" sent you a File: "+fileName);
                        MessageGUI.this.updateChatTable();
                        continue;
                    }
                    if(response.equals("close")){
                        JOptionPane.showMessageDialog(MessageGUI.this,MessageGUI.this.user+ " has left the chat");
                        MessageGUI.this.close();
                        MessageGUI.this.setVisible(false);
                        MessageGUI.this.dispose();
                    }
                    MessageGUI.this.ChatHistory.addElement(MessageGUI.this.user+": "+response);
                    MessageGUI.this.updateChatTable();
                }catch(Exception e){
                    
                }
            }
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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton SendFileButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}