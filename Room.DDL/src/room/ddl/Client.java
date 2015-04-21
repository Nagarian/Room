/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package room.ddl;

import org.json.simple.JSONObject;

/**
 *
 * @author Benoît
 */
public class Client {
    
    private final CommunicationInfo ownAddress;
    private final Room room;
    private String pseudo;

    public Client(CommunicationInfo ownAddress, Room room, String pseudo) {
        this.ownAddress = ownAddress;
        this.room = room;
        this.pseudo = pseudo;
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
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("ownAddress", this.ownAddress.toJson());
        if (this.room != null) {
            jsonObj.put("room", this.room.toJson());
        }
        
        jsonObj.put("pseudo", this.pseudo);
        
        return jsonObj;
    }
}
