/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.group;

import entities.user.User;
import entities.group.Group;
import entities.user.CurrentUser;
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
public class GroupService 
{
 private Connection cnx;
 private Statement st;
    
    public GroupService() 
    {
        cnx=MyDB.getInstance().getConnection();
    }
       
    
    public void ajouterGroup(Group group) throws SQLException
     {
        String requete = "INSERT INTO groupe (`name`,`description`) "
                + "VALUES ('"+group.getName()+"','"+group.getDescription()+"')";
        try{
             st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("groupe ajouté");
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    
    
   public int groupId () throws SQLException
     {
         int counter=0;
         CurrentUser cu = CurrentUser.CurrentUser(); 
         try{
            String request="SELECT id FROM groupe";         
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
          while(result.next())
           {
               counter=result.getInt("id");
           }
          
         }
        catch (SQLException ex)
        {
         System.out.println(ex);
        }
            return counter;
    }
   
   public ArrayList<Group> searchGroups(String search){
        ArrayList<Group> groups=new ArrayList<>();
        try{
            String request="SELECT id, name from groupe where name like '%"+search+"%'";
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
            while(result.next()){
                Group g = new Group();
                g.setId(result.getInt("id"));
                g.setName(result.getString("name"));
                groups.add(g);
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return groups;
    }
}
