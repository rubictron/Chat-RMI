/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tk.rubictron.chatserver.repository;

import tk.rubictron.chatserver.db.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rubictron
 */
public class LoginRepository {
    
    
    private Connection connection=ConnectionFactory.getInstance().getConnection();

    public LoginRepository() {
    }
    
    
    public boolean checkLog(String username,String password){
    
        try {
            connection=ConnectionFactory.getInstance().getConnection();
            
//            String sql="SELECT * FROM users WHERE username =\""+username+"\"";
//            Statement stm = connection.createStatement();
//            ResultSet rst=stm.executeQuery(sql);
            
            PreparedStatement pstm=connection.prepareStatement("SELECT * FROM users WHERE username=?");
            pstm.setObject(1, username);
            ResultSet rst=pstm.executeQuery();
            

            
            if(rst.next() && password.equals(rst.getString(2))){
               
                
               return true;
                
                
            }else {
                return false;   
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    
    
    
}
