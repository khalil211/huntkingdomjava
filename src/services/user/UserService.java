/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.user;
import entities.user.User;
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
public class UserService 
{
 private Connection cnx;
 private Statement st;
    
    public UserService() {
        cnx=MyDB.getInstance().getConnection();
    }
    
    
    public void ajouterUser(User user) throws SQLException
     {
        String requete = "INSERT INTO fos_user (`username`,`username_canonical`, `email`,`email_canonical`,`enabled`, `password`,`roles`,`about`) "
                + "VALUES ('"+user.getUsername()+"','"+user.getUsername()+"','"+user.getEmail()+"','"+user.getEmail()+"', 1 ,'"+user.getPassword()+"','a:0:{}','"+user.getAbout()+"')";
        try{
             st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("user ajouté");
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    
    public boolean check (User user) throws SQLException
     {
         int d = 0;
         int r=0;
         CurrentUser cu = CurrentUser.CurrentUser(); 
         try{
            String request="SELECT id, password, username, role FROM fos_user WHERE username='"+user.getUsername()+"'";
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
            
          while(result.next())
          {
              d++;
              User x = new User();
                x.setId(result.getInt("id"));
                x.setPassword(result.getString("password"));
                x.setUsername(result.getString("username"));
                //if ((x.getUsername()==user.getUsername()) && (x.getPassword()==user.getPassword()))

                if (x.getUsername().equals(user.getUsername()))
                {
                    
                    if (user.getPassword().equals(result.getString("password")))
                    {
                        cu.id=x.getId();
                        cu.role=result.getInt("role");
                        //System.out.println(cu.role);
                     System.out.println(x.getUsername()+" est connecte");
                       return true;    
                    }
                    else
                    {
                        System.out.println("mot de passe incorrect");
                        cu.error=1;
                    }
    
                } 
            }
         }
        catch (SQLException ex)
        {
         System.out.println(ex);
        }
         if (d==0)
         {
           System.out.println("ce username n'existe pas !");
           cu.error=2;
         }
            return false;
        }
         
     public ArrayList<User> getAllUsers(){
        ArrayList<User> users=new ArrayList<>();
        try{
            String request="SELECT id, username, email, role, about from fos_user";
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
            while(result.next()){
                User u = new User();
                u.setId(result.getInt("id"));
                u.setUsername(result.getString("username"));
                u.setEmail(result.getString("email"));
                u.setAbout(result.getString("about"));
                if ( result.getInt("role")==0)
                u.setRole("Chasseur");
                else
                {
                u.setRole("administrateur");  
                }
                users.add(u);
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return users;
    }
     
     public User GetProfile(int id){
        CurrentUser cu = CurrentUser.CurrentUser();
            User u = new User();
            try{
            String request="SELECT id, username, email, role, about from fos_user where id="+id+"";
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
            while(result.next()){
                
                u.setId(result.getInt("id"));
                u.setUsername(result.getString("username"));
                u.setEmail(result.getString("email"));
                u.setAbout(result.getString("about"));
                if ( result.getInt("role")==0)
                u.setRole("Chasseur");
                else
                {
                u.setRole("administrateur");  
                }  
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return u;
    }
     
     public ArrayList<User> searchUsers(String search){
        ArrayList<User> users=new ArrayList<>();
        try{
            String request="SELECT id, username from fos_user where username like '%"+search+"%'";
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
            while(result.next()){
                User u = new User();
                u.setId(result.getInt("id"));
                u.setUsername(result.getString("username"));
                users.add(u);
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return users;
    }
     
     public void updateCode (String code, int id)
     {
         CurrentUser cu = CurrentUser.CurrentUser();
             String requete="UPDATE fos_user SET code='"+code+"' WHERE id="+id;
         try{
             st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("code");
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     
     public void updateAbout (String about)
     {
         CurrentUser cu = CurrentUser.CurrentUser();
             String requete="UPDATE fos_user SET about='"+about+"' WHERE id="+cu.id;
         try{
             st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("Description modifié");
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
     } 
     
     public void updatePassword (String password, int id)
     {
         CurrentUser cu = CurrentUser.CurrentUser();
             String requete="UPDATE fos_user SET password='"+password+"' WHERE id="+id;
         try{
             st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("mot de passe modifié");
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     
     public void updateRole ()
     {
         CurrentUser cu = CurrentUser.CurrentUser();
             String requete="UPDATE fos_user SET role="+1+" WHERE id="+cu.targetId;
         try{
             st = cnx.createStatement();
            st.executeUpdate(requete);
            System.out.println("Role modifié");
        } catch (SQLException ex) {
            Logger.getLogger(MyDB.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     
     public int userId () throws SQLException
     {
         int counter=0;
         CurrentUser cu = CurrentUser.CurrentUser(); 
         try{
            String request="SELECT id FROM fos_user order by id";         
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
     
     public String getAlphaNumericString(int n) 
    { 
  
        // chose a Character random from this String 
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz"; 
  
        // create StringBuffer size of AlphaNumericString 
        StringBuilder sb = new StringBuilder(n); 
  
        for (int i = 0; i < n; i++) { 
  
            // generate a random number between 
            // 0 to AlphaNumericString variable length 
            int index 
                = (int)(AlphaNumericString.length() 
                        * Math.random()); 
  
            // add Character one by one in end of sb 
            sb.append(AlphaNumericString 
                          .charAt(index)); 
        } 
  
        return sb.toString(); 
    }
     
     public boolean usernameExist (String username) throws SQLException
     {
         
         CurrentUser cu = CurrentUser.CurrentUser(); 
         try{
            String request="SELECT username FROM fos_user where username='"+username+"'";         
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
     
     public String getEmailbyUsername (String username) throws SQLException
     {
         
         CurrentUser cu = CurrentUser.CurrentUser(); 
         String str="";
         try{
            String request="SELECT email FROM fos_user where username='"+username+"'";         
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
          while(result.next())
           {
               str=result.getString("email");
           }
          
         }
        catch (SQLException ex)
        {
         System.out.println(ex);
        }
            return str;
    }
     
     public int geIdbyUsername (String username) throws SQLException
     {
         
         CurrentUser cu = CurrentUser.CurrentUser(); 
         int id=0;
         try{
            String request="SELECT id FROM fos_user where username='"+username+"'";         
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
          while(result.next())
           {
               id=result.getInt("id");
           }
          
         }
        catch (SQLException ex)
        {
         System.out.println(ex);
        }
            return id;
    }
}
