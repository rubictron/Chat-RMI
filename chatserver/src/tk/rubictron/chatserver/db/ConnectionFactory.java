/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.rubictron.chatserver.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rubictron
 */
public class ConnectionFactory {
    private static ConnectionFactory dbConnection;
    private Connection connection;
    
    
    private ConnectionFactory() throws ClassNotFoundException, SQLException{
            
        
        FileInputStream IS=null;
        try {
            Properties dbsetting=new Properties();
            File settingFile=new File("setting/setting.properties");
            IS = new FileInputStream(settingFile);
            dbsetting.load(IS);
            
            Class.forName("com.mysql.jdbc.Driver");
            String dburl="jdbc:mysql://"+dbsetting.getProperty("ip")+":"+dbsetting.getProperty("port")+"/"+dbsetting.getProperty("db")+ "";
            connection=DriverManager.getConnection(dburl,dbsetting.getProperty("user"),dbsetting.getProperty("password"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                IS.close();
            } catch (IOException ex) {
                Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    public Connection getConnection() {
        return connection;
    }

    public static ConnectionFactory getInstance() {
        if(dbConnection==null){
            try {
                dbConnection=new ConnectionFactory();
                
            } catch (ClassNotFoundException | SQLException ex) {
                
                Logger.getLogger(ConnectionFactory.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dbConnection;
    }
    
}
