/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.rubictron.chatclient.main;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author rubictron
 */
public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
       
        try {
            
//            System.setProperty("java.rmi.server.hostname", "10.42.0.2");

            Parent root = FXMLLoader.load(getClass().getResource("/tk/rubictron/chatclient/view/ui/DashBord.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Chat");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
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
