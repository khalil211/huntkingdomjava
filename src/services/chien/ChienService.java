/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.chien;

import entities.chien.Chien;
import entities.user.CurrentUser;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MyDB;



/**
 *
 * @author Louay
 */
public class ChienService {
     private Connection cnx;
   
    
    public ChienService(){
        cnx=MyDB.getInstance().getConnection();
    }
     public void ajouter (Chien c) {
         try {
             CurrentUser cu = CurrentUser.CurrentUser();
             int iduser=cu.id;
            String req = "INSERT INTO `chien` (`id`, `user_id`, `nom`, `maladie`, `type_chasse`, `age`, `date_debut`, `etat`, `note`) VALUES (NULL, ?, ?, ?, ?, ?, '2000-01-01', 'en attente', NULL);";

            PreparedStatement pre=cnx.prepareStatement(req);
            pre.setInt(1,iduser);
            pre.setString(2,c.getNom());
            pre.setString(3,c.getMaladie());
            pre.setString(4, c.getTypeChase());
            pre.setInt(5,c.getAge());
            pre.executeUpdate();
            
            System.out.println("Insertion Reussie!");

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    
    }
     public  String convertIdToUser(int id) throws SQLException
    {
        String nom="la";
       
        
         Statement s=cnx.createStatement();
        
        String requet="SELECT username FROM user where id = '" + id + "'" ;
         ResultSet resul=s.executeQuery(requet);
         while(resul.next()){
                nom=resul.getString("username");
            }
        return nom;
    }
     public  String convertIdToCoach(int id) throws SQLException
    {
        String nom="la";
        int user=0;
         String request="SELECT user_id FROM coach where id = '" + id + "'" ;
         Statement s=cnx.createStatement();
         ResultSet result=s.executeQuery(request);
        
        while(result.next()){
                user=result.getInt("user_id");
            }
        String requet="SELECT username FROM user where id = '" + user + "'" ;
         ResultSet resul=s.executeQuery(requet);
         while(resul.next()){
                nom=resul.getString("username");
            }
        return nom;
    }
    
    
    public ArrayList<Chien> getAllChiens(){
        ArrayList<Chien> chiens=new ArrayList<Chien>();
         String nomCoach="la";
        try{
            String request="SELECT c.id, c.user_id, c.coach_id, c.nom, c.maladie, c.type_chasse, c.age, c.date_debut, c.etat, c.note, u.username FROM chien c join user u ON u.id = c.user_id";
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
           
           
            while(result.next()){
                Chien c=new Chien();
                c.setId(result.getInt("c.id"));
                c.setUserId(result.getInt("c.user_id"));
                c.setDateDebut(result.getTimestamp(8).toLocalDateTime());
                c.setEtat(result.getString("c.etat"));
                c.setUsername(result.getString("u.username"));
                c.setNote(result.getInt("c.note"));
                c.setAge(result.getInt("c.age"));
                c.setNom(result.getString("c.nom"));
                c.setMaladie(result.getString("c.maladie"));
                c.setTypeChase(result.getString("c.type_chasse"));
                c.setCoachId(result.getInt("c.coach_id"));
                int test=result.getInt("c.coach_id");
                if(test!=0)
                {
                    nomCoach=convertIdToCoach(result.getInt("c.coach_id"));
                c.setNomCoach(nomCoach);
                }
                else {
                    c.setNomCoach("Not yet");
                }
                
                
                chiens.add(c);
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return chiens;
    }
    
     public void accepterChien(Chien c) throws SQLException {
       java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
       
      

        try {
         
         String request="UPDATE chien SET etat = 'accepte',coach_id= ?, date_debut = ? WHERE id = ?";
         
            PreparedStatement pre=cnx.prepareStatement(request);
        
                  pre.setInt(1,c.getCoachId());
            pre.setDate(2,date);
            
            pre.setInt(3, c.getId());
            
         
         
            
            
          
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
      public void refuserChien(Chien c) {
       java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        

        try {
            String request="UPDATE chien SET etat = 'refuse', date_debut = ? WHERE id = ?";
            PreparedStatement pre=cnx.prepareStatement(request);
            
            pre.setDate(1,date);
            pre.setInt(2, c.getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
     public void supprimerChien(Chien c) {
        try {
            String request="DELETE FROM chien WHERE id = ?";
            PreparedStatement pre=cnx.prepareStatement(request);
            pre.setInt(1, c.getId());
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
     public void update (Chien c,int note){
         try{
           String request="UPDATE chien SET note = ? WHERE id = ?";
           PreparedStatement pre=cnx.prepareStatement(request);
            
            pre.setInt(1,note);
            pre.setInt(2, c.getId());
            pre.executeUpdate();
            System.out.println("Chien modifiée");
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      public ArrayList<Chien> getAllChiensOfCoach() throws SQLException{
        ArrayList<Chien> chiens=new ArrayList<Chien>();
        int user=0;
        String nomUser;
          CurrentUser cu = CurrentUser.CurrentUser();
             int iduser=cu.id;
             int userId=0;
             Statement st=cnx.createStatement();
        
        String requet="SELECT id FROM coach where user_id = '" + iduser + "'" ;
         ResultSet resul=st.executeQuery(requet);
         
         while(resul.next()){
                user=resul.getInt("id");
            }
        try{
            String request="SELECT * from chien where coach_id= '"+user+"'";
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
           
           
            while(result.next()){
                Chien c=new Chien();
                c.setId(result.getInt("id"));
                userId=result.getInt("user_id");
                c.setUserId(result.getInt("user_id"));
                c.setDateDebut(result.getTimestamp(8).toLocalDateTime());
                c.setEtat(result.getString("etat"));
                nomUser=convertIdToUser(userId);
                c.setUsername(nomUser);
                c.setNote(result.getInt("note"));
                c.setAge(result.getInt("age"));
                c.setNom(result.getString("nom"));
                c.setMaladie(result.getString("maladie"));
                c.setTypeChase(result.getString("type_chasse"));
                c.setCoachId(result.getInt("coach_id"));
               
                
                
                chiens.add(c);
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return chiens;
    }
    
}
