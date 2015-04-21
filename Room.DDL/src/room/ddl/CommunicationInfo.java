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
public class CommunicationInfo {
    
    private final String ip;
    private final int port;

    public CommunicationInfo(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }
    
    /**
     * Get the value of ip
     *
     * @return the value of ip
     */
    public String getIP() {
        return ip;
    }
    
    /**
     * Get the value of port
     *
     * @return the value of port
     */
    public int getPort() {
        return port;
    }

    public JSONObject toJson() {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("ip", this.ip);
        jsonObj.put("port", this.port);
        
        return jsonObj;
    }
}
