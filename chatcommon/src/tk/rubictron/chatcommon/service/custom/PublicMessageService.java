/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.rubictron.chatcommon.service.custom;

import java.rmi.RemoteException;
import java.util.ArrayList;
import tk.rubictron.chatcommon.service.SuperService;

/**
 *
 * @author rubictron
 */
public interface PublicMessageService extends SuperService {

//    public ArrayList<String> getAllUsers() throws RemoteException;
    public void sendMessage(String message) throws RemoteException;

    public String getMessage() throws RemoteException;

}
