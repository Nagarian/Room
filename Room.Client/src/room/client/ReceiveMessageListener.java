/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package room.client;
import java.util.EventListener;
import room.ddl.Packet;

/**
 *
 * @author Benoît
 */
public interface ReceiveMessageListener extends EventListener {
    void MessageReceived(Packet packet);
}
