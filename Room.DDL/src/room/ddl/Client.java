/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package room.ddl;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Benoît
 */
public class Client {

    private final CommunicationInfo ownAddress;
    private final Room room;
    private String pseudo;

    public Client(CommunicationInfo ownAddress, String pseudo) {
        this.ownAddress = ownAddress;
        this.pseudo = pseudo;
        this.room = null;
    }
    
    public Client(CommunicationInfo ownAddress, Room room, String pseudo) {
        this.ownAddress = ownAddress;
        this.room = room;
        this.pseudo = pseudo;
    }

    public Client(String json) throws ParseException {
        JSONObject obj = (JSONObject) new JSONParser().parse(json);
        if (obj.containsKey("ownAddress")) {
            this.ownAddress = new CommunicationInfo(obj.get("ownAddress").toString());
        } else {
            this.ownAddress = null;
        }

        if (obj.containsKey("room")) {
            this.room = new Room(obj.get("room").toString());
        } else {
            this.room = null;
        }

        if (obj.containsKey("pseudo")) {
            this.pseudo = obj.get("pseudo").toString();
        } else {
            this.pseudo = null;
        }
    }

    /**
     * Get the value of ownAddress
     *
     * @return the value of ownAddress
     */
    public CommunicationInfo getOwnAddress() {
        return ownAddress;
    }

    /**
     * Get the value of room
     *
     * @return the value of room
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Get the value of pseudo
     *
     * @return the value of pseudo
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * Set the value of pseudo
     *
     * @param pseudo new value of pseudo
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
public JSONObject toJson() {
 return this.toJson(false);
}
    
    public JSONObject toJson(Boolean isSubItem) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("ownAddress", this.ownAddress.toJson().toJSONString());
        if (this.room != null && !isSubItem) {
            jsonObj.put("room", this.room.toJson().toJSONString());
        }

        jsonObj.put("pseudo", this.pseudo);

        return jsonObj;
    }
}
