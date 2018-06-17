/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.rubictron.chatclient.proxy;

import java.io.File;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import tk.rubictron.chatcommon.service.ServiceFactory;
import tk.rubictron.chatcommon.service.SuperService;
import tk.rubictron.chatcommon.service.custom.PrivatMessageService;
import tk.rubictron.chatcommon.service.custom.PublicMessageService;
import tk.rubictron.chatcommon.service.custom.UserService;

/**
 *
 * @author rubictron
 */
public class ProxyHandler implements ServiceFactory{

 private static ProxyHandler proxyHandler;
 private ServiceFactory serviceFactory ;
 private PublicMessageService publicService;
 private PrivatMessageService privatMessageService;
 private UserService userService;
 
    
    private ProxyHandler(){
        
        try {
            
            Properties serverSettings = new Properties();
            File file = new File("proxy-setting.properties");
            FileReader reader = new FileReader(file);
            serverSettings.load(reader);
            
            serviceFactory = (ServiceFactory) Naming.lookup("rmi://"+serverSettings.getProperty("ip")+":6060/chat");
//            serviceFactory = (ServiceFactory) Naming.lookup("rmi://10.42.0.2:6060/chat");
            
            publicService =   (PublicMessageService) serviceFactory.getService(ServiceFactory.ServiceType.PUBLIC);
            
            privatMessageService=(PrivatMessageService) serviceFactory.getService(ServiceType.PRIVATE);
            
            userService =(UserService) serviceFactory.getService(ServiceType.USER);
            
        } catch (NotBoundException ex) {
            Logger.getLogger(ProxyHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(ProxyHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(ProxyHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ProxyHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static ProxyHandler getInstance(){
        if (proxyHandler == null){
            proxyHandler = new ProxyHandler();
        }
        return proxyHandler;
    }


 

    @Override
    public SuperService getService(ServiceType type) throws RemoteException {
        switch(type){
            case PRIVATE:
                System.out.println("pmsg2");
                return privatMessageService;
            case PUBLIC:
                return publicService;
            case USER:
                return userService;
            default:
                return null;
}
    
    
    
}
    
}
