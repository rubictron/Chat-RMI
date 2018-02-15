/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.rubictron.chatcommon.service.custom;

import java.rmi.RemoteException;
import tk.rubictron.chatcommon.service.SuperService;

/**
 *
 * @author rubictron
 */
public interface PrivatMessageService extends SuperService {

    public void sendMessage(String message, String user1, String user2) throws RemoteException;

    public String getMessage(String user1, String user2) throws RemoteException;
   

}
