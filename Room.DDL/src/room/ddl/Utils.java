/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package room.ddl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import room.ddl.exception.CommunicationException;
import room.ddl.exception.InvalidDataException;

/**
 *
 * @author Benoît
 */
public class Utils {
    public static Packet SendPacket(Packet packet, CommunicationInfo server) throws CommunicationException, InvalidDataException {
        Socket socket = null;
        BufferedReader reader = null;
        PrintWriter writer = null;

        try {

            socket = new Socket(server.getIP(), server.getPort());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

            packet.getUserInfo().getOwnAddress().setIp(socket.getInetAddress().toString());
            packet.getUserInfo().getOwnAddress().setPort(socket.getPort());
            
            writer.println(packet.toString());
            String result = reader.readLine();

            return new Packet(result);
        } catch (IOException ex) {
            throw new CommunicationException();
        } catch (org.json.simple.parser.ParseException ex) {
            throw new InvalidDataException();
        } finally {
            try {
                socket.close();
                reader.close();
                writer.close();
            } catch (IOException ex) {
                throw new CommunicationException();
            }
        }
    }
    
    public static void SendPacketWithoutResponse(Packet packet, CommunicationInfo server) throws CommunicationException, InvalidDataException {
        Socket socket = null;
        PrintWriter writer = null;

        try {

            socket = new Socket(server.getIP(), server.getPort());
            writer = new PrintWriter(socket.getOutputStream(), true);

            packet.getUserInfo().getOwnAddress().setIp(socket.getInetAddress().toString());
            packet.getUserInfo().getOwnAddress().setPort(socket.getPort());
            
            writer.println(packet.toString());
        } catch (IOException ex) {
            throw new CommunicationException();
        } finally {
            try {
                socket.close();
                writer.close();
            } catch (IOException ex) {
                throw new CommunicationException();
            }
        }
    }
}
