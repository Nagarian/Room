/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package room.client;

import com.jgoodies.looks.windows.WindowsLookAndFeel;
import javax.swing.UIManager;
import room.client.gui.Connection_GUI;

/**
 *
 * @author Benoît
 */
public class RoomClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            UIManager.setLookAndFeel(new WindowsLookAndFeel());
        } catch (Exception e) {
        }

        Connection_GUI _connection = new Connection_GUI();
        _connection.setVisible(true);
    }
}
