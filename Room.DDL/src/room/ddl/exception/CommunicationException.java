/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package room.ddl.exception;

/**
 *
 * @author Benoît
 */
public class CommunicationException extends Exception {

    /**
     * Creates a new instance of <code>CommunicationException</code> without
     * detail message.
     */
    public CommunicationException() {
        super("Une erreur s'est produite lors de la communication sur le réseau");
    }

    /**
     * Constructs an instance of <code>CommunicationException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public CommunicationException(String msg) {
        super(msg);
    }
}
