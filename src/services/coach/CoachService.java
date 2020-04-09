/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.coach;
import entities.chien.Chien;
import entities.coach.Coach;
import java.sql.PreparedStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.MyDB;

/**
 *
 * @author Louay
 */
public class CoachService {
      private Connection cnx;
   
    
    public CoachService(){
        cnx=MyDB.getInstance().getConnection();
    }
    
    public void ajouter (Coach c) {
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
         try {
              String req = "INSERT INTO `coach` (`id`, `user_id`, `experience`, `etat`, `hire_date`, `code`) VALUES (NULL, ?, ?, 'Disponible', ?, ?);";
            
            
            PreparedStatement pre=cnx.prepareStatement(req);
             String requete = "SELECT id FROM fos_user where username='"+ c.getNom() + "'";
              Statement st = cnx.createStatement();
                ResultSet rs = st.executeQuery(requete);
                int a;
                 while(rs.next()) {
                 
                    a=(rs.getInt("id"));
                    pre.setInt(1,a);
                 }
              
           
           
            
            
            pre.setInt(2,c.getExperienceYears());
            
            pre.setDate(3,date);
            pre.setString(4,c.getCode());
            pre.executeUpdate();
            
            System.out.println("Insertion Reussie!");

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    
    }
     public ArrayList<Coach> getAllCoachs(){
        ArrayList<Coach> coachs=new ArrayList<Coach>();
        try{
            String request="SELECT c.id, c.user_id, c.experience, c.etat , c.code, c.hire_date, u.username, u.email FROM coach c join fos_user u ON u.id = c.user_id";
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
            while(result.next()){
                Coach c=new Coach();
                c.setId(result.getInt("c.id"));
                c.setUserId(result.getInt("c.user_id"));
                c.setHireDate(result.getTimestamp(6).toLocalDateTime());
                c.setEtat(result.getString("c.etat"));
                c.setNom(result.getString("u.username"));
                c.setEmail(result.getString("u.email"));
                c.setCode(result.getString("c.code"));
               
                c.setExperienceYears(result.getInt("c.experience"));
                
                coachs.add(c);
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return coachs;
    }
     
     
       public ObservableList<String> getListeu(){  
            ObservableList<String> liste = FXCollections.observableArrayList();
            String requete = "SELECT username FROM fos_user";
            try {
                Statement st = cnx.createStatement();
                ResultSet rs = st.executeQuery(requete);
                while(rs.next()) {
                    String a;
                 
                    a=(rs.getString("username"));
                    

                    liste.add(a);
                }
            } catch (SQLException ex) {
                Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return liste;
    }

     
}
