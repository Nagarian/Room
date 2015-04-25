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

    public Room connectToRoom(Client client) {
        Room room = client.getRoom();

        for (Room ro : rooms) {
            if (ro.getName() == room.getName()) {
                Client newClient = new Client(client.getOwnAddress(), ro, client.getPseudo());
                ro.addClient(newClient);
                addClient(newClient);
                return ro;
            }
        }

        this.rooms.add(room);
        addClient(client);
        
        return room;
    }

    public void addClient(Client client) {
        if (client != null) {
            for (Client cl : this.clients) {
                if (client.getPseudo() == cl.getPseudo()
                        && client.getRoom().getName() == cl.getRoom().getName()) {
                    return;
                }
            };
            
            this.clients.add(client);
        }
    }

    public void removeClient(Client client) {
        if (client != null) {
            if (!this.clients.remove(client)) {
                this.clients.removeIf((cl) -> {
                    return cl.getPseudo() == client.getPseudo()
                            && cl.getRoom().getName() == client.getRoom().getName();
                });
            }
        }
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
