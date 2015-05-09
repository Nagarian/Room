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
public class CommunicationInfo {

    private String ip;
    private int port;

    public CommunicationInfo(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public CommunicationInfo(String json) throws ParseException {
        JSONObject obj = (JSONObject) new JSONParser().parse(json);

        if (obj.containsKey("ip")) {
            this.ip = obj.get("ip").toString();
        } else {
            this.ip = "localhost";
        }

        if (obj.containsKey("port")) {
            this.port = Integer.parseInt(obj.get("port").toString());
        } else {
            this.port = 23;
        }
    }

    /**
     * Get the value of ip
     *
     * @return the value of ip
     */
    public String getIP() {
        return ip.replace("\\", "").replace("/", "");
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
        jsonObj.put("ip", this.getIP());
        jsonObj.put("port", this.getPort());

        return jsonObj;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }
}
