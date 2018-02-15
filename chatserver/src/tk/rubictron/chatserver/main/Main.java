/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.rubictron.chatserver.main;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;
import tk.rubictron.chatcommon.service.ServiceFactory;
import tk.rubictron.chatserver.service.impl.ServiceFactoryImpl;

/**
 *
 * @author rubictron
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
         try {
             
            Registry registry=LocateRegistry.createRegistry(6060);
            
             ServiceFactory factory=ServiceFactoryImpl.getInstance();
            
            registry.rebind("chat",factory);
            
            System.out.println("Server Has Start");
            
        } catch (RemoteException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
