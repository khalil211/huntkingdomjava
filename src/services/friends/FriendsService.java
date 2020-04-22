/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.friends;

import entities.user.CurrentUser;
import entities.user.User;
import entities.friends.Friends;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.MyDB;

/**
 *
 * @author moez
 */
public class FriendsService 
{
  private Connection cnx;
  private Statement st;
  
      public FriendsService() {
        cnx=MyDB.getInstance().getConnection();
    }
 
    public void ajouterAmi() throws SQLException
     {
         System.out.println("ajouter ami");
         CurrentUser cu = CurrentUser.CurrentUser();
        String requete = "INSERT INTO friends (`firstuser`, `seconduser`, `etat`, `actionuser`) "
                + "VALUES ("+cu.id+","+cu.targetId+",0,"+cu.id+")";
        try{
             st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("invitation envoyé");
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String requete2 = "INSERT INTO friends (`firstuser`, `seconduser`, `etat`, `actionuser`) "
                + "VALUES ("+cu.targetId+","+cu.id+",0,"+cu.id+")";
        try{
             st = cnx.createStatement();
            st.executeUpdate(requete2);
            System.out.println("invitation envoyé");
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public boolean checkFriends () throws SQLException
     {
         System.out.println("here");
         CurrentUser cu = CurrentUser.CurrentUser(); 
         try{
            String request="SELECT id FROM friends WHERE firstuser="+cu.id+" and seconduser="+cu.targetId+" or seconduser="+cu.targetId+" and firstuser="+cu.id;         
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
          while(result.next())
           {
               
              return true;
           }
          
         }
        catch (SQLException ex)
        {
         System.out.println(ex);
        }
            return false;
    }
    
    public ArrayList<Friends> getAllInvitations()
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        ArrayList<Friends> invitations=new ArrayList<>();
        try{
            String request="SELECT seconduser from friends where etat=0 and firstuser="+cu.id+" and actionuser<>"+cu.id;
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
            while(result.next()){
                Friends f = new Friends();
                f.setSeconduser(result.getInt("seconduser"));
                f.setSecondusername(convertToString(result.getInt("seconduser")));
                invitations.add(f);
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return invitations;
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
    
    public void accept ()
     {
         CurrentUser cu = CurrentUser.CurrentUser();
             String requete="UPDATE friends SET etat=1 WHERE firstuser="+cu.id+" and seconduser="+cu.targetId+" or firstuser="+cu.targetId+" and seconduser="+cu.id;
         try{
             st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("Demande accepté");
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
    
    public void refuse ()
     {
         CurrentUser cu = CurrentUser.CurrentUser();
             String requete="UPDATE friends SET etat=2 WHERE firstuser="+cu.id+" and seconduser="+cu.targetId+" or firstuser="+cu.targetId+" and seconduser="+cu.id;
         try{
             st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("Demande refusé");
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
    
    public ArrayList<Friends> getMyFriends(int id)
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        ArrayList<Friends> friends =new ArrayList<>();
        try{
            String request="SELECT seconduser from friends where firstuser="+id;
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
            while(result.next()){
                Friends f = new Friends();
                
                f.setSeconduser(result.getInt("seconduser"));
                f.setSecondusername(convertToString(f.seconduser));
                
                friends.add(f);
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return friends;
    }
    
    public int nbrFriends(int id)
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        int friends=0;
        try{
            String request="SELECT seconduser from friends where firstuser="+id;
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
            while(result.next()){
                friends++;
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return friends;
    }
}
