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
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import javax.swing.Timer;
import tk.rubictron.chatclient.model.UsersTM;
import tk.rubictron.chatclient.proxy.ProxyHandler;
import tk.rubictron.chatcommon.observer.Observer;
import tk.rubictron.chatcommon.service.ServiceFactory;
import tk.rubictron.chatcommon.service.custom.PrivatMessageService;
import tk.rubictron.chatcommon.service.custom.PublicMessageService;
import tk.rubictron.chatcommon.service.custom.UserService;

/**
 * FXML Controller class
 *
 * @author rubictron
 */
public class DashBordController implements Initializable, Observer {

    @FXML
    private JFXButton btnSend;
    @FXML
    private JFXButton btnRegister;

    @FXML
    private JFXButton btnLogOUT;
    @FXML
    private Label lblLog;
    @FXML
    private JFXTextArea txtaMessages;
    @FXML
    private JFXTextField txtfSend;
    @FXML
    private JFXButton btnEmoj;
    @FXML
    private TableView<UsersTM> tblOnlineUser;

    private UserService userService;
    private PublicMessageService service;

    public static String username;
    public static String user2;
    
    Timer timer;
    @FXML
    private AnchorPane mainap;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException ex) {
            Logger.getLogger(DashBordController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tblOnlineUser.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("username"));

        try {

            userService = (UserService) ProxyHandler.getInstance().getService(ServiceFactory.ServiceType.USER);

        } catch (RemoteException ex) {
            Logger.getLogger(DashBordController.class.getName()).log(Level.SEVERE, null, ex);
        }

//        timer = new Timer(500, new ActionListener() {
//            private String username;
//
//            @Override
//            public void actionPerformed(java.awt.event.ActionEvent e) {
//
//                try {
//                    
//                    if(service!=null){
//                    txtaMessages.setText(service.getMessage());
//                    }
//                    username = this.username;
//                    try {
//                        ArrayList<String> allUsers = userService.getAllUser();
//                        ArrayList<UsersTM> aluser = new ArrayList<UsersTM>();
//                        for (String user : allUsers) {
//                            
//                            if (user != this.username) {
//                                aluser.add(new UsersTM(user));
//                            }
//                        }
//                        ObservableList<UsersTM> alluserOb = FXCollections.observableArrayList(aluser);
//                        tblOnlineUser.getItems().clear();
//                        tblOnlineUser.setItems(alluserOb);
//                        tblOnlineUser.refresh();
//                    } catch (RemoteException ex) {
//                        Logger.getLogger(DashBordController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                } catch (RemoteException ex) {
//                    Logger.getLogger(DashBordController.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        });

        tblOnlineUser.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<UsersTM>() {
            @Override
            public void changed(ObservableValue<? extends UsersTM> observable, UsersTM oldValue, UsersTM newValue) {

                UsersTM currentRow = observable.getValue();

                if (currentRow != null) {
                    
                    String chat = currentRow.getUsername();
                    System.out.println(currentRow.getUsername());
                    user2=chat;

                    if (service != null) {
                        unRegister();
                    }

                    if (chat.equals("public Chat")) {

                        try {
                           
                            service = (PublicMessageService) ProxyHandler.getInstance().getService(ServiceFactory.ServiceType.PUBLIC);
                            txtaMessages.setVisible(true);
                            txtfSend.setDisable(false);
                            btnSend.setDisable(false);
                            register();
                            update();
                        } catch (RemoteException ex) {
                            Logger.getLogger(DashBordController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else {

                       
                        try {
                            mainap.getChildren().clear();
                            Parent loder= FXMLLoader.load(getClass().getResource("/tk/rubictron/chatclient/view/ui/PrivateChat.fxml"));
                            mainap.getChildren().add(loder);
                        
                            
                            
                            Node n = (Node) loder;
                            AnchorPane.setTopAnchor(n, 0.0);
                            AnchorPane.setRightAnchor(n, 0.0);
                            AnchorPane.setLeftAnchor(n, 0.0);
                            AnchorPane.setBottomAnchor(n, 0.0);
                        } catch (IOException ex) {
                            Logger.getLogger(DashBordController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                 

                    }

                }

            }
        });

    }

    private void register() {
        try {
            service.registerObserver(this);
        } catch (Exception ex) {
            Logger.getLogger(DashBordController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void unRegister() {

        try {
            service.unregisterObserver(this);
        } catch (Exception ex) {
            Logger.getLogger(DashBordController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void abtnSend(ActionEvent event) {

        try {

            service.sendMessage(this.username + "\t :: " + txtfSend.getText());
        } catch (RemoteException ex) {
            Logger.getLogger(DashBordController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void abtnRegister(ActionEvent event) {
        
   
        
        
    }

    @FXML
    private void abtnLogOUT(ActionEvent event) throws Exception {

        try {

            btnSend.setDisable(true);
            btnLogOUT.setDisable(true);
            btnRegister.setDisable(false);
            tblOnlineUser.setDisable(true);
            userService.logout(lblLog.getText());
            lblLog.setText("LOG IN");

            userService.unregisterObserver(this);

        } catch (RemoteException ex) {
            Logger.getLogger(DashBordController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void lblLogClick(MouseEvent event) {

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Login Dialog");
        dialog.setHeaderText("Welcome To Rubictron Chat");

// Set the icon (must be included in the project).
//        dialog.setGraphic(new ImageView(this.getClass().getResource("login.png").toString()));
// Set the button types.
        ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

// Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

// Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

// Request focus on the username field by default.
        Platform.runLater(() -> username.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), password.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        result.ifPresent(usernamePassword -> {

//            System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());

            try {
                if (userService.login(usernamePassword.getKey(), usernamePassword.getValue())) {

                    this.username = usernamePassword.getKey();
                    lblLog.setText(this.username);

                    btnLogOUT.setDisable(false);
                    btnRegister.setDisable(true);
                    tblOnlineUser.setDisable(false);
//                    timer.start();

                    userService.registerObserver(this);

                }
            } catch (RemoteException ex) {
                Logger.getLogger(DashBordController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(DashBordController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

    }

    @FXML
    private void abtnEmoj(ActionEvent event) {
    }

    @Override
    public void update() throws RemoteException {

        if (service != null) {
            txtaMessages.setText(service.getMessage());
        }

        try {
            ArrayList<String> allUsers = userService.getAllUser();
            ArrayList<UsersTM> aluser = new ArrayList<UsersTM>();
            for (String user : allUsers) {

                if (user != this.username) {
                    aluser.add(new UsersTM(user));
                }
            }
            ObservableList<UsersTM> alluserOb = FXCollections.observableArrayList(aluser);
            tblOnlineUser.getItems().clear();
            tblOnlineUser.setItems(alluserOb);
            tblOnlineUser.refresh();

        } catch (RemoteException ex) {
            Logger.getLogger(DashBordController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void finalize() throws Throwable {

        abtnLogOUT(new ActionEvent());
        super.finalize(); //To change body of generated methods, choose Tools | Templates.

    }

}
