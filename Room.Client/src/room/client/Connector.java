/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package room.client;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import room.ddl.Client;
import room.ddl.CommunicationInfo;
import room.ddl.Packet;
import room.ddl.PacketStatusEnum;
import room.ddl.Room;
import room.ddl.Utils;
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
    
    public String getServerName() {
        return server.getIP() + ":" + server.getPort();
    }
    
    public String getUserName() {
        return userInfo.getPseudo();
    }
    
    
    public ClientReceiveMessageThread ConnectToRoom(String roomName, ReceiveMessageListener method) throws CommunicationException, InvalidDataException, Exception {
        Packet packet = SendPacket(new Packet(userInfo, new Room(roomName, userInfo).toJson().toJSONString(), PacketStatusEnum.EnterRoom));
        
        if (packet.getPacketStatus() != PacketStatusEnum.Valid) {
            throw new Exception(packet.getMessage());
        }
        
        try {
            JSONObject obj = (JSONObject) new JSONParser().parse(packet.getMessage());
            return new ClientReceiveMessageThread(packet.getUserInfo().getOwnAddress(), new Room(obj.toString()), method);
            
        } catch (ParseException ex) {
            throw new InvalidDataException();
        }
    }
    
    public void ExitFromRoom(Room room){
        try {
            SendPacketWithoutResponse(new Packet(userInfo, room.toJson().toJSONString(), PacketStatusEnum.ExitRoom));
        } catch (CommunicationException | InvalidDataException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<Room> Connect() throws CommunicationException, InvalidDataException, Exception {
        return GetRooms(PacketStatusEnum.Connection);
    }

    public void Disconnect() {
        try {
            SendPacketWithoutResponse(new Packet(userInfo, "", PacketStatusEnum.Disconnection));
        } catch (CommunicationException | InvalidDataException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<Room> RefreshLounge() throws CommunicationException, InvalidDataException, Exception {
        return GetRooms(PacketStatusEnum.GetRooms);
    }
    
    private ArrayList<Room> GetRooms(PacketStatusEnum status) throws CommunicationException, InvalidDataException, Exception {
        Packet packet = SendPacket(new Packet(userInfo, "", status));
        
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
    
    public void SendMessageTo(Room room, String message) throws CommunicationException, InvalidDataException {
        SendPacketWithoutResponse(new Packet(new Client(userInfo.getOwnAddress(), room, userInfo.getPseudo()), message, PacketStatusEnum.SendMessage));
    }
    
    public Packet SendPacket(Packet packet) throws CommunicationException, InvalidDataException {
        return Utils.SendPacket(packet, server);
    }
    
    public void SendPacketWithoutResponse(Packet packet) throws CommunicationException, InvalidDataException {
        Utils.SendPacketWithoutResponse(packet, server);
    }
}
