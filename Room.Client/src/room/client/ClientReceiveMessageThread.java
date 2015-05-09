/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package room.client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.event.EventListenerList;
import org.json.simple.parser.ParseException;
import room.ddl.CommunicationInfo;
import room.ddl.Packet;

/**
 *
 * @author Sylvain
 */
public class ClientReceiveMessageThread {

    private final Thread listenerThread;
//    private final EventListenerList listeners;

    public ClientReceiveMessageThread(CommunicationInfo info, ReceiveMessageListener method) {
//        listeners = new EventListenerList();
        listenerThread = new Thread(() -> {
            ServerSocket serverSocker = null;

            try {
                serverSocker = new ServerSocket(info.getPort());

                do {
                    Socket socket = serverSocker.accept();

                    new Thread(() -> {
                        try {

                            BufferedReader socketIn;
                            socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                            Packet incomingMessage = new Packet(socketIn.readLine());
                            method.MessageReceived(incomingMessage);
//                            notifyListeners(incomingMessage);
                        } catch (ParseException | IOException e) {
                        }
                    }).start();
                } while (true);

            } catch (Exception e) {
                System.err.println("Exception côté serveur - " + e.getMessage());
            } finally {
                try {
                    //On ferme tous les ports, quoi qu'il advienne
                    if (serverSocker != null) {
                        serverSocker.close();
                    }
                } catch (IOException e) {
                }
            }
        });
    }

    public void Stop() {
        listenerThread.interrupt();
    }

//    public void AddEventListener(ReceiveMessageListener listener) {
//        listeners.add(ReceiveMessageListener.class, listener);
//    }
//
//    public void RemoveEventListener(ReceiveMessageListener listener) {
//        listeners.remove(ReceiveMessageListener.class, listener);
//    }
//
//    private void notifyListeners(Packet packet) {
//        ReceiveMessageListener[] temp = listeners.getListeners(ReceiveMessageListener.class);
//
//        for (ReceiveMessageListener listener : temp) {
//            listener.MessageReceived(packet);
//        }
//    }
}
