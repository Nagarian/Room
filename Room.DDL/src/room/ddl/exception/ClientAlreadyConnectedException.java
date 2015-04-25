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
public class ClientAlreadyConnectedException extends Exception {

    /**
     * Creates a new instance of <code>ClientAlreadyConnectedException</code>
     * without detail message.
     */
    public ClientAlreadyConnectedException() {
        super("Un utilisateur avec le même pseudo existe déjà");
    }

    /**
     * Constructs an instance of <code>ClientAlreadyConnectedException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ClientAlreadyConnectedException(String msg) {
        super(msg);
    }
}
