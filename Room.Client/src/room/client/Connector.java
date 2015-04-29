/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package room.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import room.ddl.CommunicationInfo;
import room.ddl.Packet;
import room.ddl.exception.CommunicationException;
import room.ddl.exception.InvalidDataException;

/**
 *
 * @author Benoît *
 */
public class Connector {

    private final CommunicationInfo server;

    public Connector(CommunicationInfo server) {
        this.server = server;
    }

    public Packet SendPacket(Packet packet) throws CommunicationException, InvalidDataException {
        Socket socket = null;
        BufferedReader reader = null;
        PrintWriter writer = null;

        try {

            socket = new Socket(server.getIP(), server.getPort());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);

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
}
