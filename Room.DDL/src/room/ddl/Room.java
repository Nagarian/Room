/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package room.ddl;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import room.ddl.exception.CommunicationException;
import room.ddl.exception.InvalidDataException;

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
        addClient(client);

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
        if (client != null) {
            this.clients.add(client);
            sendMessage("", client, PacketStatusEnum.NewClient);
        }
    }

    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public int getId() {
        return id;
    }

    public void sendMessage(String message, Client from) throws CommunicationException, InvalidDataException {
        sendMessage(message, from, PacketStatusEnum.ReceiveMessage);
    }

    private void sendMessage(final String message, final Client from, final PacketStatusEnum status) {
        new Thread(() -> {
            for (Client client : clients) {
                if (!client.getPseudo().equals(from.getPseudo())) {
                    try {
                        Utils.SendPacketWithoutResponse(new Packet(from, message, status), client.getOwnAddress(), false);
                    } catch (CommunicationException | InvalidDataException ex) {
                    }
                }
            }
        }).start();
    }

    public JSONObject toJson() {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("id", this.id);
        jsonObj.put("name", this.name);

        JSONArray array = new JSONArray();
        clients.stream().forEach((client) -> {
            array.add(client.toJson(true).toJSONString());
        });

        jsonObj.put("clients", array);

        return jsonObj;
    }

    void removeClient(Client client) {
        if (!this.clients.remove(client)) {
            this.clients.removeIf((cl) -> {
                return cl.getPseudo().equals(client.getPseudo());
            });
        }

        sendMessage("", client, PacketStatusEnum.GoodbyClient);
    }

    int getNumberOfParticipant() {
        return clients.size();
    }
}
