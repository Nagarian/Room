/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package room.client.gui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.text.DefaultCaret;
import room.client.ClientReceiveMessageThread;
import room.client.Connector;
import room.client.ReceiveMessageListener;
import room.ddl.Client;
import room.ddl.Packet;
import room.ddl.Room;
import room.ddl.exception.CommunicationException;
import room.ddl.exception.InvalidDataException;

/**
 *
 * @author Sylvain
 */
public class Room_GUI extends javax.swing.JFrame implements ReceiveMessageListener {

    private final Connector connector;
    private Room room;
    private ClientReceiveMessageThread receiveMessageThread;
    
    Room_GUI(Connector connector, String name) {
        initComponents();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        DefaultCaret caret = (DefaultCaret)messageDisplayBox.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        
        this.connector = connector;
        this.room = null;
        
        try {
            this.receiveMessageThread = connector.ConnectToRoom(name, this);
            this.room = receiveMessageThread.getRoom();
            refreshConnectedClients();
        } catch (InvalidDataException ex) {
            Logger.getLogger(Room_GUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Room_GUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.setTitle(name);
    }

    private void refreshConnectedClients() {
        
        DefaultListModel model = new DefaultListModel();

        model.clear();
        for (Client client : this.room.getClients()) {
            model.addElement(client.getPseudo());
        }

        userListPanel.setModel(model);
        userListPanel.doLayout();
    }
    
    @Override
    public void MessageReceived(Packet packet) {                                                  
        SimpleDateFormat simpDate;
        simpDate = new SimpleDateFormat("kk:mm:ss");
        String str = String.format("[%s] - %s : %s\r\n", packet.getUserInfo().getPseudo(), simpDate.format(new Date()), packet.getMessage());
        messageDisplayBox.append(str);
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        messageDisplayBox = new javax.swing.JTextArea();
        messageInputBox = new javax.swing.JTextField();
        sendMessageButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        userListPanel = new javax.swing.JList();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        messageDisplayBox.setEditable(false);
        messageDisplayBox.setColumns(20);
        messageDisplayBox.setLineWrap(true);
        messageDisplayBox.setRows(5);
        jScrollPane1.setViewportView(messageDisplayBox);

        sendMessageButton.setText("Répondre");
        sendMessageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMessageButtonActionPerformed(evt);
            }
        });

        userListPanel.setBackground(new java.awt.Color(240, 240, 240));
        userListPanel.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(userListPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(messageInputBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sendMessageButton))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(messageInputBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sendMessageButton)))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        connector.ExitFromRoom(room);
        receiveMessageThread.Stop();
    }//GEN-LAST:event_formWindowClosing

    private void sendMessageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendMessageButtonActionPerformed
        SimpleDateFormat simpDate;
        simpDate = new SimpleDateFormat("kk:mm:ss");
        String str = String.format("[[Me]] - %s : %s\r\n", simpDate.format(new Date()), messageInputBox.getText());
        messageDisplayBox.append(str);
        
        try {
            connector.SendMessageTo(room, messageInputBox.getText());
            messageInputBox.setText("");
        } catch (CommunicationException | InvalidDataException ex) {
            Logger.getLogger(Room_GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_sendMessageButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea messageDisplayBox;
    private javax.swing.JTextField messageInputBox;
    private javax.swing.JButton sendMessageButton;
    private javax.swing.JList userListPanel;
    // End of variables declaration//GEN-END:variables
}
