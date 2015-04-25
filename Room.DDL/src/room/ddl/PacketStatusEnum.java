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
public enum PacketStatusEnum {
    Invalid,
    Valid,
    Error,
    Connection,
    Disconnection,
    EnterRoom,
    ExitRoom,
    SendMessage,
    ReceiveMessage
}
