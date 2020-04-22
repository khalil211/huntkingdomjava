/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.coach;
import entities.chien.Chien;
import entities.coach.Coach;
import entities.user.User;
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
    
    public void ajouter (Coach c) throws SQLException {
        int user=c.getUserId();
        
         
        java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
         try {
             String reqest="UPDATE user SET role='2' WHERE id = '"+user+"'";
             System.out.println(user);
         PreparedStatement p=cnx.prepareStatement(reqest);
              String req = "INSERT INTO `coach` (`id`, `user_id`, `experience`, `etat`, `hire_date`, `race`) VALUES (NULL, ?, ?, 'Disponible', ?, ?);";
            
            
            PreparedStatement pre=cnx.prepareStatement(req);
            
                    pre.setInt(1,c.getUserId());
                  
              
           
           
            
            
            pre.setInt(2,c.getExperienceYears());
            
            pre.setDate(3,date);
            pre.setString(4,c.getRace());
            pre.executeUpdate();
                         p.executeUpdate();

            System.out.println("Insertion Reussie!");

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    
    }
     public ObservableList<Coach> getAllCoachs(){
        ObservableList<Coach> coachs = FXCollections.observableArrayList();
        try{
            String request="SELECT c.id, c.user_id, c.experience, c.etat , c.race, c.hire_date, u.username, u.email FROM coach c join user u ON u.id = c.user_id";
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
                c.setRace(result.getString("c.race"));
               
                c.setExperienceYears(result.getInt("c.experience"));
                
                coachs.add(c);
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return coachs;
    }
      public void supprimerCoach(Coach c) {
        try {
            String request="DELETE FROM coach WHERE id = ?";
            PreparedStatement pre=cnx.prepareStatement(request);
            pre.setInt(1, c.getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
       public void ChangeStatus(Coach c) throws SQLException {
            Statement st=cnx.createStatement();
        
        String requet="SELECT * FROM coach where user_id = '" + c.getUserId() + "'" ;
         ResultSet resul=st.executeQuery(requet);
         int user=0;
         String etat="";
         while(resul.next()){
               user=resul.getInt("id");
               etat=resul.getString("etat");
            }
         if("Disponible".equals(etat))
         {
              try {
             String reqeust="UPDATE coach SET etat='Non Disponible' WHERE id = '"+user+"'";
             PreparedStatement pre=cnx.prepareStatement(reqeust);
             pre.executeUpdate();
             System.out.println("Status changed Successfully");

        } catch (SQLException ex) {
            System.out.println(ex);
             
         }
        }
         else
         {
              try {
             String reqeust="UPDATE coach SET etat='Disponible' WHERE id = '"+user+"'";
             PreparedStatement pre=cnx.prepareStatement(reqeust);
             pre.executeUpdate();
             System.out.println("Status changed Successfully");

        } catch (SQLException ex) {
            System.out.println(ex);
             
         }
         }
       }
     
    public ObservableList<Coach> getListeC(){  
            ObservableList<Coach> liste = FXCollections.observableArrayList();
            String request="SELECT c.id,c.etat,c.race, c.user_id,u.username FROM coach c join user u ON u.id = c.user_id";
            try {
                Statement st = cnx.createStatement();
                ResultSet rs = st.executeQuery(request);
                while(rs.next()) {
                    Coach c = new Coach();
                 
                   c.setNom(rs.getString("u.username"));
                   c.setId(rs.getInt("c.id"));
                   c.setEtat(rs.getString("c.etat"));
                     c.setRace(rs.getString("c.race"));
                    

                    liste.add(c);
                }
            } catch (SQLException ex) {
                Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return liste;
    }
     public Coach getC(int id){  
             Coach c = new Coach();

            String request="SELECT * FROM coach where user_id = '" + id + "'";
            try {
                Statement st = cnx.createStatement();
                ResultSet rs = st.executeQuery(request);
                while(rs.next()) {
                 
                   
                   c.setId(rs.getInt("id"));
                   c.setEtat(rs.getString("etat"));
                    

                  
                }
            } catch (SQLException ex) {
                Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return c;
    }
       public ObservableList<User> getListeu(){  
            ObservableList<User> liste = FXCollections.observableArrayList();
            String requete = "SELECT * FROM user where role='0'";
            try {
                Statement st = cnx.createStatement();
                ResultSet rs = st.executeQuery(requete);
                while(rs.next()) {
                    User a=new User();
                 
                    a.setUsername(rs.getString("username"));
                    a.setId(rs.getInt("id"));
                    

                    liste.add(a);
                }
            } catch (SQLException ex) {
                Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
            }
            return liste;
    }

     
}
