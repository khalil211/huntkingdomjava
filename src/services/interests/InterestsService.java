/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.interests;

import entities.interests.Myinterests;
import entities.interests.Interests;
import entities.user.CurrentUser;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import utils.MyDB;

/**
 *
 * @author moez
 */
public class InterestsService 
{
 private Connection cnx;
 private Statement st;
    
    public InterestsService() 
    {
        cnx=MyDB.getInstance().getConnection();
    }   
    
    public ArrayList<Interests> getAllInterests()
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        ArrayList<Interests> interests=new ArrayList<>();
        try{
            String request="SELECT id, interest from interest";
            Statement s=cnx.createStatement();
            ResultSet result=s.executeQuery(request);
            while(result.next()){
                Interests i = new Interests();
                i.setId(result.getInt("id"));
                i.setInterest(result.getString("interest"));
                interests.add(i);
            }
        } catch (SQLException ex){
            System.out.println(ex);
        }
        return interests;
    }
}
