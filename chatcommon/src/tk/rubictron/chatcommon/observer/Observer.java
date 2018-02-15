/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.rubictron.chatcommon.observer;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author rubictron
 */
public interface Observer extends Remote{
    
    public void update() throws RemoteException;
    
}
