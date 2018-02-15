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
import tk.rubictron.chatcommon.service.custom.UserService;
import tk.rubictron.chatserver.repository.LoginRepository;

/**
 *
 * @author rubictron
 */
public class UserServiceImpl extends UnicastRemoteObject implements UserService {

    private static ArrayList<String> users = new ArrayList<>();
    private static ArrayList<Observer> observers = new ArrayList<>();
    LoginRepository loginRepository;

    public UserServiceImpl() throws RemoteException {
        this.loginRepository = new LoginRepository();
        users.add("public Chat");

    }

    @Override
    public boolean login(String username, String password) throws RemoteException {

        if (loginRepository.checkLog(username, password)) {

            if (!users.contains(username)) {
                users.add(username);
            }
            return true;

        }
        return false;
    }

    @Override
    public ArrayList<String> getAllUser() throws RemoteException {
        return users;

    }

    @Override
    public void logout(String username) throws RemoteException {
        users.remove(username);

    }

    @Override
    public void registerObserver(Observer observer) throws Exception {
        observers.add(observer);
        notifyAllObservers();
    }

    @Override
    public void unregisterObserver(Observer observer) throws Exception {
        observers.remove(observer);
        notifyAllObservers();
    }

    @Override
    public void notifyAllObservers() throws Exception {
        observers.forEach((t) -> {

            try {
                t.update();
            } catch (RemoteException ex) {
                Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    }

}
