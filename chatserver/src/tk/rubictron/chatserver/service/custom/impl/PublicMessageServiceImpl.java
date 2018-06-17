/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.rubictron.chatserver.service.custom.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import tk.rubictron.chatcommon.observer.Observer;
import tk.rubictron.chatcommon.service.custom.PublicMessageService;

/**
 *
 * @author rubictron
 */
public class PublicMessageServiceImpl extends UnicastRemoteObject implements PublicMessageService {
    
    private static ArrayList<Observer> observers=new ArrayList<>();
    
    private String message="";

    public PublicMessageServiceImpl() throws RemoteException{

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
                Logger.getLogger(PublicMessageServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

 

    @Override
    public void sendMessage(String message) throws RemoteException {
        try {
            this.message+=message+"\n";
            System.out.println(message);
            notifyAllObservers();
        } catch (Exception ex) {
            Logger.getLogger(PublicMessageServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getMessage() throws RemoteException {
       return this.message;
    }





}
