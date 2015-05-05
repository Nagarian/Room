/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package room.server;

import room.ddl.Lounge;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import room.ddl.CommunicationInfo;

/**
 *
 * @author Benoît
 */
public class RoomServer {

    public static final String ERREUR_MESSAGE = "ERROR";
    public static Lounge serverLounge;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ServerSocket serverSocker = null;
        Socket socket = null;
        

        try {
            serverLounge = new Lounge(new CommunicationInfo(InetAddress.getLocalHost().getHostAddress(), 23));

            serverSocker = new ServerSocket(serverLounge.getAddress().getPort());
            System.out.println("Serveur ouvert et à l'écoute...");

            do {
                socket = serverSocker.accept();

                ServerThread srvThr = new ServerThread(socket);
                Thread thr = new Thread(srvThr);
                thr.start();
                System.out.println("Le serveur a ouvert une connexion");
            } while (true);

        } catch (UnknownHostException ex) {
            Logger.getLogger(RoomServer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            System.err.println("Exception côté serveur - " + e.getMessage());
        } finally {
            try {
                //On ferme tous les ports, quoi qu'il advienne
                if (serverSocker != null) {
                    serverSocker.close();
                }
                
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
            }
        }
    }
}
