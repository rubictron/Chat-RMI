/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.rubictron.chatcommon.service.custom;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import tk.rubictron.chatcommon.service.SuperService;

/**
 *
 * @author rubictron
 */
public interface UserService extends Remote,SuperService{
    public boolean login(String username,String password) throws RemoteException;
    public void logout(String username) throws RemoteException;
    public ArrayList<String> getAllUser() throws RemoteException;
}
