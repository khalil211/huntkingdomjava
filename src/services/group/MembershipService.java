/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.group;

import entities.group.Membership;
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
public class MembershipService 
{
    private Connection cnx;
 private Statement st;
    
    public MembershipService() 
    {
        cnx=MyDB.getInstance().getConnection();
    }
    
    public void ajouterMembre() throws SQLException
     {
         CurrentUser cu = CurrentUser.CurrentUser();
        String requete = "INSERT INTO membership (`user_id`, `group_id`, `role`) "
                + "VALUES ("+cu.id+","+cu.targetGroupId+",0)";
        try{
             st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("membre ajouté envoyé");
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ajouterAdmin() throws SQLException
     {
         CurrentUser cu = CurrentUser.CurrentUser();
        String requete = "INSERT INTO membership (`user_id`, `group_id`, `role`) "
                + "VALUES ("+cu.id+","+cu.targetGroupId+",1)";
        try{
             st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("membre ajouté envoyé");
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void quitterGroupe() throws SQLException
     {
         CurrentUser cu = CurrentUser.CurrentUser();
        String requete = "DELETE FROM membership WHERE user_id="+cu.id+" and group_id="+cu.targetGroupId;
        try{
             st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("vous avez quitté le groupe");
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<Membership> getMyGroups()
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        ArrayList<Membership> groups =new ArrayList<>();
        try{
            String request="SELECT group_id, role from membership where user_id="+cu.id;
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
            while(result.next()){
                Membership m = new Membership();
                m.setRole(result.getInt("role"));
                if (m.role==1)
                {
                    m.setStringRole("Administrateur");
                }
                else
                {
                    m.setStringRole("Member");
                }
                m.setGroupId(result.getInt("group_id"));
                m.setStringGroup(convertGroupToString(m.groupId));
                
                groups.add(m);
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return groups;
    }
    
    public ArrayList<Membership> getGroupMembers()
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        ArrayList<Membership> groups =new ArrayList<>();
        System.out.println(cu.targetGroupId);
        try{
            String request="SELECT user_id, role from membership where group_id = "+cu.targetGroupId+" order by id";
            //String request="SELECT user_id, role from membership ";
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
            while(result.next()){
                Membership m = new Membership();
                m.setRole(result.getInt("role"));
                if (m.role==1)
                {
                    m.setStringRole("Administrateur");
                }
                else
                {
                    m.setStringRole("Member");
                }
                
                m.setUserId(result.getInt("user_id"));
                m.setStringUsername(convertUserToString(result.getInt("user_id")));
                
                
                groups.add(m);
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return groups;
    }
    
    public int numberGroupMembers()
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        ArrayList<Membership> groups =new ArrayList<>();
        int counter=0;
        try{
            String request="SELECT user_id, role from membership where group_id="+cu.targetGroupId;
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
            while(result.next())
            {
              counter++;  
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return counter;
    }
    
    public String convertGroupToString(int groupid)
    {
        String str="";
        
        try{
            String request="SELECT name from groupe where id="+groupid;
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
            while(result.next()){
             str=result.getString("name");
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return str;
    }
    
    public String convertDescriptionToString(int groupid)
    {
        String str="";
        
        try{
            String request="SELECT description from groupe where id="+groupid;
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
            while(result.next()){
             str=result.getString("description");
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return str;
    }
    
    public String convertUserToString(int userid)
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
    public boolean isAMember (int member, int group) 
    {
        try{
            String request="SELECT id from membership where user_id="+member+" and group_id="+group;
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
}
