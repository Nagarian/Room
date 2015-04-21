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
    private final ArrayList<Room> salles;

    public Lounge(CommunicationInfo address) {
        this.address = address;
        this.salles = new ArrayList<Room>();
    }

    public Lounge(String json) throws ParseException {
        JSONObject obj = (JSONObject) new JSONParser().parse(json);

        if (obj.containsKey("address")) {
            this.address = new CommunicationInfo(obj.get("address").toString());
        } else {
            this.address = null;
        }

        this.salles = new ArrayList<>();
        if (obj.containsKey("salles")) {
            for (Object object : (JSONArray) obj.get("salles")) {
                this.salles.add(new Room(object.toString()));
            }
        }
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
