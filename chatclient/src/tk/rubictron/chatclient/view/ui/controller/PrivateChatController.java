/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.rubictron.chatclient.view.ui.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import tk.rubictron.chatclient.proxy.ProxyHandler;
import tk.rubictron.chatcommon.observer.Observer;
import tk.rubictron.chatcommon.service.ServiceFactory;
import tk.rubictron.chatcommon.service.custom.PrivatMessageService;

/**
 * FXML Controller class
 *
 * @author rubictron
 */
public class PrivateChatController implements Initializable, Observer {

    @FXML
    private JFXTextArea txtaMessages;
    @FXML
    private JFXTextField txtfSendMessage;
    @FXML
    private JFXButton btnSend;

    public String user1;
    public String user2;
    private PrivatMessageService service;
    @FXML
    private AnchorPane mainap;
    @FXML
    private Label lblusers;
    @FXML
    private JFXButton btnSend1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.user1 = DashBordController.username;
            this.user2 = DashBordController.user2;
            
            lblusers.setText("Welcome :"+ this.user1+ "-> You are connect with :"+this.user2);
            
            
            UnicastRemoteObject.exportObject(this, 0);

            service = (PrivatMessageService) ProxyHandler.getInstance().getService(ServiceFactory.ServiceType.PRIVATE);
            service.registerObserver(this);
            update();
        } catch (RemoteException ex) {
            Logger.getLogger(PrivateChatController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PrivateChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void abtnSend(ActionEvent event) {
        
        String msg=this.user1+"\t:: "+txtfSendMessage.getText();

        try {
          service.sendMessage(msg, this.user1, this.user2);
        } catch (RemoteException ex) {
            Logger.getLogger(PrivateChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void abtnExit(ActionEvent event) {
        
        
        try {
                            mainap.getChildren().clear();
                            Parent loder= FXMLLoader.load(getClass().getResource("/tk/rubictron/chatclient/view/ui/DashBord.fxml"));
                            mainap.getChildren().add(loder);
                        
                            
                            
                            Node n = (Node) loder;
                            AnchorPane.setTopAnchor(n, 0.0);
                            AnchorPane.setRightAnchor(n, 0.0);
                            AnchorPane.setLeftAnchor(n, 0.0);
                            AnchorPane.setBottomAnchor(n, 0.0);
                        } catch (IOException ex) {
                            Logger.getLogger(DashBordController.class.getName()).log(Level.SEVERE, null, ex);
                        }
        
        
        
        
        try {
            service.unregisterObserver(this);
        } catch (Exception ex) {
            Logger.getLogger(PrivateChatController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void update() throws RemoteException {
         try {
            String msg = service.getMessage(user1, user2);
            txtaMessages.setText(msg);
        } catch (RemoteException ex) {
            Logger.getLogger(PrivateChatController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
