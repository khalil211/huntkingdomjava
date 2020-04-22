/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.publication;

import entities.friends.Friends;
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
         CurrentUser cu = CurrentUser.CurrentUser();
        String requete = "INSERT INTO publication (`user_id`,`titre`, `image`, `datepublication`, `text`,`group_id`) VALUES ('"+publication.getIdUser()+"','','','2020-02-17 08:01:00','"+publication.getMypublication()+"','0')";
        try{
             st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("publication ajouté");
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public void ajouterGroupPublication(Publication publication) throws SQLException
     {
         CurrentUser cu = CurrentUser.CurrentUser();
        String requete = "INSERT INTO publication (`user_id`,`titre`, `image`, `datepublication`, `text`,`group_id`) VALUES ('"+publication.getIdUser()+"','','','2020-02-17 08:01:00','"+publication.getMypublication()+"','"+cu.targetGroupId+"')";
        try{
             st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("publication ajouté");
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public ArrayList<Publication> getAllMyPublications(int id)
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        ArrayList<Publication> publications=new ArrayList<>();
        try{
            String request="SELECT id, text from publication where user_id="+id;
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
            while(result.next()){
                Publication p = new Publication();
                p.setMypublication(result.getString("text"));
                p.setId(result.getInt("id"));
                publications.add(p);
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return publications;
    }
    
    public ArrayList<Publication> getGroupPublications()
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        ArrayList<Publication> publications=new ArrayList<>();
        try{
            String request="SELECT user_id, text from publication where group_id="+cu.targetGroupId;
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
            while(result.next()){
                Publication p = new Publication();
                p.setMypublication(result.getString("text"));
                p.setIdUser(result.getInt("user_id"));
                p.setUsernamep(convertToString(result.getInt("user_id")));
                publications.add(p);
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return publications;
    }
    
    public ArrayList<Publication> getAllPublications()
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        ArrayList<Publication> publications=new ArrayList<>();
        try{
            String request="SELECT id, user_id, text from publication order by id desc";
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
            while(result.next()){
                Publication p = new Publication();
                p.setMypublication(result.getString("text"));
                p.setIdUser(result.getInt("user_id"));
                p.setUsernamep(convertToString(result.getInt("user_id")));
                p.setId(result.getInt("id"));
                publications.add(p);
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return publications;
    }
    
    public String convertToString(int userid)
    {
        String str="";
        
        try{
            String request="SELECT username from fos_user where id="+userid;
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
            while(result.next()){
             str=result.getString("username");
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return str;
    }
    
    public String publicationToString(int pubid)
    {
        String str="";
        
        try{
            String request="SELECT text from publication where id="+pubid;
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
            while(result.next()){
             str=result.getString("text");
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return str;
    }
}
