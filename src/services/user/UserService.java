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
        String requete = "INSERT INTO user (`username`, `email`, `password`, `about`) "
                + "VALUES ('"+user.getUsername()+"','"+user.getEmail()+"','"+user.getPassword()+"','"+user.getAbout()+"')";
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
            String request="SELECT id, password, username, role FROM user WHERE username='"+user.getUsername()+"'";
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
                     System.out.println(x.getUsername()+" est connecte");
                       return true;    
                    }
                    else
                    {
                        System.out.println("mot de passe incorrect");
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
         }
            return false;
        }
         
}
