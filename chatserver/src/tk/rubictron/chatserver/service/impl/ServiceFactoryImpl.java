/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.rubictron.chatserver.service.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import tk.rubictron.chatcommon.service.ServiceFactory;
import tk.rubictron.chatcommon.service.SuperService;
import tk.rubictron.chatserver.service.custom.impl.PrivateMessageServiceImpl;
import tk.rubictron.chatserver.service.custom.impl.PublicMessageServiceImpl;
import tk.rubictron.chatserver.service.custom.impl.UserServiceImpl;

/**
 *
 * @author rubictron
 */
public class ServiceFactoryImpl extends UnicastRemoteObject implements ServiceFactory {

    private static ServiceFactoryImpl serviceFactory;
    private PublicMessageServiceImpl publicMessageService;
    private PrivateMessageServiceImpl privateMessageService;
    private UserServiceImpl userService;

    private ServiceFactoryImpl() throws RemoteException{
        
        publicMessageService=new PublicMessageServiceImpl();
        privateMessageService=new PrivateMessageServiceImpl();
        userService=new UserServiceImpl();        
    }

    public static ServiceFactoryImpl getInstance() throws RemoteException {
        if (serviceFactory == null) {
            serviceFactory = new ServiceFactoryImpl();
        }
        return serviceFactory;
        
    }

    @Override
    public SuperService getService(ServiceType type) throws RemoteException {
        switch (type) {
            case PRIVATE:
                return privateMessageService;
            case PUBLIC:
                return publicMessageService;
            case USER:
                return userService;
            default:
                return null;
        }

    }



 

}
