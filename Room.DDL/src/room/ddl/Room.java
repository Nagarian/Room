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
public class Room {

    private final String name;
    private final ArrayList<Client> clients;
    private final int id;
    //log

    public Room(String name, ArrayList<Client> clients, int id) {
        this.name = name;
        this.clients = clients;
        this.id = id;
    }

    public Room(String name, Client client) {
        this.name = name;
        this.clients = new ArrayList<>();
        if (client != null) {
            this.clients.add(client);
        }

        this.id = 0;
    }

    public Room(String json) throws ParseException {
        JSONObject obj = (JSONObject) new JSONParser().parse(json);

        if (obj.containsKey("name")) {
            this.name = obj.get("name").toString();
        } else {
            this.name = null;
        }

        this.clients = new ArrayList<Client>();
        if (obj.containsKey("clients")) {
            for (Object object : (JSONArray) obj.get("clients")) {
                this.clients.add(new Client(object.toString()));
            }
        }

        if (obj.containsKey("id")) {
            this.id = Integer.parseInt(obj.get("id").toString());
        } else {
            this.id = 0;
        }
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the value of clients
     *
     * @return the value of clients
     */
    public ArrayList<Client> getClients() {
        return clients;
    }
    
    public void addClient(Client client) {
        
    }

    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public int getId() {
        return id;
    }

    public JSONObject toJson() {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("id", this.id);
        jsonObj.put("name", this.name);

        JSONArray array = new JSONArray();
        clients.stream().forEach((client) -> {
            array.add(client.toJson());
        });

        jsonObj.put("clients", clients);

        return jsonObj;
    }
}
