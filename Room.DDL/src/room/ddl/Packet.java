/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package room.ddl;

/**
 *
 * @author Benoît
 */
public class Packet {
    
    private Client userInfo;
    private String message;

    /**
     * Get the value of userInfo
     *
     * @return the value of userInfo
     */
    public Client getUserInfo() {
        return userInfo;
    }

    /**
     * Set the value of userInfo
     *
     * @param userInfo new value of userInfo
     */
    public void setUserInfo(Client userInfo) {
        this.userInfo = userInfo;
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
     * Set the value of message
     *
     * @param message new value of message
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
