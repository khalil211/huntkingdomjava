/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.chien;

import entities.chien.Chien;
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
            String req = "INSERT INTO `chien` (`id`, `user_id`, `nom`, `maladie`, `type_chasse`, `age`, `date_debut`, `etat`, `note`) VALUES (NULL, '1', ?, ?, ?, ?, '2000-01-01', 'en attente', NULL);";

            PreparedStatement pre=cnx.prepareStatement(req);
            
            pre.setString(1,c.getNom());
            pre.setString(2,c.getMaladie());
            pre.setString(3, c.getTypeChase());
            pre.setInt(4,c.getAge());
            pre.executeUpdate();
            
            System.out.println("Insertion Reussie!");

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    
    }
    
    public ArrayList<Chien> getAllChiens(){
        ArrayList<Chien> chiens=new ArrayList<Chien>();
        try{
            String request="SELECT c.id, c.user_id, c.nom, c.maladie , c.type_chasse, c.age, c.date_debut, c.etat, c.note, u.username FROM chien c join fos_user u ON u.id = c.user_id";
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
            while(result.next()){
                Chien c=new Chien();
                c.setId(result.getInt("c.id"));
                c.setUserId(result.getInt("c.user_id"));
                c.setDateDebut(result.getTimestamp(7).toLocalDateTime());
                c.setEtat(result.getString("c.etat"));
                c.setUsername(result.getString("u.username"));
                c.setNote(result.getInt("c.note"));
                c.setAge(result.getInt("c.age"));
                c.setNom(result.getString("c.nom"));
                c.setMaladie(result.getString("c.maladie"));
                c.setTypeChase(result.getString("c.type_chasse"));
                chiens.add(c);
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return chiens;
    }
    
     public void accepterChien(Chien c) {
       java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
        

        try {
            String request="UPDATE chien SET etat = 'accepte', date_debut = ? WHERE id = ?";
            PreparedStatement pre=cnx.prepareStatement(request);
            
            pre.setDate(1,date);
            pre.setInt(2, c.getId());
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
    
}
