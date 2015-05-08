/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package room.ddl;

import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import room.ddl.exception.ClientAlreadyConnectedException;
import room.ddl.exception.RoomNotFoundException;

/**
 *
 * @author Benoît
 */
public class Lounge {

    private final CommunicationInfo address;
    private final ArrayList<Room> rooms;
    private final ArrayList<Client> clients;

    public Lounge(CommunicationInfo address) {
        this.address = address;
        this.rooms = new ArrayList<Room>();
        this.clients = new ArrayList<Client>();
    }
    
    public void addRoom(String name, Client client){
        this.rooms.add(new Room(name, client));
    }

    public Lounge(String json) throws ParseException {
        JSONObject obj = (JSONObject) new JSONParser().parse(json);

        if (obj.containsKey("address")) {
            this.address = new CommunicationInfo(obj.get("address").toString());
        } else {
            this.address = null;
        }

        this.rooms = new ArrayList<>();
        if (obj.containsKey("salles")) {
            for (Object object : (JSONArray) obj.get("salles")) {
                this.rooms.add(new Room(object.toString()));
            }
        }

        this.clients = new ArrayList<>();
    }

    /**
     * Get the value of address
     *
     * @return the value of address
     */
    public CommunicationInfo getAddress() {
        return address;
    }

    /**
     * Get the value of rooms
     *
     * @return the value of rooms
     */
    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public JSONArray getRoomsToJson() {
        JSONArray array = new JSONArray();
        for (Room room : this.rooms) {
            array.add(room.toJson());
        }

        return array;
    }

    /**
     * Permet de se connecter à une salle, si elle n'existe pas la créer
     *
     * @param client client qui se connecte
     * @param room salle à laquelle se connecter
     * @return salle connecté (le même objet si création de salle)
     */
    public Room connectToRoom(Client client, Room room) {
        for (Room ro : rooms) {
            if (ro.getName().equals(room.getName())) {
                Client newClient = new Client(client.getOwnAddress(), ro, client.getPseudo());
                ro.addClient(newClient);
                return ro;
            }
        }

        this.rooms.add(room);

        return room;
    }

    public void disconnectToRoom(Client client, Room room) {
        Room roomToDelete = null;

        for (Room ro : rooms) {
            if (ro.getName().equals(room.getName())) {
                ro.removeClient(client);
                if (ro.getNumberOfParticipant() == 0) {
                    roomToDelete = ro;
                    break;
                }
            }
        }

        if (roomToDelete != null) {
            this.rooms.remove(roomToDelete);
        }
    }

    public void addClient(Client client) throws ClientAlreadyConnectedException {
        if (client != null) {
            for (Client cl : this.clients) {
                if (client.getPseudo().equals(cl.getPseudo())) {
                    throw new ClientAlreadyConnectedException();
                }
            }
            
            this.clients.add(new Client(client.getOwnAddress(), null, client.getPseudo()));
        }
    }

    public void removeClient(Client client) {
        if (client != null) {
            if (!this.clients.remove(client)) {
                this.clients.removeIf((cl) -> {
                    return cl.getPseudo().equals(client.getPseudo());
                });
            }

            ArrayList<Room> roomToDelete = new ArrayList<Room>();

            for (Room room : rooms) {
                room.removeClient(client);

                if (room.getNumberOfParticipant() == 0) {
                    roomToDelete.add(room);
                }
            }

            this.rooms.removeAll(roomToDelete);
        }
    }

    public void sendMessage(Packet packet) throws RoomNotFoundException {
        Room room = packet.getUserInfo().getRoom();
        Client sender = packet.getUserInfo();
        String message = packet.getMessage();
        
        for (Room ro : rooms) {
            if (room.getName().equals(ro.getName())) {
                ro.sendMessage(message, sender);
                return;
            }
        }
        
        throw new RoomNotFoundException();
    }

    public JSONObject toJson() {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("address", this.address.toJson());

        JSONArray array = new JSONArray();
        rooms.stream().forEach((salle) -> {
            array.add(salle.toJson());
        });

        jsonObj.put("salles", array);

        return jsonObj;
    }
}
