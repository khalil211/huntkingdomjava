/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.user;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author moez
 */
public class User {
    
    private int id;
    private String username;
    private String email;
    private String password;
    private String about;
    private int role=0;
   
    public User()
    {
        
    }
    
     public User(String username, String password) 
    {
        this.username = username;
        this.password = password;
    }
     
    public User(String username, String email, String password) 
    {
        this.username = username;
        this.email = email;
        this.password = password;
        this.about = "";
    }
    public int getId() 
    {
        return id;
    }

    public void setId(int id) 
    {
        this.id = id;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public void setEmail(String Email)
    {
        this.email = email;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String Password)
    {
        this.password = password;
    }
    
    public String getAbout()
    {
        return about;
    }
    
    public void setAbout(String about)
    {
        this.about = about;
    }
    
    public int getRole() 
    {
        return role;
    }

    public void setRole(int role) 
    {
        this.role = role;
    }
    
    @Override
    public String toString() 
    {
        return "User{" + "id= " + id + ", username= " + username + ", email=" + email + ", about=" + about + '}';
    }
}
