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
public class Packet {

    private final Client userInfo;
    private final String message;
    private String status;

    public Packet(Client userInfo, String message, PacketStatusEnum status) {
        this.userInfo = userInfo;
        this.message = message;
        setPacketStatusEnum(status);
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

    public Packet(Packet packetRoResponseSuccessful, String message) {
        this.userInfo = packetRoResponseSuccessful.getUserInfo();
        this.message = message;
        setPacketStatusEnum(PacketStatusEnum.Valid);
    }

    public Packet(Packet packetRoResponse, String message, PacketStatusEnum status) {
        this.userInfo = packetRoResponse.getUserInfo();
        this.message = message;
        setPacketStatusEnum(status);
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

    public PacketStatusEnum getPacketStatus() {
        switch (status) {
            case "connection":
                return PacketStatusEnum.Connection;
            case "disconnection":
                return PacketStatusEnum.Disconnection;
            case "valid":
                return PacketStatusEnum.Valid;
            case "invalid":
                return PacketStatusEnum.Invalid;
            case "error":
                return PacketStatusEnum.Error;
            case "getrooms":
                return PacketStatusEnum.GetRooms;
            case "enterroom":
                return PacketStatusEnum.EnterRoom;
            case "exitroom":
                return PacketStatusEnum.ExitRoom;
            case "sendmessage":
                return PacketStatusEnum.SendMessage;
            case "receivemessage":
                return PacketStatusEnum.ReceiveMessage;
            default:
                return PacketStatusEnum.Invalid;
        }
    }

    private void setPacketStatusEnum(PacketStatusEnum packetStatus) {
        switch (packetStatus) {

            case Connection:
                this.status = "connection";
                break;
            case Disconnection:
                this.status = "disconnection";
                break;
            case GetRooms:
                this.status = "getrooms";
                break;

            case Valid:
                this.status = "valid";
                break;
            case Invalid:
                this.status = "invalid";
                break;
            case Error:
                this.status = "error";
                break;

            case EnterRoom:
                this.status = "enterroom";
                break;
            case ExitRoom:
                this.status = "exitroom";
                break;

            case SendMessage:
                this.status = "sendmessage";
                break;
            case ReceiveMessage:
                this.status = "receivemessage";
                break;

            default:
                this.status = "";
                break;
        }
    }

    public JSONObject toJson() {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("userInfo", this.userInfo.toJson());
        jsonObj.put("message", this.message);
        jsonObj.put("status", this.status);

        return jsonObj;
    }

    @Override
    public String toString() {
        return toJson().toJSONString();
    }
}
