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
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import room.ddl.Client;
import room.ddl.CommunicationInfo;
import room.ddl.Packet;
import room.ddl.PacketStatusEnum;
import room.ddl.Room;
import room.ddl.exception.CommunicationException;
import room.ddl.exception.InvalidDataException;

/**
 *
 * @author Benoît *
 */
public class Connector {

    private final CommunicationInfo server;
    private final Client userInfo;

    public Connector(CommunicationInfo server, Client userInfo) {
        this.server = server;
        this.userInfo = userInfo;
    }
    
    public Room ConnectToRoom(String _roomName) throws CommunicationException, InvalidDataException, Exception {
        Packet packet = SendPacket(new Packet(userInfo, new Room(_roomName, userInfo).toJson().toJSONString(), PacketStatusEnum.EnterRoom));
        
        if (packet.getPacketStatus() != PacketStatusEnum.Valid) {
            throw new Exception(packet.getMessage());
        }
        
        try {
            JSONObject obj = (JSONObject) new JSONParser().parse(packet.getMessage());
            return new Room(obj.toString());
            
        } catch (ParseException ex) {
            throw new InvalidDataException();
        }
    }
    
    public ArrayList<Room> Connect() throws CommunicationException, InvalidDataException, Exception {
        Packet packet = SendPacket(new Packet(userInfo, "", PacketStatusEnum.Connection));
        
        if (packet.getPacketStatus() != PacketStatusEnum.Valid) {
            throw new Exception(packet.getMessage());
        }
        
        try {
            JSONArray obj = (JSONArray) new JSONParser().parse(packet.getMessage());
            ArrayList<Room> rooms = new ArrayList<>();
            
            
            for (Object jsonRoom : obj) {
                rooms.add(new Room(jsonRoom.toString()));
            }
            
            return rooms;
        } catch (ParseException ex) {
            throw new InvalidDataException();
        }
    }

    public Packet SendMessageTo(Room room, String message) throws CommunicationException, InvalidDataException {
        return SendPacket(new Packet(new Client(userInfo.getOwnAddress(), room, userInfo.getPseudo()), message, PacketStatusEnum.SendMessage));
    }
    
    public Packet SendPacket(Packet packet) throws CommunicationException, InvalidDataException {
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
}
