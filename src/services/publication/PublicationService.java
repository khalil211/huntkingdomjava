/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.publication;

import entities.user.User;
import entities.publication.Publication;
import entities.user.CurrentUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import utils.MyDB;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author moez
 */
public class PublicationService 
{
     private Connection cnx;
 private Statement st;
    
    public PublicationService() {
        cnx=MyDB.getInstance().getConnection();
    }
    
    public void ajouterPublication(Publication publication) throws SQLException
     {
        String requete = "INSERT INTO publication (`user_id`,`titre`, `image`, `datepublication`, `text`) VALUES ('"+publication.getIdUser()+"','aaa','hey','2020-02-17 08:01:00',"+publication.getMypublication()+"')";
        try{
             st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("publication ajouté");
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
}
