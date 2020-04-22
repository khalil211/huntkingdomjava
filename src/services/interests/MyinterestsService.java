/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.interests;

import entities.interests.Myinterests;
import entities.publication.Publication;
import entities.user.CurrentUser;
import entities.user.User;
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
public class MyinterestsService 
{
    private Connection cnx;
    private Statement st;
    
    public MyinterestsService() 
    {
        cnx=MyDB.getInstance().getConnection();
    }
    
    public void ajouterMyinterests(Myinterests inter) throws SQLException
     {
         CurrentUser cu = CurrentUser.CurrentUser();
        String requete = "INSERT INTO myinterests (`user_id`,`interest`) VALUES ('"+cu.id+"','"+inter.getMyinterest()+"')";
        try{
             st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("ajouté");
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public ArrayList<Myinterests> getAllMyInterests(int id)
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        ArrayList<Myinterests> myinterests=new ArrayList<>();
        try{
            String request="SELECT id, user_id, interest from myinterests where user_id="+id;
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
            while(result.next()){
                Myinterests mi = new Myinterests();
                mi.setId(result.getInt("id"));
                mi.setUserId(result.getInt("user_id"));
                mi.setMyinterest(result.getString("interest"));
                myinterests.add(mi);
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return myinterests;
    }
    
    public boolean interestExist(int id, String interest) throws Exception
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        
        try{
            String request="SELECT id from myinterests where user_id="+id+" and interest='"+interest+"'";
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
            while(result.next()){
               return true;
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return false;
    }
    
    public void unlike(int id, String string) throws SQLException
     {
         CurrentUser cu = CurrentUser.CurrentUser();
        String requete = "DELETE FROM myinterests WHERE user_id="+id+" and interest='"+string+"'";
        try{
             st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("unlike");
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int nbrInterests(int id)
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        int friends=0;
        try{
            String request="SELECT id from myinterests where user_id="+id;
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
    
    public ArrayList<Myinterests> searchUsers(String search){
        ArrayList<Myinterests> users=new ArrayList<>();
        try{
            String request="SELECT user_id from myinterests where interest='"+search+"'";
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
            while(result.next()){
                Myinterests m = new Myinterests();
                m.setUserId(result.getInt("user_id"));
                m.setUserString(convertToString(m.getUserId()));
                users.add(m);
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return users;
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
}
