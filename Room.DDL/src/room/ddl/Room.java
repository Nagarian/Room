/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package room.ddl;

import java.util.ArrayList;

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
        this.clients.add(client);
        this.id = 0;
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

    /**
     * Get the value of id
     *
     * @return the value of id
     */
    public int getId() {
        return id;
    }
}
