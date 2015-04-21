/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package room.ddl;

import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author Benoît
 */
public class Lounge {
    
    private final CommunicationInfo address;
    private final ArrayList<Room> salles;

    public Lounge(CommunicationInfo address) {
        this.address = address;
        this.salles = new ArrayList<Room>();
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
     * Get the value of salles
     *
     * @return the value of salles
     */
    public ArrayList<Room> getSalles() {
        return salles;
    }

    public JSONObject toJson() {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("address", this.address.toJson());

        JSONArray array = new JSONArray();
        salles.stream().forEach((salle) -> {
            array.add(salle.toJson());
        });
        
        jsonObj.put("salles", array);
        
        return jsonObj;
    }
}
