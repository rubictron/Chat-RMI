/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.rubictron.chatserver.service.custom.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import tk.rubictron.chatcommon.observer.Observer;
import tk.rubictron.chatcommon.service.custom.PrivatMessageService;

/**
 *
 * @author rubictron
 */
public class PrivateMessageServiceImpl extends UnicastRemoteObject implements PrivatMessageService {

    HashMap<String, String> userHash = new HashMap();
    ArrayList<Observer> observers = new ArrayList<>();

    public PrivateMessageServiceImpl() throws RemoteException {

    }

    @Override
    public void registerObserver(Observer observer) throws Exception {

        observers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) throws Exception {
        observers.remove(observer);
    }

    @Override
    public void notifyAllObservers() throws Exception {
        observers.forEach((t) -> {
            try {
                t.update();
            } catch (RemoteException ex) {
                Logger.getLogger(PrivateMessageServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @Override
    public String getMessage(String user1, String user2) throws RemoteException {
         
        if (userHash.containsKey(user1 + user2)) {

            return userHash.get(user1 + user2);
            
        }else if(userHash.containsKey(user2 + user1)){
            
            return userHash.get(user2+ user1);
            
        }else{
            
            return "there is no messages";
        }
    }

    @Override
    public void sendMessage(String message, String user1, String user2) throws RemoteException {

        try {
            if (userHash.containsKey(user1 + user2)) {
                
                String msg = userHash.get(user1 + user2) +"\n"+ message;
                userHash.remove(user1 + user2);
                userHash.put(user1 + user2, msg);
                
            } else if (userHash.containsKey(user2 + user1)) {
                
                String msg = userHash.get(user2 + user1) + message;
                userHash.remove(user2 + user1);
                userHash.put(user2 + user1, msg);
                
            } else {
                
                userHash.put(user1 + user2, message);
                
            }
            
            notifyAllObservers();

        } catch (Exception ex) {
            Logger.getLogger(PrivateMessageServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
