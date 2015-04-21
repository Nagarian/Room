/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package room.ddl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Benoît
 */
public class Packet {

    private final Client userInfo;
    private final String message;
    private final String status;

    public Packet(Client userInfo, String message, String status) {
        this.userInfo = userInfo;
        this.message = message;
        this.status = status;
    }

    public Packet(String json) throws ParseException {
        JSONObject obj = (JSONObject) new JSONParser().parse(json);

        if (obj.containsKey("userInfo")) {
            this.userInfo = new Client(obj.get("userInfo").toString());
        } else {
            this.userInfo = null;
        }

        if (obj.containsKey("message")) {
            this.message = obj.get("message").toString();
        } else {
            this.message = null;
        }

        if (obj.containsKey("status")) {
            this.status = obj.get("status").toString();
        } else {
            this.status = null;
        }
    }

    /**
     * Get the value of userInfo
     *
     * @return the value of userInfo
     */
    public Client getUserInfo() {
        return userInfo;
    }

    /**
     * Get the value of message
     *
     * @return the value of message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Get the value of status
     *
     * @return the value of status
     */
    public String getStatus() {
        return status;
    }

    public JSONObject toJson() {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("userInfo", this.userInfo.toJson());
        jsonObj.put("message", this.message);
        jsonObj.put("status", this.status);

        return jsonObj;
    }
}
